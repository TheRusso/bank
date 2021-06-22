package com.example.bank.services.service_impl;

import com.example.bank.errors_handler.errors.ApiUserNotFoundException;
import com.example.bank.model.Role;
import com.example.bank.model.entities.User;
import com.example.bank.repositories.TransactionsRepository;
import com.example.bank.repositories.UserRepository;
import com.example.bank.utils.ErrorKeys;
import com.example.bank.utils.ErrorMessageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionsRepository transactionsRepository;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository, transactionsRepository);
    }

    @Test
    void findByCard() {
        Mockito.when(userRepository.findByCard("111111111111111")).thenReturn(
                Optional.of(new User().builder()
                        .card("111111111111111")
                        .input(new ArrayList<>())
                        .output(new ArrayList<>())
                        .balance(new BigDecimal(10.10))
                        .role(Role.USER)
                        .pin("$2a$12$ZZfDsDRGa8YG72v0f1UqMOgKTNoZdCgjTBtzrCffFd7MOwAPPZVfm")
                        .build())
        );

        User user = userRepository.findByCard("111111111111111").orElse(null);
        System.out.println(user);

        assertNotNull(user);
        assertTrue(user.getCard().length() > 13);
        assertEquals("111111111111111", user.getCard());
    }

    @Test
    void findByCard_when_cant_find() {
        Mockito.when(userRepository.findByCard("111111111111111"))
                .thenThrow(new ApiUserNotFoundException(
                        ErrorMessageUtil.getInstance().getMessageByKey(ErrorKeys.CANT_FIND_USER.getKey())
                        )
                );

        assertThrows(ApiUserNotFoundException.class, () -> {
            userService.findByCard("111111111111111");
        });
    }

    @Test
    void findById() {
        Mockito.when(userRepository.findById(1L)).thenReturn(
                Optional.of(new User().builder()
                        .card("123456789012345")
                        .input(new ArrayList<>())
                        .output(new ArrayList<>())
                        .balance(new BigDecimal(10.10))
                        .role(Role.USER)
                        .pin("$2a$12$ZZfDsDRGa8YG72v0f1UqMOgKTNoZdCgjTBtzrCffFd7MOwAPPZVfm")
                        .build())
        );

        User user = userService.findById(1L).orElse(null);

        assertNotNull(user);
        assertEquals("123456789012345", user.getCard());
    }

    @Test
    void findById_when_cant_find() {
        Mockito.when(userRepository.findById(1L)).thenThrow(new ApiUserNotFoundException("Test"));

        assertThrows(ApiUserNotFoundException.class, () -> userService.findById(1L));
    }
}