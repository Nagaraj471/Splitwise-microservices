package GROUP_SERVICE.group_service.services;

import GROUP_SERVICE.group_service.dto.GroupDetailResponse;
import GROUP_SERVICE.group_service.dto.GroupRequest;
import GROUP_SERVICE.group_service.dto.GroupResponse;
import GROUP_SERVICE.group_service.dto.MemberResponse;
import GROUP_SERVICE.group_service.entities.ExpenseGroup;
import GROUP_SERVICE.group_service.entities.GroupMember;
import GROUP_SERVICE.group_service.enums.GroupMemberRole;
import GROUP_SERVICE.group_service.enums.GroupStatus;
import GROUP_SERVICE.group_service.exceptions.ConflictException;
import GROUP_SERVICE.group_service.exceptions.ResourceAlreadyExistsException;
import GROUP_SERVICE.group_service.exceptions.ResourceNotFoundException;
import GROUP_SERVICE.group_service.repos.ExpenseGroupRepo;
import GROUP_SERVICE.group_service.repos.GroupMemberRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import GROUP_SERVICE.group_service.exceptions.AccessDeniedException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GroupService {
    private final ExpenseGroupRepo expenseGroupRepo;
    private final GroupMemberRepo groupMemberRepo;

    @Transactional
    public ResponseEntity<GroupResponse> createGroup(GroupRequest request, Long userId){
        ExpenseGroup expenseGroup=new ExpenseGroup();
        expenseGroup.setName(request.name());
        expenseGroup.setDescription(request.description());
        expenseGroup.setCreatedBy(userId);

        ExpenseGroup savedGroup = expenseGroupRepo.save(expenseGroup);

        GroupMember groupMember=new GroupMember();
        groupMember.setGroup(savedGroup);
        groupMember.setRole(GroupMemberRole.OWNER);
        groupMember.setUserId(userId);

        groupMemberRepo.save(groupMember);
//        return new GroupResponse(
//                expenseGroup.getId(),
//                expenseGroup.getName(),
//                expenseGroup.getDescription(),
//                expenseGroup.getCreatedBy(),
//                expenseGroup.getCreatedAt()
//        );
        return ResponseEntity.status(HttpStatus.CREATED).body(GroupToGroupResponse(savedGroup));
    }

    @Transactional
    public List<GroupResponse> getMyGroups(Long UserId){
        List<GroupMember> members=groupMemberRepo.findAllByUserId(UserId);
        return  members.stream()
                .map(GroupMember::getGroup)
                .map(group->new GroupResponse(
                        group.getId(),
                        group.getName(),
                        group.getDescription(),
                        group.getCreatedBy(),
                        group.getCreatedAt()
                ))
                .toList();

    }

    @Transactional
    public MemberResponse addMember(Long requesterId,Long groupId,Long targetUserId){
        ExpenseGroup group=expenseGroupRepo.findById(groupId)
                .orElseThrow(()->new ResourceNotFoundException("group not found"));
        GroupMember member=groupMemberRepo.findByGroup_IdAndUserId(groupId,requesterId)
                .orElseThrow(()->new AccessDeniedException("you are not member of this gropup"));
        if (member.getRole() != GroupMemberRole.OWNER){
            throw new AccessDeniedException("Only owners can add members");
        }

        if (groupMemberRepo.existsByGroup_IdAndUserId(groupId,targetUserId)){
            throw new ResourceAlreadyExistsException("user is already a member of this group");
        }

        GroupMember newMember=new GroupMember();
        newMember.setGroup(group);
        newMember.setRole(GroupMemberRole.MEMBER);
        newMember.setUserId(targetUserId);
        GroupMember saved=groupMemberRepo.save(newMember);

        return new MemberResponse(
                saved.getId(),
                groupId,
                saved.getUserId(),
                saved.getRole(),
                saved.getJoinedAt()
        );

    }

    @Transactional
    public ResponseEntity<String> removeMember(Long requesterId,Long groupId,Long targetUserId){
        ExpenseGroup group=expenseGroupRepo.findById(groupId)
                .orElseThrow(()->new ResourceNotFoundException("group not found"));
        GroupMember member=groupMemberRepo.findByGroup_IdAndUserId(groupId,requesterId)
                .orElseThrow(()->new AccessDeniedException("you are not the member of this group"));


        if (group.getStatus() == GroupStatus.ARCHIVED) {
            throw new ConflictException("Cannot add members to an archived group");
        }
        if (member.getRole() != GroupMemberRole.OWNER){
            throw new AccessDeniedException("Only owner can remove members");
        }
        if (requesterId.equals(targetUserId)) {
            throw new IllegalArgumentException("Owner cannot remove themselves");
        }
        GroupMember member1=groupMemberRepo.findByGroup_IdAndUserId(groupId,targetUserId)
                .orElseThrow(()->new ResourceNotFoundException("member is not in the group"));
        groupMemberRepo.delete(member1);
        return ResponseEntity.ok("member removed successfully");

    }

    @Transactional
    public ResponseEntity<String> leaveGroup(Long requesterId,Long groupId){
        ExpenseGroup group=expenseGroupRepo.findById(groupId).orElseThrow(()->new ResourceNotFoundException("Group not found"));
        GroupMember member=groupMemberRepo.findByGroup_IdAndUserId(groupId,requesterId)
                .orElseThrow(()->new AccessDeniedException("you are not member of this group"));
        if (group.getStatus() == GroupStatus.ARCHIVED) {
            throw new ConflictException("Cannot add members to an archived group");
        }
      if (member.getRole()==GroupMemberRole.OWNER && group.getMembers().size()>1){
          throw new ConflictException(
                  "Owner cannot leave while other members are in the group");
      }
        groupMemberRepo.delete(member);

        return ResponseEntity.ok("You left the group successfully");
    }

    @Transactional
    public void archiveGroup(Long requesterId,Long groupId){
        ExpenseGroup group=expenseGroupRepo.findById(groupId)
                .orElseThrow(()->new ResourceNotFoundException("group not found"));
        GroupMember member=groupMemberRepo.findByGroup_IdAndUserId(groupId,requesterId)
                .orElseThrow(()->new ResourceNotFoundException("you r not the member of this group"));
        if (member.getRole()!=GroupMemberRole.OWNER){
            throw new AccessDeniedException("you are not the owner of this group");
        }
        if (group.getStatus()==GroupStatus.ARCHIVED){
            throw new ConflictException("Group is already archived");
        }

        group.setStatus(GroupStatus.ARCHIVED);
        expenseGroupRepo.save(group);
    }

    public GroupDetailResponse getGroup(Long requesterId, Long groupId){
        ExpenseGroup group=expenseGroupRepo.findById(groupId)
                .orElseThrow(()->new ResourceNotFoundException("group not found"));

        GroupMember members=groupMemberRepo.findByGroup_IdAndUserId(groupId,requesterId)
                .orElseThrow(()->new AccessDeniedException("you are not the member of this group"));
        List<MemberResponse> memberResponses = group.getMembers()
                .stream()
                .map(member -> new MemberResponse(
                        member.getId(),
                        group.getId(),
                        member.getUserId(),
                        member.getRole(),
                        member.getJoinedAt()
                ))
                .toList();
        return new GroupDetailResponse(
                group.getId(),
                group.getName(),
                group.getDescription(),
                group.getCreatedBy(),
                group.getCreatedAt(),
                group.getStatus(),
                memberResponses
        );
    }

    @Transactional
    public GroupResponse update(Long requesterId,Long groupId,GroupRequest request){
        ExpenseGroup group=expenseGroupRepo.findById(groupId)
                .orElseThrow(()->new ResourceNotFoundException("Group not found "));
        GroupMember member=groupMemberRepo.findByGroup_IdAndUserId(groupId,requesterId)
                .orElseThrow(()->new AccessDeniedException("you are not the member of the group"));
        if (member.getRole()!=GroupMemberRole.OWNER){
            throw new AccessDeniedException("only owner can update the things");
        }
        if (group.getStatus()!=GroupStatus.ACTIVE){
            throw new ConflictException("the group is archived");
        }
        group.setName(request.name());
        group.setDescription(request.description());
        expenseGroupRepo.save(group);
        return new GroupResponse(
                group.getId(),
                group.getName(),
                group.getDescription(),
                group.getCreatedBy(),
                group.getCreatedAt()
        );
    }

    @Transactional
    public Page<MemberResponse> getGroupMembers(Long requesterId, Long groupId, Pageable pageable){
        ExpenseGroup group=expenseGroupRepo.findById(groupId)
                .orElseThrow(()->new ResourceNotFoundException("Group not found"));
        GroupMember member=groupMemberRepo.findByGroup_IdAndUserId(groupId,requesterId).orElseThrow(
                ()->new AccessDeniedException("you are not the member of this group")
        );
        return groupMemberRepo.findAllByGroup_Id(groupId,pageable)
                .map(mem->new MemberResponse(
                        mem.getId(),
                        mem.getGroup().getId(),
                        mem.getUserId(),
                        mem.getRole(),
                        mem.getJoinedAt()
                ));

    }
    
    public GroupResponse GroupToGroupResponse(ExpenseGroup group){
                return new GroupResponse(
                group.getId(),
                group.getName(),
                group.getDescription(),
                group.getCreatedBy(), 
                        group.getCreatedAt()
    );
    }
}
