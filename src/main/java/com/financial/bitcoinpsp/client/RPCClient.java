package com.financial.bitcoinpsp.client;

import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class RPCClient {
	private static final String COMMAND_GET_BALANCE = "getbalance";
	private static final String COMMAND_GET_INFO = "getinfo";
	private static final String COMMAND_GET_NEW_ADDRESS = "getnewaddress";
	private static final String RESULT_PROPERTY = "result";

	@Value("${rpc.userName}")
	private String rpcUserName;
	@Value("${rpc.userPassword}")
	private String rpcUserPassword;
	@Value("${rpc.url}")
	private String rpcUrl;

	public Double getBalance(String account) {
		String[] params = {account};
		JSONObject json = invokeRPC(UUID.randomUUID().toString(), COMMAND_GET_BALANCE, Arrays.asList(params));
		return (Double) json.get(RESULT_PROPERTY);
	}

	public String getNewAddress(String account) {
		String[] params = {account};
		JSONObject json = invokeRPC(UUID.randomUUID().toString(), COMMAND_GET_NEW_ADDRESS, Arrays.asList(params));
		return (String) json.get(RESULT_PROPERTY);
	}

	public JSONObject getInfo() {
		JSONObject json = invokeRPC(UUID.randomUUID().toString(), COMMAND_GET_INFO, null);
		return (JSONObject) json.get(RESULT_PROPERTY);
	}

	@SneakyThrows
	private JSONObject invokeRPC(String id, String method, List<String> params) {
		CloseableHttpClient httpclient = getCloseableHttpClient();

		//TODO consider to use Jackson ot Gson instead of JSON.simple
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("method", method);
		if (null != params) {
			JSONArray array = new JSONArray();
			array.addAll(params);
			json.put("params", params);
		}

		StringEntity myEntity = new StringEntity(json.toJSONString());
		System.out.println(json.toString());
		HttpPost httppost = new HttpPost(rpcUrl);
		httppost.setEntity(myEntity);

		System.out.println("executing request" + httppost.getRequestLine());
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();

		System.out.println("----------------------------------------");
		System.out.println(response.getStatusLine());
		if (entity != null) {
			System.out.println("Response content length: " + entity.getContentLength());
		}
		JSONParser parser = new JSONParser();
		return (JSONObject) parser.parse(EntityUtils.toString(entity));
	}

	private CloseableHttpClient getCloseableHttpClient() {
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(rpcUserName, rpcUserPassword);
		provider.setCredentials(AuthScope.ANY, credentials);
		return HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
	}
}