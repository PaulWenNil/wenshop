package com.wen.shop.service.impl;

import com.wen.shop.constant.Constant;
import com.wen.shop.dao.UserDao;
import com.wen.shop.dao.impl.UserDaoImpl;
import com.wen.shop.domain.User;
import com.wen.shop.service.UserService;
import com.wen.shop.utils.MailUtils;

public class UserServiceImpl implements UserService{
    //用户注册
    public void regist(User user) throws Exception{
        //调用DAO完成注册
        UserDao ud = new UserDaoImpl();
        ud.save(user);

        //发送激活邮件
        String emailMsg = "欢迎"+user.getName()+"成为商城的一员,<a href='http://120.79.99.56:8080/shop/user?method=active&code="+user.getCode()+"'>点此激活</a>";
        MailUtils.sendMail(user.getEmail(), emailMsg);
    }

    //用户激活
    public User active(String code) throws Exception {
        UserDao ud = new UserDaoImpl();
        //通过code获取用户
        User user = ud.getByCode(code);

        //通过激活码没找到用户
        if(user == null){
            return null;
        }
        //若获取到了修改用户
        user.setState(Constant.USER_IS_ACTIVE);
        user.setCode(null);

        ud.update(user);
        return user;
    }

    //用户登录
    public User login(String username, String password) throws Exception {
        UserDao ud = new UserDaoImpl();

        return ud.getByUsernameAndPwd(username,password);
    }

    //查找用户
    public User findUser(String uid) throws Exception {
        UserDao ud = new UserDaoImpl();
        return ud.findUser(uid);
    }

}