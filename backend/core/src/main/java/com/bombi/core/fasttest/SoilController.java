package com.bombi.core.fasttest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bombi.core.infrastructure.external.soil.client.SoilCharacterApiClient;
import com.bombi.core.infrastructure.external.soil.client.SoilChemicalApiClient;
import com.bombi.core.infrastructure.external.soil.dto.SoilCharacterResponseDto;
import com.bombi.core.infrastructure.external.soil.dto.SoilChemicalResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SoilController {

	private final SoilChemicalApiClient soilChemicalApiClient;
	private final SoilCharacterApiClient soilCharacterApiClient;

	@GetMapping("/soil/chemical")
	ResponseEntity<?> soilChemical() {
		SoilChemicalResponseDto response = soilChemicalApiClient.sendSoilChemical("4617033028100130004");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/soil/character")
	ResponseEntity<?> soilCharacter() {
		SoilCharacterResponseDto response = soilCharacterApiClient.sendSoilCharacter("4617033028100130004");
		return ResponseEntity.ok(response);
	}
}
