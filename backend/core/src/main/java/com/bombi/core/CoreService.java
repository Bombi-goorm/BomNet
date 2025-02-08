package com.bombi.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoreService {
    private final CoreRepository coreRepository;

    public List<CoreEntity> getCoreEntity(){
        return coreRepository.findAll();

    }
}
