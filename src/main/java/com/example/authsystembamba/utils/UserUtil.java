package com.example.authsystembamba.utils;

import com.example.authsystembamba.config.JwtUtils;
import com.example.authsystembamba.exceptions.UserNotFoundException;
import com.example.authsystembamba.exceptions.UserWithEmailAlreadyExistException;
import com.example.authsystembamba.models.MyUserDetails;
import com.example.authsystembamba.models.User;
import com.example.authsystembamba.pojos.HttpResponse;
import com.example.authsystembamba.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.authsystembamba.utils.Constants.*;


@Slf4j
@Component
@RequiredArgsConstructor
public class UserUtil {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
//    private final EmailService emailService;

    public static ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        HttpResponse httpResponse =
                new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase(), message);
        return new ResponseEntity<>(httpResponse, httpStatus);
    }

    public void checkIfEmailAlreadyExist(String email) {
        Optional<User> existingUser = userRepository.findUserByEmail(email);
        if (existingUser.isPresent())
            throw new UserWithEmailAlreadyExistException(USER_ALREADY_EXIST);
    }

    public String getAuthenticatedUserEmail() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }

    public User getUserWithEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }



    public void sendVerificationEmail(User user) {//throws MessagingException {
        String content = "Thank you for signing up to ServByte, " +
                "please click on the link below to activate your account : \n" +
                BASE_URL + "api/v1/user/account-verification/";
        UserDetails userDetails = new MyUserDetails(user);
        String token = jwtUtils.generateToken(userDetails);
//        MailDto mailDto = new MailDto();
//        mailDto.setTo(user.getEmail());
//        mailDto.setSubject("Account Verification");
//        mailDto.setBody(content + token);
//        emailService.sendMail(mailDto);
    }

}
