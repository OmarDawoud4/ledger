package org.dawoud.ledger.account;

import org.dawoud.ledger.journal.AccountNotFoundException;
import org.dawoud.ledger.journal.InsufficientFundsException;
import org.dawoud.ledger.journal.InvalidTransferException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class AccountExceptionHandler {

    @ExceptionHandler(DuplicateAccountException.class)
    ProblemDetail handleDuplicate(DuplicateAccountException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problem.setTitle("Duplicate account");
        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "request validation failed");
        problem.setProperty("errors", ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList());
        return problem;
    }

    @ExceptionHandler(AccountNotFoundException.class)
    ProblemDetail handleNotFound(AccountNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Account not found");
        return problem;
    }

    @ExceptionHandler(InsufficientFundsException.class)
    ProblemDetail handleInsufficientFunds(InsufficientFundsException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problem.setTitle("Insufficient funds");
        return problem;
    }

    @ExceptionHandler(InvalidTransferException.class)
    ProblemDetail handleInvalidTransfer(InvalidTransferException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Invalid transfer");
        return problem;
    }
}