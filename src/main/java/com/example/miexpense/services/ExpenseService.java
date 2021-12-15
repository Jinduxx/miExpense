package com.example.miexpense.services;

import com.example.miexpense.model.Expense;
import com.example.miexpense.model.User;
import com.example.miexpense.payLoad.ExpenseDetail;

import java.util.List;

public interface ExpenseService {
    boolean makeExpenses(Expense expense, Long userId);
    List<Expense> getExpenseById(Long expenseId);
    List<ExpenseDetail> getExpenses(User user);
    Expense deleteExpenses(Long expenseId, Long userId);
    double calculatePurchase(Expense expense);
    boolean editExpense(Long expenseId, String details, double noOfItems, double amountPerPurchase, double totalAmount);
//    double calculateMinusBalance(Expense expense);
//    double calculateAddBalance(Expense expense);
}
