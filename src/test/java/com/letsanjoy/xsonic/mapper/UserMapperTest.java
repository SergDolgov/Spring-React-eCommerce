package com.letsanjoy.xsonic.mapper;

import com.letsanjoy.xsonic.domain.Review;
import com.letsanjoy.xsonic.domain.User;
import com.letsanjoy.xsonic.dto.RegistrationRequest;
import com.letsanjoy.xsonic.dto.review.ReviewRequest;
import com.letsanjoy.xsonic.dto.user.UpdateUserRequest;
import com.letsanjoy.xsonic.dto.user.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.letsanjoy.xsonic.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserMapperTest {

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void convertUserRequestDtoToEntity() {
        UpdateUserRequest userRequest = new UpdateUserRequest();
        userRequest.setFirstName(FIRST_NAME);

        User user = modelMapper.map(userRequest, User.class);
        assertEquals(userRequest.getFirstName(), user.getFirstName());
    }

    @Test
    public void convertRegistrationRequestDtoToEntity() {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setFirstName(FIRST_NAME);
        registrationRequest.setEmail(USER_EMAIL);
        registrationRequest.setPassword(USER_PASSWORD);

        User user = modelMapper.map(registrationRequest, User.class);
        assertEquals(registrationRequest.getFirstName(), user.getFirstName());
        assertEquals(registrationRequest.getEmail(), user.getEmail());
        assertEquals(registrationRequest.getPassword(), user.getPassword());
    }

    @Test
    public void convertReviewToEntity() {
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setAuthor(FIRST_NAME);
        reviewRequest.setMessage("Hello World!");

        Review review = modelMapper.map(reviewRequest, Review.class);
        assertEquals(reviewRequest.getAuthor(), review.getAuthor());
        assertEquals(reviewRequest.getMessage(), review.getMessage());
    }

    @Test
    public void convertToResponseDto() {
        User user = new User();
        user.setFirstName(FIRST_NAME);
        user.setEmail(USER_EMAIL);

        UserResponse userRequestDto = modelMapper.map(user, UserResponse.class);
        assertEquals(user.getFirstName(), userRequestDto.getFirstName());
        assertEquals(user.getEmail(), userRequestDto.getEmail());
    }
}
