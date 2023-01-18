// BERKAY GÜRSU - STAJCELL 2022
package com.sale.Project.model;

import com.sale.Project.repository.ConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "consumers")
public class Consumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @NotBlank(message = "Name is mandatory")
    @Column(name = "name")
    private String name;

    //    @NotBlank(message = "Surname is mandatory")
    @Column(name = "surname")
    private String surname;

    @Column(name = "phone_no")
    private long phoneNo;

    @Column(name = "address")
    private String address;

    @OneToMany
    private List<Product> products;

    @OneToMany
    private List<Meeting> meetings;

    @ManyToOne
    @JoinColumn(name = "sales_rep_id", referencedColumnName = "id")
    private SalesRep salesRep;

    public SalesRep getSalesRep() {
        return salesRep;
    }

    public void setSalesRep(SalesRep salesRep) {
        this.salesRep = salesRep;
    }


    public Consumer(Long id, String name, String surname, long phoneNo, String address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNo = phoneNo;
        this.address = address;
    }

    public Consumer() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
