package com.algonix.service

import com.algonix.dto.CategoryDto
import com.algonix.model.Category
import com.algonix.repository.CategoryRepository
import com.algonix.util.Result
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {

    @Transactional
    fun createCategory(categoryDto: CategoryDto): Category {
        val name = categoryDto.name

        // 중복된 이름 체크
        if (categoryRepository.existsByName(name)) {
            throw IllegalArgumentException("이미 존재하는 카테고리 이름입니다.")
        }

        val category = Category(name = name)
        return categoryRepository.save(category)
    }

    fun getCategoryById(id: Long): ResponseEntity<Map<String, Any>> {
        return try {
            val category = categoryRepository.findById(id)
            if (category.isPresent) {
                Result.ok(category.get())
            } else {
                Result.notFound("해당 카테고리를 찾을 수 없습니다.")
            }
        } catch (e: Exception) {
            Result.internalServerError("카테고리 조회 중 오류가 발생했습니다.")
        }
    }

    fun getAllCategories(): ResponseEntity<Map<String, Any>> {
        return try {
            val categories = categoryRepository.findAll()
            Result.ok(categories)
        } catch (e: Exception) {
            Result.internalServerError("카테고리 목록 조회 중 오류가 발생했습니다.")
        }
    }
}
