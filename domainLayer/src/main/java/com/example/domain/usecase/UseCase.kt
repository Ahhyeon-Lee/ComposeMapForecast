package com.example.domain.usecase

interface UseCase {
    fun <T>invoke(vararg arg:T)
}