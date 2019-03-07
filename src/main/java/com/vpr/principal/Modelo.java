package com.vpr.principal;


import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.vpr.base.Arma;
import com.vpr.base.Personaje;
import com.vpr.base.Usuario;
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

	/*@Override
	protected void finalize() throws Throwable {
		super.finalize();
		desconectar();
	}*/
	
	public void desconectar() {
		mongoClient.close();
	}
	
	public boolean iniciarSesion(Usuario usuario) {
		MongoCollection<Usuario> coleccionUsuarios = db.getCollection("usuarios", Usuario.class);
		return coleccionUsuarios.find().into(new ArrayList<Usuario>()).contains(usuario);
	}
	
	public boolean registrarUsuario(Usuario usuario) {
		MongoCollection<Usuario> coleccionUsuarios = db.getCollection("usuarios", Usuario.class);
		List<Usuario> listaUsuarios = new ArrayList<>();
		coleccionUsuarios.find().into(listaUsuarios);
		
		if(listaUsuarios.contains(usuario))
			return false;
		
		coleccionUsuarios.insertOne(usuario);
		return true;
	}
	
	public void guardar(Personaje personaje) {
		Document documento = new Document();
		documento.append("nombre", personaje.getNombre());
		documento.append("vida", personaje.getVida());
		documento.append("ataque", personaje.getAtaque());
		documento.append("defensa", personaje.getDefensa());
		documento.append("armasId", personaje.getArmasId());
		
		db.getCollection("personajes").insertOne(documento);
		ObjectId id = (ObjectId) documento.get("_id");
		personaje.setId(id); // le pongo la id asignada en la db para luego poder ponersela a sus armas
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
		// Borro el personajeId de toda las armas que tenga el personaje
		for(ObjectId armaId : personaje.getArmasId()) {
			Arma arma = getArma(armaId);
			arma.setPersonajeId(null);
			modificar(arma);
		}
		
		personaje.getArmasId().clear(); // se pierden todas las armas
		personajeBorrado = personaje.clone();
		
		MongoCollection<Personaje> coleccionPersonajes = db.getCollection("personajes", Personaje.class);
		coleccionPersonajes.deleteOne(eq("_id", personaje.getId()));
	}
	
	public void borrar(Arma arma) {
		// Borro el armaId que tiene el personaje de este arma
		if(arma.getPersonajeId() != null) {
			Personaje personaje = getPersonaje(arma.getPersonajeId());
			personaje.borrarArma(arma.getId());
			modificar(personaje);
		}
		arma.setPersonajeId(null); // una vez se borra un arma pierde la relacion con el personaje
		armaBorrada = arma.clone();
		
		MongoCollection<Arma> coleccionArmas = db.getCollection("armas", Arma.class);
		coleccionArmas.deleteOne(eq("_id", arma.getId()));
	}
	
	public List<Personaje> getPersonajes(){
		MongoCollection<Personaje> coleccionPersonajes = db.getCollection("personajes", Personaje.class);
		return coleccionPersonajes.find().into(new ArrayList<Personaje>());
	}
	
	public Personaje getPersonaje(ObjectId personajeId) {
		MongoCollection<Personaje> coleccionPersonajes = db.getCollection("personajes", Personaje.class);
		return coleccionPersonajes.find(eq("_id", personajeId)).first();
	}
	
	public List<Arma> getArmas() {
		MongoCollection<Arma> coleccionArmas = db.getCollection("armas", Arma.class);
		return coleccionArmas.find().into(new ArrayList<Arma>());
	}
	
	public Arma getArma(ObjectId armaId) {
		MongoCollection<Arma> coleccionArmas = db.getCollection("armas", Arma.class);
		return coleccionArmas.find(eq("_id", armaId)).first();
	}
	
	public List<Arma> getArmas(List<ObjectId> armasId) {
		MongoCollection<Arma> coleccionArmas = db.getCollection("armas", Arma.class);
		List<Arma> listaArmas = new ArrayList<>();
		// anado a la lista de armas segun su id
		for(ObjectId id : armasId) 
			listaArmas.add(coleccionArmas.find(eq("_id", id)).first());
		return listaArmas;
	}
	
	public List<Personaje> buscarPersonajes(String[] parametros){
		MongoCollection<Personaje> coleccionPersonajes = db.getCollection("personajes", Personaje.class);
		
		if(parametros.length == 1) 
			return coleccionPersonajes.find(eq("nombre", parametros[0])).into(new ArrayList<Personaje>());
		else if(parametros.length == 2)
			return coleccionPersonajes.find(and(eq("nombre", parametros[0]), eq("vida", Integer.parseInt(parametros[1])))).into(new ArrayList<Personaje>());
		return new ArrayList<Personaje>();
	}
	
	public List<Arma> getArmasLibres(){
		MongoCollection<Arma> coleccionArmas = db.getCollection("armas", Arma.class);
		// Compruebo que el campo personajeId no este en estas armas
		return coleccionArmas.find(Filters.exists("personajeId", false)).into(new ArrayList<Arma>());
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
	
	public long getNumeroPersonajes() {
		MongoCollection<Personaje> coleccionPersonajes = db.getCollection("personajes", Personaje.class);
		return coleccionPersonajes.countDocuments();
	}
	
	public long getNumeroArmas() {
		MongoCollection<Arma> coleccionArmas = db.getCollection("armas", Arma.class);
		return coleccionArmas.countDocuments();
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
