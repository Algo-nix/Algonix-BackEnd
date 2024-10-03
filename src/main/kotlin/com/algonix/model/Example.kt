package com.algonix.model

import jakarta.persistence.*

@Entity
@Table(name = "examples")
data class Example(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    val problem: Problem,  // 문제와의 연관 관계

    @Column(nullable = false)
    val inputExample: String,  // 입력 예제

    @Column(nullable = false)
    val outputExample: String  // 출력 예제
)
