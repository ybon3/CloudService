package com.nkg.ventilator.pojo;

import com.google.gson.annotations.SerializedName;

public class AnalyticsGaData {

	public String kind;
	public String id;
	public Query query;
	public Integer itemsPerPage;
	public Integer totalResults;
	public String selfLink;
	//"profileInfo" field pass
	public Boolean containsSampledData;
	public String[][] rows;
}

class Query {
	@SerializedName("start-date")
	public String startDate;

	@SerializedName("end-date")
	public String endDate;

	public String ids;
	public String dimensions;
	public String[] metrics;
	public String[] sort;

	@SerializedName("start-index")
	public String startIndex;

	@SerializedName("max-results")
	public String maxResults;
}
