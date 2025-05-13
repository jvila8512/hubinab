package com.mycompany.myapp.web.rest;

import java.time.Year;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SystemResource {

    @GetMapping("/system/year")
    public ResponseEntity<Integer> getCurrentYear() {
        return ResponseEntity.ok(Year.now().getValue());
    }
}
