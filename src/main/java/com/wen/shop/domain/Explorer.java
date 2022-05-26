package com.wen.shop.domain;


import com.wen.shop.service.ProductService;
import com.wen.shop.service.UserService;
import com.wen.shop.service.impl.ProductServiceImpl;
import com.wen.shop.service.impl.UserServiceImpl;

public class Explorer {
    private String uid;
    private String IP;
    private String pid;

    private Long time;
    private Long period;
    private String explorer_id;

    private User user;
    private Product product;

    public User getUser(String uid) throws Exception {
        UserService userService = new UserServiceImpl();
        User user = userService.findUser(uid);
        return user;
    }

    public Product getProduct(String pid) throws Exception {
        ProductService productService = new ProductServiceImpl();
        Product product = productService.getById(pid);
        return product;
    }

    public Explorer() {
    }

    public Explorer(String uid, String IP, String pid, Long time, Long peroid, String explorer_id) {
        this.uid = uid;
        this.IP = IP;
        this.pid = pid;
        this.time = time;
        this.period = peroid;
        this.explorer_id = explorer_id;
    }

    public String getExplorer_id() {
        return explorer_id;
    }

    public void setExplorer_id(String explorer_id) {
        this.explorer_id = explorer_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getPeriod() {
        return period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }
}
