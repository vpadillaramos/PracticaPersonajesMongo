package com.vpr.principal;

import com.vpr.base.Usuario;
import com.vpr.ui.Login;
import com.vpr.ui.Vista;
import com.vpr.util.Util;
import com.vpr.ui.Login.Accion;

public class Controlador {
	
	// Atributos
	private Modelo modelo;
	private Vista vista;
	
	// Constructor
	public Controlador(Modelo modelo, Vista vista) {
		this.modelo = modelo;
		this.vista = vista;
		
		iniciarSesion();
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
	
	private void iniciarSesion() {
		boolean autenticado = false;
		boolean registrado = false;
		Login login = new Login();
		
		do {
			login.hacerVisible(true);
			Usuario usuario = new Usuario();
			usuario.setNombre(login.getUsuario());
			usuario.setContrasena(login.getContrasena());
			
			if(login.getAccion().equals(Accion.LOGIN)) {
				autenticado = modelo.iniciarSesion(usuario);
				if(!autenticado) {
					login.mensajeError("Error en el usuario o contraseña");
					continue;
				}
			}
			else if(login.getAccion().equals(Accion.REGISTRAR)) {
				registrado = modelo.registrarUsuario(usuario);
				if(!registrado) {
					login.mensajeError("El usuario ya existe");
					continue;
				}
				else
					Util.mensajeInformacion("Registrado", "Registrado correctamente");
			}
			
		}while(!autenticado && !registrado);
	}
	
}
