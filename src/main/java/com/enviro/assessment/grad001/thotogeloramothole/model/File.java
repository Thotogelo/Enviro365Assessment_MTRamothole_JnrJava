package com.enviro.assessment.grad001.thotogeloramothole.model;

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
@Table(name = "Files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILEID", nullable = false)
    private Long id;

    @Column(name = "FileName", nullable = false)
    private String fileName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UploadDate", nullable = false)
    private Date uploadDate;

    @Lob
    @Column(name = "ProcessedData", nullable = false)
    private byte[] processedData;
}