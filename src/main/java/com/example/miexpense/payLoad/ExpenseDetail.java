package com.example.miexpense.payLoad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDetail {

    private Long id;
    private String details;
    private double noOfItems;
    private double amountPerItem;
    private double totalAmount;
    private Date dateOfPurchases;
    private Long UserId;
}
