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
		cacheConfigurationMap.put("Forecast", customRedisCacheConfiguration(Duration.ofMinutes(30L)));
		cacheConfigurationMap.put("News", customRedisCacheConfiguration(Duration.ofMinutes(30L)));
		cacheConfigurationMap.put("AnnualPrice", customRedisCacheConfiguration(Duration.ofDays(1L)));
		cacheConfigurationMap.put("MonthlyPrice", customRedisCacheConfiguration(Duration.ofDays(1L)));
		cacheConfigurationMap.put("DailyPrice", customRedisCacheConfiguration(Duration.ofDays(1L)));
		cacheConfigurationMap.put("BestProduct", customRedisCacheConfiguration(Duration.ofMinutes(30L)));

		cacheConfigurationMap.put("Soil", customRedisCacheConfiguration(Duration.ofDays(7L)));
		cacheConfigurationMap.put("ProductChart", customRedisCacheConfiguration(Duration.ofHours(12L)));

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

	private RedisCacheConfiguration customRedisCacheConfiguration(Duration timeToLive) {
		BasicPolymorphicTypeValidator typeValidator = BasicPolymorphicTypeValidator.builder()
			.allowIfSubType(Object.class)
			.build();

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.activateDefaultTyping(typeValidator, ObjectMapper.DefaultTyping.NON_FINAL);

		return RedisCacheConfiguration.defaultCacheConfig()
			.computePrefixWith(key -> key + "::")
			.entryTtl(timeToLive)
			.disableCachingNullValues()
			.serializeKeysWith(
				RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
			)
			.serializeValuesWith(
				RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(
					objectMapper))
			);
	}

}
