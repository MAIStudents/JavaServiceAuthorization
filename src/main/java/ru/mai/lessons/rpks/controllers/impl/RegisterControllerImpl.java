package ru.mai.lessons.rpks.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.mai.lessons.rpks.controllers.RegisterController;
import ru.mai.lessons.rpks.dto.response.TokenResponse;

@RestController
@RequiredArgsConstructor
public class RegisterControllerImpl implements RegisterController {

  //TODO inject RegisterService...

  @Override
  @GetMapping("/register")
  @ResponseStatus(HttpStatus.OK)
  public TokenResponse register(@RequestParam("username") String username) {
    //TODO code here...
    return new TokenResponse();
  }
}
