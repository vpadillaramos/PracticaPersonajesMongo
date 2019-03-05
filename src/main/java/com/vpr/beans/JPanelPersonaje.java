package com.vpr.beans;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.vpr.base.Arma;
import com.vpr.base.Personaje;
import com.vpr.principal.Modelo;
import com.vpr.util.Util;

public class JPanelPersonaje extends JPanel implements ActionListener, ListSelectionListener, MouseListener, DocumentListener {
	public JBotonesCrud botonesCrud;
	public JLabel lblNombre;
	public JLabel lblAtaque;
	public JLabel lblVida;
	public JLabel lblDefensa;
	public JTextField tfNombre;
	public JTextField tfAtaque;
	public JTextField tfDefensa;
	public JPanelBusqueda panelBusqueda;
	public JPanelAnadirArma panelAnadirArma;
	public JLabel lblArma;
	
	//Atributos
	private enum Accion {
		NUEVO, MODIFICAR, DESHACER
	}
	private Accion accion;
	private Personaje personajeActual;
	private byte[] imagen;
	public JButton btDeshacer;
	public JTextField tfVida;
	

	public JPanelPersonaje() {
		setLayout(null);
		
		botonesCrud = new JBotonesCrud();
		botonesCrud.btModificar.setLocation(10, 145);
		botonesCrud.btCancelar.setLocation(10, 44);
		botonesCrud.btGuardar.setLocation(10, 111);
		botonesCrud.setBounds(222, 87, 104, 231);
		add(botonesCrud);
		
		lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(16, 24, 51, 14);
		add(lblNombre);
		
		lblAtaque = new JLabel("Ataque");
		lblAtaque.setBounds(16, 92, 37, 14);
		add(lblAtaque);
		
		lblVida = new JLabel("Vida");
		lblVida.setBounds(16, 60, 43, 14);
		add(lblVida);
		
		lblDefensa = new JLabel("Defensa");
		lblDefensa.setBounds(16, 126, 63, 14);
		add(lblDefensa);
		
		tfNombre = new JTextField();
		tfNombre.setBounds(101, 21, 104, 20);
		add(tfNombre);
		tfNombre.setColumns(10);
		
		tfAtaque = new JTextField();
		tfAtaque.setBounds(101, 92, 104, 20);
		add(tfAtaque);
		tfAtaque.setColumns(10);
		
		tfDefensa = new JTextField();
		tfDefensa.setBounds(101, 123, 104, 20);
		add(tfDefensa);
		tfDefensa.setColumns(10);
		
		btDeshacer = new JButton("Deshacer borrado");
		btDeshacer.setActionCommand("DESHACER");
		btDeshacer.setBounds(353, 224, 151, 23);
		add(btDeshacer);
		
		panelBusqueda = new JPanelBusqueda();
		panelBusqueda.setBounds(353, 23, 151, 190);
		add(panelBusqueda);
		
		panelAnadirArma = new JPanelAnadirArma();
		panelAnadirArma.setBounds(16, 186, 198, 190);
		add(panelAnadirArma);
		
		lblArma = new JLabel("Armas");
		lblArma.setBounds(94, 161, 46, 14);
		add(lblArma);
		
		tfVida = new JTextField();
		tfVida.setBounds(101, 57, 104, 20);
		add(tfVida);
		tfVida.setColumns(10);
	}

	
	//Metodos
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btDeshacer) {
			deshacer();
			return;
		}
		
		switch(JBotonesCrud.Accion.valueOf(e.getActionCommand())) {
		case NUEVO:
			nuevoPokemon();
			break;
		case MODIFICAR:
			modificarPokemon();
			break;
		case GUARDAR:
			guardarPokemon();
			break;
		case CANCELAR:
			cancelar();
			break;
		case BORRAR:
			borrarPokemon();
			break;
		default:
			
			break;
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		//Si no hay nada seleccionado
		if(panelBusqueda.lista.getSelectedIndex() == -1)
			return;
		personajeActual = (Personaje) panelBusqueda.lista.getSelectedValue();
		rellenarCampos();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void iniciar() {
		modoEdicion(false);
		//refrescar();
		
		//Listeners
		botonesCrud.addListeners(this);
		btDeshacer.addActionListener(this);
		panelBusqueda.lista.addListSelectionListener(this);
		panelBusqueda.tfBuscar.getDocument().addDocumentListener(this);
	}
	
	public void refrescar() {
		Modelo modelo = new Modelo();
		
		//Ordeno los pokemon alfabeticamente
		List<Personaje> aux = modelo.getPersonajes();
		aux.sort(Comparator.comparing(Personaje::getNombre));
		panelBusqueda.refrescar(aux);
		panelAnadirArma.refrescar();
	}
	
	private void limpiar() {
		tfNombre.setText("");
		tfVida.setText("");
		tfAtaque.setText("");
		tfDefensa.setText("");
		panelAnadirArma.limpiar();
	}
	
	private void modoEdicion(boolean b) {
		if(b) {
			botonesCrud.modoEdicion(b);
			btDeshacer.setEnabled(!b);
			panelAnadirArma.modoEdicion(b);
			
			tfNombre.setEditable(b);
			tfVida.setEditable(b);
			tfAtaque.setEditable(b);
			tfDefensa.setEditable(b);
			
			panelBusqueda.lista.setEnabled(!b);
		}
		else {
			botonesCrud.modoEdicion(b);
			btDeshacer.setEnabled(!b);
			panelAnadirArma.modoEdicion(b);
			
			tfNombre.setEditable(b);
			tfVida.setEditable(b);
			tfAtaque.setEditable(b);
			tfDefensa.setEditable(b);
			
			panelBusqueda.lista.setEnabled(!b);
			panelBusqueda.lista.clearSelection();
		}
		
	}
	
	private List<Personaje> getListaPersonajes() {
		return Collections.list(panelBusqueda.modelo.elements());
	}
	
	
	private void rellenarCampos() {
		tfNombre.setText(personajeActual.getNombre());
		tfVida.setText(String.valueOf(personajeActual.getVida()));
		tfAtaque.setText(String.valueOf(personajeActual.getAtaque()));
		tfDefensa.setText(String.valueOf(personajeActual.getDefensa()));
		
		//Muestro las armas en la lista
		panelAnadirArma.anadirArmas(personajeActual.getArmas());
		
		//Quito las armas que ya tiene
		panelAnadirArma.cbArmas.refrescar(new Modelo().getArmas()); //TODO ordenar esta lista
		for(Arma arma : personajeActual.getArmas()) {
			panelAnadirArma.cbArmas.removeItem(arma);
		}
		
		botonesCrud.btModificar.setEnabled(true);
		botonesCrud.btBorrar.setEnabled(true);
	}
	
	// TODO
	private void deshacer() {
		Modelo modelo = new Modelo();
		if(modelo.deshacerPersonaje()) {
			refrescar();
			Util.mensajeInformacion("Hecho", "Pokemon recuperado");
		}
		else
			Util.mensajeInformacion("Deshacer", "Nada que deshacer");
	}
	
	// TODO
	private void borrarTodo() {
		Modelo modelo = new Modelo();
		
		if(!Util.mensajeConfirmacion("¡ATENCIÓN!", "¿Quieres borrar todos los pokemon?"))
			return;
		//modelo.borrarTodoPokemon();
		refrescar();
		Util.mensajeInformacion("Hecho", "Todos los pokemon han sido borrados correctamente");
	}
	
	private void nuevoPokemon() {
		limpiar();
		modoEdicion(true);
		tfNombre.requestFocus();
		accion = Accion.NUEVO;
	}
	
	private void modificarPokemon() {
		rellenarCampos();
		tfNombre.selectAll();
		tfNombre.requestFocus();
		modoEdicion(true);
		accion = Accion.MODIFICAR;
	}
	
	private void cancelar() {
		limpiar();
		refrescar();
		modoEdicion(false);
	}
	
	private void borrarPokemon() {
		Modelo modelo = new Modelo();
		Personaje personaje = null;
		personaje = (Personaje) panelBusqueda.getSeleccionado();
		modelo.borrar(personaje);
		Util.mensajeInformacion("Hecho", personaje.getNombre() + " eliminado correctamente");
		refrescar();
		limpiar();
		modoEdicion(false);
	}
	
	private void guardarPokemon() {
		Modelo modelo = new Modelo();
		
		//************Verificacion de datos****************
		if(tfNombre.getText().equals("")) {
			Util.mensajeError("Error", "El nombre es obligatorio");
			return;
		}
		
		if(tfVida.getText().isEmpty())
			tfVida.setText("0");
		if(!modelo.isInt(tfVida.getText())) {
			Util.mensajeError("Error", "La vida debe ser un entero");
			tfVida.selectAll();
			tfVida.requestFocus();
			return;
		}
		
		if(tfAtaque.getText().equals(""))
			tfAtaque.setText("0");
		if(!modelo.isInt(tfAtaque.getText())) {
			Util.mensajeError("Error", "El ataque debe ser un entero");
			tfAtaque.selectAll();
			tfAtaque.requestFocus();
			return;
		}
		
		if(tfDefensa.getText().equals(""))
			tfDefensa.setText("0");
		if(!modelo.isInt(tfDefensa.getText())) {
			Util.mensajeError("Error", "La defensa debe ser un entero");
			tfDefensa.selectAll();
			tfDefensa.requestFocus();
			return;
		}
		
		Personaje personaje = null;
		switch(accion) {
		case NUEVO:
			personaje = new Personaje();
			break;
		case MODIFICAR:
			personaje = personajeActual;
			break;
		default:
			
			break;
		}
		
		//Recogida de datos
		personaje.setNombre(tfNombre.getText().trim());
		personaje.setVida(Integer.parseInt(tfVida.getText().trim()));
		personaje.setAtaque(Integer.parseInt(tfAtaque.getText().trim()));
		personaje.setDefensa(Integer.parseInt(tfDefensa.getText().trim()));
		personaje.setArmas(panelAnadirArma.getListaArmas());

		if(accion == Accion.MODIFICAR) {
			Util.mensajeInformacion("Hecho", "Personaje modificada");
			modelo.modificar(personaje);
		}
		else {
			modelo.guardar(personaje);
			Util.mensajeInformacion("Hecho", "Personaje guardado");
		}
		
		refrescar();
		limpiar();
		modoEdicion(false);
	}


	@Override
	public void insertUpdate(DocumentEvent e) {
		String cadena = panelBusqueda.tfBuscar.getText().toLowerCase();
		
		for(Personaje p : getListaPersonajes()) {
			if(!p.getNombre().toLowerCase().contains(cadena)) 
				panelBusqueda.modelo.removeElement(p);
		}
	}


	@Override
	public void removeUpdate(DocumentEvent e) {
		String cadena = panelBusqueda.tfBuscar.getText().toLowerCase();
		Modelo modelo = new Modelo();
		for(Personaje p : modelo.getPersonajes()) {
			if(!panelBusqueda.modelo.contains(p) && p.getNombre().toLowerCase().contains(cadena))
				panelBusqueda.modelo.addElement(p);
		}
	}


	@Override
	public void changedUpdate(DocumentEvent e) {
		
	}
}
