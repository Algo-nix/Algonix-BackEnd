package com.algonix.repository

import com.algonix.model.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long> {
    fun existsByName(name: String): Boolean
}
