// BERKAY GÜRSU - STAJCELL 2022
package com.sale.Project.service;

import com.sale.Project.model.Consumer;
import com.sale.Project.model.Meeting;
import com.sale.Project.repository.ConsumerRepository;
import com.sale.Project.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository mrepo;

    public List<Meeting> listByName(Long id, String mkeyword) {
        if(mkeyword != null){
            return mrepo.findByConsumer_IdEqualsAndNameContains(id, mkeyword); // getting meeting list which contains that name from meeting repo
        }
        return mrepo.findByConsumer_IdEquals(id); // if name is null, then get just meeting list by id from meeting repo
    }

}
