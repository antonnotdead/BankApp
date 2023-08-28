package com.clevertecbank.entity;

import com.clevertecbank.jdbc.DBconnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDAO implements DAO<Currency>{
    private static CurrencyDAO instance = null;
    private final DBconnector DBCONNECTOR = DBconnector.getInstance();

    public static CurrencyDAO getInstance() {
        CurrencyDAO localInstance = instance;
        if (localInstance == null) {
            synchronized (CurrencyDAO.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CurrencyDAO();
                }
            }
        }
        return localInstance;
    }
    @Override
    public Optional<Currency> get(long id) {
        try (Connection connection = DBCONNECTOR.getConnection()){
            String query = "SELECT * FROM bankapp.currency WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()){
                    while (resultSet.next()){
                        Currency currency = Currency.builder().id(resultSet.getLong("id"))
                                .code(resultSet.getString("code"))
                                .build();
                        return Optional.of(currency);
                    }
                }

            }
        }catch (SQLException e){
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public List<Currency> getAll() {
        List<Currency>  currencyList = new ArrayList<>();
        try(Connection connection = DBCONNECTOR.getConnection()) {
            String query = "SELECT * FROM bankapp.user_data";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                try (ResultSet resultSet = statement.executeQuery()){
                    while (resultSet.next()){
                        Currency currency = Currency.builder().id(resultSet.getLong("id"))
                                .code(resultSet.getString("code"))
                                .build();
                        currencyList.add(currency);
                    }
                }

            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return currencyList;
    }

    @Override
    public boolean create(Currency currency) {
        try(Connection connection = DBCONNECTOR.getConnection()) {
            String query = "INSERT INTO bankapp.currency(code) VALUES (?)";
            try(PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, currency.getCode());
                statement.executeUpdate();
                return true;
            }
        }catch (SQLException e){
            return false;
        }
    }

    @Override
    public boolean update(Currency Updatedcurrency) {
        if (checkIfExitsById(Updatedcurrency.getId())){
            Currency oldcurrency = this.get(Updatedcurrency.getId()).get();
            try (Connection connection = DBCONNECTOR.getConnection()){
                String query = "UPDATE bankapp.currency SET code =?";
                try (PreparedStatement statement = connection.prepareStatement(query)){
                    statement.setString(1, (Updatedcurrency.getCode() == null) ? oldcurrency.getCode() : Updatedcurrency.getCode());
                    statement.executeUpdate();
                }

            } catch (SQLException e){
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Currency currency) {
        if (checkIfExitsById(currency.getId())) {
            try (Connection connection = DBCONNECTOR.getConnection()){
                String query = "DELETE FROM bankapp.currency WHERE id=?";
                try(PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setLong(1, currency.getId());
                    statement.executeUpdate();
                    return true;
                }
            }catch (SQLException e){
                return false;
            }
        }
        return false;
    }
    private boolean checkIfExitsById(Long id){
        Optional<Currency> userOptional = this.get(id);
        return userOptional.isPresent();
    }
}
