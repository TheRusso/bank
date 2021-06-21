package com.example.bank.repositories;

import com.example.bank.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends CrudRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.userTo.card = :card")
    public List<Transaction> findByCardTo(@Param("card") String card);

    @Query("SELECT t FROM Transaction t WHERE t.userFrom.card = :card")
    public List<Transaction> findByCardFrom(@Param("card") String card);
}
