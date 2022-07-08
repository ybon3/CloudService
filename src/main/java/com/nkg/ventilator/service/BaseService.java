package com.nkg.ventilator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * To provide all Service common functions
 * <p>
 * @author Dante
 */
public class BaseService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	//TODO Refractor
	protected Boolean isDefined(String str) {
		if (str != null && !str.equals("") && str.length() > 0) return true;
		else return false;
	}
}
