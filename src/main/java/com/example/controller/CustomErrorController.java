package com.example.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object requestUri = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        
        if (status != null) {
            int statusCode = Integer.valueOf(status.toString());
            model.addAttribute("errorCode", statusCode);
            model.addAttribute("requestUri", requestUri != null ? requestUri.toString() : "unknown");
            model.addAttribute("errorMessage", message != null ? message.toString() : "No additional information");
            
            if (statusCode == 404) {
                return "error/404";
            }
        }
        
        return "error/general";
    }
}
