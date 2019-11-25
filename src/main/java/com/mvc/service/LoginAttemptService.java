package com.mvc.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import lombok.Getter;

@Service
@Getter
public class LoginAttemptService {

	final int MAX_ATTEMPTS=3;
	
	private LoadingCache<String,Integer> attemptsCache;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginAttemptService.class);

	public LoginAttemptService() {
		super();
		attemptsCache = CacheBuilder.newBuilder()
				.expireAfterWrite(1, TimeUnit.DAYS)
				.build(new CacheLoader<String, Integer>()
				{
					public Integer load(String key) {
		                return 0;
		            }
				});
		LOGGER.debug("Cache initialized");
	}
	public void loginFailed(String username) {
		
		int attempts=0;
		try {
			attempts = attemptsCache.get(username);
		} catch (ExecutionException e) {
			attempts=0;
		}
		attempts++;
		LOGGER.info("Failed attempts for {} = {}",username,attempts);
		attemptsCache.put(username, attempts);
	}

	public void loginSucceeded(String username) {
		LOGGER.info("Cache invalidated for {}",username);
		attemptsCache.invalidate(username);
	}

	 public boolean isBlocked(String username) 
	 {
	        try 
	        {
	            return attemptsCache.get(username) >= MAX_ATTEMPTS;
	        } catch (ExecutionException e) {
	            return false;
	        }
	    }
}
