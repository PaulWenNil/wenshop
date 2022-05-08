package com.wen.shop.domain;


import com.wen.shop.service.UserService;
import com.wen.shop.service.impl.UserServiceImpl;

public class UserLogin {

    private User user;
    private String IP;
    private String OS;

    private String explorer;
    private String clientType;
    private Long time;

    private int status;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public User getUser(String uid) throws Exception {
        UserService userService = new UserServiceImpl();
        return userService.findUser(uid);
    }

    public void setUser(User user) {
        this.user = user;
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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
