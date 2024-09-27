package com.algonix.util

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class Result {
    companion object {
        /**
         * 요청이 성공적으로 처리되었음을 나타냅니다.
         * @param message 응답 메시지 또는 데이터
         * @return 200 OK 응답
         */
        fun ok(message: Any): ResponseEntity<Map<String, Any>> {
            return ResponseEntity.status(HttpStatus.OK).body(mapOf("status" to 200, "data" to message))
        }

        /**
         * 요청이 성공적으로 처리되어 리소스가 생성되었음을 나타냅니다.
         * @param message 응답 메시지 또는 데이터
         * @return 201 Created 응답
         */
        fun created(message: Any): ResponseEntity<Map<String, Any>> {
            return ResponseEntity.status(HttpStatus.CREATED).body(mapOf("status" to 201, "data" to message))
        }

        /**
         * 요청이 성공적으로 접수되었지만, 아직 처리되지 않았음을 나타냅니다.
         * @param message 응답 메시지 또는 데이터
         * @return 202 Accepted 응답
         */
        fun accepted(message: Any): ResponseEntity<Map<String, Any>> {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(mapOf("status" to 202, "data" to message))
        }

        /**
         * 요청이 성공적으로 처리되었지만, 반환할 콘텐츠가 없음을 나타냅니다.
         * @return 204 No Content 응답
         */
        fun noContent(): ResponseEntity<Map<String, Any>> {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        }

        /**
         * 클라이언트의 요청이 잘못되었음을 나타냅니다.
         * @param message 응답 메시지 또는 데이터
         * @return 400 Bad Request 응답
         */
        fun badRequest(message: Any): ResponseEntity<Map<String, Any>> {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("status" to 400, "data" to message))
        }

        /**
         * 클라이언트가 인증되지 않았음을 나타냅니다.
         * @param message 응답 메시지 또는 데이터
         * @return 401 Unauthorized 응답
         */
        fun unauthorized(message: Any): ResponseEntity<Map<String, Any>> {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("status" to 401, "data" to message))
        }

        /**
         * 클라이언트가 접근 권한이 없음을 나타냅니다.
         * @param message 응답 메시지 또는 데이터
         * @return 403 Forbidden 응답
         */
        fun forbidden(message: Any): ResponseEntity<Map<String, Any>> {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(mapOf("status" to 403, "data" to message))
        }

        /**
         * 요청한 리소스를 찾을 수 없음을 나타냅니다.
         * @param message 응답 메시지 또는 데이터
         * @return 404 Not Found 응답
         */
        fun notFound(message: Any): ResponseEntity<Map<String, Any>> {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("status" to 404, "data" to message))
        }

        /**
         * 클라이언트의 요청이 서버의 상태와 충돌이 발생했음을 나타냅니다.
         * @param message 응답 메시지 또는 데이터
         * @return 409 Conflict 응답
         */
        fun conflict(message: Any): ResponseEntity<Map<String, Any>> {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(mapOf("status" to 409, "data" to message))
        }

        /**
         * 서버 내부에서 오류가 발생했음을 나타냅니다.
         * @param message 응답 메시지 또는 데이터
         * @return 500 Internal Server Error 응답
         */
        fun internalServerError(message: Any): ResponseEntity<Map<String, Any>> {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("status" to 500, "data" to message))
        }

        /**
         * 서버에서 요청을 처리할 기능이 구현되지 않았음을 나타냅니다.
         * @param message 응답 메시지 또는 데이터
         * @return 501 Not Implemented 응답
         */
        fun notImplemented(message: Any): ResponseEntity<Map<String, Any>> {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(mapOf("status" to 501, "data" to message))
        }

        /**
         * 서버가 잘못된 게이트웨이로 응답했음을 나타냅니다.
         * @param message 응답 메시지 또는 데이터
         * @return 502 Bad Gateway 응답
         */
        fun badGateway(message: Any): ResponseEntity<Map<String, Any>> {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(mapOf("status" to 502, "data" to message))
        }

        /**
         * 서버가 일시적으로 사용할 수 없음을 나타냅니다.
         * @param message 응답 메시지 또는 데이터
         * @return 503 Service Unavailable 응답
         */
        fun serviceUnavailable(message: Any): ResponseEntity<Map<String, Any>> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(mapOf("status" to 503, "data" to message))
        }

        /**
         * 서버가 게이트웨이 시간 초과로 응답했음을 나타냅니다.
         * @param message 응답 메시지 또는 데이터
         * @return 504 Gateway Timeout 응답
         */
        fun gatewayTimeout(message: Any): ResponseEntity<Map<String, Any>> {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(mapOf("status" to 504, "data" to message))
        }

        /**
         * 요청이 너무 많음을 나타냅니다.
         * @param message 응답 메시지 또는 데이터
         * @return 429 Too Many Requests 응답
         */
        fun tooManyRequests(message: String): ResponseEntity<Map<String, Any>> {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body(mapOf("status" to 429, "message" to message))
        }
    }
}