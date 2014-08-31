package com.bd.Modelo;

public class Dream {
	
	private int Id;
	private String Dream;
	private int isURL;
	private String URL;
	private int idImg;
	private String Date;
	private int total_Saving;
	
	
	public Dream(int id, String dream, int isURL, String uRL, int idImg,
			String date, int saving) {
		super();
		Id = id;
		Dream = dream;
		this.isURL = isURL;
		URL = uRL;
		this.idImg = idImg;
		Date = date;
		total_Saving = saving;
	}


	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}


	public String getDream() {
		return Dream;
	}


	public int getIsURL() {
		return isURL;
	}


	public String getURL() {
		return URL;
	}


	public int getIdImg() {
		return idImg;
	}


	public String getDate() {
		return Date;
	}
	
	public int getSaving(){
		return total_Saving;
	}
	
	public void setSaving(int saving){
		total_Saving = saving;
	}
	
	
	
}
