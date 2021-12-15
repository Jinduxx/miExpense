package com.example.miexpense.services.ServiceImplementation;

import com.example.miexpense.model.CashFlow;
import com.example.miexpense.model.Expense;
import com.example.miexpense.model.User;
import com.example.miexpense.repository.CashFlowRepo;
import com.example.miexpense.repository.ExpenseRepo;
import com.example.miexpense.repository.UserRepo;
import com.example.miexpense.services.CashFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CashFlowServiceImpl implements CashFlowService {

    private UserRepo userRepo;
    private CashFlowRepo cashFlowRepo;
    private ExpenseRepo expenseRepo;

    @Autowired
    public CashFlowServiceImpl(UserRepo userRepo, CashFlowRepo cashFlowRepo, ExpenseRepo expenseRepo) {
        this.userRepo = userRepo;
        this.cashFlowRepo = cashFlowRepo;
        this.expenseRepo = expenseRepo;
    }

    @Override
    public boolean creditAccount(Long userId, CashFlow cashFlow) {
        boolean result = false;
        Optional<User> user = userRepo.findById(userId);
        if(user.isPresent()){
            cashFlowRepo.save(cashFlow);
            result = true;
        }
        return result;
    }

    @Override
    public Boolean balance(double amount) {
        return null;
    }

    @Override
    public double calculateBalance(CashFlow cashFlow) {
        return cashFlow.getBalance() + cashFlow.getIncome();
    }


}
