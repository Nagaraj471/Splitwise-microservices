package EXPENSE_SERVICE.expense_service.dtos;

public record ErrorResponse(
        Integer status,
        String message,
        String code
) {
}
