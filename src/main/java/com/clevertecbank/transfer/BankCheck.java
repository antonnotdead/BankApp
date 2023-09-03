package com.clevertecbank.transfer;

import com.clevertecbank.entity.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BankCheck {
    private static String checkSavePath = "src\\main\\java\\com\\clevertecbank\\check";
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
                         +"|Senders Bank:                " +bankDAO.get(accountDAO.get(testtransaction.getSender_id()).get().getBank_id()).get().getBank_name()+ "|\n"
                         +"|Receivers Bank:              " +bankDAO.get(accountDAO.get(testtransaction.getReceiver_id()).get().getBank_id()).get().getBank_name()+ "|\n"
                         +"|Senders Account Number:        " + accountDAO.get(testtransaction.getSender_id()).get().getAccount_number() + "|\n"
                         +"|Receivers Account Number:      " + accountDAO.get(testtransaction.getReceiver_id()).get().getAccount_number() + "|\n"
                         +"|Money Value:                   " + testtransaction.getTransaction_value() +" "+ currencyDAO.get(accountDAO.get(testtransaction.getSender_id()).get().getCurrency_id()).get().getCode() +"|\n"
                         +"|_________________________________________| "
                    ;
            System.out.println(check);
            saveCheck(testtransaction, check);
    }
    private static void saveCheck(Transaction transaction, String check){
        String path = checkSavePath + "\\check"+transaction.getId()+".txt";
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))){
            bufferedWriter.write(check);
        }catch (IOException exception){
            throw new RuntimeException();
        }
    }
}
