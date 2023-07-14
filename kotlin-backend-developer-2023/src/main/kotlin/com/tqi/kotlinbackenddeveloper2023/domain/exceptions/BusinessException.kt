package com.tqi.kotlinbackenddeveloper2023.domain.exceptions

data class BusinessException(override val message: String?) : RuntimeException(message)
