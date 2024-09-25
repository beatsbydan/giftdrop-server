package com.tobipeter.giftdrop.controllers;

import com.tobipeter.giftdrop.dtos.response.invoice.InvoiceResponse;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import com.tobipeter.giftdrop.services.InvoiceMgtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/invoices")
@RequiredArgsConstructor
@Validated
public class InvoiceController {
    private final InvoiceMgtService mgtService;

    @GetMapping("{code}")
    public Page<InvoiceResponse> getInvoices(
            @PathVariable(name="code") String code,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size
    ) throws NotFoundException {
        return mgtService.getPaginatedInvoices(code, page, size);
    }
}
