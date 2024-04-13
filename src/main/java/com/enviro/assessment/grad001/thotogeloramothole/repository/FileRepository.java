package com.enviro.assessment.grad001.thotogeloramothole.repository;

import com.enviro.assessment.grad001.thotogeloramothole.Models.File;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Repository
public interface FileRepository extends CrudRepository<File, Long> {

    @Query("SELECT f FROM File f")
    public List<File> getAllFiles();

    public File getFileById(Long id);

}
