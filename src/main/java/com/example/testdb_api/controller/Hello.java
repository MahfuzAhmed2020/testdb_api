package com.example.testdb_api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/HI")
public class Hello {
    // POST endpoint
    // Full URL: http://localhost:8080/HI/hello4
    @PostMapping("/hello4")
    public Map<String, Object> sayHello(@RequestBody Map<String, Object> payload) {

        Map<String, Object> response = new HashMap<>();
        response.put("received", payload); // keeps all keys and values
        response.put("message", "This message is from hello4 POST endpoint");

        return response; // Spring Boot automatically returns JSON
    }

    // GET endpoint
    // Example:
    // http://localhost:8080/HI/hello3?name=aaaaaaa&city=Buffalo
    @GetMapping("/hello3")
    public Map<String, Object> getSsayHello(@RequestParam Map<String, String> allParams) {

        Map<String, Object> response = new HashMap<>();
        response.put("received", allParams); // keeps key names as they are
        response.put("message", "Here are your query parameters");

        return response; // Spring Boot automatically converts to JSON
    }

    // @GetMapping("/hello")
    // public HashMap<String, Object> sayHello() {

    // HashMap<String, Object> newPayload = new HashMap<>();

    // HashMap<String, Object> arr = new HashMap<>();
    // HashMap<String, String> response = new HashMap<>();
    // response.put("message", "Hello, World!");
    // response.put("user", "John Doe");
    // response.put("phone", "1234567890");

    // HashMap<String, String> response2 = new HashMap<>();
    // response2.put("message2", "Hello, World!");
    // response2.put("user2", "John Doe");
    // response2.put("phone2", "1234567890");

    // newPayload.put("response", response);
    // newPayload.put("response2", response2);

    // arr.put("arr", newPayload);

    // //return response.get("message");
    // //return newPayload.get("response").toString();
    // return newPayload;
    // //return "Hello, World!";

    // //return arr;
    // }
}
