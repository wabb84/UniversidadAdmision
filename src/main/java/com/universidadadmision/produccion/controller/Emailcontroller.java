package com.universidadadmision.produccion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.universidadadmision.produccion.components.EmailService;

import jakarta.mail.MessagingException;

@RestController
public class Emailcontroller {
	@Autowired
    private EmailService emailService;

    @GetMapping("/send-html-email")
    public String sendHtmlEmail(
            @RequestParam String to, 
            @RequestParam String subject, 
            @RequestParam String body) {
        try {
            emailService.sendHtmlEmail(to, subject, body);
            return "Email HTML enviado con éxito!";
        } catch (MessagingException e) {
            return "Error al enviar el correo: " + e.getMessage();
        }
    }
}
