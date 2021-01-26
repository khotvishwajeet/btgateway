package com.bt.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mySecond")
public class SecondController {

    @GetMapping("/message")
    public String test(){
        return "Hello !!! This is Second Service";
    }

}
