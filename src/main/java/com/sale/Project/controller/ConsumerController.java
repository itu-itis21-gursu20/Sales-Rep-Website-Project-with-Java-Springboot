// BERKAY GÜRSU - STAJCELL 2022
package com.sale.Project.controller;
import com.sale.Project.model.*;
import com.sale.Project.repository.*;
import com.sale.Project.service.ConsumerService;
import com.sale.Project.service.MeetingService;
import com.sale.Project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
@RequestMapping("/consumers/")
public class ConsumerController {

    private final ConsumerRepository consumerRepository;
    private final ProductRepository productRepository;
    private final MeetingRepository meetingRepository;

    private final ProductCatalogRepository productCatalogRepository;

    private final SalesRepRepository salesRepRepository;


    public ConsumerController(ConsumerRepository consumerRepository, ProductRepository productRepository, MeetingRepository meetingRepository, ProductCatalogRepository productCatalogRepository, SalesRepRepository salesRepRepository, ConsumerService service) {
        this.consumerRepository = consumerRepository;
        this.productRepository = productRepository;
        this.meetingRepository = meetingRepository;
        this.productCatalogRepository = productCatalogRepository;
        this.salesRepRepository = salesRepRepository;
        this.service = service;
    }

    @Autowired
    private ConsumerService service;

    @Autowired
    private MeetingService mservice;

    @Autowired
    private ProductService pservice;


    @GetMapping("signup") //
    public String showSignUpForm(Consumer consumer) {
        return "add-consumer";
    }


    @GetMapping("list")
    public String showUpdateForm(Model model, HttpServletRequest request, @Param("name") String name) {

        SalesRep salesRep = (SalesRep)request.getSession().getAttribute("salesRep"); // getting salesRep object from session

        model.addAttribute("username", salesRep.getUsername()); // adding its username to model


        List<Consumer> consumerFilterList = service.listByName(salesRep.getId(), name); // filtering consumer list by input name
        model.addAttribute("consumerFilterList", consumerFilterList); // adding consumer list to model
        model.addAttribute("name", name); // adding input name to model

        List<Meeting> meetingList = meetingRepository.findByConsumer_SalesRep_IdEquals(salesRep.getId()); // getting meetings list by salesrep_id
        model.addAttribute("meetingList", meetingList); // adding meeting list to model

        return "sale-representer-home";
    }


    @PostMapping("add")
    public String addConsumer(@Valid Consumer consumer, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "add-consumer";
        }

        SalesRep salesRep = (SalesRep)request.getSession().getAttribute("salesRep"); // getting salesRep object from session
        consumer.setSalesRep(salesRep); // setting this salesrep to consumer
        consumerRepository.save(consumer); // saving to consumer repo

        return "redirect:list";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Consumer consumer = consumerRepository.findById(id) // getting consumer object by id
                .orElseThrow(() -> new IllegalArgumentException("Invalid consumer Id:" + id));
        model.addAttribute("consumer", consumer); // adding this to model
        return "update-consumer";
    }

    @PostMapping("update/{id}")
    public String updateConsumer(@PathVariable("id") long id, @Valid Consumer consumer, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            consumer.setId(id);
            return "update-consumer";
        }

        SalesRep salesRep = (SalesRep)request.getSession().getAttribute("salesRep"); // getting salesRep object from session
        consumer.setSalesRep(salesRep); // setting this salesrep to consumer
        consumerRepository.save(consumer); // saving to consumer repo

        List<Consumer> consumerList = consumerRepository.findBySalesRep_IdEquals(salesRep.getId()); // getting consumer list by salesrep_id
        model.addAttribute("consumers", consumerList); // adding it to model

        return "redirect:/consumers/list";
    }

    @GetMapping("delete/{id}")
    public String deleteConsumer(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        Consumer consumer = consumerRepository.findById(id) // getting consumer list by id
                .orElseThrow(() -> new IllegalArgumentException("Invalid consumer Id:" + id));
        consumerRepository.delete(consumer); // deleting related consumer

        SalesRep salesRep = (SalesRep)request.getSession().getAttribute("salesRep"); // getting salesRep object from session
        List<Consumer> consumerList = consumerRepository.findBySalesRep_IdEquals(salesRep.getId()); // getting consumer list by salesrep_id
        model.addAttribute("consumers", consumerList); // adding it to model

        return "redirect:/consumers/list";
    }



    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable("id") Long id, Model model, HttpServletRequest request, @Param("mkeyword") String mkeyword, @Param("pkeyword") String pkeyword){

        if(consumerRepository.findById(id).isPresent()) { // if related consumer is available in repo

            Consumer consumer = consumerRepository.findById(id).get(); // getting this consumer by id from repo
            request.getSession().setAttribute("consumer", consumer); // setting it to session
            model.addAttribute("consumer", consumer); // adding it to model

            List<Product> productFilterList = pservice.listByName(consumer.getId(), pkeyword); // filtering product list by consumer id
            model.addAttribute("productFilterList", productFilterList); // adding list to model
            model.addAttribute("pkeyword", pkeyword); // adding keyword to model

            List<Meeting> meetingFilterList = mservice.listByName(consumer.getId(), mkeyword); // filtering meeting list by consumer id
            model.addAttribute("meetingFilterList", meetingFilterList); // adding list to model
            model.addAttribute("mkeyword", mkeyword); // adding keyword to model

        }

        return "consumer-detail";
    }
}