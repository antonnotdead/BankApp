package com.clevertecbank.entity;

import java.util.List;
import java.util.Optional;

public class TransactionDAO implements DAO<Transaction>{
    @Override
    public Optional<Transaction> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Transaction> getAll() {
        return null;
    }

    @Override
    public boolean create(Transaction transaction) {
        return false;
    }

    @Override
    public boolean update(Transaction transaction) {
        return false;
    }

    @Override
    public boolean delete(Transaction transaction) {
        return false;
    }
}
