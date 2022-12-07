package com.example.authsystembamba.services.serviceImpl;

import com.example.authsystembamba.enums.Role;
import com.example.authsystembamba.models.User;
import com.example.authsystembamba.pojos.SignupRequest;
import com.example.authsystembamba.repositories.UserRepository;
import com.example.authsystembamba.services.AuthService;
import com.example.authsystembamba.utils.Constants;
import com.example.authsystembamba.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.example.authsystembamba.enums.Role.ROLE_USER;
import static com.example.authsystembamba.utils.Constants.REGISTRATION_SUCCESSFUL;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserUtil userUtil;
    private final ModelMapper mapper;
    private final UserRepository userRepository;

    @Override
    public String signup(SignupRequest request) {
        userUtil.checkIfEmailAlreadyExist(request.getEmail());
        User user = mapper.map(request, User.class);
        user.setRole(ROLE_USER);
        userRepository.save(user);
        //TODO: SEND VERIFICATION EMAIL
        return REGISTRATION_SUCCESSFUL;
    }
}
