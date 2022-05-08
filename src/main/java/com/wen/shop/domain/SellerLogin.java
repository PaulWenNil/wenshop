package com.wen.shop.domain;

import com.wen.shop.service.SellerService;
import com.wen.shop.service.impl.SellerServiceImpl;

public class SellerLogin {
    private String sid;
    private String IP;
    private String OS;

    private String explorer;
    private String clientType;
    private Long time;

    private int status;
    private Seller seller;

    public Seller getSeller(String sid) throws Exception {
        SellerService sellerService = new SellerServiceImpl();
        return sellerService.findSeller(sid);
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public String getExplorer() {
        return explorer;
    }

    public void setExplorer(String explorer) {
        this.explorer = explorer;
    }

}
