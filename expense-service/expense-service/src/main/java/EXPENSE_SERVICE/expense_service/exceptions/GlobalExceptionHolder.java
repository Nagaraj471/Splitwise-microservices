package EXPENSE_SERVICE.expense_service.exceptions;

import EXPENSE_SERVICE.expense_service.dtos.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHolder {

    @ExceptionHandler(GroupNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGroupNotFound(GroupNotFoundException exception){
        ErrorResponse response=new ErrorResponse(404, exception.getMessage(),"GROUP_NOT_FOUND");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMemberNotFound(MemberNotFoundException exception){
        ErrorResponse response=new ErrorResponse(404, exception.getMessage(), "MEMBER_NOT_FOUND");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(GroupServiceUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleGroupServiceUnavailable(GroupServiceUnavailableException exception){
        ErrorResponse response=new ErrorResponse(503, exception.getMessage(), "GROUP_SERVICE_UNAVAILABLE");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @ExceptionHandler(InvalidParticipantCountException.class)
    public ResponseEntity<ErrorResponse> handleParticipantsMustNotBeZero(InvalidParticipantCountException exception){
        ErrorResponse response=new ErrorResponse(400,exception.getMessage(),"PARTIIPANTS_MUST_NOT_BE_ZERO");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(InvalidExpenseAmountException.class)
    public ResponseEntity<ErrorResponse> handleTotalAntNotLessThanZero(InvalidExpenseAmountException exception){
        ErrorResponse response=new ErrorResponse(400, exception.getMessage(),"ANT_MUST_NOT_BE_ZERO");
        return ResponseEntity.status(400).body(response);
    }

}
