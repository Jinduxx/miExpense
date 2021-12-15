package com.example.miexpense.services;

import com.example.miexpense.model.CashFlow;
import com.example.miexpense.model.Expense;

public interface CashFlowService {
    boolean creditAccount(Long Userid, CashFlow cashFlow);
    Boolean balance(double amount);
    double calculateBalance(CashFlow cashFlow);


}
