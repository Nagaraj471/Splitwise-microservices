package GROUP_SERVICE.group_service.dto;

import jakarta.validation.constraints.NotNull;

public record AddMemberRequest(
        @NotNull Long userId
) {
}
