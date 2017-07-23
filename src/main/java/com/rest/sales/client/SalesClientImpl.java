package com.rest.sales.client;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.framed.Header;

public class SalesClientImpl implements SalesClientInterface {

	public void viewSalesData(String clientId) {

		try {
			OkHttpClient client = new OkHttpClient();

			Request request = new Request.Builder().url(SalesClientConstants.SALES_SERVICE_REST_EP).get()
					.addHeader("clientId", clientId).build();

			Response response = client.newCall(request).execute();
			if (!response.isSuccessful()) {
				throw new IOException("Unexpected code " + response);
			}
			System.out.println(response.body().string());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void uploadSalesExcelFile(String clientId) {
		File file = null;
		try {
			OkHttpClient client = new OkHttpClient();
			final MediaType MEDIA_TYPE_XLSX = MediaType
					.parse("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

			file = new File("/Users/ramans/Downloads/Java-Test/seeded_excel_for_java_test.xlsx");
			RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("upload",
					"seeded_excel_for_java_test.xlsx", RequestBody.create(MEDIA_TYPE_XLSX, file)).build();

			Request request = new Request.Builder().url(SalesClientConstants.SALES_SERVICE_REST_EP)
					.addHeader("clientId", clientId).post(requestBody).build();

			Response response = client.newCall(request).execute();
			if (!response.isSuccessful()) {
				throw new IOException("Unexpected code " + response);
			}
			System.out.println(response.body().string());

		} catch (Exception e) {
			System.out.println("exception");
			e.printStackTrace();
		} finally {

		}

	}

	public static void main(String[] args) {
		 new SalesClientImpl().uploadSalesExcelFile("client");

		// new SalesClientImpl().viewSalesData();
	}

	
}
