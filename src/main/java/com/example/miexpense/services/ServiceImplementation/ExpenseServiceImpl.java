package com.example.miexpense.services.ServiceImplementation;

import com.example.miexpense.model.Expense;
import com.example.miexpense.model.User;
import com.example.miexpense.payLoad.ExpenseDetail;
import com.example.miexpense.repository.ExpenseRepo;
import com.example.miexpense.repository.UserRepo;
import com.example.miexpense.services.ExpenseService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

  private final  UserRepo userRepo;
    private final ExpenseRepo expenseRepo;

    @Autowired
    public ExpenseServiceImpl(UserRepo userRepo, ExpenseRepo expenseRepo) {
        this.userRepo = userRepo;
        this.expenseRepo = expenseRepo;
    }

    @Override
    public boolean makeExpenses(Expense expense, Long userId) {
        boolean result = false;
        Optional<User> user = userRepo.findById(userId);
        if(user.isPresent()){
            expenseRepo.save(expense);
            result = true;
        }
        return result;
    }

    @Override
    public List<Expense> getExpenseById(Long expenseId) {
//        List<Expense> expenseList;
//        expenseList= expenseRepo.findExpenseById(expenseId);
//        return expenseList;
        return expenseRepo.findExpenseById(expenseId);
    }



    @Override
    public List<ExpenseDetail> getExpenses(User user) {
        List<ExpenseDetail> expenseDetailList = new ArrayList<>();
        //get all expenses
        List<Expense> expensesInfo = expenseRepo.findAllByUserId(user.getId());

        for (Expense exp: expensesInfo) {
            ExpenseDetail expenses = new ExpenseDetail();
            expenses.setId(exp.getId());
            expenses.setDetails(exp.getDetails());
            expenses.setNoOfItems(exp.getNoOfItems());
            expenses.setAmountPerItem(exp.getAmountPerPurchase());
            expenses.setTotalAmount(exp.getTotalAmount());
            expenses.setDateOfPurchases(exp.getDateOfPurchases());
            expenseDetailList.add(expenses);
        }
        return expenseDetailList;
    }

    @Override
    public boolean editExpense(Long expenseId, String details, double noOfItems,
                               double amountPerPurchase, double totalAmount) {
        boolean status = false;
        Optional<Expense> expense = expenseRepo.findById(expenseId);
        if(expense.isPresent()){
            expense.get().setDetails(details);
            expense.get().setNoOfItems(noOfItems);
            expense.get().setAmountPerPurchase(amountPerPurchase);
            expense.get().setTotalAmount(totalAmount);
            expenseRepo.save(expense.get());
            status = true;
        }

        return status;
    }

    @Override
    public int getAmountOfExpensesByUser(@NonNull Long userId) {

        return expenseRepo.sumOfExpensesByUser(userId);
    }

    @Override
    public List<Expense> getUsersById(Long id) {
        return expenseRepo.findAllByUserId(id);
    }

    @Override
    public boolean deleteExpenses(Long expenseId, Long userId) {
        List<Expense> expenses = expenseRepo.findExpenseById(expenseId);
        if(!expenses.isEmpty()){
            expenseRepo.deleteExpenseByIdAndUserId(expenseId,userId);
            return true;
        }
        return false;
    }

    @Override
    public double calculatePurchase(Expense expense) {
        return expense.getNoOfItems() * expense.getAmountPerPurchase();
    }

//    @Override
//    public double calculateMinusBalance(Expense expense) {
//        return expense.getBalance() - expense.getTotalAmount();
//    }
//
//    @Override
//    public double calculateAddBalance(Expense expense) {
//        return expense.getBalance() + expense.getIncome();
//    }


}
