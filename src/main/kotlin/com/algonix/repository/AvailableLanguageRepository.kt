package com.algonix.repository

import com.algonix.model.AvailableLanguage
import org.springframework.data.jpa.repository.JpaRepository

interface AvailableLanguageRepository : JpaRepository<AvailableLanguage, Long>
