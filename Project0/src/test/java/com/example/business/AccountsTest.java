package com.example.business;

import com.example.repo.Accounthandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountsTest {

    private Accounthandler mockAccountHandler;
    private accounts accountService;

    @BeforeEach
    void setUp() {
        mockAccountHandler = mock(Accounthandler.class);
        accountService = new accounts(mockAccountHandler);
    }

    @Test
    void depositValidAccountReturnsTrue() {

        int accountId = 1;
        String pin = "1234";
        BigDecimal amount = new BigDecimal("100.00");

        when(mockAccountHandler.validatePin(accountId, pin))
                .thenReturn(true);

        when(mockAccountHandler.depoist(accountId, amount))
                .thenReturn(true);

        boolean result =
                accountService.depoist(pin, amount, accountId);

        assertTrue(result);

        verify(mockAccountHandler).validatePin(accountId, pin);
        verify(mockAccountHandler).depoist(accountId, amount);
    }
}