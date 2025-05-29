package com.api.sales_management.interfaces

import com.api.sales_management.application.dto.ApiResponseDTO
import com.api.sales_management.application.dto.request.product.ProductRequestDTO
import com.api.sales_management.application.dto.response.product.ProductResponseDTO
import com.api.sales_management.application.service.ProductService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/v1/products")
class ProductController(
    private val productService: ProductService
) {

    @PostMapping
    fun createProduct(
        @Valid @RequestBody productRequestDTO: ProductRequestDTO,
        principal: Principal
    ): ResponseEntity<ApiResponseDTO<ProductResponseDTO>> {
        val authenticatedUserId = principal.name.toLong()
        val createdProduct = productService.createProduct(productRequestDTO, authenticatedUserId)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponseDTO(true, "Product created successfully", createdProduct))
    }

    @GetMapping("/{id}")
    fun getProductById(
        @PathVariable id: Long
        // principal: Principal // Descomente se precisar do ID do usuário para lógica de acesso
    ): ResponseEntity<ApiResponseDTO<ProductResponseDTO>> {
        // Se getProductById no service for restrito ao dono, você precisará do authenticatedUserId:
        // val authenticatedUserId = principal.name.toLong()
        // val product = productService.getProductById(id, authenticatedUserId)
        val product = productService.getProductById(id) // Versão atual permite qualquer user ver
        return ResponseEntity.ok(ApiResponseDTO(true, "Product retrieved successfully", product))
    }

    @GetMapping
    fun getAllProductsBydUser(
        principal: Principal
    ): ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> {
        val authenticatedUserId = principal.name.toLong()
        val products = productService.getAllProductsBydUser(authenticatedUserId)
        return ResponseEntity.ok(ApiResponseDTO(true, "Products retrieved successfully for the user", products))
    }

    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @Valid @RequestBody productRequestDTO: ProductRequestDTO,
        principal: Principal
    ): ResponseEntity<ApiResponseDTO<ProductResponseDTO>> {
        val authenticatedUserId = principal.name.toLong()
        val updatedProduct = productService.updateProduct(id, productRequestDTO, authenticatedUserId)
        return ResponseEntity.ok(ApiResponseDTO(true, "Product '${updatedProduct.name}' updated successfully", updatedProduct))
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(
        @PathVariable id: Long,
        principal: Principal
    ): ResponseEntity<ApiResponseDTO<Unit>> {
        val authenticatedUserId = principal.name.toLong()
        productService.deleteProduct(id, authenticatedUserId)
        return ResponseEntity.ok(ApiResponseDTO(true, "Product with ID: $id deleted successfully"))
    }
}
