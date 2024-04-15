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
    @Column(name = "FILE_ID", nullable = false)
    private Long id;

    @Column(name = "File_Name", nullable = false)
    private String fileName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Upload_Date", nullable = false)
    private Date uploadDate;

    @Lob
    @Column(name = "Processed_Data", nullable = false)
    private String processedData;
}