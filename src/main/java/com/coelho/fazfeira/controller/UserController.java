package com.coelho.fazfeira.controller;


import com.coelho.fazfeira.dto.TokenDto;
import com.coelho.fazfeira.dto.UserDto;
import com.coelho.fazfeira.dto.UserInfo;
import com.coelho.fazfeira.dto.UserRequest;
import com.coelho.fazfeira.excepitonhandler.UserNotAuthException;
import com.coelho.fazfeira.excepitonhandler.UserNotFoundException;
import com.coelho.fazfeira.model.User;
import com.coelho.fazfeira.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/v1/user")
@Tag(name = "User operations")
public class UserController {
    private UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> postUser(@RequestBody User user) {
        UserDto userDto = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserRequest user) {
        try {
            if (user.getEmail() == null || user.getPassword() == null) {
                throw new UserNotFoundException("UserName or Password is Empty");
            }
            final Optional<TokenDto> tokenDtoOptional = this.userService.validate(user);

            if (tokenDtoOptional.isPresent()) {
                return new ResponseEntity<>(tokenDtoOptional.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new UserNotFoundException("error"), HttpStatus.UNAUTHORIZED);
            }

        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Operation(description = "Do login with google")
    @GetMapping("/login/google")
    public ResponseEntity<UserInfo> loginUserGoogle(@RequestHeader("token") String token) throws UserNotAuthException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.authGoogle(token));
    }

    @GetMapping("/login/facebook")
    public ResponseEntity<UserInfo> loginUserFacebook(@RequestHeader("token") String token) throws UserNotAuthException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.authFacebook(token));
    }
}
