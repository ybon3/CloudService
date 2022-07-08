package com.nkg.ventilator.common.util;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.lang.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class RESTClient {

	private static RestTemplate restTemplate = new RestTemplate();

	static {
		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		httpRequestFactory.setReadTimeout(30000);
		restTemplate.setRequestFactory(httpRequestFactory);
		restTemplate.setInterceptors(Arrays.asList(new PlusEncoderInterceptor()));
	}

	/**
	 * Fixed header: ContentType: application/utf-8
	 */
	public static <T> T request(HttpMethod method, String url, String input, Class<T> responseType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return request(method, url, headers, input, responseType);
	}

	public static <T> T request(HttpMethod method, String url, HttpHeaders headers, String input, Class<T> responseType) {
		HttpEntity<String> entity = new HttpEntity<>(input, headers);
		ResponseEntity<T> resp = restTemplate.exchange(url, method, entity, responseType);
		return resp.getBody();
	}

	public static <T> T get(String url, Map<String, String> params, Class<T> responseType) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		if (params != null) {
			Iterator<Entry<String, String>> it = params.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				builder.queryParam(entry.getKey(), entry.getValue());
			}
		}

		return restTemplate.getForObject(builder.build().toUri(), responseType);
	}

	public static <T> T get(String url, @Nullable Params params, Class<T> responseType) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		if (params != null) {
			Iterator<Entry<String, String>> it = params.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				builder.queryParam(entry.getKey(), entry.getValue());
			}
		}

		return restTemplate.getForObject(builder.build().toUri(), responseType);
	}

	public static <T> T get(String url, Class<T> responseType) {
		return request(HttpMethod.GET, url, null, responseType);
	}

	public static <T> T post(String url, String input, Class<T> responseType) {
		return request(HttpMethod.POST, url, input, responseType);
	}

	public static <T> T postForm(String url, Map<String,String> params, Class<T> responseType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		return postForm(url, headers, params, responseType);
	}

	public static <T> T postForm(String url, HttpHeaders headers, Map<String,String> params, Class<T> responseType) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.setAll(params);
		return restTemplate.postForObject(url, new HttpEntity<>(formData, headers), responseType);
	}

	public static <T> T put(String url, String input, Class<T> responseType) {
		return request(HttpMethod.PUT, url, input, responseType);
	}

	public static class Params extends HashMap<String, String> {
		private static final long serialVersionUID = 1L;

		public Params add(String name, String value) {
			put(name, value);
			return this;
		}
	}

	/**
	 * Reference: <a href="https://stackoverflow.com/questions/54294843/plus-sign-not-encoded-with-resttemplate-using-string-url-but-interpreted">HERE</a>
	 */
	public static class PlusEncoderInterceptor implements ClientHttpRequestInterceptor {
		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
			return execution.execute(new HttpRequestWrapper(request) {
				@Override
				public URI getURI() {
					URI u = super.getURI();
					String strictlyEscapedQuery = StringUtils.replace(u.getRawQuery(), "+", "%2B");
					return UriComponentsBuilder.fromUri(u)
							.replaceQuery(strictlyEscapedQuery)
							.build(true).toUri();
				}
			}, body);
		}
	}
}
