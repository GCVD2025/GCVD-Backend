package com.gcvd.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length
import java.time.LocalDateTime

@Schema(description = "방명록 DTO")
class GuestBookDTO {

    @Schema(description = "방명록 입력 데이터")
    data class Input(
        @field:Length(min = 1, max = 50, message = "작성자명은 1 ~ 50 글자 사이로만 가능합니다.")
        @field:NotBlank(message = "작성자명은 공백이 아니여야 합니다.")
        @field:Schema(description = "작성자 이름", example = "홍길동", maxLength = 50)
        val author: String,

        @field:Length(min = 1, max = 300, message = "내용은 1 ~ 300 글자 사이로만 가능합니다.")
        @field:NotBlank(message = "내용은 공백이 아니여야 합니다.")
        @field:Schema(description = "방명록 내용", example = "안녕하세요! 좋은 하루 되세요.", maxLength = 300)
        val content: String
    )

    @Schema(description = "방명록 응답 데이터")
    data class Output(
        @field:Schema(description = "작성자 이름", example = "홍길동")
        val author: String,

        @field:Schema(description = "방명록 내용", example = "안녕하세요! 좋은 하루 되세요.")
        val content: String,

        @field:Schema(description = "작성 시간", example = "2025-09-08T15:30:00")
        val createdAt: LocalDateTime
    )
}
