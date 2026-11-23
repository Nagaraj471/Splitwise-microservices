package EXPENSE_SERVICE.expense_service.services;

import EXPENSE_SERVICE.expense_service.clients.GroupServiceClient;
import EXPENSE_SERVICE.expense_service.dtos.*;
import EXPENSE_SERVICE.expense_service.entities.Expense;
import EXPENSE_SERVICE.expense_service.entities.ExpenseSplit;
import EXPENSE_SERVICE.expense_service.exceptions.MemberNotFoundException;
import EXPENSE_SERVICE.expense_service.repos.ExpenseRepo;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ExpenseService {
    private final GroupServiceClient groupServiceClient;
    private final ExpenseSplitCalculator expenseSplitCalculator;
    private final ExpenseRepo expenseRepo;

    public ValidateGroupResponse validate(Long groupId, List<Long> participants){

    if (participants.size() != participants.stream().distinct().count()){
        throw new IllegalArgumentException("Duplicate members are not allowed");
    }
        ValidateGroupResponse response=groupServiceClient.validateMembers(groupId,new ValidateGroupRequest(participants));

        if (!response.validUserIds().containsAll(participants)) {
            throw new IllegalArgumentException("Invalid group participants");

        }
        return response;
    }


    @Transactional
    public ExpenseResponse createExpense(Long paidBy, @Valid ExpenseRequest request) {
        List<Long> participants=request.participants();
        if (participants==null || participants.isEmpty()){
            throw new IllegalArgumentException("Atleast one participant is required");
        }
        if (participants.contains(null)){
            throw new IllegalArgumentException("Participants can not be null");
        }

        Set<Long> uniqueParticipants=new HashSet<>(participants);
        if (uniqueParticipants.size() != participants.size()){
            throw new IllegalArgumentException("Duplicate participants are not allowed ");
        }

        if (paidBy==null){
            throw new IllegalArgumentException("Payer Id cannot be null");
        }

        Set<Long> membersToValidate=new HashSet<>(participants);
        membersToValidate.add(paidBy);

        ValidateGroupResponse response=groupServiceClient.validateMembers(request.groupId()
        ,new ValidateGroupRequest(new ArrayList<>(membersToValidate)));

        if (response==null || response.validUserIds()==null || !response.validUserIds().containsAll(membersToValidate)){
            throw new MemberNotFoundException("Payer Or Participants ar not valid group members");
        }
        List<Long> shares=expenseSplitCalculator.calculate(request.totalAmount(), participants.size());
        Expense expense=new Expense();
        expense.setGroupId(request.groupId());
        expense.setTotalAmount(BigDecimal.valueOf(request.totalAmount()));
        expense.setDescription(request.description());
        expense.setPaidBy(paidBy);

        for (int i = 0; i < participants.size(); i++) {

            ExpenseSplit expenseSplit=new ExpenseSplit();
            expenseSplit.setUserId(participants.get(i));
            expenseSplit.setShareAmount(BigDecimal.valueOf(shares.get(i)));
            expenseSplit.setExpense(expense);
            expense.getExpenseSplitList().add(expenseSplit);
        }
        Expense savedExpense= expenseRepo.save(expense);

        List<ExpenseSplitResponse> responses=savedExpense.getExpenseSplitList()
                .stream()
                .map(expenseSplit -> new ExpenseSplitResponse(
                        expenseSplit.getId(),
                        expenseSplit.getUserId(),
                        expenseSplit.getShareAmount()
                )).toList();


        return new ExpenseResponse(
                savedExpense.getId(),
                savedExpense.getGroupId(),
                savedExpense.getDescription(),
                savedExpense.getTotalAmount(),
                savedExpense.getPaidBy(),
                savedExpense.getCreatedAt(),
                responses
        );
    }
}
