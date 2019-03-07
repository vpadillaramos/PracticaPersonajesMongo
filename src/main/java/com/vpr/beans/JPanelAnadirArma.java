package com.vpr.beans;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.vpr.base.Arma;
import com.vpr.principal.Modelo;

public class JPanelAnadirArma extends JPanel implements ActionListener{
	public JPanel panel;
	public JComboGenerico<Arma> cbArmas;
	public JButton btAnadir;
	public JScrollPane scrollPane;
	public JList<Arma> lista;
	public DefaultListModel modelLista;
	public JButton btEliminar;

	public JPanelAnadirArma() {
		setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		cbArmas = new JComboGenerico<>();
		cbArmas.setPreferredSize(new Dimension(100, 20));
		cbArmas.setMinimumSize(new Dimension(100, 20));
		panel.add(cbArmas);
		
		btAnadir = new JButton("+");
		panel.add(btAnadir);
		
		btEliminar = new JButton("-");
		panel.add(btEliminar);
		
		scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		lista = new JList<>();
		modelLista = new DefaultListModel();
		lista.setModel(modelLista);
		scrollPane.setViewportView(lista);
		
		inicializar();
	}
	
	//Metodos
	private void inicializar() {
		Modelo modelo = new Modelo();
		
		//Ordeno las armas alfabeticamente en el combobox
		/*List<Arma> aux = modelo.getArmas();
		aux.sort(Comparator.comparing(Arma::getNombre));
		cbArmas.refrescar(aux);*/
		
		btAnadir.addActionListener(this);
		btEliminar.addActionListener(this);
	}
	
	public void modoEdicion(boolean b) {
		if(b) {
			lista.setEnabled(b);
			cbArmas.setEnabled(b);
			btAnadir.setEnabled(b);
			btEliminar.setEnabled(b);
		}
		else {
			lista.setEnabled(b);
			cbArmas.setEnabled(b);
			btAnadir.setEnabled(b);
			btEliminar.setEnabled(b);
		}
	}
	
	public List<Arma> getListaArmas() {
		return Collections.list(modelLista.elements());
	}
	
	public void anadirArmas(List<Arma> armas) {
		modelLista.removeAllElements();
		for(Arma a: armas)
			modelLista.addElement(a);
	}
	
	public void limpiar() {
		modelLista.removeAllElements();
	}
	
	public void refrescar() {
		Modelo modelo = new Modelo();
		modelLista.removeAllElements();
		
		List<Arma> aux = modelo.getArmasLibres();
		aux.sort(Comparator.comparing(Arma::getNombre));
		cbArmas.refrescar(aux);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(e.getActionCommand()) {
		case "+":
			Arma armaSeleccionada = cbArmas.getDatoSeleccionada();
			if(armaSeleccionada == null)
				return;
			if(modelLista.contains(armaSeleccionada))
				return;
			
			modelLista.addElement(armaSeleccionada);
			cbArmas.removeItem(armaSeleccionada);
			break;
		case "-":
			if(lista.getSelectedIndex() == -1)
				return;
			/*Modelo modelo = new Modelo();
			Arma arma = lista.getSelectedValue();
			arma.setPokemon(null);
			modelo.modificarArma(arma);*/
			cbArmas.addItem((Arma) modelLista.remove(lista.getSelectedIndex()));
			
			break;
		}
	}
}
