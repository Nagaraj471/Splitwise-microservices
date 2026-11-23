package EXPENSE_SERVICE.expense_service.services;

import EXPENSE_SERVICE.expense_service.exceptions.InvalidParticipantCountException;
import EXPENSE_SERVICE.expense_service.exceptions.InvalidExpenseAmountException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExpenseSplitCalculator {
    public List<Long> calculate(long totalAmtPaise, long participantsCount){
            if (totalAmtPaise<=0){
                throw new InvalidExpenseAmountException("Amount should not be zero or less than zero");
            }
            if (participantsCount<=0){
                throw new InvalidParticipantCountException("Partcipants must not be zero");
            }

            long baseShare=totalAmtPaise/participantsCount;
            long rem=totalAmtPaise%participantsCount;

            List<Long> shares=new ArrayList<>();
        for (int i = 0; i < participantsCount; i++) {
            long share=baseShare;
            if (i<rem){
                share++;
            }
            shares.add(share);
        }
        return shares;
    }
}
