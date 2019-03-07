package com.vpr.base;

import org.bson.types.ObjectId;

public class Usuario {
	// Atributos
	private ObjectId _id;
	private String nombre;
	private String contrasena;
	
	// Constructor
	public Usuario() {
		
	}
	
	// Metodos
	public ObjectId getId() {
		return _id;
	}
	
	public void setId(ObjectId id) {
		this._id = id;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	@Override
	public int hashCode() {
		return nombre.hashCode() + contrasena.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Usuario))
			return false;
		Usuario usuario = (Usuario) obj;
		return (nombre.equals(usuario.getNombre()) && contrasena.equals(usuario.getContrasena()));
	}
	
	
	
}
