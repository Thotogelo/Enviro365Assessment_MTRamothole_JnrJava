package com.enviro.assessment.grad001.thotogeloramothole.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

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
    private UUID id;

    @Column(name = "File_Name", nullable = false)
    private String fileName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Upload_Date", nullable = false)
    private Date uploadDate;

    @Lob
    @Column(name = "Processed_Data", nullable = false)
    private String processedData;

    public File(RequestFileDTO requestFileDTO) {
        this.setId(UUID.randomUUID());
        this.setUploadDate(Date.from(new Date().toInstant()));
        this.setFileName(requestFileDTO.requestFile().getOriginalFilename());
    }
}