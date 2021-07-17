package com.jimmy.jimmyhomepage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@Controller
@SpringBootApplication
@Slf4j
public class JimmyHomePageApplication {

    public static void main(String[] args) {
        SpringApplication.run(JimmyHomePageApplication.class, args);
    }

    @Autowired
    CommentRepository commentRepository;

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

//    @DeleteMapping("/{id}")
//    public ResponseEntity deleteComment(@PathVariable Long id){
//        Comment comment = commentRepository.findById(id).orElse(null);
//        if(comment == null){
//            return new ResponseEntity("Comment with id " + id + " does not exist.", HttpStatus.BAD_REQUEST);
//        }
//        commentRepository.delete(comment);
//        return new ResponseEntity("Deleted Comment " + id + " successful", HttpStatus.OK);
//    }
    @PostMapping("/springPrj/add")
    public String AddComments(@ModelAttribute Comment comment, Model model) {
        // need to create a new comment for different reference object then comment parameter
        Comment newComment = new Comment();
        newComment.setName(comment.getName());
        newComment.setEmail(comment.getEmail());
        newComment.setCommentValue(comment.getCommentValue());
        newComment = commentRepository.save(newComment);

        // refresh the input field
        model.addAttribute("comment", new Comment());
        List<Comment> comments = commentRepository.findAll();
        model.addAttribute("commentsList", comments);
//        log.info("added successful: " + Arrays.toString(comments.toArray()));
        return "springPrj";
    }

    @GetMapping("/delete")
    public String deleteComment(@RequestParam(name="id") String commentId, Model model){
        Long id = Long.parseLong(commentId);
        Comment comment = commentRepository.findById(id).orElse(null);
        log.info("delete getmapping: " + comment.toString());

        String result = null;
        if(comment == null){
            result = "Delete Failed";
            model.addAttribute("result", result);
            return "springPrj";
        }
        commentRepository.delete(comment);
        result = "Deleted Comment " + id + " successful";
        model.addAttribute("result", result);

        model.addAttribute("comment", new Comment());
        List<Comment> comments = commentRepository.findAll();
        model.addAttribute("commentsList", comments);
        return "springPrj";
    }
}