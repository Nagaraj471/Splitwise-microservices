package EXPENSE_SERVICE.expense_service.clients;

import EXPENSE_SERVICE.expense_service.config.FeignClientConfig;
import EXPENSE_SERVICE.expense_service.dtos.ValidateGroupRequest;
import EXPENSE_SERVICE.expense_service.dtos.ValidateGroupResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "GROUP-SERVICE",
            configuration = FeignClientConfig.class)
public interface GroupServiceClient {

    @PostMapping("/groups/internal/{groupId}/members/validate")
    public ValidateGroupResponse validateMembers(
            @PathVariable("groupId") Long groupId,
            @RequestBody ValidateGroupRequest request
    );
}
