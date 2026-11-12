package EXPENSE_SERVICE.expense_service.exceptions;

public class GroupServiceUnavailableException extends RuntimeException{
    public GroupServiceUnavailableException(String message) {
        super(message);
    }
}
