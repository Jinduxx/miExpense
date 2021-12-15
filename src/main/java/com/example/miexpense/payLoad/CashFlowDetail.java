package com.example.miexpense.payLoad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashFlowDetail {
    private Long id;
    List<Double> balance;
    List<Double> expenditure;
    private double income;
}
