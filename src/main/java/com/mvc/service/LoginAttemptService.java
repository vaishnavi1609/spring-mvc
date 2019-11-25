package com.mvc.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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
	}
	public void loginFailed(String remoteAddress) {
		
		int attempts=0;
		try {
			attempts = attemptsCache.get(remoteAddress);
		} catch (ExecutionException e) {
			attempts=0;
		}
		attempts++;
		attemptsCache.put(remoteAddress, attempts);
	}

	public void loginSucceeded(String remoteAddress) {
		attemptsCache.invalidate(remoteAddress);
	}

	 public boolean isBlocked(String key) {

	        try {
	            return attemptsCache.get(key) >= MAX_ATTEMPTS;
	        } catch (ExecutionException e) {
	            return false;
	        }
	    }
}
