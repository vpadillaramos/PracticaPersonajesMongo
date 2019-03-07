package com.vpr.base;

import org.bson.types.ObjectId;

public class Arma {
	// Atributos
	private ObjectId _id;
	private String nombre;
	private int fuerza;
	private int duracion;
	private ObjectId personajeId;
	
	
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
	
	public ObjectId getPersonajeId() {
		return personajeId;
	}
	
	public void setPersonajeId(ObjectId personajeId) {
		this.personajeId = personajeId;
	}
	
	public Arma clone() {
		Arma a = new Arma();
		a.setNombre(this.nombre);
		a.setFuerza(this.fuerza);
		a.setDuracion(this.duracion);
		a.setPersonajeId(this.personajeId);
		
		return a;
	}

	@Override
	public int hashCode() {
		return nombre.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Arma))
			return false;
		Arma arma = (Arma) obj;
		
		return nombre.equals(arma.nombre);
	}

	@Override
	public String toString() {
		return nombre;
	}
	
	public String informacionCompleta() {
		return "Nombre: " + nombre + ", fuerza: " + fuerza + ", duración: " + duracion;
	}
	
}
