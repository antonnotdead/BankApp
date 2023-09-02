package com.clevertecbank.entity;

import com.clevertecbank.jdbc.DBconnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDAO implements DAO<Account> {
    private static AccountDAO instance = null;
    private final DBconnector DBCONNECTOR = DBconnector.getInstance();

    public static AccountDAO getInstance() {
        AccountDAO localInstance = instance;
        if (localInstance == null) {
            synchronized (AccountDAO.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new AccountDAO();
                }
            }
        }
        return localInstance;
    }
    @Override
    public Optional<Account> get(long id) {
        try (Connection connection = DBCONNECTOR.getConnection()){
            String query = "SELECT * FROM bankapp.account_data WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()){
                    while (resultSet.next()){
                        Account account = Account.builder().id(resultSet.getLong("id"))
                                .account_number(resultSet.getString("account_number"))
                                .bank_id(resultSet.getLong("bank_id"))
                                .user_id(resultSet.getLong("user_id"))
                                .currency_id(resultSet.getLong("currency_id"))
                                .creation_date(resultSet.getDate("creation_date"))
                                .build();
                        return Optional.of(account);
                    }
                }

            }
        }catch (SQLException e){
            return Optional.empty();
        }
        return Optional.empty();
    }

    public Optional<Account> getByName(String bank_name) {
        try (Connection connection = DBCONNECTOR.getConnection()) {
            String query = "SELECT * FROM bankapp.account_data WHERE account_number = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setString(1, bank_name);
                try (ResultSet resultSet = statement.executeQuery()){
                    while (resultSet.next()){
                        Account account = Account.builder().id(resultSet.getLong("id"))
                                .account_number(resultSet.getString("account_number"))
                                .bank_id(resultSet.getLong("bank_id"))
                                .user_id(resultSet.getLong("user_id"))
                                .currency_id(resultSet.getLong("currency_id"))
                                .creation_date(resultSet.getDate("creation_date"))
                                .build();
                        return Optional.of(account);
                    }
                }

            }
        }catch (SQLException e){
            return Optional.empty();
        }
        return Optional.empty();
    }
    @Override
    public List<Account> getAll() {
        List<Account>  accountList = new ArrayList<>();
        try(Connection connection = DBCONNECTOR.getConnection()) {
            String query = "SELECT * FROM bankapp.account_data";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                try (ResultSet resultSet = statement.executeQuery()){
                    while (resultSet.next()){
                        Account account = Account.builder().id(resultSet.getLong("id"))
                                .account_number(resultSet.getString("account_number"))
                                .bank_id(resultSet.getLong("bank_id"))
                                .user_id(resultSet.getLong("user_id"))
                                .currency_id(resultSet.getLong("currency_id"))
                                .creation_date(resultSet.getDate("creation_date"))
                                .build();
                        accountList.add(account);
                    }
                }

            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return accountList;
    }

    @Override
    public boolean create(Account account) {
        try(Connection connection = DBCONNECTOR.getConnection()) {
            String query = "INSERT INTO bankapp.account_data data(account_number, bank_id, user_id, currency_id, creation_date) VALUES (?,?,?,?,?)";
            try(PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, account.getAccount_number());
                statement.setLong(2, account.getBank_id());
                statement.setLong(3, account.getUser_id());
                statement.setLong(4, account.getCurrency_id());
                statement.setDate(5, account.getCreation_date());

                statement.executeUpdate();
                return true;
            }
        }catch (SQLException e){
            return false;
        }    }

    @Override
    public boolean update(Account updatedAccount) {
        if (checkIfExistsById(updatedAccount.getId())){
            Account oldAccount = this.get(updatedAccount.getId()).get();
            try (Connection connection = DBCONNECTOR.getConnection()){
                String query = "UPDATE bankapp.account_data SET first_name=?, surname=?, patronymic=?";
                try (PreparedStatement statement = connection.prepareStatement(query)){
                    statement.setString(1, (updatedAccount.getAccount_number() == null) ? oldAccount.getAccount_number() : updatedAccount.getAccount_number());
                    statement.setLong(2, (updatedAccount.getBank_id() == 0) ? oldAccount.getBank_id() : updatedAccount.getBank_id());
                    statement.setLong(3, (updatedAccount.getUser_id() == 0) ? oldAccount.getUser_id() : updatedAccount.getUser_id());
                    statement.setLong(4, (updatedAccount.getCurrency_id() == 0) ? oldAccount.getCurrency_id() : updatedAccount.getCurrency_id());
                    statement.setDate(5, (updatedAccount.getCreation_date() == null) ? oldAccount.getCreation_date() : updatedAccount.getCreation_date());

                    statement.executeUpdate();
                }

            } catch (SQLException e){
                return false;
            }
        }
        return false;    }

    @Override
    public boolean delete(Account account) {
        if (checkIfExistsById(account.getId())) {
            try (Connection connection = DBCONNECTOR.getConnection()){
                String query = "DELETE FROM bankapp.account_data WHERE id=?";
                try(PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setLong(1, account.getId());
                    statement.executeUpdate();
                    return true;
                }
            }catch (SQLException e){
                return false;
            }
        }
        return false;
    }
    private boolean checkIfExistsById(Long id){
        Optional<Account> accountOptional = this.get(id);
        return accountOptional.isPresent();
    }
    private boolean checkIfExistsByBankName(String bank_name){
        Optional<Account> bankOptional = this.getByName(bank_name);
        return bankOptional.isPresent();
    }
}
