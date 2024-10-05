package com.algonix.model

import jakarta.persistence.*

@Entity
@Table(name = "available_languages")
data class AvailableLanguage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    val name: String,  // 언어 이름

    @Column(nullable = false)
    val compileCommand: String,  // 컴파일 명령

    @Column(nullable = false)
    val executeCommand: String,  // 실행 명령

    @Column(nullable = false)
    val version: String,  // 컴파일러 버전

    @Lob
    @Column(nullable = false)
    val exampleCode: String  // 예제 코드
)
