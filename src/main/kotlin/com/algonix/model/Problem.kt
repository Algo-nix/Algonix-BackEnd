package com.algonix.model

import jakarta.persistence.*

@Entity
@Table(name = "problems")
data class Problem(
    // ALTER TABLE problems AUTO_INCREMENT = 1000; 를 사용하여 1000번부터 문제가 시작하도록 해야 함
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,  // 문제 번호 (1,000부터 시작)

    val title: String,  // 문제 제목

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    val category: Category,  // 문제 분류

    val restrictions: String,  // 문제 제한 사항

    val timeLimit: Int,  // 시간 제한 (초)
    val memoryLimit: Int,  // 메모리 제한 (MB)

    val submissions: Int = 0,  // 제출 수
    val correctSubmissions: Int = 0,  // 맞힌 수

    val description: String,  // 문제 설명
    val inputDescription: String,  // 입력 설명
    val outputDescription: String,  // 출력 설명

    val hints: String? = null,  // 도움말 (선택 사항)

    val difficulty: Int,  // 문제 난이도 (0~5)

    @ManyToMany
    @JoinTable(
        name = "problem_similar",
        joinColumns = [JoinColumn(name = "problem_id")],
        inverseJoinColumns = [JoinColumn(name = "similar_problem_id")]
    )
    val similarProblems: Set<Problem> = setOf(),  // 비슷한 문제들

    @Column(name = "author_id")
    val authorId: Long,  // 문제 제작자 아이디

    @OneToMany(mappedBy = "problem", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    val examples: MutableList<Example> = mutableListOf()  // 입력/출력 예제들
)
