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

import javax.servlet.http.HttpSession;
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

    @PostMapping("/deposit")
    public String makeDeposit(Model model, HttpSession session){

        User user1 = (User) session.getAttribute("user");

        User user = userService.findUser(user1.getId());
        model.addAttribute("cashFlow", new CashFlow());
        model.addAttribute("user", user);
        return "deposit";
    }

    @RequestMapping(value="/calculate", method= RequestMethod.POST, params="action=deposit")
    public String calculateDeposit (@ModelAttribute("cashFlow") CashFlow cashFlow, Model model, HttpSession session){

        User user1 = (User) session.getAttribute("user");
        User user = userService.findUser(user1.getId());
        cashFlow.setUser(user);

        if(cashFlowService.creditAccount(user.getId(), cashFlow))
            model.addAttribute("message", "Credit Successfully made");
        else
            model.addAttribute("message", "Error Crediting Account");
        model.addAttribute("cashFlow", cashFlow);
        model.addAttribute("user", user);
        return "redirect:/home";
    }

    @GetMapping("/showCashFlow")
    public String showHome(HttpSession session, Model model){

        User user1 = (User) session.getAttribute("user");

        List<CashFlow> cash = cashFlowService.getCashFlow(user1.getId());
        User user = userService.findUser(user1.getId());

        model.addAttribute("cash", cash);
        model.addAttribute("userDetail", user);
        model.addAttribute("expenses", new Expense());
        return "cashFlow";
    }

    @GetMapping("/deleteCashFlow/{id}")
    public String deleteCashFlow(@PathVariable("id") Long cashFlowId, Model model, HttpSession session){
        User user1 = (User) session.getAttribute("user");
        cashFlowService.deleteTransaction(cashFlowId, user1.getId());
        return "redirect:/showCashFlow";
    }
}
