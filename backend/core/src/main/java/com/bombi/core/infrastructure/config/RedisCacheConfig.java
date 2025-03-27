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
		return builder -> {
			Map<String, RedisCacheConfiguration> cacheConfigurationMap = new HashMap<>();

			cacheConfigurationMap.put("member", RedisCacheConfiguration.defaultCacheConfig()
				.computePrefixWith(key -> "member::" + key + "::")
				.entryTtl(Duration.ofSeconds(120))
				.disableCachingNullValues()
				.serializeKeysWith(
					RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
				)
				.serializeValuesWith(
					RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())
				));
		};
	}

}
