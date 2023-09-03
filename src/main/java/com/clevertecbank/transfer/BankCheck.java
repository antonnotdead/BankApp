package com.clevertecbank.transfer;

import com.clevertecbank.entity.*;

public class BankCheck {
    private static final AccountDAO accountDAO = AccountDAO.getInstance();
    private static final CurrencyDAO currencyDAO = CurrencyDAO.getInstance();
    private static final TransactionDAO transactionDAO = TransactionDAO.getInstance();
    private static final BankDAO bankDAO = BankDAO.getInstance();
    public static void printCheck(){
        Transaction testtransaction = transactionDAO.get(2).get();
            String check ="___________________________________________\n"
                         +"|                 Bank check              |\n"
                         +"|Check number:                           " + testtransaction.getId() + "|\n"
                         +"|Time and Date:      "+testtransaction.getDate() +"|\n"
                         +"|Transaction type:                "+ testtransaction.getType() + "|\n"
                         +"|Senders Bank:         " +bankDAO.get(accountDAO.get(testtransaction.getSender_id()).get().getBank_id()).get().getBank_name();
                    ;
            System.out.println(check);
    }
    public static void main(String[] args){
        printCheck();

    }
}
