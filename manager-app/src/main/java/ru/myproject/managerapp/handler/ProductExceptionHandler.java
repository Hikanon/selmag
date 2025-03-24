package ru.myproject.managerapp.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;
import java.util.NoSuchElementException;

@ControllerAdvice
@RequiredArgsConstructor
public class ProductExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchProductException(NoSuchElementException e, Model model, Locale locale) {
        model.addAttribute("message", messageSource.getMessage(e.getMessage(),
                new Object[0],
                e.getMessage(),
                locale));
        return "error/404";
    }
}
