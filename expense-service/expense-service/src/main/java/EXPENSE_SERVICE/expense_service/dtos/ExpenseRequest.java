package EXPENSE_SERVICE.expense_service.dtos;

import java.util.List;

public record ExpenseRequest(
        Long groupId,
        String description,
        Long totalAmount,
        List<Long> participants
) {
}
