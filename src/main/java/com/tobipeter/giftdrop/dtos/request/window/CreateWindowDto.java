package com.tobipeter.giftdrop.dtos.request.window;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


@Data
public class CreateWindowDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String endDate;
}
