package com.vpr.base;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

public class Personaje {
	// Atributos
	private ObjectId _id;
	private String nombre;
	private int vida;
	private int ataque;
	private int defensa;
	private List<ObjectId> armasId;
	
	
	// Constructor
	public Personaje() {
		armasId = new ArrayList<>();
	}


	// Metodos
	public ObjectId getId() {
		return _id;
	}


	public String getNombre() {
		return nombre;
	}


	public int getVida() {
		return vida;
	}


	public int getAtaque() {
		return ataque;
	}


	public int getDefensa() {
		return defensa;
	}


	public List<ObjectId> getArmasId() {
		return armasId;
	}


	public void setId(ObjectId id) {
		this._id = id;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public void setVida(int vida) {
		this.vida = vida;
	}


	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}


	public void setDefensa(int defensa) {
		this.defensa = defensa;
	}


	public void setArmasId(List<ObjectId> armasId) {
		this.armasId.clear();
		this.armasId.addAll(armasId);
	}
	
	public void borrarArma(ObjectId armaId) {
		armasId.remove(armaId);
	}
	
	public Personaje clone() {
		Personaje p = new Personaje();
		p.setNombre(this.nombre);
		p.setVida(this.vida);
		p.setAtaque(this.ataque);
		p.setDefensa(this.defensa);
		p.setArmasId(this.armasId);
		
		return p;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	
	public String informacionCompleta() {
		return "Nombre: " + nombre + ", vida: " + vida + ", ataque: " + ataque + ", defensa: " + defensa;
	}
	
}
