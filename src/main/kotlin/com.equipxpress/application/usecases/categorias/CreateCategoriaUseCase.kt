package com.equipxpress.application.usecases.categorias

import com.equipxpress.domain.models.Categoria
import com.equipxpress.domain.repositories.CategoriaRepository

class CreateCategoriaUseCase(private val categoriaRepository: CategoriaRepository) {
    suspend operator fun invoke(categoria: Categoria): Result<Categoria> {
        return try {
            if (categoria.nombre.isBlank()) {
                return Result.failure(Exception("El nombre es requerido"))
            }
            
            val createdCategoria = categoriaRepository.create(categoria)
            Result.success(createdCategoria)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
