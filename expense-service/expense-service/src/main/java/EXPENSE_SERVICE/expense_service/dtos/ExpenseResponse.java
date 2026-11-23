package EXPENSE_SERVICE.expense_service.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ExpenseResponse(
        Long expenseId,
        Long groupId,
        String description,
        BigDecimal totalAmount,
        Long paidBy,
        LocalDateTime createdAt,
        List<ExpenseSplitResponse> participants
) {
}
