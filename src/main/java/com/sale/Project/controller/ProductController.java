// BERKAY GÜRSU - STAJCELL 2022
package com.sale.Project.controller;

import com.sale.Project.model.Consumer;
import com.sale.Project.model.Meeting;
import com.sale.Project.model.Product;
import com.sale.Project.model.ProductCatalog;
import com.sale.Project.repository.ConsumerRepository;
import com.sale.Project.repository.MeetingRepository;
import com.sale.Project.repository.ProductCatalogRepository;
import com.sale.Project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;

    private final MeetingRepository meetingRepository;

    private final ProductCatalogRepository productCatalogRepository;

    private final ConsumerRepository consumerRepository;


    @Autowired
    public ProductController(ProductRepository productRepository, MeetingRepository meetingRepository, ProductCatalogRepository productCatalogRepository, ConsumerRepository consumerRepository) {
        this.productRepository = productRepository;
        this.meetingRepository = meetingRepository;
        this.productCatalogRepository = productCatalogRepository;
        this.consumerRepository = consumerRepository;
    }


    @GetMapping("signup/{id}")
    public String showSignUpForm(@PathVariable("id") Long id, Model model) {

        Product productToAdd = new Product();
        model.addAttribute("productToAdd", productToAdd); // adding product object to model

        List<ProductCatalog> productCatalogList = productCatalogRepository.findAll(); // getting product catalog list from product repo
        model.addAttribute("productCatalogList", productCatalogList); // adding this list to model


        model.addAttribute("consumerId", id); // adding consumer id to model

        return "add-product";
    }


    @GetMapping("list")
    public String showUpdateForm(Model model, HttpServletRequest request) {

        Consumer consumer = (Consumer)request.getSession().getAttribute("consumer"); // getting consumer object from session
        model.addAttribute("consumer", consumer); // adding it to model

        return "consumer-detail";

    }


    @PostMapping("add/{id}")
    public String addProduct(@Valid Product productToAdd, BindingResult result, Model model, HttpServletRequest request,@PathVariable("id") Long id) {
        if (result.hasErrors()) {
            return "add-product";
        }

        Consumer consumer = consumerRepository.findById(id).get(); // getting consumer object by id from repo
        productToAdd.setConsumer(consumer); // setting it to product object
        productRepository.save(productToAdd); // saving to product repo

        return "redirect:/consumers/details/{id}";
    }



    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Product product = productRepository.findById(id) // getting product object by id from meeting repo
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("productToUpdate", product); // adding it to model

        List<ProductCatalog> productCatalogList = productCatalogRepository.findAll(); // getting product catalog list from repo
        model.addAttribute("productCatalogList", productCatalogList); // adding it to model

        return "update-product";
    }

    @PostMapping("update/{id}")
    public String updateProduct(@PathVariable("id") Long id, @Valid Product productToUpdate, BindingResult result,
                                Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            productToUpdate.setId(id);
            return "update-product";
        }

        Consumer consumer = (Consumer)request.getSession().getAttribute("consumer"); // getting consumer object from session
        List<Product> productList = productRepository.findByConsumer_IdEquals(consumer.getId()); // getting product list by consumer_id from product repo
        productToUpdate.setConsumer(consumer); // setting consumer to product object
        productRepository.save(productToUpdate); // saving product object to product repo

        model.addAttribute("products", productList); // adding list to model

        return "redirect:/consumers/details/{id}";

    }


    @GetMapping("delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, Model model, HttpServletRequest request) {

        Product product = productRepository.findById(id) // getting product object by id from product repo
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        productRepository.delete(product); // deleting product object from product repo

        Consumer consumer = (Consumer)request.getSession().getAttribute("consumer"); // getting consumer object from session
        List<Product> productList = productRepository.findByConsumer_IdEquals(consumer.getId()); // getting product list by consumer_id from product repo
        model.addAttribute("products", productList); // adding list to model

        return "redirect:/consumers/details/{id}";
    }
}
