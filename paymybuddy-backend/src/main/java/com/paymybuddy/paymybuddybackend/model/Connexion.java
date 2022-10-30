package com.paymybuddy.paymybuddybackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "connexion")
public class Connexion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String etat;
    @OneToOne
    private User userExpediteur;
    @OneToOne
    private User userDestinataire;
}
