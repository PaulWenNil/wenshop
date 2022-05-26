package com.wen.shop.dao;

import com.wen.shop.domain.Explorer;

import java.util.List;

public interface ExplorerDao {

    void insert(Explorer explorer) throws Exception;

    void update(Long period, String explorer_id) throws Exception;


    List<Explorer> findAllExplorer() throws Exception;
}
