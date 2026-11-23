package GROUP_SERVICE.group_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ValidateMemberRequest(
        @NotEmpty List<@NotNull Long> userIds
) {
}
