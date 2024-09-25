package com.tobipeter.giftdrop.db.repositories;

import com.tobipeter.giftdrop.db.models.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("SELECT i from Invoice i WHERE i.giftDropUser.code = :code")
    Page<Invoice> findAll(@Param("code") String code, Pageable pageable);
}
