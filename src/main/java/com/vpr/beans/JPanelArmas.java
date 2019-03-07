package com.vpr.beans;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.vpr.base.Arma;
import com.vpr.base.Personaje;
import com.vpr.principal.Modelo;
import com.vpr.ui.Vista;
import com.vpr.util.Util;

public class JPanelArmas extends JPanel implements ActionListener, ListSelectionListener {
	public JBotonesCrud botonesCrud;
	public JLabel lblNombre;
	public JLabel lblFuerza;
	public JLabel lblDuracion;
	public JTextField tfNombre;
	public JTextField tfFuerza;
	public JTextField tfDuracion;
	public JScrollPane scrollPane;
	public JList<Arma> listArmas;
	public DefaultListModel<Arma> modelArma;
	
	private enum Accion{
		NUEVO, MODIFICAR, DESHACER
	}
	private Accion accion;
	private Arma armaActual;
	public JButton btDeshacer;

	public JPanelArmas() {
		setLayout(null);
		
		botonesCrud = new JBotonesCrud();
		botonesCrud.setBounds(10, 168, 210, 121);
		add(botonesCrud);
		
		
		lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(21, 37, 58, 14);
		add(lblNombre);
		
		lblFuerza = new JLabel("Fuerza");
		lblFuerza.setBounds(21, 77, 58, 14);
		add(lblFuerza);
		
		lblDuracion = new JLabel("Duraci\u00F3n");
		lblDuracion.setBounds(21, 113, 58, 14);
		add(lblDuracion);
		
		tfNombre = new JTextField();
		tfNombre.setBounds(89, 34, 96, 20);
		add(tfNombre);
		tfNombre.setColumns(10);
		
		tfFuerza = new JTextField();
		tfFuerza.setBounds(89, 74, 96, 20);
		add(tfFuerza);
		tfFuerza.setColumns(10);
		
		tfDuracion = new JTextField();
		tfDuracion.setBounds(89, 110, 96, 20);
		add(tfDuracion);
		tfDuracion.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(269, 31, 161, 192);
		add(scrollPane);
		
		listArmas = new JList<>();
		modelArma = new DefaultListModel<>();
		listArmas.setModel(modelArma);
		scrollPane.setViewportView(listArmas);
		
		btDeshacer = new JButton("Deshacer borrado");
		btDeshacer.setActionCommand("DESHACER");
		btDeshacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btDeshacer.setBounds(269, 232, 161, 23);
		add(btDeshacer);
		
	}
	
	public void iniciar() {
		//refrescarLista();
		modoEdicion(false);
		botonesCrud.addListeners(this);
		btDeshacer.addActionListener(this);
		listArmas.addListSelectionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btDeshacer) {
			deshacer();
			return;
		}
		
