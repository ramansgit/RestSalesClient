package com.sales.client.rest;

import java.io.File;
import java.io.IOException;

import com.sales.client.constants.SalesClientConstants;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SalesRestClientImpl implements SalesRestClient {

	/**
	 * allows client to view sales data
	 */
	public void viewSalesData(String clientId) {

		try {
			OkHttpClient client = new OkHttpClient();

			Request request = new Request.Builder().url(SalesClientConstants.SALES_SERVICE_REST_EP).get()
					.addHeader("clientId", clientId).build();

			Response response = client.newCall(request).execute();
			if (!response.isSuccessful()) {
				throw new IOException("view sales data rest call failed " + response);
			}
			System.out.println(response.body().string());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * allows client to upload sales file
	 * 
	 * @param clientId
	 * @param filePath
	 * @param fileName
	 */
	public void uploadSalesExcelFile(String clientId, String filePath, String fileName) {
		File file = null;
		try {
			OkHttpClient client = new OkHttpClient();
			final MediaType MEDIA_TYPE_XLSX = MediaType.parse(SalesClientConstants.FILE_MEDIA_TYPE);

			file = new File(filePath);
			RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
					.addFormDataPart("upload", fileName, RequestBody.create(MEDIA_TYPE_XLSX, file)).build();

			Request request = new Request.Builder().url(SalesClientConstants.SALES_SERVICE_REST_EP)
					.addHeader("clientId", clientId).post(requestBody).build();

			Response response = client.newCall(request).execute();
			if (!response.isSuccessful()) {
				throw new IOException("upload sales file xlsx file rest call failed " + response);
			}
			System.out.println(response.body().string());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

}
