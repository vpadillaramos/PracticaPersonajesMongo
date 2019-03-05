package com.vpr.principal;


import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.vpr.base.Arma;
import com.vpr.base.Personaje;
import com.vpr.util.Constantes;

public class Modelo {
	// Atributos
	private MongoClient mongoClient;
	private MongoDatabase db;
	private static Personaje personajeBorrado;
	private static Arma armaBorrada;
	
	// Constructor
	public Modelo() {
		conectar();
	}
	
	// Metodos
	public void conectar() {
		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
				MongoClient.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		mongoClient = new MongoClient(Constantes.HOST,
				MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
		db = mongoClient.getDatabase(Constantes.DB);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		desconectar();
	}
	
	public void desconectar() {
		mongoClient.close();
	}
	
	public void guardar(Personaje personaje) {
		MongoCollection<Personaje> coleccionPersonajes = db.getCollection("personajes", Personaje.class);
		coleccionPersonajes.insertOne(personaje);
	}
	
	public void guardar(Arma arma) {
		MongoCollection<Arma> coleccionArmas = db.getCollection("armas", Arma.class);
		coleccionArmas.insertOne(arma);
	}
	
	public void modificar(Personaje personaje) {
		MongoCollection<Personaje> coleccionPersonajes = db.getCollection("personajes", Personaje.class);
		coleccionPersonajes.replaceOne(eq("_id", personaje.getId()), personaje);
	}
	
	public void modificar(Arma arma) {
		MongoCollection<Arma> coleccionArmas = db.getCollection("armas", Arma.class);
		coleccionArmas.replaceOne(eq("_id", arma.getId()), arma);
	}
	
	public void borrar(Personaje personaje) {
		personajeBorrado = personaje.clone();
		personajeBorrado.getArmas().clear();
		MongoCollection<Personaje> coleccionPersonajes = db.getCollection("personajes", Personaje.class);
		coleccionPersonajes.deleteOne(eq("_id", personaje.getId()));
	}
	
	public void borrar(Arma arma) {
		armaBorrada = arma.clone();
		
		MongoCollection<Arma> coleccionArmas = db.getCollection("armas", Arma.class);
		coleccionArmas.deleteOne(eq("_id", arma.getId()));
	}
	
	public List<Personaje> getPersonajes(){
		MongoCollection<Personaje> coleccionPersonajes = db.getCollection("personajes", Personaje.class);
		System.out.println("Tamaño: " + coleccionPersonajes.find().into(new ArrayList<Personaje>()).size());
		return coleccionPersonajes.find().into(new ArrayList<Personaje>());
	}
	
	public List<Arma> getArmas(){
		MongoCollection<Arma> coleccionArmas = db.getCollection("armas", Arma.class);
		return coleccionArmas.find().into(new ArrayList<Arma>());
	}
	
	public boolean deshacerPersonaje() {
		if(personajeBorrado != null) {
			guardar(personajeBorrado);
			personajeBorrado = null;
			return true;
		}
		
		return false;
	}
	
	public boolean deshacerArma() {
		if(armaBorrada != null) {
			guardar(armaBorrada);
			armaBorrada = null;
			return true;
		}
		return false;
	}
	
	/***
	 * Copmprueba si una cadena es un entero
	 * @param cadena
	 * @return
	 */
	public boolean isInt(String cadena) {
		boolean resultado = false;
		if(cadena.matches("\\d*"))	//esto es una expresion regular que comprueba si son numeros
			resultado = true;
		return resultado;
	}
	
	/***
	 * Comprueba si una cadena es float (detecta el punto)
	 * @param cadena
	 * @return
	 */
	public boolean isDecimal(String cadena) {
		boolean resultado = false;
		if(cadena.matches("\\d*\\.\\d*") || cadena.matches("\\d*\\,\\d*") || cadena.matches("\\d*"))	//esto es una expresion regular que comprueba si son numeros
			resultado = true;
		cadena = cadena.replaceAll(",", ".");
		return resultado;
	}
}
