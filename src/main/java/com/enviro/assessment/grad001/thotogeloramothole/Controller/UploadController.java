package com.enviro.assessment.grad001.thotogeloramothole.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/upload")
public class UploadController {

    @GetMapping
    public String test() {
        return "Upload controller working";
    }

    @PostMapping
    public String fileUpload() {
        return "File uploaded";
    }

}
