package com.clevertecbank.transfer;

import com.clevertecbank.entity.*;

import java.util.Optional;
import java.util.Scanner;

public class Transfer {
    private static final AccountDAO accountDAO = AccountDAO.getInstance();
    private static final TransactionDAO transactionDAO = TransactionDAO.getInstance();

    public void doMoneyTransfer(Account currentAcc){
        Transaction newTransaction = new Transaction();
        newTransaction = this.chooseTransType(currentAcc);

    }
    private Transaction chooseTransType (Account currentAcc){
        Transaction transaction = new Transaction();

        System.out.println("Choose transaction type\n"
        +"1. TRANSFER\n"
        +"2. WITHDRAW\n"
        +"3. DEPOSIT");

        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (true) {
            if (t <= 3 && t >= 0) {
                switch (t) {
                    case 1 -> {
                        transaction.setType(Transaction_type.TRANSFER);
                        transaction.setSender_id(currentAcc.getId());
                        transaction.setReceiver_id(this.getReceiver());
                    }
                    case 2 -> {
                        transaction.setType(Transaction_type.TRANSFER);
                        transaction.setSender_id(currentAcc.getId());
                        transaction.setReceiver_id(0);
                    }
                    case 3 -> {
                        transaction.setType(Transaction_type.TRANSFER);
                        transaction.setReceiver_id(currentAcc.getId());
                        transaction.setSender_id(0);
                    }
                }return transaction;
            } else{
                System.out.println("Try again! Wrong input!");
            }
        }
    }
    private long getReceiver(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter receiver account number:");
        while (true) {
            String receiverNum = sc.nextLine();
            Optional<Account> receiverOptional = accountDAO.getByName(receiverNum);
            if (receiverOptional.isPresent()){
                Account receiver = receiverOptional.get();
                return receiver.getId();
            }
        }
    }
}
