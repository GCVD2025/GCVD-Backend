package com.gcvd.api

import com.gcvd.dto.GuestBookDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity

@Tag(name = "방명록", description = "방명록 관리 API")
interface GuestBookApi {

    @Operation(
        summary = "방명록 전체 조회",
        description = "저장된 모든 방명록 항목을 최신순으로 조회합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "조회 성공",
                content = [Content(
                    mediaType = "application/json",
                    array = ArraySchema(schema = Schema(implementation = GuestBookDTO.Output::class))
                )]
            )
        ]
    )
    fun getGuestBook(request: HttpServletRequest): ResponseEntity<List<GuestBookDTO.Output>>

    @Operation(
        summary = "방명록 작성",
        description = "새로운 방명록 항목을 작성합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "400",
                description = "입력값 검증 실패 - 다음 중 하나의 메시지가 반환됩니다",
                content = [Content(
                    mediaType = "text/plain",
                    schema = Schema(
                        type = "string",
                        description = "가능한 에러 메시지들",
                        example = "작성자명은 공백이 아니여야 합니다."
                    ),
                    examples = [
                        ExampleObject(
                            name = "작성자 공백 에러",
                            value = "작성자명은 공백이 아니여야 합니다."
                        ),
                        ExampleObject(
                            name = "작성자 길이 에러",
                            value = "작성자명은 1 ~ 50 글자 사이로만 가능합니다."
                        ),
                        ExampleObject(
                            name = "내용 공백 에러",
                            value = "내용은 공백이 아니여야 합니다."
                        ),
                        ExampleObject(
                            name = "내용 길이 에러",
                            value = "내용은 1 ~ 300 글자 사이로만 가능합니다."
                        )
                    ]
                )]
            )
        ]
    )

    fun createGuestBook(
        request: HttpServletRequest,
        guestBookDTO: GuestBookDTO.Input
    ): ResponseEntity<GuestBookDTO.Output>
}
