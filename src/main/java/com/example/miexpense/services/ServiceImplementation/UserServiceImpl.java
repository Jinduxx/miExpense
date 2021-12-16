package com.example.miexpense.services.ServiceImplementation;

import com.example.miexpense.model.User;
import com.example.miexpense.repository.UserRepo;
import com.example.miexpense.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean createUser(User user) {
        boolean result;
        User userInfo = userRepo.findPersonByEmail(user.getEmail());
        if(userInfo == null){
            System.out.println(user);
            userRepo.save(user);
            result = true;
        } else result = false;
        return  result;
    }

    @Override
    public User getUser(String email, String password) {
        User userInfo;
        userInfo = userRepo.findPersonByEmail(email);
        if(userInfo == null){
            userInfo = null;
        } else{
            if(!password.equals(userInfo.getPassword())){
                userInfo = null;
            }
        }


        return userInfo;
    }

    @Override
    public User deleteUser(String email) {
        User user  = userRepo.findPersonByEmail(email);
        User deletedUser = null;
        if(user != null){
            deletedUser = userRepo.deleteUserByEmail(email);
        }
        return deletedUser;
    }

    @Override
    public User findUser(Long userId) {
        return userRepo.findUserById(userId);
    }
}
