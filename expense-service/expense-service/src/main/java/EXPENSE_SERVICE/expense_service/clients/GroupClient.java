package EXPENSE_SERVICE.expense_service.clients;

import EXPENSE_SERVICE.expense_service.dtos.GroupResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "GROUP-SERVICE",
                fallback = GroupClientFallback.class)
public interface GroupClient {

    @GetMapping("groups/{groupId}")
    GroupResponse getGroup(@PathVariable("groupId") Long groupId);
}
