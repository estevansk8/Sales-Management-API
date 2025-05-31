package com.api.sales_management.interfaces

import com.api.sales_management.application.dto.ApiResponseDTO
import com.api.sales_management.application.dto.request.sale.SaleRequestDTO
import com.api.sales_management.application.dto.request.sale.SaleUpdateStatusRequestDTO
import com.api.sales_management.application.dto.response.sale.SaleResponseDTO
import com.api.sales_management.application.service.SaleService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/sales")
class SaleController(
    private val saleService: SaleService
) {

    @PostMapping
    fun createSale(
        @Valid @RequestBody saleRequestDTO: SaleRequestDTO,
        principal: Principal
    ): ResponseEntity<ApiResponseDTO<SaleResponseDTO>> {
        val authenticatedUserId = principal.name.toLong()
        val createdSale = saleService.createSale(saleRequestDTO, authenticatedUserId)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponseDTO(true, "Sale created successfully", createdSale))
    }

    @GetMapping("/{id}")
    fun getSaleById(
        @PathVariable id: Long,
        principal: Principal
    ): ResponseEntity<ApiResponseDTO<SaleResponseDTO>> {
        val authenticatedUserId = principal.name.toLong()
        val sale = saleService.getSaleByIdForUser(id, authenticatedUserId)
        return ResponseEntity.ok(ApiResponseDTO(true, "Sale retrieved successfully", sale))
    }

    @GetMapping
    fun getAllSales(
        principal: Principal
    ): ResponseEntity<ApiResponseDTO<List<SaleResponseDTO>>> {
        val authenticatedUserId = principal.name.toLong()
        val sales = saleService.getAllSalesForUser(authenticatedUserId)
        return ResponseEntity.ok(ApiResponseDTO(true, "Sales retrieved successfully for the user", sales))
    }

    @PatchMapping("/{id}/status") // PATCH é mais apropriado para atualizações parciais como status
    fun updateSaleStatus(
        @PathVariable id: Long,
        @Valid @RequestBody statusRequest: SaleUpdateStatusRequestDTO,
        principal: Principal
    ): ResponseEntity<ApiResponseDTO<SaleResponseDTO>> {
        val authenticatedUserId = principal.name.toLong()
        val updatedSale = saleService.updateSaleStatus(id, statusRequest.newStatus, authenticatedUserId)
        return ResponseEntity.ok(ApiResponseDTO(true, "Sale status updated successfully", updatedSale))
    }

    @DeleteMapping("/{id}")
    fun deleteSale(
        @PathVariable id: Long,
        principal: Principal
    ): ResponseEntity<ApiResponseDTO<Unit>> {
        val authenticatedUserId = principal.name.toLong()
        saleService.deleteSaleForUser(id, authenticatedUserId)
        return ResponseEntity.ok(ApiResponseDTO(true, "Sale with ID: $id deleted successfully"))
    }
}