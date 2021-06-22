package com.example.bank.model.entities;

import com.example.bank.model.Role;
import com.example.bank.model.Status;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

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
    private BigDecimal balance;

    @Column(name = "enabled")
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "userTo")
    private List<Transaction> input;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "userFrom")
    private List<Transaction> output;
}
