package com.equipxpress.application.usecases.categorias

import com.equipxpress.domain.models.Categoria
import com.equipxpress.domain.repositories.CategoriaRepository

class GetCategoriaByIdUseCase(private val categoriaRepository: CategoriaRepository) {
    suspend operator fun invoke(id: Int): Result<Categoria> {
        return try {
            val categoria = categoriaRepository.getById(id)
                ?: return Result.failure(Exception("Categoría no encontrada"))
            Result.success(categoria)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}