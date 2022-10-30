package com.paymybuddy.paymybuddybackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal montant;
    private String etat;
    private String description;
    private Date dateExecution;
    @OneToOne
    private Compte compteEmetteur;
    @OneToOne
    private Compte compteBeneficiaire;
}
