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

    // 여러 카테고리와 연결 가능하도록 ManyToMany 설정
    @ManyToMany
    @JoinTable(
        name = "problem_categories",
        joinColumns = [JoinColumn(name = "problem_id")],
        inverseJoinColumns = [JoinColumn(name = "category_id")]
    )
    val categories: Set<Category> = setOf(),  // 문제 분류들

    val restrictions: String,  // 문제 제한 사항
    val timeLimit: Int,  // 시간 제한 (초)
    val memoryLimit: Int,  // 메모리 제한 (MB)
    val description: String,  // 문제 설명
    val inputDescription: String,  // 입력 설명
    val outputDescription: String,  // 출력 설명
    val hints: String? = null,  // 도움말 (선택 사항)
    val difficulty: Int,  // 문제 난이도 (0~5)

    // 비슷한 문제들과 연결 가능하도록 설정
    @ManyToMany
    @JoinTable(
        name = "problem_similar",
        joinColumns = [JoinColumn(name = "problem_id")],
        inverseJoinColumns = [JoinColumn(name = "similar_problem_id")]
    )
    val similarProblems: Set<Problem> = setOf(),  // 비슷한 문제들

    @Column(name = "author_id")
    val authorId: Long,  // 문제 제작자 아이디

    // 사용 가능한 언어와의 Many-to-Many 관계 설정
    @ManyToMany
    @JoinTable(
        name = "problem_languages",
        joinColumns = [JoinColumn(name = "problem_id")],
        inverseJoinColumns = [JoinColumn(name = "language_id")]
    )
    val availableLanguages: Set<AvailableLanguage> = setOf(),  // 사용 가능한 언어들

    @OneToMany(mappedBy = "problem", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    val examples: MutableList<Example> = mutableListOf()  // 입력/출력 예제들
)
