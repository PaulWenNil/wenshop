package com.wen.shop.dao.impl;

import com.wen.shop.dao.ExplorerDao;
import com.wen.shop.domain.Explorer;
import com.wen.shop.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

public class ExplorerDaoImpl implements ExplorerDao {

    @Override
    public void insert(Explorer explorer) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into explorer_record values(?,?,?,?,?,?)";
        qr.update(sql, explorer.getUid(), explorer.getPid(), explorer.getTime(), explorer.getIP(),
                explorer.getPeriod(),explorer.getExplorer_id());
    }

    @Override
    public void update(Long period, String explorer_id) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update explorer_record set period = ? + period where explorer_id = ?";
        qr.update(sql, period, explorer_id);
    }

    @Override
    public List<Explorer> findAllExplorer() throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from explorer_record order by `time` DESC";
        return qr.query(sql, new BeanListHandler<>(Explorer.class));
    }
}
