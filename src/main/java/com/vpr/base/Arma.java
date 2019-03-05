package com.vpr.base;

import org.bson.types.ObjectId;

public class Arma {
	// Atributos
	private ObjectId _id;
	private String nombre;
	private int fuerza;
	private int duracion;
	
	
	// Constructor
	public Arma() {
		
	}

	// Metodos
	public ObjectId getId() {
		return _id;
	}


	public String getNombre() {
		return nombre;
	}


	public int getFuerza() {
		return fuerza;
	}


	public int getDuracion() {
		return duracion;
	}


	public void setId(ObjectId id) {
		this._id = id;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public void setFuerza(int fuerza) {
		this.fuerza = fuerza;
	}


	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	
	public Arma clone() {
		Arma a = new Arma();
		a.setNombre(this.nombre);
		a.setFuerza(this.fuerza);
		a.setDuracion(this.duracion);
		
		return a;
	}

	@Override
	public String toString() {
		return nombre;
	}
	
}
