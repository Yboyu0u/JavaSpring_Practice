package com.example.eatgo.application;

import com.example.eatgo.domain.User;
import com.example.eatgo.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class UserServiceTests {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        userService = new UserService(userRepository,passwordEncoder);
    }

    @Test
    public void authenticateWithValidAttributes(){
        String email = "test@example.com";
        String password = "test";

        User mockUser= User.builder()
                .email(email)
                .build();

        given(userRepository.findByEmail(email))
                .willReturn(Optional.of(mockUser));

        given(passwordEncoder.matches(any(),any())).willReturn(true);

        User user = userService.authenticate(email,password);

        assertThat(user.getEmail()).isEqualTo(email);
    }

    @Test(expected = EmailNotExistedException.class)
    public void authenticateWithNotExistedValidAttributes(){
        String email = "test@example.com";
        String password = "test";


        given(userRepository.findByEmail(email))
                .willReturn(Optional.empty());

        User user = userService.authenticate(email,password);

        assertThat(user.getEmail()).isEqualTo(email);
    }
    @Test(expected = PasswordWrongException.class)
    public void authenticateWithWrongPassword(){
        String email = "test@example.com";
        String password = "X";

        User mockUser= User.builder()
                .email(email)
                .build();

        given(userRepository.findByEmail(email))
                .willReturn(Optional.of(mockUser));

        given(passwordEncoder.matches(any(),any())).willReturn(false);

        User user = userService.authenticate(email,password);

        assertThat(user.getEmail()).isEqualTo(email);
    }

}