// BERKAY GÜRSU - STAJCELL 2022
package com.sale.Project.repository;

import com.sale.Project.model.Consumer;
import com.sale.Project.model.Meeting;
import com.sale.Project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByConsumer_IdEquals(Long id);

    List<Product> findByConsumer_IdEqualsAndProductCatalog_NameContains(Long id, String name);

}
