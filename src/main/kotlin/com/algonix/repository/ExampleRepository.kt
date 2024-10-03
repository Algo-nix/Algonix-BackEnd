package com.algonix.repository

import com.algonix.model.Example
import org.springframework.data.jpa.repository.JpaRepository

interface ExampleRepository : JpaRepository<Example, Long> {
    fun findByProblemId(problemId: Long): List<Example>
}