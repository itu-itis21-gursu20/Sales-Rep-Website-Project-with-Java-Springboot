// BERKAY GÜRSU - STAJCELL 2022
package com.sale.Project.controller;
import com.sale.Project.model.Consumer;
import com.sale.Project.model.Meeting;
import com.sale.Project.model.Product;
import com.sale.Project.repository.ConsumerRepository;
import com.sale.Project.repository.MeetingRepository;
import com.sale.Project.repository.ProductRepository;
import com.sale.Project.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/meetings")
public class MeetingController {

    private final MeetingRepository meetingRepository;
    private final ProductRepository productRepository;
    private final ConsumerRepository consumerRepository;

    @Autowired
    public MeetingController(MeetingRepository meetingRepository, ProductRepository productRepository, ConsumerRepository consumerRepository) {
        this.meetingRepository = meetingRepository;
        this.productRepository = productRepository;
        this.consumerRepository = consumerRepository;
    }

    @Autowired
    private MeetingService mservice;


    @GetMapping("signup/{id}")
    public String showSignUpForm(@PathVariable("id") Long id,  Model model) {

        Meeting meeting = new Meeting();
        model.addAttribute("meeting", meeting); // adding meeting object to model

        model.addAttribute("consumerId", id); // adding consumer id to model

        return "add-meeting";
    }


    @GetMapping("list")
    public String showUpdateForm(Model model, HttpServletRequest request) {

        Consumer consumer = (Consumer)request.getSession().getAttribute("consumer"); // getting consumer object from session
        model.addAttribute("consumer", consumer);  // adding it to model

        return "consumer-detail";
    }


    @PostMapping("add/{id}")
    public String addMeeting(@Valid Meeting meeting, BindingResult result, Model model, HttpServletRequest request,@PathVariable("id") Long id) {
        if (result.hasErrors()) {
            return "add-meeting";
        }

        Consumer consumer = consumerRepository.findById(id).get(); // getting consumer object by id from repo
        meeting.setConsumer(consumer); // setting it to meeting object
        meetingRepository.save(meeting); // saving to meeting repo

        return "redirect:/consumers/details/{id}";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Meeting meeting = meetingRepository.findById(id) // getting meeting object by id from meeting repo
                .orElseThrow(() -> new IllegalArgumentException("Invalid meeting Id:" + id));
        model.addAttribute("meeting", meeting); // adding it to model
        return "update-meeting";
    }

    @PostMapping("update/{id}")
    public String updateMeeting(@PathVariable("id") Long id, @Valid Meeting meeting, BindingResult result,
                                Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            meeting.setId(id);
            return "update-meeting";
        }


        Consumer consumer = (Consumer)request.getSession().getAttribute("consumer"); // getting consumer object from session
        List<Meeting> meetingList = meetingRepository.findByConsumer_IdEquals(consumer.getId()); // getting meeting list by consumer_id from meeting repo
        meeting.setConsumer(consumer); // setting consumer to meeting object
        meetingRepository.save(meeting); // saving meeting object to meeting repo

        model.addAttribute("meetings", meetingList); // adding list to model

        return "redirect:/consumers/details/{id}";

    }

    @GetMapping("delete/{id}")
    public String deleteMeeting(@PathVariable("id") Long id, Model model, HttpServletRequest request) {

        Meeting meeting = meetingRepository.findById(id) // getting meeting object by id from meeting repo
                .orElseThrow(() -> new IllegalArgumentException("Invalid meeting Id:" + id));
        meetingRepository.delete(meeting); // deleting meeting object from meeting repo

        Consumer consumer = (Consumer)request.getSession().getAttribute("consumer"); // getting consumer object from session
        List<Meeting> meetingList = meetingRepository.findByConsumer_IdEquals(consumer.getId()); // getting meeting list by consumer_id from meeting repo
        model.addAttribute("meetings", meetingList);  // adding list to model

        return "redirect:/consumers/details/{id}";
    }
}