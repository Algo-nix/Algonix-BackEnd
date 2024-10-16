package com.algonix.controller

import com.algonix.dto.SubmitCode.SubmitCodeRequestDto
import com.algonix.security.UserDetailsImpl
import com.algonix.service.SubmitCodeService
import com.algonix.util.Result
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/submit codes")
class SubmitCodeController(
    private val submitCodeService: SubmitCodeService
) {
    @PostMapping
    fun submitCode(
        @RequestBody submitCodeRequestDto: SubmitCodeRequestDto,
        authentication: Authentication
    ): ResponseEntity<Map<String, Any>> {
        val authorId = (authentication.principal as UserDetailsImpl).id
        return Result.ok(submitCodeService.createSubmitCode(submitCodeRequestDto, authorId))
    }
}