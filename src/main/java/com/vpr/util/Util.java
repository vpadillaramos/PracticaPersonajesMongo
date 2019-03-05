package com.vpr.util;

import javax.swing.JOptionPane;

public class Util {
	/***
	 * Metodo que muestra una ventana de mensaje informativo
	 * @param titulo Titulo de la ventana
	 * @param mensaje Mensaje que se mostrara en la ventana
	 */
	public static void mensajeInformacion(String titulo, String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
	}
	
	/***
	 * Metodo que muestra una ventana de mensaje de error
	 * @param titulo Titulo de la ventana
	 * @param mensaje Mensaje de error
	 */
	public static void mensajeError(String titulo, String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
	}
	
	
	/**
	 * Metodo que muestra una ventana de confirmacion Si o No
	 * @param titulo
	 * @param mensaje
	 * @return Devuelve true si YES, sino false
	 */
	public static boolean mensajeConfirmacion(String titulo, String mensaje) {
		int confirmacion = JOptionPane.showConfirmDialog(null, mensaje, titulo, JOptionPane.YES_NO_OPTION);
		if(confirmacion == JOptionPane.NO_OPTION || confirmacion == JOptionPane.CLOSED_OPTION)
			return false;
		
		return true;
	}
}
