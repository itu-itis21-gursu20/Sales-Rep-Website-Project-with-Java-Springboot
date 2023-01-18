// BERKAY GÜRSU - STAJCELL 2022
package com.sale.Project.service;

import com.sale.Project.model.Meeting;
import com.sale.Project.model.Product;
import com.sale.Project.repository.MeetingRepository;
import com.sale.Project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository prepo;

    public List<Product> listByName(Long id, String pkeyword) {
        if(pkeyword != null){
            return prepo.findByConsumer_IdEqualsAndProductCatalog_NameContains(id, pkeyword); // getting product list which contains that name from product repo
        }
        return prepo.findByConsumer_IdEquals(id); // if name is null, then get just product list by id from product repo
    }

}
