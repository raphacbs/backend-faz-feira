package com.coelho.fazfeira.service;

import com.coelho.fazfeira.base.BusinessCode;
import com.coelho.fazfeira.config.JwtGenerator;
import com.coelho.fazfeira.dto.TokenDto;
import com.coelho.fazfeira.dto.UserDto;
import com.coelho.fazfeira.dto.UserInfo;
import com.coelho.fazfeira.dto.UserRequest;
import com.coelho.fazfeira.excepitonhandler.BusinessException;
import com.coelho.fazfeira.excepitonhandler.UserNotAuthException;
import com.coelho.fazfeira.excepitonhandler.UserNotFoundException;
import com.coelho.fazfeira.model.Role;
import com.coelho.fazfeira.model.User;
import com.coelho.fazfeira.repository.UserRepository;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintDeclarationException;
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

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(PasswordEncoder passwordEncoder,
                       JwtGenerator jwtGenerator,
                       UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.userRepository = userRepository;
    }

    public UUID getLoggedUserId() {
        Map<String, String> map = (Map<String, String>) request.getAttribute("claims");
        return UUID.fromString(Optional.ofNullable(map.get("sub")).orElseGet(() -> map.get("id")));
    }

    public UserDto save(User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(Role.USER);
        user.setActive(true);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        try {
            user = userRepository.save(user);
            return UserDto.builder()
                    .lastName(user.getLastName())
                    .firstName(user.getFirstName())
                    .id(user.getId())
                    .email(user.getEmail())
                    .build();
        } catch (DataIntegrityViolationException ex) {
            throw new BusinessException(BusinessCode.USER_ALL_READY);
        }
    }

    public Optional<TokenDto> validate(UserRequest userRequest) throws UserNotFoundException {
        logger.info("Login request by '{}'", userRequest.getEmail());
        final Optional<User> userOptional = userRepository.findByEmail(userRequest.getEmail());

        if (userOptional.isEmpty()) {
            String message = MessageFormat.format("The user '{0}' not found", userRequest.getEmail());
            logger.error(message);
            throw new UserNotFoundException(message);
        }

        if (!userOptional.get().isActive()) {
            String message = MessageFormat.format("User '{0}' is not actived", userRequest.getEmail());
            logger.error(message);
            throw new UserNotFoundException(message);
        }

        boolean isValid = passwordEncoder.matches(userRequest.getPassword(), userOptional.get().getPassword());

        if (isValid) {
            User user = userOptional.get();
            UserDto userDto = UserDto.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .build();
            logger.info("Login success by '{}'", userRequest.getEmail());

            return Optional.ofNullable(this.jwtGenerator.generateToken(userDto));
        } else {
            logger.error("Email or password is not valid");
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
