package com.enviro.assessment.grad001.thotogeloramothole;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/testendpoint")
public class SampleController {

    @GetMapping
    public String hello() {
        return "Hello World";
    }
}
