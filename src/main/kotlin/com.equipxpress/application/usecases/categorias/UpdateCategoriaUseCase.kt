package com.equipxpress.application.usecases.categorias

import com.equipxpress.domain.models.Categoria
import com.equipxpress.domain.repositories.CategoriaRepository

class UpdateCategoriaUseCase(private val categoriaRepository: CategoriaRepository) {
    suspend operator fun invoke(id: Int, categoria: Categoria): Result<Categoria> {
        return try {
            if (categoria.nombre.isBlank()) {
                return Result.failure(Exception("El nombre es requerido"))
            }
            
            val updatedCategoria = categoriaRepository.update(id, categoria)
                ?: return Result.failure(Exception("Categoría no encontrada"))
            
            Result.success(updatedCategoria)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}