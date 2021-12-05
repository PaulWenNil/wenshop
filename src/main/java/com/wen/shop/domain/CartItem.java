package com.wen.shop.domain;

//购物项
public class CartItem {
    //商品对象
    private Product product;

    //小计
    private Double subtotal;

    //数量
    private Integer count;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getSubtotal() {
        return product.getShop_price()*count;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public CartItem(Product product, Integer count) {
        super();
        this.product = product;
        this.count = count;
    }
}
