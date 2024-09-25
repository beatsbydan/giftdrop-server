package com.tobipeter.giftdrop.db.services.invoice;

import com.tobipeter.giftdrop.db.models.Invoice;
import com.tobipeter.giftdrop.db.repositories.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService{
    private final InvoiceRepository repo;

    @Override
    public void save(Invoice invoice) {
        repo.save(invoice);
    }

    @Override
    public Page<Invoice> findAll(String code, Pageable pageable) {
        return repo.findAll(code, pageable);
    }
}
