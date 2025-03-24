package ru.myproject.managerapp.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class NotFoundException extends RuntimeException {

    private final List<String> errors;
}
