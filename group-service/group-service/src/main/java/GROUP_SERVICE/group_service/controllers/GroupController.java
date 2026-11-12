package GROUP_SERVICE.group_service.controllers;

import GROUP_SERVICE.group_service.dto.*;
import GROUP_SERVICE.group_service.services.GroupService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/groups")
public class GroupController {


    private final GroupService groupService;
    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(
            @Valid @RequestBody GroupRequest request,
            @RequestHeader("X-User-Id") Long userId){
        return groupService.createGroup(request,userId);
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getMyGroups(
            @RequestHeader("X-User-Id") Long userId
    ){
        return ResponseEntity.ok(groupService.getMyGroups(userId));
    }

    @PostMapping("/{groupId}/members")
    public ResponseEntity<MemberResponse> addMember(
            @PathVariable Long groupId,
            @RequestHeader("X-User-Id") Long requesterId,
            @Valid @RequestBody AddMemberRequest request
            ){
        MemberResponse response=groupService.addMember(requesterId,groupId,request.userId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{groupId}/members/{targetUserId}")
    public ResponseEntity<String> removeMember(
            @RequestHeader("X-User-Id") Long requesterId,
            @PathVariable Long groupId,
            @PathVariable Long targetUserId
    ) {
        return groupService.removeMember(
                requesterId, groupId, targetUserId
        );
    }

    @DeleteMapping("/{groupId}/leave")
    public ResponseEntity<String> leaveGroup(
            @RequestHeader("X-User-Id") Long requesterId,
            @PathVariable Long groupId) {

        return groupService.leaveGroup(requesterId, groupId);
    }

    @PatchMapping("/{groupId}/archive")
    public ResponseEntity<String> archiveGroup(
            @RequestHeader("X-User-Id") Long requesterId,
            @PathVariable Long groupId){
        groupService.archiveGroup(requesterId,groupId);
        return ResponseEntity.ok("The group id archived");
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDetailResponse> getGroup(
            @PathVariable Long groupId,
            @RequestHeader("X-User-Id") Long requesterId) {

        return ResponseEntity.ok(
                groupService.getGroup(requesterId, groupId)
        );
    }

    @PatchMapping("/{groupId}")
    public ResponseEntity<GroupResponse> update(
            @RequestHeader("X-User-Id") Long requesterId,
            @PathVariable Long groupId,
            @Valid @RequestBody GroupRequest request){
        return ResponseEntity.ok(groupService.update(requesterId,groupId,request));
    }

    @GetMapping("/{groupId}/members")
    public ResponseEntity<Page<MemberResponse>> getGroupMembers(
            @RequestHeader("X-User-Id") Long requesterId,
            @PathVariable Long groupId,
            Pageable pageable){
        return ResponseEntity.ok(groupService.getGroupMembers(requesterId, groupId,pageable));
    }
}
