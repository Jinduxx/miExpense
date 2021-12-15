package com.example.miexpense.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "expenseId")
    private Long id;

    @Column(name = "details", nullable = false, columnDefinition = "VARCHAR(150)")
    private String details;

    @Column(name = "noOfItems", nullable = false)
    private double noOfItems;

    @Column(name = "amountPerPurchase", nullable = false)
    private double amountPerPurchase;

    @Column(name = "totalAmount", nullable = false) //todo: sum
    private double totalAmount;

//    @Column(name = "income") //todo: remove
//    private double income;
//
//    @Column(name = "balance", nullable = false)
//    private double balance;

//    tub = userincomesum - totalamountsum

    @CreationTimestamp
    private Date dateOfPurchases;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

}
