package com.algonix.util

import org.json.JSONObject
import java.io.File
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class Docker {

    private val dockerHost = "http://localhost:2375"  // Docker Daemon의 호스트 URL

    /**
     * Docker 컨테이너를 실행하고 입력 값을 전달하여 출력 결과를 반환하는 함수
     *
     * @param imageName Docker 이미지 이름
     * @param input 컨테이너에 전달할 입력 값
     * @param code Python 코드 내용
     * @param codeNumber Docker 경로 내 코드 번호
     * @return Docker 컨테이너의 출력 결과
     *
     * TODO: 테스트 진행을 위해 Python만 실행되도록 HardCoding 되어 있어 이를 수정해야 함
     */
    fun runContainerWithInput(imageName: String, input: String, code: String, codeNumber: Long): String {
        val dockerDir = "D:/$codeNumber" // 새로운 경로 구조 반영

        // Python 소스코드 파일 저장
        val sourceFile = File("$dockerDir/main.py")
        sourceFile.writeText(code)

        // Docker 이미지 빌드
        try {
            buildDockerImage(dockerDir, imageName)
        } catch (e: Exception) {
            return "Docker image build failed: ${e.message}"
        }

        // 컨테이너 생성
        val url = URL("$dockerHost/containers/create")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.setRequestProperty("Content-Type", "application/json")

        val sanitizedInput = input.replace("\n", "\\n").replace("\"", "\\\"").replace("$", "\\$")
        val payload = """
    {
        "Image": "$imageName",
        "Cmd": ["/bin/sh", "-c", "printf \"$sanitizedInput\" | python3 ./main.py"],
        "AttachStdout": true,
        "AttachStderr": true,
        "Tty": true,
        "HostConfig": {
            "AutoRemove": false
        }
    }
""".trimIndent()


        val writer = OutputStreamWriter(connection.outputStream)
        writer.write(payload)
        writer.flush()
        writer.close()

        if (connection.responseCode != HttpURLConnection.HTTP_CREATED) {
            return "Failed to create Docker container: ${connection.responseMessage}"
        }

        val containerId = connection.inputStream.bufferedReader().readText().let { extractContainerId(it) }

        // 컨테이너 시작 및 대기
        startContainerAndWaitForCompletion(containerId)

        // 종료 후 로그 가져오기
        val logs = fetchLogs(containerId)
        return if (logs.isNotBlank()) "Docker 실행 결과: $logs" else "Docker 실행 결과: (빈값)"
    }

    private fun buildDockerImage(dockerDir: String, imageName: String) {
        val buildCommand = listOf("docker", "build", "--no-cache", "-t", imageName, dockerDir)
        val buildProcess = ProcessBuilder(buildCommand)
            .directory(File(dockerDir))
            .redirectErrorStream(true)
            .start()

        val buildOutput = StringBuilder()
        buildProcess.inputStream.bufferedReader().forEachLine { line ->
            buildOutput.append(line).append("\n")
        }

        if (buildProcess.waitFor() != 0) {
            throw RuntimeException("Failed to build Docker image for $imageName. Error: $buildOutput")
        }
    }

    private fun extractContainerId(response: String): String {
        val json = JSONObject(response)
        return json.getString("Id")
    }

    private fun startContainerAndWaitForCompletion(containerId: String) {
        val startUrl = URL("$dockerHost/containers/$containerId/start")
        val startConnection = startUrl.openConnection() as HttpURLConnection
        startConnection.requestMethod = "POST"
        startConnection.doOutput = true
        startConnection.connect()

        if (startConnection.responseCode != HttpURLConnection.HTTP_NO_CONTENT) {
            throw RuntimeException("Failed to start Docker container: ${startConnection.responseMessage}")
        }

        // 컨테이너가 종료될 때까지 기다림
        val waitUrl = URL("$dockerHost/containers/$containerId/wait")
        val waitConnection = waitUrl.openConnection() as HttpURLConnection
        waitConnection.requestMethod = "POST"
        waitConnection.inputStream.bufferedReader().use { it.readText() }
    }

    private fun fetchLogs(containerId: String): String {
        val logUrl = URL("$dockerHost/containers/$containerId/logs?stdout=true&stderr=true")
        val logConnection = logUrl.openConnection() as HttpURLConnection
        logConnection.requestMethod = "GET"

        val logs = StringBuilder()
        logConnection.inputStream.bufferedReader().forEachLine { line ->
            // 바이너리 로그 헤더 제거, 예: ASCII 코드만 남김
            val filteredLine = line.filter { it.isLetterOrDigit() || it.isWhitespace() || it in "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~" }
            logs.append(filteredLine).append("\n")
        }
        return logs.toString().trim()
    }
}
