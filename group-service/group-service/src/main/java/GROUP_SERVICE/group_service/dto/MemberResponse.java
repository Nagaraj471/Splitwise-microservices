package GROUP_SERVICE.group_service.dto;

import GROUP_SERVICE.group_service.enums.GroupMemberRole;

import java.time.LocalDateTime;

public record MemberResponse(

        Long id,
        Long groupId,
        Long userId,
        GroupMemberRole role,
        LocalDateTime joinedAt


) {
}
