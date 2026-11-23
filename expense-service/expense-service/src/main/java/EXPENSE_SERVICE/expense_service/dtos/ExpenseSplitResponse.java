package EXPENSE_SERVICE.expense_service.dtos;

import java.math.BigDecimal;

public record ExpenseSplitResponse(
        Long id,
        Long userId,
        BigDecimal shareAmount
) {
}
