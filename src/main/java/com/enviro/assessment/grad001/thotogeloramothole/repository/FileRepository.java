package com.enviro.assessment.grad001.thotogeloramothole.repository;

import com.enviro.assessment.grad001.thotogeloramothole.model.File;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends CrudRepository<File, Long> {

    File getFileById(Long id);

    @Override
    List<File> findAll();
}
