package EXPENSE_SERVICE.expense_service.controller;

import EXPENSE_SERVICE.expense_service.dtos.ExpenseRequest;
import EXPENSE_SERVICE.expense_service.dtos.ExpenseResponse;
import EXPENSE_SERVICE.expense_service.dtos.ValidateGroupRequest;
import EXPENSE_SERVICE.expense_service.dtos.ValidateGroupResponse;
import EXPENSE_SERVICE.expense_service.services.ExpenseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("expenses")
@AllArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping("/groups/{groupId}/validate-members")
    public ResponseEntity<ValidateGroupResponse> validateMembers(
            @PathVariable Long groupId,
            @RequestBody ValidateGroupRequest request) {

        ValidateGroupResponse response =
                expenseService.validate(groupId, request.userIds());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(
            @RequestHeader("X-User-Id") Long paidBy,
            @Valid @RequestBody ExpenseRequest request) {
        System.out.println("Received payer ID: " + paidBy);
        ExpenseResponse response = expenseService.createExpense(paidBy, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
