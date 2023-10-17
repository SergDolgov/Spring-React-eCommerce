package com.letsanjoy.xsonic.service;

import com.letsanjoy.xsonic.domain.Product;
import com.letsanjoy.xsonic.domain.User;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User getUserById(Long userId);

    User getUserInfo(String email);
    
    Page<User> getAllUsers(Pageable pageable);

    List<Product> getCart(List<Long> productIds);

    User updateUserInfo(String email, User user);

}
