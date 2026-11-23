package EXPENSE_SERVICE.expense_service.exceptions;

public class GroupNotFoundException extends RuntimeException{
    public GroupNotFoundException(String message) {
        super(message);
    }
}
