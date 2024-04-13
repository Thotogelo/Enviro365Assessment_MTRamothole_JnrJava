package com.enviro.assessment.grad001.thotogeloramothole.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SavedResults")
public class SavedResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ProcessedDataID", nullable = false)
    private Long processedDataId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SaveDate", nullable = false)
    private Date saveDate;
}