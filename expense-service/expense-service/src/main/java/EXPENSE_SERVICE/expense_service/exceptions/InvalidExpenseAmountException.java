package EXPENSE_SERVICE.expense_service.exceptions;

public class InvalidExpenseAmountException extends RuntimeException{
    public InvalidExpenseAmountException(String message) {
        super(message);
    }
}
