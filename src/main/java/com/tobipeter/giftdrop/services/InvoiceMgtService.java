package com.tobipeter.giftdrop.services;

import com.tobipeter.giftdrop.db.models.ExpenseRecord;
import com.tobipeter.giftdrop.db.models.Invoice;
import com.tobipeter.giftdrop.db.models.auth.GiftDropUser;
import com.tobipeter.giftdrop.db.services.invoice.InvoiceService;
import com.tobipeter.giftdrop.dtos.response.invoice.CreateInvoiceRequest;
import com.tobipeter.giftdrop.dtos.response.invoice.InvoiceResponse;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceMgtService {
    private final InvoiceService invoiceService;

    public void createInvoice(CreateInvoiceRequest request, GiftDropUser user, ExpenseRecord record){
        invoiceService.save(toDbModel(request, user, record));
    }

    public Page<InvoiceResponse> getPaginatedInvoices(String code, int page, int size) throws NotFoundException {
        Pageable pageable = PageRequest.of(page, size);
        return toPaginatedResponse(invoiceService.findAll(code, pageable));
    }

    private Invoice toDbModel(CreateInvoiceRequest request, GiftDropUser user, ExpenseRecord record){
        Invoice invoice = new Invoice();

        invoice.setName(invoice.generateName());
        invoice.setAmountSpent(Long.parseLong(request.getAmountSpent()));
        invoice.setGiftDropUser(user);
        invoice.setExpenseRecord(record);

        return invoice;
    }

    private InvoiceResponse toInvoiceResponse(Invoice invoice){
        InvoiceResponse response = new InvoiceResponse();

        response.setName(invoice.getName());
        response.setAmountSpent(invoice.getAmountSpent());
        response.setDate(invoice.getCreatedAt().toLocalDate());

        return response;
    }

    private Page<InvoiceResponse> toPaginatedResponse(Page<Invoice> invoices){
        List<InvoiceResponse> listResponse = new ArrayList<>();

        for(Invoice invoice : invoices){
            listResponse.add(toInvoiceResponse(invoice));
        }

        return new PageImpl<>(listResponse, invoices.getPageable(), invoices.getTotalElements());
    }


}
