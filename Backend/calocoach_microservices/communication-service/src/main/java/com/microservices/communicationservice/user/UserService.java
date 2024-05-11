package com.microservices.communicationservice.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void saveUser(User user) {
        user.setStatus(Status.ONLINE);
        repository.save(user);
    }

    public void disconnect(User user) {
        var storedUser = repository.findById(user.getNickName()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(Status.OFFLINE);
            repository.save(storedUser);
        }
    }

    public List<User> findConnectedUsers() {
        return repository.findAllByStatus(Status.ONLINE);
    }

    public boolean findUserStatus(String  userNickName){
        var storedUser = repository.findById(userNickName).orElse(null);
        if (storedUser!=null){
            boolean status = storedUser.getStatus() == Status.ONLINE;
            return true;
        }else{
            return false;
        }
    }
}
