// BERKAY GÜRSU - STAJCELL 2022
package com.sale.Project.repository;

import com.sale.Project.model.Consumer;
import com.sale.Project.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByConsumer_IdEquals(Long id); // consumera göre
    List<Meeting> findByName(String name);

    List<Meeting> findByConsumer_SalesRep_IdEquals(Long id);
    List<Meeting> findByConsumer_IdEqualsAndNameContains(Long id, String name);

}


