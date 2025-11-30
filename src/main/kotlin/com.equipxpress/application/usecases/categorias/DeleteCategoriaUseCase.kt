package com.equipxpress.application.usecases.categorias

import com.equipxpress.domain.models.Categoria
import com.equipxpress.domain.repositories.CategoriaRepository

class DeleteCategoriaUseCase(private val categoriaRepository: CategoriaRepository) {
    suspend operator fun invoke(id: Int): Result<Boolean> {
        return try {
            val deleted = categoriaRepository.delete(id)
            if (deleted) {
                Result.success(true)
            } else {
                Result.failure(Exception("Categoría no encontrada"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}