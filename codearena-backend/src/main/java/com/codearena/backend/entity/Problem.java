

package com.codearena.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "problems",
    indexes = {
        @Index(name = "idx_problem_slug", columnList = "problem_slug")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(
        name = "problem_slug",
        nullable = false,
        unique = true
    )
    private String problemSlug;

    @Column(nullable = false)
    private String difficulty;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String topics;

    @Column(columnDefinition = "TEXT")
    private String constraints;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean visible = true;

    @Column(columnDefinition = "TEXT")
    private String examples;
}
