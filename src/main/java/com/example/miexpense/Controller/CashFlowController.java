package com.example.miexpense.Controller;

import com.example.miexpense.model.CashFlow;
import com.example.miexpense.model.Expense;
import com.example.miexpense.model.User;
import com.example.miexpense.repository.CashFlowRepo;
import com.example.miexpense.repository.ExpenseRepo;
import com.example.miexpense.repository.UserRepo;
import com.example.miexpense.services.ServiceImplementation.CashFlowServiceImpl;
import com.example.miexpense.services.ServiceImplementation.ExpenseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CashFlowController {

    private UserRepo userRepo;
    private CashFlowRepo cashFlowRepo;
    private CashFlowServiceImpl cashFlowService;
    private ExpenseServiceImpl expenseService;
    private ExpenseRepo expenseRepo;

    @Autowired
    public CashFlowController(UserRepo userRepo, CashFlowRepo cashFlowRepo, CashFlowServiceImpl cashFlowService, ExpenseServiceImpl expenseService, ExpenseRepo expenseRepo) {
        this.userRepo = userRepo;
        this.cashFlowRepo = cashFlowRepo;
        this.cashFlowService = cashFlowService;
        this.expenseService = expenseService;
        this.expenseRepo = expenseRepo;
    }



    @PostMapping("/deposit/{id}")
    public String makeDeposit(@PathVariable("id") Long userId, Model model){

        User user = userRepo.findUserById(userId);
        model.addAttribute("cashFlow", new CashFlow());
        model.addAttribute("user", user);
        return "deposit";
    }

    @RequestMapping(value="/calculate/{id}", method=RequestMethod.POST, params="action=getBalance")
    public String calculatePurchase(@PathVariable("id") Long userId, @ModelAttribute("cashFlow") CashFlow cashFlow, Model model){

        User user = userRepo.findUserById(userId);
        cashFlow.setBalance(cashFlowService.calculateBalance(cashFlow));
//        expense.setBalance(expenseService.calculateMinusBalance(expense));

        model.addAttribute("cashFlow", cashFlow);
        model.addAttribute("user", user);
        return "deposit";
    }

    @RequestMapping(value="/calculate/{id}", method= RequestMethod.POST, params="action=deposit")
    public String calculateDeposit (@PathVariable("id") Long userId, @ModelAttribute("cashFlow") CashFlow cashFlow, Model model){

        User user = userRepo.findUserById(userId);
        Expense expenseList = expenseRepo.findExpenseByUserId(userId);
        System.out.println(userId);
        cashFlow.setUser(user);
        cashFlow.setExpense(expenseList);

//        double grossTotal = 0;
//        double
//        for(Expense e: exp){
//            grossTotal += e.getTotalAmount();
//        }


//        expense.setBalance(expenseService.calculateAddBalance(expense));
        if(cashFlowService.creditAccount(user.getId(), cashFlow))
            model.addAttribute("message", "Credit Successfully made");
        else
            model.addAttribute("message", "Error Crediting Account");
        model.addAttribute("cashFlow", cashFlow);
        model.addAttribute("user", user);
        return "redirect:/home";
    }

    @PostMapping("/showCashFlow/{id}")
    public String showCashFlow(@PathVariable("id") Long userId,  Model model){

        Expense exp = expenseRepo.findExpenseByUserId(userId);

        List<CashFlow> cas = cashFlowRepo.findAllByUserId(userId);

        User user = userRepo.findUserById(userId);

        model.addAttribute("expense", exp);
        model.addAttribute("cashFlow", cas);
        model.addAttribute("userDetail", user);

        return "cashFlow";
    }

//    @PostMapping("/processDeposit/{id}")
//    public String addDeposit(@PathVariable("id") Long userId, @ModelAttribute("cashFlow") CashFlow cashFlow, Model model){
//
//        User user = userRepo.findUserById(userId);
//        List<ExpenseDetail> expense = expenseService.getExpenses(user);
//
//        cashFlowService.creditAccount(user.getId(), cashFlow);
//        model.addAttribute("cashFlow", new CashFlow());
//        model.addAttribute("user", user);
//
//        return "redirect:/cashflow";
//    }
}
