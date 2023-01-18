// BERKAY GÜRSU - STAJCELL 2022
package com.sale.Project.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private int number;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "buyDate")
    private Date buyDate;

    @Column(name = "address")
    private String address;

    @ManyToOne()
    @JoinColumn(name = "consumer_id", referencedColumnName = "id")
    private Consumer consumer;


    @ManyToOne()
    @JoinColumn(name = "product_catalog_id", referencedColumnName = "id")

    private ProductCatalog productCatalog;



    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }


    public ProductCatalog getProductCatalog() {
        return productCatalog;
    }

    public void setProductCatalog(ProductCatalog productCatalog) {
        this.productCatalog = productCatalog;
    }



    public Product(Long id, int number, Date buyDate, String address) {
        this.id = id;
        this.number = number;
        this.buyDate = buyDate;
        this.address = address;
    }

    public Product() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}

