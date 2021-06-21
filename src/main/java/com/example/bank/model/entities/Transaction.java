package com.example.bank.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "value")
    private long value;

    @ManyToOne
    @JoinColumn(name = "card_id_to")
    private User userTo;

    @ManyToOne
    @JoinColumn(name = "card_id_from")
    private User userFrom;
}