package com.wen.shop.dao.impl;

import com.wen.shop.dao.OperationDao;
import com.wen.shop.domain.Operation;
import com.wen.shop.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

public class OperationDaoImpl implements OperationDao {
    @Override
    public List<Operation> findAllOperation() throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from operation order by `time` DESC";
        return qr.query(sql, new BeanListHandler<>(Operation.class));
    }

    @Override
    public void insert(Operation operation) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into operation values(?,?,?,?)";
        qr.update(sql, operation.getOperation_id(), operation.getSid(),
                operation.getTime(), operation.getDetail());
    }
}
