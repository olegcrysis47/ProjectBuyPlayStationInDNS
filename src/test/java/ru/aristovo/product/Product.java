package ru.aristovo.product;

public class Product {

    private String nameProduct; // наименование товара
    private int price;          // цена товара
    private int count;          // количество товара

    // конструктор
    public Product(String nameProduct, int price) {
        this.nameProduct = nameProduct;
        this.price = price;
        this.count = 1; // товар кладется в корзину только ОДИН, в корзине его можно увеличить/уменьшить.
    }

    // геттеры и сеттеры
    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
