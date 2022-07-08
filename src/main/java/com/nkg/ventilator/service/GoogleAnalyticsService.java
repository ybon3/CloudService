package com.nkg.ventilator.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.nkg.ventilator.common.util.RESTClient;
import com.nkg.ventilator.pojo.AnalyticsGaData;
import com.nkg.ventilator.pojo.GoogleAnalyticsResp;

@Service
public class GoogleAnalyticsService extends BaseService {

	public static int PAGE_SIZE = 1000;

	public String refreshToken() {
		String url = "https://www.googleapis.com/oauth2/v3/token";

		Map<String,String> params = new HashMap<>();
		GoogleAnalyticsResp response = RESTClient.postForm(url, params, GoogleAnalyticsResp.class);
		logger.info("access_token: " + response.access_token);

		return response.access_token;
	}

	public int updateGaCount() {
		return updateGaCount("today", "today");
	}

	public int updateGaCount(String startDate, String endDate) {
		String access_token = refreshToken();

		//Get analytics data from GA api
		String url = "https://www.googleapis.com/analytics/v3/data/ga";

		Map<String,String> params = new HashMap<>();
		params.put("access_token", access_token);
		params.put("ids", "ga:153762889");
		params.put("dimensions", "ga:pagePath,ga:dimension5");
		params.put("metrics", "ga:pageviews,ga:avgTimeOnPage");
		params.put("sort", "ga:dimension5");
		params.put("start-date", startDate);
		params.put("end-date", endDate);
		params.put("max-results", "1");

		//First time query is just for get totalResults
		AnalyticsGaData result = RESTClient.get(url, params, AnalyticsGaData.class);

		if (result.totalResults == null || result.totalResults <= 0) {
			return 0;
		}

		//Prepare get GA data and save it
		int totalResults = result.totalResults;
		params.put("max-results", PAGE_SIZE + "");
		for (int index = 1; index <= totalResults; index += PAGE_SIZE) {
			//Get GA data by paging
			params.put("start-index", index + "");
			result = RESTClient.get(url, params, AnalyticsGaData.class);

			//add into database
			for (String[] row : result.rows) {
				//TODO
			}
		}

		return totalResults;
	}
}
