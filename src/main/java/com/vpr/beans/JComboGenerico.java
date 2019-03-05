package com.vpr.beans;

import java.util.List;

import javax.swing.JComboBox;

public class JComboGenerico<T> extends JComboBox<T>{
	
	//Atributos
	private List<T> datos;
	
	//Constructor
	public JComboGenerico() {
		super(); //heredo todo
	}
	
	//Metodos
	public void refrescar(List<T> datos) {
		removeAllItems();
		this.datos = datos;
		listar();
	}
	
	public void listar() {
		if(datos == null)
			return;
		for(T dato : datos)
			addItem(dato);
	}
	
	public T getDatoSeleccionada() {
		T dato = (T) getSelectedItem();
		return dato;
	}
}
