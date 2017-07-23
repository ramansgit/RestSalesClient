package com.sales.client.rest;

public abstract interface SalesRestClient {

	/**
	 * allows client to view sales data
	 * 
	 * @return
	 */
	public abstract void viewSalesData(String clientId);
	
	/**
	 * allows client to upload sales excel file
	 */
	public abstract void uploadSalesExcelFile(String clientId);

}
