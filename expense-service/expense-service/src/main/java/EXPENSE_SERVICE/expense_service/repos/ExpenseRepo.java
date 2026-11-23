package EXPENSE_SERVICE.expense_service.repos;

import EXPENSE_SERVICE.expense_service.entities.Expense;
import EXPENSE_SERVICE.expense_service.entities.ExpenseSplit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepo extends JpaRepository<Expense,Long> {
}
