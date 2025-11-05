package ru.hogwarts.school_1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
// создан контроллер InfoController
    @GetMapping("/port")
    public String getPort() {
        int port = System.getenv().containsKey("SERVER_PORT") ? Integer.parseInt(System.getenv("SERVER_PORT")) : 8080;
        return "Порт приложения: " + port;
    }
}