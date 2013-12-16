package com.bd.Modelo;

/** Handler para listado.
 * @author Ramon Invarato Menéndez
 * www.jarroba.es
 */

public class Lista_entrada {
	private String id;
	private int idImagen; 
	private String textoEncima; 
	private String textoDebajo; 
	private int plazo;
	private boolean mov;
	
	
	  
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isMov() {
		return mov;
	}

	public int getPlazo() {
		return plazo;
	}

	public Lista_entrada (int idImagen, String textoEncima, String textoDebajo) { 
	    this.idImagen = idImagen; 
	    this.textoEncima = textoEncima; 
	    this.textoDebajo = textoDebajo; 
	}
	
	public Lista_entrada (int idImagen, String textoEncima) { 
	    this.idImagen = idImagen; 
	    this.textoEncima = textoEncima;
	    this.textoDebajo = "";
	}
	
	public Lista_entrada (int idImagen, String textoEncima, int plazo) { 
	    this.idImagen = idImagen; 
	    this.textoEncima = textoEncima; 
	    this.plazo=plazo;
	    this.textoDebajo = "";
	}
	public Lista_entrada (int idImagen, String textoEncima, int plazo,String id) { 
	    this.idImagen = idImagen; 
	    this.textoEncima = textoEncima; 
	    this.plazo=plazo;
	    this.textoDebajo = "";
	    this.id = id;
	}
	
	public Lista_entrada (boolean mov, String textoEncima, String textoDebajo,int plazo) { 
	    this.textoEncima = textoEncima; 
	    this.mov=mov;
	    this.textoDebajo = textoDebajo;
	    this.plazo=plazo;
	}
	public Lista_entrada ( String textoEncima, String textoDebajo) { 
	    this.textoEncima = textoEncima; 
	    this.textoDebajo = textoDebajo; 
	}
	
	
	public Lista_entrada (String textoEncima) { 
	    this.textoEncima = textoEncima; 
	}
	
	
	public String get_textoEncima() { 
	    return textoEncima; 
	}
	
	public String get_textoDebajo() { 
	    return textoDebajo; 
	}
	
	public int get_idImagen() {
	    return idImagen; 
	} 
	
	public void setIdImage(int Id){
		idImagen = Id;
	}
}
