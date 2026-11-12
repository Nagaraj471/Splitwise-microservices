package GROUP_SERVICE.group_service.entities;

import GROUP_SERVICE.group_service.enums.GroupMemberRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "group_members",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"group_id","user_id"}
                )
        }
)
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id",nullable = false)
    private ExpenseGroup group;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupMemberRole role;


    private LocalDateTime joinedAt;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @PrePersist
    public void prePersist() {
        joinedAt = LocalDateTime.now();
    }
}
