package com.clevertecbank.entity;

import com.clevertecbank.jdbc.DBconnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BankDAO implements DAO<Bank>{
    private static BankDAO instance = null;
    private final DBconnector DBCONNECTOR = DBconnector.getInstance();
    @Override
    public Optional<Bank> get(long id) {
        try (Connection connection = DBCONNECTOR.getConnection()) {
            String query = "SELECT * FROM bankapp.bank WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery() ){
                    while (resultSet.next()){
                        Bank bank = Bank.builder().id(resultSet.getLong("id"))
                                .bank_name(resultSet.getString("bank_name"))
                                .build();
                        return Optional.of(bank);
                    }
                }
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public List<Bank> getAll() {
        List<Bank> bankList = new ArrayList<>();
        try (Connection connection = DBCONNECTOR.getConnection()) {
            String query = "SELECT * FROM bankapp.bank";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery() ){
                    while (resultSet.next()){
                        Bank bank = Bank.builder().id(resultSet.getLong("id"))
                                .bank_name(resultSet.getString("bank_name"))
                                .build();
                        bankList.add(bank);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bankList;
    }

    @Override
    public boolean create(Bank bank) {
        if (checkIfExitsByBankName(bank.getBank_name()))
        try (Connection connection = DBCONNECTOR.getConnection()) {
            String query = "INSERT INTO bankapp.bank(name) VALUES (?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, bank.getBank_name());
                statement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    @Override
    public boolean update(Bank updatedbank) {
        if (checkIfExitsById(updatedbank.getId())){
            Bank oldbank = this.get(updatedbank.getId()).get();
            try (Connection connection = DBCONNECTOR.getConnection()) {
                String query = "UPDATE bankapp.bank SET bank_name =?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, (updatedbank.getBank_name() == null) ? oldbank.getBank_name() : updatedbank.getBank_name());
                }
            } catch (SQLException e) {
                return false;
            }

        }
        return false;
    }

    @Override
    public boolean delete(Bank bank) {
        if (checkIfExitsById(bank.getId())) {
            try (Connection connection = DBCONNECTOR.getConnection()) {
            String query = "DELETE FROM bankapp.bank WHERE  id =?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, bank.getId());
                statement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        }

        return false;
    }
    public boolean deleteById(long id){
        if (checkIfExitsById(id)){
            return this.delete(get(id).get());
        }else {
            return false;
        }
    }
    private boolean checkIfExitsById(Long id){
        Optional<Bank> bankOptional = this.get(id);
        return bankOptional.isPresent();
    }
    public Optional<Bank> getByName(String bank_name) {
        try (Connection connection = DBCONNECTOR.getConnection()) {
            String query = "SELECT * FROM bankapp.bank WHERE bank_name = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, bank_name);
                try (ResultSet resultSet = statement.executeQuery() ){
                    while (resultSet.next()){
                        Bank bank = Bank.builder().id(resultSet.getLong("id"))
                                .bank_name(resultSet.getString("bank_name"))
                                .build();
                        return Optional.of(bank);
                    }
                }
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }
    private boolean checkIfExitsByBankName(String bank_name){
        Optional<Bank> bankOptional = this.getByName(bank_name);
        return bankOptional.isPresent();
    }

}


