package com.example.foundit.core.app

import kotlinx.coroutines.flow.Flow

interface UseCases<T, Params> {
    operator fun invoke(params: Params): Flow<Resource<T>>
}

interface StreamUseCases<T, Params> {
    fun invoke(params: Params): Flow<T>
}

class NoParams

fun main() {
    val noParams = NoParams()
    println(noParams)
}