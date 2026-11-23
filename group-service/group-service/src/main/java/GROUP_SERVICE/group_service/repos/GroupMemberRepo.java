package GROUP_SERVICE.group_service.repos;

import GROUP_SERVICE.group_service.entities.ExpenseGroup;
import GROUP_SERVICE.group_service.entities.GroupMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupMemberRepo extends JpaRepository<GroupMember,Long> {

    Page<GroupMember> findAllByGroup_Id(Long groupId, Pageable pageable);
    boolean existsByGroup_IdAndUserId(Long groupId,Long userId);
    List<GroupMember> findAllByUserId(Long userId);
    Optional<GroupMember> findByGroup_IdAndUserId(Long groupId,Long userId);

    List<GroupMember> findAllByGroup_Id(Long groupId);

}
