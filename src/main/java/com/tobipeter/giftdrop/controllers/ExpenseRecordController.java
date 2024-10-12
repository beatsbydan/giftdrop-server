package com.tobipeter.giftdrop.controllers;

import com.tobipeter.giftdrop.dtos.request.expenseRecord.ExpenseRecordRequest;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import com.tobipeter.giftdrop.services.ExpenseRecordMgtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/expense-records")
@Validated
@Slf4j
public class ExpenseRecordController {
    private final ExpenseRecordMgtService mgtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createExpenseRecord(
            @Valid @RequestBody ExpenseRecordRequest request
    ) throws NotFoundException {
        mgtService.createExpenseRecord(request);
    }

}
