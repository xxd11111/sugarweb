package com.sugarcoat.support.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.Objects;

/**
 * Redis 配置类
 */
@Configuration
@EnableCaching
@EnableConfigurationProperties(RedissonProperties.class)
@Slf4j
public class RedisAutoConfiguration {

	@Autowired
	private RedissonProperties redissonProperties;

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * 创建 RedisTemplate Bean，使用 JSON 序列化方式
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		// 创建 RedisTemplate 对象
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		// 设置 RedisConnection 工厂。😈 它就是实现多种 Java Redis 客户端接入的秘密工厂。感兴趣的胖友，可以自己去撸下。
		template.setConnectionFactory(factory);
		// 使用 String 序列化方式，序列化 KEY 。
		template.setKeySerializer(RedisSerializer.string());
		template.setHashKeySerializer(RedisSerializer.string());
		// 使用 JSON 序列化方式（库是 Jackson ），序列化 VALUE 。
		template.setValueSerializer(RedisSerializer.json());
		template.setHashValueSerializer(RedisSerializer.json());
		return template;
	}

	@Bean
	public RedissonAutoConfigurationCustomizer redissonCustomizer() {
		return config -> {
			config.setThreads(redissonProperties.getThreads()).setNettyThreads(redissonProperties.getNettyThreads());
			// todo 先看看默认的性能和效果如何
			// .setCodec(new JsonJacksonCodec(objectMapper));
			RedissonProperties.SingleServerConfig singleServerConfig = redissonProperties.getSingleServerConfig();
			if (Objects.nonNull(singleServerConfig)) {
				// 使用单机模式
				config.useSingleServer()
						// 设置redis key前缀
						.setNameMapper(new RedisKeyPrefixMapper(redissonProperties.getKeyPrefix()))
						.setTimeout(singleServerConfig.getTimeout()).setClientName(singleServerConfig.getClientName())
						.setIdleConnectionTimeout(singleServerConfig.getIdleConnectionTimeout())
						.setSubscriptionConnectionPoolSize(singleServerConfig.getSubscriptionConnectionPoolSize())
						.setConnectionMinimumIdleSize(singleServerConfig.getConnectionMinimumIdleSize())
						.setConnectionPoolSize(singleServerConfig.getConnectionPoolSize());
			}
			// 集群配置方式 参考下方注释
			RedissonProperties.ClusterServersConfig clusterServersConfig = redissonProperties.getClusterServersConfig();
			if (Objects.nonNull(clusterServersConfig)) {
				config.useClusterServers()
						// 设置redis key前缀
						.setNameMapper(new RedisKeyPrefixMapper(redissonProperties.getKeyPrefix()))
						.setTimeout(clusterServersConfig.getTimeout())
						.setClientName(clusterServersConfig.getClientName())
						.setIdleConnectionTimeout(clusterServersConfig.getIdleConnectionTimeout())
						.setSubscriptionConnectionPoolSize(clusterServersConfig.getSubscriptionConnectionPoolSize())
						.setMasterConnectionMinimumIdleSize(clusterServersConfig.getMasterConnectionMinimumIdleSize())
						.setMasterConnectionPoolSize(clusterServersConfig.getMasterConnectionPoolSize())
						.setSlaveConnectionMinimumIdleSize(clusterServersConfig.getSlaveConnectionMinimumIdleSize())
						.setSlaveConnectionPoolSize(clusterServersConfig.getSlaveConnectionPoolSize())
						.setReadMode(clusterServersConfig.getReadMode())
						.setSubscriptionMode(clusterServersConfig.getSubscriptionMode());
			}
			log.info("初始化 redis 配置");
		};
	}

	/**
	 * redis集群配置 yml
	 *
	 * --- # redis 集群配置(单机与集群只能开启一个另一个需要注释掉) spring: redis: cluster: nodes: -
	 * 192.168.0.100:6379 - 192.168.0.101:6379 - 192.168.0.102:6379 # 密码 password: #
	 * 连接超时时间 timeout: 10s # 是否开启ssl ssl: false
	 *
	 * redisson: # 线程池数量 threads: 16 # Netty线程池数量 nettyThreads: 32 # 集群配置
	 * clusterServersConfig: # 客户端名称 clientName: ${ruoyi.name} # master最小空闲连接数
	 * masterConnectionMinimumIdleSize: 32 # master连接池大小 masterConnectionPoolSize: 64 #
	 * slave最小空闲连接数 slaveConnectionMinimumIdleSize: 32 # slave连接池大小
	 * slaveConnectionPoolSize: 64 # 连接空闲超时，单位：毫秒 idleConnectionTimeout: 10000 #
	 * 命令等待超时，单位：毫秒 timeout: 3000 # 发布和订阅连接池大小 subscriptionConnectionPoolSize: 50 # 读取模式
	 * readMode: "SLAVE" # 订阅模式 subscriptionMode: "MASTER"
	 */

}
