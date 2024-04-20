package com.enviro.assessment.grad001.thotogeloramothole.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//WARN
//Use of @Data annotation in a JPA entity can cause issues with lazy loading, I used it because there's no relationship.

@Data
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