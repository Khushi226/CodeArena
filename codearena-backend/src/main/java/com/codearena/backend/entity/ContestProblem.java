package com.codearena.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "contest_problems",
    uniqueConstraints = @UniqueConstraint(columnNames = {"contest_id", "problem_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContestProblem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contest_id", nullable = false)
    private Long contestId;

    @Column(name = "problem_id", nullable = false)
    private Long problemId;

    @Column(nullable = false)
    private Integer points;

    // Display order within the contest: 0 = Problem A, 1 = Problem B, ...
    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;
}