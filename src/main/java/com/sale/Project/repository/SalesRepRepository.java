// BERKAY GÜRSU - STAJCELL 2022
package com.sale.Project.repository;

import com.sale.Project.model.SalesRep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalesRepRepository extends JpaRepository<SalesRep, String> {
    Optional<SalesRep> findByUsernameEqualsAndPasswordEquals(String username, String password);

    SalesRep findByIdEquals(Long id);

    SalesRep findByConsumerList_SalesRep_UsernameEquals(String username);

}