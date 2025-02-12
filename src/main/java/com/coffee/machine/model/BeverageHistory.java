package com.coffee.machine.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "beverage_history")
public class BeverageHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drink_id", nullable = false)
    private Recipe drink;

    @Column(name = "prepared_at", nullable = false, updatable = false)
    private Instant preparedAt = Instant.now();
}