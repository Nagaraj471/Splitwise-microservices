package GROUP_SERVICE.group_service.repos;

import GROUP_SERVICE.group_service.entities.ExpenseGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseGroupRepo extends JpaRepository<ExpenseGroup,Long> {
    Optional<ExpenseGroup> findByName(String name);

    List<ExpenseGroup> findAllByCreatedBy(Long userId);
}
