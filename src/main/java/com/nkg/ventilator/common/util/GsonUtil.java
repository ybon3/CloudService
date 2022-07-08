package com.nkg.ventilator.common.util;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

/**
 * Provide convenience way to instantiated {@link Gson},
 * and keep consistency.
 * @author Dante
 */
public class GsonUtil {
	public static Gson create() {
		return commonSetting(new GsonBuilder()).create();
	}

	public static Gson createByDateFormat() {
		return createByDateFormat("yyyy/MM/dd HH:mm:ss");
	}

	public static Gson createByDateFormat(String dateFormatPattern) {
		return commonSetting(new GsonBuilder())
				.setDateFormat(dateFormatPattern)
				.create();
	}

	private static GsonBuilder commonSetting(GsonBuilder builder) {
		return builder
				.disableHtmlEscaping()
				.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()))
				.registerTypeAdapter(Date.class, (JsonSerializer<Date>) (date, type, jsonSerializationContext) -> new JsonPrimitive(date.getTime()));
	}
}
