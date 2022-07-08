package com.nkg.ventilator.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;
import com.nkg.ventilator.pojo.APIAuth;

@Service
public class APIAuthService extends BaseService {

	public static final APIAuth FAKE = new APIAuth();
	public static final Integer FAKE_INT = new Integer(1);

	public HashMap<BU, APIAuth> getStatus() {
		HashMap<BU, APIAuth> map = new HashMap<>();
		map.put(BU.XYZ, FAKE);
		map.put(BU.HIMIRROR, FAKE);
		return map;
	}

	public HashMap<BU, Integer> stopSchedule() {
		HashMap<BU, Integer> map = new HashMap<>();
		map.put(BU.XYZ, FAKE_INT);
		map.put(BU.HIMIRROR, FAKE_INT);
		return map;
	}

	public HashMap<BU, Integer> recoverSchedule() {
		HashMap<BU, Integer> map = new HashMap<>();
		map.put(BU.XYZ, FAKE_INT);
		map.put(BU.HIMIRROR, FAKE_INT);
		return map;
	}

	public HashMap<BU, Integer> updateKey(String new_key, String old_key) {
		HashMap<BU, Integer> map = new HashMap<>();
		map.put(BU.XYZ, FAKE_INT);
		map.put(BU.HIMIRROR, FAKE_INT);
		return map;
	}

	public static enum BU {
		XYZ, HIMIRROR;
	}
}
