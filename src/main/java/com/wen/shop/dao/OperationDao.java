package com.wen.shop.dao;

import com.wen.shop.domain.Operation;

import java.util.List;

public interface OperationDao {
    List<Operation> findAllOperation() throws Exception;

    void insert(Operation operation) throws Exception;
}
