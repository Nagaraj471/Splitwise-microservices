package EXPENSE_SERVICE.expense_service.clients;

import EXPENSE_SERVICE.expense_service.dtos.GroupResponse;
import EXPENSE_SERVICE.expense_service.exceptions.GroupServiceUnavailableException;
import org.springframework.stereotype.Component;

@Component
public class GroupClientFallback implements GroupClient {
    @Override
    public GroupResponse getGroup(Long groupId) {
        throw new GroupServiceUnavailableException(
                "Group Service is currently unavailable. Please try again later."
        );
    }
}
