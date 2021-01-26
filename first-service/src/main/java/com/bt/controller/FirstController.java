package com.bt.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/myfirst")
public class FirstController {

	@GetMapping("/message")
	public String test() {
		return "Hello !!! This is First Service";
	}
}
