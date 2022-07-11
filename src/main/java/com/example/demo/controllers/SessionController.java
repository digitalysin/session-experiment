package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/session")
public class SessionController {
    @GetMapping(path = "/lock")
    public Mono<ResponseEntity<String>> lock() {
        return Mono.just(ResponseEntity.ok("ok"));
    }

}