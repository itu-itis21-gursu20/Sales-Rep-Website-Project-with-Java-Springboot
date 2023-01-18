// BERKAY GÜRSU - STAJCELL 2022
package com.sale.Project.repository;

import com.sale.Project.model.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {

    List<Consumer> findByName(String name);
    List<Consumer> findByIdEquals(Long id);

    List<Consumer> findBySalesRep_IdEquals(Long id);

    List<Consumer> findBySalesRep_IdEqualsAndNameContains(Long id, String name);

}