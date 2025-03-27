package com.bombi.core.infrastructure.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@EnableCaching
@Configuration
public class RedisCacheConfig {

	@Bean
	public RedisCacheConfiguration redisCacheConfiguration() {
		return RedisCacheConfiguration.defaultCacheConfig()
			.entryTtl(Duration.ofDays(1))
			.disableCachingNullValues()
			.serializeKeysWith(
				RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
			)
			.serializeValuesWith(
				RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())
			);
	}

	@Bean
	public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
		Map<String, RedisCacheConfiguration> cacheConfigurationMap = new HashMap<>();
		cacheConfigurationMap.put("MemberInfo", memberInfoCache());
		cacheConfigurationMap.put("Forecast", forecastCache());
		cacheConfigurationMap.put("News", naverNewsCache());

		return builder -> {
			builder.withInitialCacheConfigurations(cacheConfigurationMap);
		};
	}

	private RedisCacheConfiguration memberInfoCache() {
		return RedisCacheConfiguration.defaultCacheConfig()
			.computePrefixWith(key -> key + "::")
			.entryTtl(Duration.ofSeconds(120))
			.disableCachingNullValues()
			.serializeKeysWith(
				RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
			)
			.serializeValuesWith(
				RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())
			);
	}

	private RedisCacheConfiguration forecastCache() {
		BasicPolymorphicTypeValidator typeValidator = BasicPolymorphicTypeValidator.builder()
			.allowIfSubType(Object.class)
			.build();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.activateDefaultTyping(typeValidator, ObjectMapper.DefaultTyping.NON_FINAL);

		return RedisCacheConfiguration.defaultCacheConfig()
			.computePrefixWith(key -> key + "::")
			.entryTtl(Duration.ofHours(1L))
			.disableCachingNullValues()
			.serializeKeysWith(
				RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
			)
			.serializeValuesWith(
				RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper))
			);
	}

	private RedisCacheConfiguration naverNewsCache() {
		return RedisCacheConfiguration.defaultCacheConfig()
			.computePrefixWith(key -> key + "::")
			.entryTtl(Duration.ofMinutes(30L))
			.disableCachingNullValues()
			.serializeKeysWith(
				RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
			)
			.serializeValuesWith(
				RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())
			);
	}
}
