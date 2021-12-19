package com.example.miexpense.services.ServiceImplementation;

import com.example.miexpense.model.CashFlow;
import com.example.miexpense.model.User;
import com.example.miexpense.repository.CashFlowRepo;
import com.example.miexpense.repository.UserRepo;
import com.example.miexpense.services.CashFlowService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CashFlowServiceImpl implements CashFlowService {

    private final UserRepo userRepo;
    private final CashFlowRepo cashFlowRepo;

    @Autowired
    public CashFlowServiceImpl(UserRepo userRepo, CashFlowRepo cashFlowRepo) {
        this.userRepo = userRepo;
        this.cashFlowRepo = cashFlowRepo;
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
    public int getAmountOfIncomeByUser(@NonNull Long userId) {
        return cashFlowRepo.sumOfIncomeByUserId(userId);
    }

    @Override
    public List<CashFlow> getCashFlow(Long id) {
        return cashFlowRepo.findCashFlowByUserId(id);
    }

    @Override
    public void deleteTransaction(Long cashFlowId, Long userId) {
        cashFlowRepo.deleteByIdAndUserId(cashFlowId, userId);
    }


}
