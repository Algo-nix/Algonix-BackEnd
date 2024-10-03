package com.algonix.controller

import com.algonix.dto.CategoryDto
import com.algonix.service.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.algonix.util.Result

@RestController
@RequestMapping("/v1/categories")
class CategoryController(
    private val categoryService: CategoryService
) {

    @PostMapping
    fun createCategory(@RequestBody categoryDto: CategoryDto): ResponseEntity<Map<String, Any>> {
        return try {
            val category = categoryService.createCategory(categoryDto)
            Result.created(category)
        } catch (e: IllegalArgumentException) {
            Result.conflict(e.message ?: "잘못된 요청입니다.")
        } catch (e: Exception) {
            Result.internalServerError("카테고리 생성 중 오류가 발생했습니다.")
        }
    }

    @GetMapping("/{id}")
    fun getCategoryById(@PathVariable id: Long): ResponseEntity<Map<String, Any>> {
        return categoryService.getCategoryById(id)
    }

    @GetMapping
    fun getAllCategories(): ResponseEntity<Map<String, Any>> {
        return categoryService.getAllCategories()
    }
}
