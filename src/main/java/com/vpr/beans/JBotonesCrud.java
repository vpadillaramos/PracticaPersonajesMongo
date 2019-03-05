package com.vpr.beans;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class JBotonesCrud extends JPanel {
	public JButton btNuevo;
	public JButton btGuardar;
	public JButton btCancelar;
	public JButton btModificar;
	public JButton btBorrar;
	
	public static enum Accion{
		NUEVO, MODIFICAR, GUARDAR, CANCELAR, BORRAR
	}

	public JBotonesCrud() {
		setLayout(null);
		
		btNuevo = new JButton("Nuevo");
		btNuevo.setActionCommand(Accion.NUEVO.toString());
		btNuevo.setBounds(10, 10, 89, 23);
		add(btNuevo);
		
		btGuardar = new JButton("Guardar");
		btGuardar.setBounds(118, 10, 89, 23);
		btGuardar.setActionCommand(Accion.GUARDAR.toString());
		add(btGuardar);
		
		btCancelar = new JButton("Cancelar");
		btCancelar.setBounds(10, 48, 89, 23);
		btCancelar.setActionCommand(Accion.CANCELAR.toString());
		add(btCancelar);
		
		btModificar = new JButton("Modificar");
		btModificar.setBounds(118, 48, 89, 23);
		btModificar.setActionCommand(Accion.MODIFICAR.toString());
		add(btModificar);
		
		btBorrar = new JButton("Borrar");
		btBorrar.setBounds(10, 77, 89, 23);
		btBorrar.setActionCommand(Accion.BORRAR.toString());
		add(btBorrar);
	}
	
	public void addListeners(ActionListener l) {
		btNuevo.addActionListener(l);
		btGuardar.addActionListener(l);
		btCancelar.addActionListener(l);
		btModificar.addActionListener(l);
		btBorrar.addActionListener(l);
	}
	
	public void modoEdicion(boolean b) {
		if(b) {
			btNuevo.setEnabled(!b);
			btModificar.setEnabled(!b);
			btGuardar.setEnabled(b);
			btCancelar.setEnabled(b);
			btBorrar.setEnabled(!b);
		}
		else {
			btNuevo.setEnabled(!b);
			btModificar.setEnabled(b);
			btGuardar.setEnabled(b);
			btCancelar.setEnabled(b);
			btBorrar.setEnabled(b);
		}
	}
}
