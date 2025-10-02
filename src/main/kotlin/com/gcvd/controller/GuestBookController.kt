package com.gcvd.controller

import com.gcvd.api.GuestBookApi
import com.gcvd.dto.GuestBookDTO
import com.gcvd.service.GuestBookService
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/guestbook")
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
class GuestBookController(
    private val guestBookService: GuestBookService,
    private val logger: Logger = LoggerFactory.getLogger(GuestBookController::class.java)
) : GuestBookApi {

    @GetMapping
    override fun getGuestBook(request: HttpServletRequest): ResponseEntity<List<GuestBookDTO.Output>> {
        val startTime = System.currentTimeMillis()
        logger.info("방명록 목록 조회 요청, IP: {}", request.remoteAddr)

        return try {
            val result = guestBookService.getAllGuestBooks()
            val processingTime = System.currentTimeMillis() - startTime

            logger.info("방명록 조회 처리 소요 시간: {}ms, IP: {}", processingTime, request.remoteAddr)

            ResponseEntity.ok().body(result)

        } catch (e: Exception) {
            logger.error(
                "방명록 조회 중 오류 발생 - IP: {}, Error: {}",
                request.remoteAddr, e.message, e
            )
            throw e
        }
    }

    @PostMapping
    override fun createGuestBook(
        request: HttpServletRequest,
        @RequestBody @Valid
        guestBookDTO: GuestBookDTO.Input,
    ): ResponseEntity<GuestBookDTO.Output> {
        val startTime = System.currentTimeMillis()
        logger.info(
            "방명록 작성 요청 - author: '{}', contentLength: {}, IP: {}",
            guestBookDTO.author, guestBookDTO.content.length, request.remoteAddr
        )
        return try {
            val result = guestBookService.createGuestBook(guestBookDTO)

            val processingTime = System.currentTimeMillis() - startTime
            logger.info("방명록 작성 처리 소요 시간: {}ms, IP: {}", processingTime, request.remoteAddr)

            ResponseEntity.ok().body(result)
        } catch (e: Exception) {
            logger.error(
                "방명록 작성 중 오류 발생 - 요청데이터: [author='{}', content='{}'], IP: {}, Error: {}",
                guestBookDTO.author, guestBookDTO.content, request.remoteAddr, e.message, e
            )
            throw e
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(
        ex: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        val message = ex.bindingResult.fieldErrors.firstOrNull()?.defaultMessage
            ?: "입력값이 올바르지 않습니다"

        // 요청된 데이터 추출
        val rejectedValue = ex.bindingResult.fieldErrors.firstOrNull()?.rejectedValue ?: "N/A"
        val fieldName = ex.bindingResult.fieldErrors.firstOrNull()?.field ?: "unknown"

        logger.warn(
            "방명록 작성 실패 - 유효성 검증 오류: '{}', 필드: '{}', 입력값: '{}', IP: {}",
            message, fieldName, rejectedValue, request.remoteAddr
        )

        // 전체 요청 데이터 로깅 (디버그용)
        logger.debug(
            "검증 실패한 전체 요청 데이터 - IP: {}, 오류 개수: {}, 상세: {}",
            request.remoteAddr,
            ex.bindingResult.errorCount,
            ex.bindingResult.allErrors.joinToString("; ") { error ->
                "${error.objectName}.${(error as org.springframework.validation.FieldError).field}: ${error.rejectedValue}"
            })

        return ResponseEntity.badRequest().body(message)
    }
}
