package com.example.contentcashingrequest.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("main")
public class MainController {

    @GetMapping(path = "")
    public Map<String, Object> main() {
        return Collections.singletonMap("result", true);

    }

}
