package com.uj15.timedeal.product.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@RequiredArgsConstructor
public class ProductUpdateRequest {

    @NotBlank
    @Length(max = 50)
    private final String name;

    @NotBlank
    @Length(max = 500)
    private final String description;

    @Min(value = 0)
    private final long quantity;

    @Min(value = 0)
    private final long price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime dealTime;
}
