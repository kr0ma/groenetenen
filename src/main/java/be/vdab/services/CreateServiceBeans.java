package be.vdab.services;

import java.util.Arrays;

import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@EnableTransactionManagement
@EnableScheduling
@EnableCaching
public class CreateServiceBeans {
	@Bean
	public SimpleCacheManager simpleCacheManager() {
		SimpleCacheManager manager = new SimpleCacheManager();
		Cache cache = new ConcurrentMapCache("filialen");
		manager.setCaches(Arrays.asList(cache));
		return manager;
	}
}