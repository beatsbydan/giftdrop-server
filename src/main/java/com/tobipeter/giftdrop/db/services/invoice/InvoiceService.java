package com.tobipeter.giftdrop.db.services.invoice;

import com.tobipeter.giftdrop.db.models.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvoiceService {
    void save(Invoice invoice);
    Page<Invoice> findAll(String code, Pageable pageable);
}
