package com.algonix.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "submit_code")
data class SubmitCode(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long = 0,

	@Column(nullable = false)
	val language: Long, // 제출 언어 코드

	@Column(nullable = false)
	val problem: Long, // 문제 아이디

	@Column(nullable = false)
	val authorId: Long, // 제출자 아이디

	@Column(nullable = false, columnDefinition = "TEXT")
	val code: String, // 제출 코드

	@Column(nullable = false)
	val status: SubmitStatus, // 제출 상태

	@Column(nullable = false)
	@CreationTimestamp
	val submitAt: LocalDateTime = LocalDateTime.now()
)