		switch(JBotonesCrud.Accion.valueOf(e.getActionCommand())) {
		case NUEVO:
			nuevaArma();
			break;
		case MODIFICAR:
			modificarArma();
			break;
		case GUARDAR:
			guardarArma();
			break;
		case CANCELAR:
			cancelar();
			break;
		case BORRAR:
			borrar();
			break;
		default:
			
			break;
		}
	}
	
	private void limpiar() {
		tfNombre.setText("");
		tfFuerza.setText("");
		tfDuracion.setText("");
	}
	
	public void rellenarCampos() {
		tfNombre.setText(armaActual.getNombre());
		tfFuerza.setText(String.valueOf(armaActual.getFuerza()));
		tfDuracion.setText(String.valueOf(armaActual.getDuracion()));
		
		botonesCrud.btModificar.setEnabled(true);
		botonesCrud.btBorrar.setEnabled(true);
	}
	
	public void modoEdicion(boolean b) {
		if(b) {
			botonesCrud.modoEdicion(b);
			btDeshacer.setEnabled(!b);
			
			tfNombre.setEditable(b);
			tfFuerza.setEditable(b);
			tfDuracion.setEditable(b);
		}
		else {
			botonesCrud.modoEdicion(b);
			btDeshacer.setEnabled(!b);
			
			tfNombre.setEditable(b);
			tfFuerza.setEditable(b);
			tfDuracion.setEditable(b);
		}
	}
	
	private void borrar() {
		Modelo modelo = new Modelo();
		modelo.borrar(listArmas.getSelectedValue());
		Util.mensajeInformacion("Hecho", listArmas.getSelectedValue().getNombre() + " eliminado correctamente");
		
		refrescar();
		limpiar();
		modoEdicion(false);
	}
	
	private void deshacer() {
		Modelo modelo = new Modelo();
		if(modelo.deshacerArma()) {
			refrescar();
			Util.mensajeInformacion("Hecho", "Arma recuperada");
		}
		else 
			Util.mensajeInformacion("Deshacer", "Nada que deshacer");
		
	}
	
	private void nuevaArma() {
		limpiar();
		modoEdicion(true);
		tfNombre.requestFocus();
		accion = Accion.NUEVO;
	}
	
	private void cancelar(){
		limpiar();
		modoEdicion(false);
	}
	
	public void guardarArma() {
		Modelo modelo = new Modelo();
		if(tfNombre.getText().equals("")) {
			Util.mensajeError("Error", "El nombre es obligatorio");
			tfNombre.selectAll();
			tfNombre.requestFocus();
			return;
		}
		
		if(tfFuerza.getText().trim().equals(""))
			tfFuerza.setText("0");
		if(!modelo.isInt(tfFuerza.getText().trim())) {
			Util.mensajeError("Error", "La fuerza debe ser entero");
			tfFuerza.selectAll();
			tfFuerza.requestFocus();
			return;
		}
		
		if(tfDuracion.getText().trim().equals(""))
			tfDuracion.setText("0");
		if(!modelo.isInt(tfDuracion.getText().trim())) {
			Util.mensajeError("Error", "La duración debe ser entera");
			tfDuracion.selectAll();
			tfDuracion.requestFocus();
			return;
		}
		
		Arma arma = null;
		switch(accion) {
		case NUEVO:
			arma = new Arma();
			break;
		case MODIFICAR:
			arma = armaActual;
			break;
			
		default:
			
			break;
		}
		
		//recogida de datos
		arma.setNombre(tfNombre.getText().trim());
		arma.setFuerza(Integer.parseInt(tfFuerza.getText().trim()));
		arma.setDuracion(Integer.parseInt(tfDuracion.getText().trim()));
		arma.setPersonajeId(null);
		
		if(accion == Accion.MODIFICAR) {
			modelo.modificar(arma);
			Util.mensajeInformacion("Hecho", "Arma modificada");
		}
		else {
			modelo.guardar(arma);
			Util.mensajeInformacion("Hecho", "Arma guardada");
		}
		
		refrescar();
		limpiar();
		modoEdicion(false);
	}
	
	private void modificarArma() {
		rellenarCampos();
		modoEdicion(true);
		accion = Accion.MODIFICAR;
	}
	
	public void refrescar() {
		modelArma.removeAllElements();
		Modelo modelo = new Modelo();
		List<Arma> aux = modelo.getArmas();
		aux.sort(Comparator.comparing(Arma::getNombre));
		for(Arma a: aux)
			modelArma.addElement(a);
		
		Vista.estado.setMensajeInformativo("Armas totales: " + modelo.getNumeroArmas());
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		//Si no hay nada seleccionado
		if(listArmas.getSelectedIndex() == -1)
			return;
		
		armaActual = listArmas.getSelectedValue();
		rellenarCampos();
		Modelo modelo = new Modelo();
		if(armaActual.getPersonajeId() != null) {
			Personaje personaje = modelo.getPersonaje(armaActual.getPersonajeId());
			Vista.estado.setMensajeInformativo(armaActual.informacionCompleta() + ". Pertenezco a " + personaje.getNombre());
		}
		else
			Vista.estado.setMensajeInformativo(armaActual.informacionCompleta());
	}
}
