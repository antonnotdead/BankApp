package com.clevertecbank.entity;

import com.clevertecbank.jdbc.DBconnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        return null;
    }

    @Override
    public boolean save(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }
}
