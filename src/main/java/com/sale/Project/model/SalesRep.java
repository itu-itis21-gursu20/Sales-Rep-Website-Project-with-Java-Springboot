// BERKAY GÜRSU - STAJCELL 2022
package com.sale.Project.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class SalesRep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @OneToMany
    private List<Consumer> consumerList;

    public SalesRep(Long id, String username, String password, List<Consumer> consumerList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.consumerList = consumerList;
    }

    public SalesRep() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
