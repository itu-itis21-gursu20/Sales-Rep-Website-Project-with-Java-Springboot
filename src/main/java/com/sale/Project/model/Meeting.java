// BERKAY GÜRSU - STAJCELL 2022
package com.sale.Project.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "meetings")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name; // görüşme adı

    @DateTimeFormat(pattern = "dd/MM/yyyy") // ???
    @Column(name = "date")
    private Date date;

    @Column(name = "type")
    private String type;

    @Column(name = "address")
    private String address;


    @ManyToOne
    @JoinColumn(name="consumer_id" , referencedColumnName = "id")


    private Consumer consumer;

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public Meeting(Long id, String name, Date date, String type, String address) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.type = type;
        this.address = address;
    }

    public Meeting() {

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
