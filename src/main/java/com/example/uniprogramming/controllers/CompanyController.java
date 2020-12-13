package com.example.uniprogramming.controllers;

import com.example.uniprogramming.models.Company;
import com.example.uniprogramming.models.User;
import com.example.uniprogramming.services.CompanyService;
import com.example.uniprogramming.services.UserService;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {


        private final CompanyService companyService;
        private final UserService userService;

        @Autowired
        public CompanyController(CompanyService companyService, UserService userService) {
            this.companyService = companyService; this.userService = userService;
        }


        @RequestMapping(value = "", method = RequestMethod.GET)
        public List<Company> getPage(
                @RequestParam("page") Optional<Integer> page,
                @RequestParam("size") Optional<Integer> size) {
            int currentPage = page.orElse(1);
            int pageSize = size.orElse(10);

            return companyService.getPage(currentPage, pageSize);
        }

        @RequestMapping(value = "/all", method = RequestMethod.GET)
        public List<Company> getAll(@RequestParam("user") Optional<Integer> userId) {
            if(userId.isPresent()){
                User user = userService.getUser(userId.get());
                    return companyService.getByUser(user);
            }
            return companyService.getAll();
        }

        @RequestMapping(value = "/{id}", method = RequestMethod.GET)
        public Company getCompany(@PathVariable int id) {
            return companyService.getCompany(id);
        }


        @RequestMapping(value = "/{id}/delete", method = RequestMethod.PUT)
        public Company deleteCompany(@PathVariable int id) {
            User logged = userService.getLoggedUser();
            Company company = companyService.getCompany(id);
            if (id < 4) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!");
            }
            if (!logged.getRole().getName().equals("ROLE_ADMIN") && logged.getId() != company.getUser().getId()){

                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't do that!");
            }
            return companyService.deleteCompany(id);
        }

        @RequestMapping(value = "/add", method = RequestMethod.POST)
        public Company addCompany(@RequestBody Company company) {
            if(companyService.getCompany(company.getName()).isPresent()){
                throw new ResponseStatusException(HttpStatus.FOUND, "Company Already exists");
            }
            return companyService.saveCompany(company);
        }

        @RequestMapping(value = "/{id}/setindustry", method = RequestMethod.PUT)
        public Company setRoles(@RequestBody TextNode industry, @PathVariable int id) {
            User logged = userService.getLoggedUser();
            if (!logged.getRole().getName().equals("ROLE_ADMIN")) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't do that!");
            }
            return companyService.setIndustry(id, industry.asText());
        }

        @RequestMapping(value = "/{id}/modify", method = RequestMethod.PUT)
        public Company modifyCompany(@RequestBody Company company, @PathVariable int id) {
            User logged = userService.getLoggedUser();
            if (id < 4) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!");
            }
            if (!logged.getRole().getName().equals("ROLE_ADMIN") && logged.getId() != company.getUser().getId()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't do that!");
            }
            if(companyService.companyExists(company.getName()) && company.getId() != id){
                throw new ResponseStatusException(HttpStatus.FOUND, "Company Already exists");
            }
            return companyService.saveCompany(company);
        }

        @RequestMapping(value = "/count", method = RequestMethod.GET)
        public long count() {
            return companyService.count();
        }



    }


