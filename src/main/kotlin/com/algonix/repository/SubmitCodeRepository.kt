package com.algonix.repository

import com.algonix.model.SubmitCode
import org.springframework.data.jpa.repository.JpaRepository

interface SubmitCodeRepository : JpaRepository<SubmitCode, Long> {
}

