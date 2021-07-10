package com.jimmy.jimmyhomepage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;


@Controller
@SpringBootApplication

public class JimmyHomePageApplication {

    public static void main(String[] args) {
        SpringApplication.run(JimmyHomePageApplication.class, args);
    }

    @Autowired
    CommentRepository commentRepository;

    @GetMapping("/highschool")
    public String highschool (){
        return "Highschool";
    }


    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @PostMapping("/springPrj/add")
    public String AddComments(@ModelAttribute Comment comment, Model model) {
        comment = commentRepository.save(comment);
        List<Comment> comments = commentRepository.findAll();
        model.addAttribute("commentsList", comments);
        return "springPrj";
    }

    @GetMapping("/home")
    public String toHomeView() {
        return "index";
    }

    @GetMapping("/comment")
    public String toCommentView(Model model) {
        model.addAttribute("comment", new Comment());
        List<Comment> comments = commentRepository.findAll();
        model.addAttribute("commentsList", comments);
        return "springPrj";
    }
}