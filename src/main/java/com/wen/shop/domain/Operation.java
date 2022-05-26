package com.wen.shop.domain;


import com.wen.shop.service.SellerService;
import com.wen.shop.service.impl.SellerServiceImpl;

public class Operation {
    private String sid;
    private String detail;
    private Long time;

    private String operation_id;
    private Seller seller;

    public Operation() {
    }

    public Operation(String sid, String detail, Long time, String operation_id) {
        this.sid = sid;
        this.detail = detail;
        this.time = time;
        this.operation_id = operation_id;
    }

    public Seller getSeller(String sid) throws Exception {
        SellerService sellerService = new SellerServiceImpl();
        return sellerService.findSeller(sid);
    }

    public String getOperation_id() {
        return operation_id;
    }

    public void setOperation_id(String operation_id) {
        this.operation_id = operation_id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
