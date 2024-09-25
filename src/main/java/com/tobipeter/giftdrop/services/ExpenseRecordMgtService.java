package com.tobipeter.giftdrop.services;

import com.tobipeter.giftdrop.db.models.ExpenseRecord;
import com.tobipeter.giftdrop.db.models.Window;
import com.tobipeter.giftdrop.db.models.auth.GiftDropUser;
import com.tobipeter.giftdrop.db.services.auth.user.UserService;
import com.tobipeter.giftdrop.db.services.expenseRecord.ExpenseRecordService;
import com.tobipeter.giftdrop.db.services.window.WindowService;
import com.tobipeter.giftdrop.dtos.request.expenseRecord.ExpenseRecordRequestDto;
import com.tobipeter.giftdrop.dtos.response.invoice.CreateInvoiceRequestDto;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ExpenseRecordMgtService {
    private final ExpenseRecordService expenseRecordService;
    private final InvoiceMgtService invoiceMgtService;
    private final UserService userService;
    private final WindowService windowService;

    public void createExpenseRecord(ExpenseRecordRequestDto request) throws NotFoundException {
        CreateInvoiceRequestDto invoiceRequest = new CreateInvoiceRequestDto();

        GiftDropUser user = userService.getByCode(request.getUserCode());
        Window window = windowService.getByCode(request.getWindowCode());

        invoiceRequest.setAmountSpent(request.getTotalFee());

        ExpenseRecord record = expenseRecordService.save(toDbModel(request, user, window));
        invoiceMgtService.createInvoice(invoiceRequest, user, record);
    }
    private ExpenseRecord toDbModel(ExpenseRecordRequestDto request, GiftDropUser user, Window window){
        ExpenseRecord record = new ExpenseRecord();

        record.setDeliveryFee(Long.parseLong(request.getDeliveryFee()));
        record.setServiceFee(Long.parseLong(request.getServiceFee()));
        record.setProductsFee(Long.parseLong(request.getProductsFee()));
        record.setTotal(Long.parseLong(request.getTotalFee()));

        record.setWindow(window);
        record.setGiftDropUser(user);

        return record;
    }
}
