package EXPENSE_SERVICE.expense_service.exceptions;

public class InvalidParticipantCountException extends RuntimeException{
    public InvalidParticipantCountException(String message) {
        super(message);
    }
}
