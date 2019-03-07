package com.vpr.ui;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.vpr.base.Arma;
import com.vpr.base.Personaje;
import com.vpr.beans.JEstado;
import com.vpr.beans.JPanelArmas;
import com.vpr.beans.JPanelPersonaje;


public class Vista extends JFrame {
	public JPanel panel;
	public static JEstado estado;
	public JTabbedPane tabbedPane;
	public JPanelArmas panelArmas;
	public JPanelPersonaje panelPersonaje;

	public Vista() {
		setSize(548,498);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		estado = new JEstado();
		estado.setMensajeInformativo("Personajes MongoDB 1.0");
		getContentPane().add(estado, BorderLayout.SOUTH);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		panelPersonaje = new JPanelPersonaje();
		panelPersonaje.lblAtaque.setBounds(16, 92, 51, 14);
		panelPersonaje.botonesCrud.setLocation(222, 97);
		tabbedPane.addTab("Personaje", null, panelPersonaje, null);
		
		panelArmas = new JPanelArmas();
		tabbedPane.addTab("Arma", null, panelArmas, null);
		
		setResizable(false);
		setLocationRelativeTo(null);
		
		// Eventos
		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if(tabbedPane.getSelectedIndex() == 0) {
					panelPersonaje.refrescar();
				}
				else if(tabbedPane.getSelectedIndex() == 1){
					panelArmas.refrescar();
				}
				else
					estado.setMensajeInformativo("Bienvenido");
			}
			
		});
		
		repaint();
	}
	
	//Metodos
	public void hacerVisible(boolean b) {
		setVisible(b);
	}
	
	public void refrescar() {
		panelArmas.iniciar();
		panelArmas.refrescar();
		panelPersonaje.iniciar();
		panelPersonaje.refrescar();
	}
}

