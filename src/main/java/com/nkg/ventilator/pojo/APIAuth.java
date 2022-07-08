package com.nkg.ventilator.pojo;

import java.io.Serializable;
import java.util.Map;

public class APIAuth implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String account;
	private String keyValue;
	private String scheduleEnable;

	public APIAuth() {
		super();
	}

	public APIAuth(Map<String, Object> data) {
		id = (Integer)data.get("id");
		name = (String)data.get("name");
		account = (String)data.get("account");
		keyValue = (String)data.get("key_value");
		scheduleEnable = (String)data.get("schedule_enable");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getScheduleEnable() {
		return scheduleEnable;
	}

	public void setScheduleEnable(String scheduleEnable) {
		this.scheduleEnable = scheduleEnable;
	}
}
