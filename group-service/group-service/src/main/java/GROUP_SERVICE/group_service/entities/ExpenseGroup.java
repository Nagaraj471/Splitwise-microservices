package GROUP_SERVICE.group_service.entities;

import GROUP_SERVICE.group_service.enums.GroupStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "expense_groups")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseGroup {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    private String description;


    @Column(nullable = false)
    private Long createdBy;


    private LocalDateTime createdAt;


    @OneToMany(
            mappedBy = "group",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<GroupMember> members=new ArrayList<>();

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupStatus status=GroupStatus.ACTIVE;
}
