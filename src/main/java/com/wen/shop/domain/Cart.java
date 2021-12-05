package com.wen.shop.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<String, CartItem> itemMap = new HashMap<String, CartItem>();
    private Double total=0.0;

    //获取所有的购物项
    public Collection<CartItem> getCartItems(){
        return itemMap.values();
    }

    public Map<String, CartItem> getItemMap() {
        return itemMap;
    }

    public void setItemMap(Map<String, CartItem> itemMap) {
        this.itemMap = itemMap;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    //加入购物车
    public void add2cart(CartItem item){
        //获取商品id
        String pid = item.getProduct().getPid();

        //判断购物车是否有
           //有
        if(itemMap.containsKey(pid)){
            CartItem oItem = itemMap.get(pid);//原有的购物项

            oItem.setCount(oItem.getCount()+ item.getCount());
        }
           //无
        else{
            itemMap.put(pid,item);
        }

        //修改总金额
        total += item.getSubtotal();
    }

    //从购物车移除一个购物项
    public void removeFromCart(String pid){
        //从map移除购物项
        CartItem item = itemMap.remove(pid);

        //修改总金额
        total -= item.getSubtotal();
    }

    //清空购物车
    public void clearCart(){
        //清空map
        itemMap.clear();

        //修改总金额
        total=0.0;
    }
}
