// BERKAY GÜRSU - STAJCELL 2022
package com.sale.Project.repository;

import com.sale.Project.model.Product;
import com.sale.Project.model.ProductCatalog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCatalogRepository extends JpaRepository<ProductCatalog, Long> {
    List<ProductCatalog> findAll();
}
