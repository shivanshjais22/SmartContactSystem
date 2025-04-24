package com.springsecurity;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class Serveses {

    public static void removeMessage() {
        try {
            System.out.println("Removing the message from session");
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest()
                    .getSession();
            session.removeAttribute("message");
        } catch (Exception e) {
            e.printStackTrace(); // Optional: log it for debugging
        }
    }

}


