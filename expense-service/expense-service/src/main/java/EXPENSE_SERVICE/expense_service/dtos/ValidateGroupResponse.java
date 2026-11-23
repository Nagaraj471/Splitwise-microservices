package EXPENSE_SERVICE.expense_service.dtos;

import java.util.List;

public record ValidateGroupResponse(
        List<Long> validUserIds
) {
}
