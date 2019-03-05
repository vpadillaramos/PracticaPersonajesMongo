package com.vpr.principal;

import com.vpr.ui.Vista;

public class Controlador {
	
	// Atributos
	private Modelo modelo;
	private Vista vista;
	
	// Constructor
	public Controlador(Modelo modelo, Vista vista) {
		this.modelo = modelo;
		this.vista = vista;
		
		iniciar();
	}
	
	// Metodos
	
	private void iniciar() {
		System.out.println("Conectado");
		vista.hacerVisible(true);
		refrescar();
	}
	
	
	private void refrescar() {
		vista.refrescar();
	}
	
}
