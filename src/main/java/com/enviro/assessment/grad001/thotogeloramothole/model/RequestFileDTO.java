package com.enviro.assessment.grad001.thotogeloramothole.model;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record RequestFileDTO(@NotBlank MultipartFile requestFile) {

}
