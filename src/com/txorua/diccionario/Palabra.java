package com.txorua.diccionario;

public class Palabra {
	String palabra;
	String definicion;
	
	public Palabra() {
		
	}
	
	public Palabra(String palabra, String definicion) {
		this.palabra = palabra;
		this.definicion = definicion;
	}
	
	public String getPalabra() {
		return this.palabra;
	}
	
	public void setPalabra(String palabra) {
		this.palabra = palabra;
	}
	
	public String getDefinicion() {
		return this.definicion;
	}
	
	public void setDefinicion(String definicion) {
		this.definicion = definicion;
	}

}
