package com.example.miexpense.services;

import com.example.miexpense.model.CashFlow;
import lombok.NonNull;

import java.util.List;

public interface CashFlowService {
    boolean creditAccount(Long Userid, CashFlow cashFlow);
    Boolean balance(double amount);
    int getAmountOfIncomeByUser(@NonNull Long userId);
    List<CashFlow> getCashFlow(Long id);
    void deleteTransaction(Long cashFlowId, Long userId);
}
