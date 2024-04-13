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
@Table(name = "ProcessedData")
public class ProcessedData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FileID", nullable = false)
    private Long fileId;

    @Lob
    @Column(name = "ProcessedData", nullable = false)
    private String processedData;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ProcessDate", nullable = false)
    private Date processDate;

}