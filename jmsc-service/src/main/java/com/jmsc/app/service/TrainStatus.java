package com.jmsc.app.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class TrainStatus {

	public String status(){
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
			.url("http://localhost:8001/jmsc/api/v1/party/bankaccount/allAccounts")
			.get()
			.addHeader("content-type", "application/json")
			.build();
		try {
			Response response = client.newCall(request).execute();
			return response.body().string();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}

}
