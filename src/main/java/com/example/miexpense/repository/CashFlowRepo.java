package com.example.miexpense.repository;

import com.example.miexpense.model.CashFlow;
import com.example.miexpense.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CashFlowRepo extends JpaRepository<CashFlow, Long> {

    List<CashFlow> findCashFlowByUserId(Long userId);
    List<CashFlow> findAllByUserId(Long userId);

}
