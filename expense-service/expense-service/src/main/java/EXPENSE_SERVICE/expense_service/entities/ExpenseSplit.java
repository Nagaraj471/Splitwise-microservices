package EXPENSE_SERVICE.expense_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class ExpenseSplit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Long userId;

    @Column(nullable = false)
    BigDecimal shareAmount;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_id",nullable = false)
    Expense expense;
}
