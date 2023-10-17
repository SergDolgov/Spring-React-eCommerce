package com.letsanjoy.xsonic.service.Impl;

import com.letsanjoy.xsonic.domain.Product;
import com.letsanjoy.xsonic.domain.User;
import com.letsanjoy.xsonic.exception.ApiRequestException;
import com.letsanjoy.xsonic.repository.ProductRepository;
import com.letsanjoy.xsonic.repository.UserRepository;
import com.letsanjoy.xsonic.service.UserService;
import com.letsanjoy.xsonic.constants.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException(ErrorMessage.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    public User getUserInfo(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException(ErrorMessage.EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAllByOrderByIdAsc(pageable);
    }

    @Override
    public List<Product> getCart(List<Long> productIds) {
        return productRepository.findByIdIn(productIds);
    }

    @Override
    @Transactional
    public User updateUserInfo(String email, User user) {
        User userFromDb = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException(ErrorMessage.EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));
        userFromDb.setFirstName(user.getFirstName());
        userFromDb.setLastName(user.getLastName());
        userFromDb.setCity(user.getCity());
        userFromDb.setAddress(user.getAddress());
        userFromDb.setPhoneNumber(user.getPhoneNumber());
        userFromDb.setPostIndex(user.getPostIndex());
        return userFromDb;
    }

}
