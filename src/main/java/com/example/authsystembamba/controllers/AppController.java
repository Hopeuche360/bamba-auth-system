package com.example.authsystembamba.controllers;

import com.example.authsystembamba.pojos.APIResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping("/ping")
    public APIResponse<String> ping() {
        return new APIResponse<>("success", true, "I am alive");
    }

}
