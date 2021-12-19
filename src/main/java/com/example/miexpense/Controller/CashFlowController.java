package com.example.miexpense.Controller;

import com.example.miexpense.model.CashFlow;
import com.example.miexpense.model.Expense;
import com.example.miexpense.model.User;
import com.example.miexpense.services.ServiceImplementation.CashFlowServiceImpl;
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

    @Autowired
    public CashFlowController(CashFlowServiceImpl cashFlowService, UserServiceImpl userService) {
        this.cashFlowService = cashFlowService;
        this.userService = userService;
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

    @GetMapping("/showCashFlow/{id}")
    public String showHome(@PathVariable("id") Long userId, Model model){
        System.out.println(userId);

        List<CashFlow> cash = cashFlowService.getCashFlow(userId);
        User user = userService.findUser(userId);

        model.addAttribute("cash", cash);
        model.addAttribute("userDetail", user);
        model.addAttribute("expenses", new Expense());
        return "cashFlow";
    }

    @GetMapping("/deleteCashFlow/{id}/{userId}")
    public String deleteCashFlow(@PathVariable("id") Long cashFlowId,
                                 @PathVariable("userId") Long userId, Model model){
        cashFlowService.deleteTransaction(cashFlowId, userId);
        model.addAttribute("userId",userId);
        return "redirect:/showCashFlow/{userId}";
    }
}
