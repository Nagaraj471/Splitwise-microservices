package GROUP_SERVICE.group_service.dto;

import java.util.List;

public record ValidateMemberResponse(
        List<Long> validUserIds
) {
}
