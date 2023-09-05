package com.ftn.EUprava.listeners;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.stereotype.Component;


@Component
public class InitHttpSessionListener implements HttpSessionListener{

	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		
		
		HttpSession session = httpSessionEvent.getSession();
		
		
		
		
	}
}
