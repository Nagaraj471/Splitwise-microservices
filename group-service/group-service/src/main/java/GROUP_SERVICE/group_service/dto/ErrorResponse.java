package GROUP_SERVICE.group_service.dto;

public record ErrorResponse(
        Integer status,
        String message,
        String code
) {
}
