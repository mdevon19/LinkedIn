package com.ex.service;

import com.ex.dao.HibDao;
import com.ex.models.Category;
import com.ex.models.Post;
import com.ex.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserService {

    private HibDao dao;

    @Autowired
    public UserService(HibDao dao){
        this.dao = dao;
    }

    public List<User> getAllUsers() {
        return dao.getAllUsers();
    }

    public User getUser(String username) {
        return dao.getExistingUser(username);
    }


    public User getNewUser(User u) {
        if(dao.checkCreds(u.getUsername())){
            u = dao.addNewUser(u);
            return u;
        }else{
            return new User();
        }

    }

    public boolean deleteUser(String username){
        if(dao.deleteUser(username) >= 1){
                return true;
            }
            else{
                return false;
            }
    }

    public User applyUser(String u, int p){
        User user = dao.userApply(u,p);

        return user;

    }

    public User addCategory(int id, String u){
        Category c = dao.getCategoryById(id);
        User user = dao.getExistingUser(u);

        return dao.addCategoryForUser(c,user);

    }

    public User addPost(String u, Post p){
        User user = dao.getExistingUser(u);

        return dao.addPostForUser(user,p);
    }

    public User checkCreds(String username, String password) {
        if(dao.checkCreds(username,password)){
            return dao.getExistingUser(username);
        }
        else{
            throw new EntityNotFoundException();
        }
    }

    public User deleteApply(String u, Post p) {

        User user = dao.getExistingUser(u);

        return dao.deleteApplied(user, p);
    }
}
