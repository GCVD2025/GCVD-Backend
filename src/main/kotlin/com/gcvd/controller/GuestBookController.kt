package com.gcvd.controller

import com.gcvd.api.GuestBookApi
import com.gcvd.dto.GuestBookDTO
import com.gcvd.service.GuestBookService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/guestbook")
class GuestBookController(
    private val guestBookService: GuestBookService
) : GuestBookApi {

    @GetMapping
    override fun getGuestBook(): ResponseEntity<List<GuestBookDTO.Output>> {
        return ResponseEntity.ok()
            .body(guestBookService.getAllGuestBooks())
    }

    @PostMapping
    override fun createGuestBook(
        @RequestBody @Valid
        guestBookDTO: GuestBookDTO.Input
    ): ResponseEntity<GuestBookDTO.Output> {
        return ResponseEntity.ok()
            .body(guestBookService.createGuestBook(guestBookDTO))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException): ResponseEntity<String> {
        val message = ex.bindingResult.fieldErrors.firstOrNull()?.defaultMessage
            ?: "입력값이 올바르지 않습니다"
        return ResponseEntity.badRequest().body(message)
    }

}