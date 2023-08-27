package com.clevertecbank.entity;

import com.clevertecbank.jdbc.DBconnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO implements DAO<User>{
    private static UserDAO instance = null;
    private final DBconnector DBCONNECTOR = DBconnector.getInstance();

    public static UserDAO getInstance() {
        UserDAO localInstance = instance;
        if (localInstance == null) {
            synchronized (UserDAO.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new UserDAO();
                }
            }
        }
        return localInstance;
    }
    public Optional<User> get(long id){
        try (Connection connection = DBCONNECTOR.getConnection()){
            String query = "SELECT * FROM bankapp.user_data WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()){
                    while (resultSet.next()){
                        User user = User.builder().id(resultSet.getLong("id"))
                                .name(resultSet.getString("name"))
                                .surname(resultSet.getString("surname"))
                                .patronymic(resultSet.getString("patronymic"))
                                .build();
                        return Optional.of(user);
                    }
                }

            }
        }catch (SQLException e){
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        List<User>  userList = new ArrayList<>();
        try(Connection connection = DBCONNECTOR.getConnection()) {
            String query = "SELECT * FROM bankapp.user_data";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                try (ResultSet resultSet = statement.executeQuery()){
                    while (resultSet.next()){
                        User user = User.builder().id(resultSet.getLong("id"))
                                .name(resultSet.getString("name"))
                                .surname(resultSet.getString("surname"))
                                .patronymic(resultSet.getString("patronymic"))
                                .build();
                        userList.add(user);
                    }
                }

            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean create(User user) {
        try(Connection connection = DBCONNECTOR.getConnection()) {
            String query = "INSERT INTO bankapp.user_data(name, surname, patronymic) VALUES (?,?,?,?)";
            try(PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, user.getName());
                statement.setString(2, user.getSurname());
                statement.setString(3, user.getPatronymic());
                statement.executeUpdate();
                return true;
            }
        }catch (SQLException e){
            return false;
        }
    }

    @Override
    public boolean update(User user) {
        if (checkIfExixtsById(user.getId())){
            User olduser = this.get(user.getId()).get();
            try (Connection connection = DBCONNECTOR.getConnection()){
                String query = "UPDATE bankapp.user_data SET name=?, surname=?, patronymic=?";
                try (PreparedStatement statement = connection.prepareStatement(query)){
                    statement.setString(1, user.getName());
                    statement.setString(2, user.getSurname());
                    statement.setString(3, user.getPatronymic());
                    statement.executeUpdate();
                }

            } catch (SQLException e){
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean delete(User user) {
        if (checkIfExixtsById(user.getId())) {
            try (Connection connection = DBCONNECTOR.getConnection()){
                String query = "DELETE FROM bankapp.user_data WHERE id=?";
                try(PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setLong(1, user.getId());
                    statement.executeUpdate();
                    return true;
                }
            }catch (SQLException e){
                return false;
            }
        }
            return false;
    }

    private boolean checkIfExixtsById(Long id){
        Optional<User> userOptional = this.get(id);
        return userOptional.isPresent();
    }
}
