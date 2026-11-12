package GROUP_SERVICE.group_service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GroupRequest(

        @NotBlank
        @Size(max = 100)
        String name,

        @Size(max = 100)
        String description
) {
}
