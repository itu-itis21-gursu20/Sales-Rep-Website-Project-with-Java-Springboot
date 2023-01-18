// BERKAY GÜRSU - STAJCELL 2022
package com.sale.Project.service;

import com.sale.Project.model.Consumer;
import com.sale.Project.repository.ConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerService {

    @Autowired
    private ConsumerRepository repo;

    public List<Consumer> listByName(Long id, String name) {
        if (name != null) {
            return repo.findBySalesRep_IdEqualsAndNameContains(id, name);
        }
        return repo.findBySalesRep_IdEquals(id);
    }

}

