package GROUP_SERVICE.group_service.dto;

import java.time.LocalDateTime;

public record GroupResponse(
        Long id,
        String name,
        String description,
        Long createdBy,
        LocalDateTime createdAt
) {
}
