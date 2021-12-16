package com.example.miexpense.Controller;

import com.example.miexpense.model.CashFlow;
import com.example.miexpense.model.Expense;
import com.example.miexpense.model.User;
import com.example.miexpense.services.ServiceImplementation.CashFlowServiceImpl;
import com.example.miexpense.services.ServiceImplementation.ExpenseServiceImpl;
import com.example.miexpense.services.ServiceImplementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CashFlowController {

    private final UserServiceImpl userService;
    private final CashFlowServiceImpl cashFlowService;
    private final ExpenseServiceImpl expenseService;

    @Autowired
    public CashFlowController(CashFlowServiceImpl cashFlowService, UserServiceImpl userService, ExpenseServiceImpl expenseService) {
        this.cashFlowService = cashFlowService;
        this.userService = userService;
        this.expenseService = expenseService;
    }

    @PostMapping("/deposit/{id}")
    public String makeDeposit(@PathVariable("id") Long userId, Model model){

        User user = userService.findUser(userId);
        model.addAttribute("cashFlow", new CashFlow());
        model.addAttribute("user", user);
        return "deposit";
    }

    @RequestMapping(value="/calculate/{id}", method= RequestMethod.POST, params="action=deposit")
    public String calculateDeposit (@PathVariable("id") Long userId, @ModelAttribute("cashFlow") CashFlow cashFlow, Model model){

        User user = userService.findUser(userId);
        cashFlow.setUser(user);

        if(cashFlowService.creditAccount(user.getId(), cashFlow))
            model.addAttribute("message", "Credit Successfully made");
        else
            model.addAttribute("message", "Error Crediting Account");
        model.addAttribute("cashFlow", cashFlow);
        model.addAttribute("user", user);
        return "redirect:/home";
    }

    @PostMapping("/showCashFlow/{id}")
    public String showHome(@PathVariable("id") Long userId, Model model){


        List<CashFlow> cash = cashFlowService.getCashFlow(userId);
        User user = userService.findUser(userId);
        model.addAttribute("cash", cash);
        model.addAttribute("userDetail", user);
        model.addAttribute("expenses", new Expense());
        return "cashFlow";
    }
}
