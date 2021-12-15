package com.example.miexpense.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CashFlow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cashFlowId")
    private Long id;

    @Column(name = "income")
    private double income;

    @Column(name = "details", nullable = false)
    private String detail;


    @Column(name = "balance", nullable = false)
    private double balance;


    @OneToOne
    @JoinColumn(name = "expenditure", referencedColumnName = "totalAmount")
    private Expense expense;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

}
