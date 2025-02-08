package com.bombi.core;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final CoreService coreService;

    @GetMapping("/core")
    public ResponseEntity<?> coreHealth(){
        return ResponseEntity.ok("Core :: Healthy");
    }

    @PostMapping("/core")
    public ResponseEntity<?> coreEntityList(){
        List<CoreEntity> coreEntity = coreService.getCoreEntity();
        return ResponseEntity.ok(coreEntity);
    }
}
