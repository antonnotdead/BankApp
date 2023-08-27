package com.clevertecbank.entity;


import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    Optional<T> get(long id);
    List<T> getAll();
    boolean create(T t);
    boolean update(T t);
    boolean delete(T t);
}
