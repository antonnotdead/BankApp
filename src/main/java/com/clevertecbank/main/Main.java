package com.clevertecbank.main;

import com.clevertecbank.entity.AccountDAO;
import com.clevertecbank.entity.BankDAO;
import com.clevertecbank.entity.CurrencyDAO;
import com.clevertecbank.entity.TransactionDAO;
import com.clevertecbank.entity.UserDAO;
import com.clevertecbank.interestcalc.InterestCalculationThread;
public class Main {
    private static final UserDAO userDAO = UserDAO.getInstance();
    private static final AccountDAO accountDAO = AccountDAO.getInstance();
    private static final CurrencyDAO currencyDAO = CurrencyDAO.getInstance();
    private static final TransactionDAO transactionDAO = TransactionDAO.getInstance();
    private static final BankDAO bankDAO = BankDAO.getInstance();

    public Main() {
    }

    public static void main(String[] args) {
        InterestCalculationThread interest = new InterestCalculationThread();
        Thread threadInterest = new Thread(interest);
        threadInterest.start();
    }
}
