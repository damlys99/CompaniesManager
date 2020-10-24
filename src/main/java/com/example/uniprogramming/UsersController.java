package com.example.uniprogramming;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

    @RestController
    @RequestMapping("/api/users")
    public class UsersController {
        UsersRepository usersRepository;

        @Autowired
        public UsersController(UsersRepository usersRepository){
            this.usersRepository = usersRepository;
        }

        @RequestMapping(value = "", method = RequestMethod.GET)
        public List<User> getPage(
                @RequestParam("page") Optional<Integer> page,
                @RequestParam("size") Optional<Integer> size){
            int currentPage = page.orElse(1);
            int pageSize = size.orElse(10);

            Pageable userPage = PageRequest.of(currentPage-1,  pageSize);
            return usersRepository.findAllByIsDeletedIsFalse(userPage);

        }

        @RequestMapping(value = "/all", method = RequestMethod.GET)
        public List<User> getAll(){
            return usersRepository.findAllByIsDeletedIsFalse();
        }


        @RequestMapping(value = "/delete/{idToDelete}", method = RequestMethod.PUT)
        public List<User> delUser(@PathVariable int idToDelete){
            User userGettingDeleted = usersRepository.findById(idToDelete);
            userGettingDeleted.delete();
            usersRepository.save(userGettingDeleted);
            return usersRepository.findAllByIsDeletedIsFalse();
        }

        @RequestMapping(value = "/add", method = RequestMethod.POST)
        public List<User> addUser(@RequestBody User user){
            usersRepository.save(user);

            return usersRepository.findAllByIsDeletedIsFalse();
        }

        @RequestMapping(value = "/count", method = RequestMethod.GET)
        public long getCount(){
            return usersRepository.countByIsDeletedIsFalse();
        }

}
