package com.coelho.fazfeira.service;

import com.coelho.fazfeira.config.JwtGenerator;
import com.coelho.fazfeira.dto.TokenDto;
import com.coelho.fazfeira.dto.UserDto;
import com.coelho.fazfeira.dto.UserInfo;
import com.coelho.fazfeira.dto.UserRequest;
import com.coelho.fazfeira.excepitonhandler.UserNotAuthException;
import com.coelho.fazfeira.excepitonhandler.UserNotFoundException;
import com.coelho.fazfeira.model.Role;
import com.coelho.fazfeira.model.User;
import com.coelho.fazfeira.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    HttpServletRequest request;

    private final PasswordEncoder passwordEncoder;
    private JwtGenerator jwtGenerator;

    private UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder,
                       JwtGenerator jwtGenerator,
                       UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.userRepository = userRepository;
    }

    public UUID getLoggedUserId() {
        Map<String, String> map = (Map<String, String>) request.getAttribute("claims");
        return UUID.fromString(Optional.ofNullable(map.get("sub")).orElseGet(()->map.get("id")));
    }

    public UserDto save(User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(Role.USER);
        user.setActive(true);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return UserDto.builder()
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }

    public Optional<TokenDto> validate(UserRequest userRequest) throws UserNotFoundException {
        final Optional<User> userOptional = userRepository.findByEmail(userRequest.getEmail());

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Invalid email user not found");
        }

        if (!userOptional.get().isActive()) {
            throw new UserNotFoundException("User is not actived");
        }

        boolean isValid = userOptional.get().isActive() && passwordEncoder.matches(userRequest.getPassword(), userOptional.get().getPassword());

        if (isValid) {
            User user = userOptional.get();
            UserDto userDto = UserDto.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .build();
            return Optional.ofNullable(this.jwtGenerator.generateToken(userDto));
        } else {
            return Optional.empty();
        }
    }

    public UserInfo authGoogle(String token) throws UserNotAuthException {
        final RestTemplate restTemplate = new RestTemplate();
        UserDto userDto = null;

        String url = "https://www.googleapis.com/userinfo/v2/me";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<UserInfo> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                UserInfo.class
        );

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new UserNotAuthException("Não foi possível realizar a autenticação via Google");
        }

        UserInfo userInfo = response.getBody();

        final Optional<User> userOptional = userRepository.findByEmail(userInfo.getEmail());

        if (userOptional.isEmpty()) {
            User userToSave = User
                    .builder()
                    .createdAt(LocalDateTime.now())
                    .role(Role.USER)
                    .isActive(true)
                    .email(userInfo.getEmail())
                    .firstName(userInfo.getGivenName())
                    .lastName(userInfo.getFamilyName())
                    .password(userInfo.getId())
                    .build();

            userDto = save(userToSave);

        } else {
            userDto = UserDto.builder()
                    .id(userOptional.get().getId())
                    .email(userOptional.get().getEmail())
                    .firstName(userOptional.get().getFirstName())
                    .lastName(userOptional.get().getLastName())
                    .build();
        }


        final TokenDto tokenDto = this.jwtGenerator.generateToken(userDto);
        userInfo.setTokenDto(tokenDto);


        return userInfo;
    }

    public UserInfo authFacebook(String token) throws UserNotAuthException {
        final RestTemplate restTemplate = new RestTemplate();
        UserDto userDto = null;

        String url = MessageFormat
                .format("https://graph.facebook.com/me?access_token={0}&fields=id,name,picture.type(large)",
                        token);

        ResponseEntity<UserInfo> response = restTemplate.getForEntity(url, UserInfo.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new UserNotAuthException("Não foi possível realizar a autenticação via Facebook");
        }

        UserInfo userInfo = response.getBody();

        final Optional<User> userOptional = userRepository.findByEmail(userInfo.getEmail());

        if (userOptional.isEmpty()) {
            User userToSave = User
                    .builder()
                    .createdAt(LocalDateTime.now())
                    .role(Role.USER)
                    .isActive(true)
                    .email(userInfo.getEmail())
                    .firstName(userInfo.getGivenName())
                    .lastName(userInfo.getFamilyName())
                    .password(userInfo.getId())
                    .build();

            userDto = save(userToSave);

        } else {
            userDto = UserDto.builder()
                    .id(userOptional.get().getId())
                    .email(userOptional.get().getEmail())
                    .firstName(userOptional.get().getFirstName())
                    .lastName(userOptional.get().getLastName())
                    .build();
        }


        final TokenDto tokenDto = this.jwtGenerator.generateToken(userDto);
        userInfo.setTokenDto(tokenDto);


        return userInfo;
    }

}
