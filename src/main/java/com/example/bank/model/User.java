package com.example.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity

@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "card")
    private String card;

    @Column(name = "pin")
    private String pin;

    @Column(name = "balance")
    private long balance;

    @Column(name = "enabled")
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;
}
