package com.clevertecbank.entity;

import com.clevertecbank.jdbc.DBconnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionDAO implements DAO<Transaction> {
    private static TransactionDAO instance = null;
    private final DBconnector DBCONNECTOR = DBconnector.getInstance();

    public static TransactionDAO getInstance() {
        TransactionDAO localInstance = instance;
        if (localInstance == null) {
            Class var1 = TransactionDAO.class;
            synchronized (TransactionDAO.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new TransactionDAO();
                }
            }
        }

        return localInstance;
    }

    @Override
    public Optional<Transaction> get(long id) {
        try(Connection connection = DBCONNECTOR.getConnection()) {
            String query = "SELECT * FROM bankapp.transaction WHERE id = ?";
            try(PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Transaction transaction = Transaction.builder().id(resultSet.getLong("id"))
                                .receiver_id(resultSet.getLong("receiver_id"))
                                .sender_id(resultSet.getLong("sender_id"))
                                .transaction_value(resultSet.getDouble("money_value"))
                                .date(resultSet.getTimestamp("date"))
                                .type(Transaction_type.valueOf(resultSet.getString("transaction_type")))
                                .build();
                        return Optional.of(transaction);
                    }
                }
            }

        }catch (SQLException e){
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public List<Transaction> getAll() {
        List<Transaction> transactionList = new ArrayList();
        try(Connection connection = this.DBCONNECTOR.getConnection()) {
            String query = "SELECT * FROM bankapp.transaction";
            try(PreparedStatement statement = connection.prepareStatement(query)) {
                try(ResultSet resultSet = statement.executeQuery()) {
                    while(resultSet.next()) {
                            Transaction transaction = Transaction.builder().id(resultSet.getLong("id"))
                                    .receiver_id(resultSet.getLong("receiver_id"))
                                    .sender_id(resultSet.getLong("sender_id"))
                                    .transaction_value(resultSet.getDouble("money_value"))
                                    .date(resultSet.getTimestamp("date"))
                                    .type(Transaction_type.valueOf(resultSet.getString("transaction_type")))
                                    .build();
                            transactionList.add(transaction);
                        }
                    }
                    }
                } catch (SQLException e){
            throw new RuntimeException();
        }
        return transactionList;
    }

    @Override
    public boolean create(Transaction transaction) {
        try(Connection connection = DBCONNECTOR.getConnection()) {
            String query = "INSERT INTO bankapp.transaction data(sender_id, receiver_id, money_value, transaction_date, transaction_type) VALUES (?,?,?,?,?)";
            try(PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, transaction.getSender_id());
                statement.setLong(2, transaction.getReceiver_id());
                statement.setDouble(3, transaction.getTransaction_value());
                statement.setTimestamp(4, transaction.getDate());
                statement.setString(5, transaction.getType().name());
                statement.executeUpdate();
                return true;
                }
        } catch (SQLException var11) {
            return false;
        }
    }

    @Override
    public boolean update(Transaction updatedtransaction) {
        if (this.checkIfExitsById(updatedtransaction.getId())) {
            Transaction oldtransaction = this.get(updatedtransaction.getId()).get();
            try (Connection connection = this.DBCONNECTOR.getConnection()) {
                String query = "UPDATE bankapp.transaction SET sender_id=?, receiver_id=?, money_value=?, transaction_date =?,transaction_type =? ";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setLong(1, updatedtransaction.getSender_id() == 0L ? oldtransaction.getSender_id() : updatedtransaction.getSender_id());
                    statement.setLong(2, updatedtransaction.getReceiver_id() == 0L ? oldtransaction.getReceiver_id() : updatedtransaction.getReceiver_id());
                    statement.setDouble(3, updatedtransaction.getTransaction_value() == 0.0 ? oldtransaction.getTransaction_value() : updatedtransaction.getTransaction_value());
                    statement.setTimestamp(4, updatedtransaction.getDate() == null ? oldtransaction.getDate() : updatedtransaction.getDate());
                    statement.setString(5, updatedtransaction.getType().name() == null ? oldtransaction.getType().name() : updatedtransaction.getType().name());
                    statement.executeUpdate();
                }
            }catch (SQLException e) {
                return false;
            }
        }
        return false; }

            @Override
            public boolean delete (Transaction transaction){
                if (checkIfExitsById(transaction.getId())) {
                    try (Connection connection = DBCONNECTOR.getConnection()) {
                        String query = "DELETE FROM bankapp.transaction WHERE id=?";
                        try (PreparedStatement statement = connection.prepareStatement(query)) {
                            statement.setLong(1, transaction.getId());
                            statement.executeUpdate();
                            return true;
                        }
                    } catch (SQLException E) {
                        return false;
                    }
                }
                return false;
            }
            private boolean checkIfExitsById (Long id){
                Optional<Transaction> accountOptional = this.get(id);
                return accountOptional.isPresent();
            }
}
