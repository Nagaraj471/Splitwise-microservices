package EXPENSE_SERVICE.expense_service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Long groupId;

    String description;

    @Column(nullable = false)
    BigDecimal totalAmount;

    @Column(nullable = false)
    Long paidBy;

    @Column(nullable = false)
    LocalDateTime createdAt;

    @OneToMany(mappedBy = "expense",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    List<ExpenseSplit> expenseSplitList=new ArrayList<>();

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
