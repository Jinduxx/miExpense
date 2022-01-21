package com.example.miexpense.repository;

import com.example.miexpense.model.CashFlow;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CashFlowRepo extends JpaRepository<CashFlow, Long> {

    List<CashFlow> findCashFlowByUserId(Long userId);
    @Transactional
    void deleteByIdAndUserId(Long cashFlowId, Long userId);

//    @Query("SELECT sum(income) from CashFlow ")
//    int sumOfIncome();

    @Query(value = "SELECT sum(income) from cash_flow where user_id =?1 ", nativeQuery = true)
    int sumOfIncomeByUserId(@NonNull Long userId);


}
