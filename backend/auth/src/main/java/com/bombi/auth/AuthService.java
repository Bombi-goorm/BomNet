package com.bombi.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;

    public List<AuthEntity> getAuthEntity(){
        return authRepository.findAll();

    }
}
