package ru.ivanov.springCalc.Calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.ivanov.springCalc.Calculator.model.Calc;
import ru.ivanov.springCalc.Calculator.service.CalcService;

@Controller
public class CalcController {

    private final CalcService calcService;

    @Autowired
    public CalcController(CalcService calcService) {
        this.calcService = calcService;
    }

    @GetMapping()
    public String index() {
        return "index";
    }

    @PostMapping("/calc")
    public String indexCalc(@ModelAttribute Calc calc, Model model) {
        calc.setResult(calcService.calculate(calc.getIn()));
        model.addAttribute("calculator", calc);
        return "redirect: /";
    }
}
