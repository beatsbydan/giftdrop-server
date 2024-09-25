package com.tobipeter.giftdrop.db.services.expenseRecord;

import com.tobipeter.giftdrop.db.models.ExpenseRecord;
import com.tobipeter.giftdrop.db.repositories.ExpenseRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExpenseRecordServiceImpl implements ExpenseRecordService {
    private final ExpenseRecordRepository repo;

    @Override
    public ExpenseRecord save(ExpenseRecord expenseRecord) {
        return repo.save(expenseRecord);
    }
}
