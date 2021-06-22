package com.example.bank.services.service_impl;

import com.example.bank.model.Role;
import com.example.bank.model.entities.Transaction;
import com.example.bank.model.entities.User;
import com.example.bank.repositories.TransactionsRepository;
import com.example.bank.repositories.UserRepository;
import com.example.bank.services.TransactionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionsServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionsRepository transactionsRepository;

    private TransactionsServiceImpl transactionsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        transactionsService = new TransactionsServiceImpl(transactionsRepository);
    }

    @Test
    void findByCardTo() {
        User to = new User().builder()
                .card("111111111111111")
                .input(new ArrayList<>())
                .output(new ArrayList<>())
                .balance(new BigDecimal(10.10))
                .role(Role.USER)
                .pin("$2a$12$ZZfDsDRGa8YG72v0f1UqMOgKTNoZdCgjTBtzrCffFd7MOwAPPZVfm")
                .build();

        User from = new User().builder()
                .card("222222222222222222")
                .input(new ArrayList<>())
                .output(new ArrayList<>())
                .balance(new BigDecimal(10.10))
                .role(Role.USER)
                .pin("$2a$12$ZZfDsDRGa8YG72v0f1UqMOgKTNoZdCgjTBtzrCffFd7MOwAPPZVfm")
                .build();

        Mockito.when(transactionsService.findByCardTo("111111111111111111"))
                .thenReturn(List.of(
                        new Transaction().builder()
                            .userFrom(from)
                            .userTo(to)
                            .value(new BigDecimal(20))
                            .build()
                ));

        List<Transaction> list = transactionsService.findByCardTo("111111111111111111");
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    void findByCardFrom() {
        User to = new User().builder()
                .card("111111111111111")
                .input(new ArrayList<>())
                .output(new ArrayList<>())
                .balance(new BigDecimal(10.10))
                .role(Role.USER)
                .pin("$2a$12$ZZfDsDRGa8YG72v0f1UqMOgKTNoZdCgjTBtzrCffFd7MOwAPPZVfm")
                .build();

        User from = new User().builder()
                .card("222222222222222222")
                .input(new ArrayList<>())
                .output(new ArrayList<>())
                .balance(new BigDecimal(10.10))
                .role(Role.USER)
                .pin("$2a$12$ZZfDsDRGa8YG72v0f1UqMOgKTNoZdCgjTBtzrCffFd7MOwAPPZVfm")
                .build();

        Mockito.when(transactionsService.findByCardFrom("111111111111111111"))
                .thenReturn(List.of(
                        new Transaction().builder()
                                .userFrom(from)
                                .userTo(to)
                                .value(new BigDecimal(20))
                                .build()
                ));

        List<Transaction> list = transactionsService.findByCardFrom("111111111111111111");
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }
}