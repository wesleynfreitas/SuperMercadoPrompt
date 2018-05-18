/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetomercado;

import java.util.Arrays;

/**
 *
 * @author wesleyfreitas
 */
public class Product {

    public String code;
    public String name;
    public Double price;

    public Product() {

    }

    public Product(String code) {
        this.code = code;
    }

    public Product(String code, String name, Double price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public Product findProduct() {
        Product[] array = productList();
        Product product = null;

        // Find product
        for (int i = 0; i < array.length; i++) {
            if (array[i].code.matches(this.code.toUpperCase().trim())) {
                product = array[i];
            }
        }

        return product;
    }

    private Product[] productList() {

        Product[] list = new Product[]{
            new Product("A01", "Arroz", 8.99),
            new Product("A02", "Feijão", 4.99),
            new Product("B03", "Leite", 3.19),
            new Product("A08", "Macarrão", 2.29),
            new Product("J04", "Margarina", 5.99),
            new Product("C01", "Óleo de Soja", 2.29),
            new Product("B07", "Sabonete", 3.05)
        };

        return list;
    }

}
