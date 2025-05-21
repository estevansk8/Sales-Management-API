package com.api.sales_management.shared.controller

import com.api.sales_management.application.dto.ApiResponseDTO
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ApiResponseDTO<Any>> {
        val errors = ex.bindingResult.fieldErrors
            .associate { it.field to (it.defaultMessage ?: "Invalid value") }

        return ResponseEntity.badRequest().body(
            ApiResponseDTO(
                success = false,
                message = errors.values.toString(),
                data = errors
            )
        )
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(ex: NoSuchElementException): ResponseEntity<ApiResponseDTO<Any>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ApiResponseDTO(
                success = false,
                message = ex.message ?: "Resource not found"
            )
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneric(ex: Exception): ResponseEntity<ApiResponseDTO<Any>> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ApiResponseDTO(
                success = false,
                message = "Unexpected error: ${ex.localizedMessage}"
            )
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleInvalidJson(ex: HttpMessageNotReadableException): ResponseEntity<ApiResponseDTO<Nothing>> {
        val rootCause = ex.mostSpecificCause

        print(rootCause.toString())
        val message = if (rootCause is MismatchedInputException) {
            val path = rootCause.path
            if (path.isNotEmpty()) {
                "Invalid or missing field: ${path.joinToString(".") { it.fieldName ?: "unknown" }}"
            } else {
                "Invalid or missing field in request body"
            }
        } else {
            rootCause?.message ?: "Failed to process request."
        }

        val response = ApiResponseDTO(
            success = false,
            message = message,
            data = null
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

}