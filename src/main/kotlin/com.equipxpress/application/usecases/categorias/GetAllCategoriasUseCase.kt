package com.equipxpress.application.usecases.categorias

import com.equipxpress.domain.models.Categoria
import com.equipxpress.domain.repositories.CategoriaRepository


class GetAllCategoriasUseCase(private val categoriaRepository: CategoriaRepository) {
    suspend operator fun invoke(): Result<List<Categoria>> {
        return try {
            val categorias = categoriaRepository.getAll()
            Result.success(categorias)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
