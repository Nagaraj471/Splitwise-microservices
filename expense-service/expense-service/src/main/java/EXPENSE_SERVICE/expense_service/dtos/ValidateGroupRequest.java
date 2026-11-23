package EXPENSE_SERVICE.expense_service.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ValidateGroupRequest(
        @NotEmpty List<@NotNull Long> userIds
) {
}
