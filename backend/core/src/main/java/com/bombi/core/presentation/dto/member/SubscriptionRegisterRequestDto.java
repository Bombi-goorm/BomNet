package com.bombi.core.presentation.dto.member;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubscriptionRegisterRequestDto {

	private String endpoint;
	private String p256dh;
	private String auth;


}
