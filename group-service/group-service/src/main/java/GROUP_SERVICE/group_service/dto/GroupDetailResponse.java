package GROUP_SERVICE.group_service.dto;

import GROUP_SERVICE.group_service.dto.MemberResponse;
import GROUP_SERVICE.group_service.enums.GroupStatus;

import java.time.LocalDateTime;
import java.util.List;

public record GroupDetailResponse(
        Long id,
        String name,
        String description,
        Long createdBy,
        LocalDateTime createdAt,
        GroupStatus status,
        List<MemberResponse> members
) {}