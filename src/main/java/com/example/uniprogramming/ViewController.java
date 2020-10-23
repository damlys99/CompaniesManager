package com.example.uniprogramming;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ViewController {
    UsersRepository usersRepository;

    @Autowired
    public ViewController(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    @RequestMapping(value="/users")
    public String index(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size){
                int currentPage = page.orElse(1);
                int pageSize = size.orElse(10);

                Pageable userPage = PageRequest.of(currentPage-1,  pageSize);
                model.addAttribute("users", usersRepository.findAllByIsDeletedIsFalse(userPage));

                return "users";
    }
}
