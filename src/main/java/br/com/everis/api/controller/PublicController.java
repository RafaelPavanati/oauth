package br.com.everis.api.controller;

import br.com.everis.api.model.UserEntity;
import br.com.everis.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("public")
public class PublicController {

    @Autowired
    private UserService service;

    @RequestMapping(path = "/sing-up", method = RequestMethod.POST)
    public ResponseEntity<UserEntity> register(@RequestBody UserEntity user) {
        return ResponseEntity.ok(service.create(user));
    }
}
