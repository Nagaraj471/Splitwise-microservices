package EXPENSE_SERVICE.expense_service.repos;

import EXPENSE_SERVICE.expense_service.entities.ExpenseSplit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseSplitRepo extends JpaRepository<ExpenseSplit,Long> {
}
