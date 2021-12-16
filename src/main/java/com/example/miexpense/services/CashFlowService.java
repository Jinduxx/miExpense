package com.example.miexpense.services;

import com.example.miexpense.model.CashFlow;
import com.example.miexpense.model.Expense;
import com.example.miexpense.model.User;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface CashFlowService {
    boolean creditAccount(Long Userid, CashFlow cashFlow);
    Boolean balance(double amount);
//    double calculateBalance(CashFlow cashFlow, User user);
    int getAmountOfIncomeByUser(@NonNull Long userId);
    List<CashFlow> getCashFlow(Long id);
}
