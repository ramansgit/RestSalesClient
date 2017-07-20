package com.rest.sales.client;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SalesClientImpl implements SalesClientInterface {

	public void viewSalesData() {

		try {
			OkHttpClient client = new OkHttpClient();

			Request request = new Request.Builder().url(SalesClientConstants.SALES_SERVICE_REST_EP).get().build();

			Response response = client.newCall(request).execute();
			if (!response.isSuccessful()) {
				throw new IOException("Unexpected code " + response);
			}
			System.out.println(response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	public void uploadSalesExcelFile() {
		try {
			OkHttpClient client = new OkHttpClient();
			final MediaType MEDIA_TYPE_XLSX = MediaType
					.parse("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

			File file = new File("/Users/ramans/Downloads/Java-Test/seeded_excel_for_java_test.xlsx");
			RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("upload",
					"seeded_excel_for_java_test.xlsx", RequestBody.create(MEDIA_TYPE_XLSX, file)).build();

			Request request = new Request.Builder().url(SalesClientConstants.SALES_SERVICE_REST_EP).post(requestBody)
					.build();

			Response response = client.newCall(request).execute();
			if (!response.isSuccessful()) {
				throw new IOException("Unexpected code " + response);
			}
			System.out.println(response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// new SalesClientImpl().uploadSalesExcelFile();

		new SalesClientImpl().viewSalesData();
	}
}
