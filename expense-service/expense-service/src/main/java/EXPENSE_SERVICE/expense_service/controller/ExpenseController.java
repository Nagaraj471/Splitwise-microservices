package EXPENSE_SERVICE.expense_service.controller;

import EXPENSE_SERVICE.expense_service.clients.GroupClient;
import EXPENSE_SERVICE.expense_service.dtos.GroupResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("expenses")

public class ExpenseController {
    private final GroupClient groupClient;

    public ExpenseController(GroupClient groupClient) {
        this.groupClient = groupClient;
    }

    @GetMapping("/validate-group/{groupId}")
    public GroupResponse validateGroup(@PathVariable Long groupId){
        return groupClient.getGroup(groupId);
    }
}
