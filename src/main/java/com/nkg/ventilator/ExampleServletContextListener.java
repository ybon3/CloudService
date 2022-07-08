package com.nkg.ventilator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleServletContextListener implements ServletContextListener {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.info("Callback triggered - contextDestroyed.");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("Callback triggered - contextInitialized.");
	}
}
