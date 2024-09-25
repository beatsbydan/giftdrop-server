package com.tobipeter.giftdrop.db.repositories;

import com.tobipeter.giftdrop.db.models.ExpenseRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExpenseRecordRepository extends JpaRepository<ExpenseRecord, Long> {
}
