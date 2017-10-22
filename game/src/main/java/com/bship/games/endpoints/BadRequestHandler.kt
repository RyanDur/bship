package com.bship.games.endpoints

import com.bship.games.endpoints.errors.RequestErrors.*
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.badRequest
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*
import java.util.Optional.of

interface BadRequestHandler {

    fun processValidationError(check: Exception): ResponseEntity<*>

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun processArgumentError(ex: MethodArgumentNotValidException): ResponseEntity<*> = ex.bindingResult.let {
        badRequest().body(getErrors(
                of(it).filter { it.hasFieldErrors() }.map { it.fieldErrors }.map(fieldErrors),
                of(it).filter { it.hasGlobalErrors() }.map { it.globalErrors }.map(objectErrors)))
    }

    fun getErrors(vararg errors: Optional<*>): GameErrors =
            GameErrors.builder().withErrors(errors.filter { it.isPresent }.map { it.get() }).build()

    companion object {

        val error: (String) -> (Exception) -> ObjectError = { name ->
            { error ->
                ObjectError(name, arrayOf(error.javaClass.simpleName),
                        error.stackTrace, error.message)
            }
        }

        val objectErrors: (List<ObjectError>) -> ObjectValidation =
                { errors -> ObjectValidation.builder().withValidations(errors.map(objectError)).build() }

        val fieldErrors: (List<FieldError>) -> FieldValidation =
                { errors -> FieldValidation.builder().withValidations(errors.map(fieldError)).build() }

        private val fieldError: (FieldError) -> ValidationFieldError =
                { err -> ValidationFieldError.builder().withError(err).build() }

        private val objectError: (ObjectError) -> ValidationObjectError =
                { err -> ValidationObjectError.builder().withError(err).build() }
    }
}
