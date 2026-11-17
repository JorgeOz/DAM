package ejercicio1MongoDB;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

public class Ejercicio1MongoDB {
    public static void main(String[] args) {
    	
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ciudad (Torrent, Alzira, Valencia): ");
        String ciudad = scanner.nextLine().trim();

        if (ciudad.equalsIgnoreCase("Torrente")) {
            ciudad = "Torrent";
        }

        MongoClient client = MongoClients.create();
        MongoDatabase db = client.getDatabase("Clinica");

        MongoCollection<Document> col_clinicas = db.getCollection("clinicas");
        MongoCollection<Document> col_odontologos = db.getCollection("odontologos");
        MongoCollection<Document> col_citas = db.getCollection("citas");
        MongoCollection<Document> col_clientes = db.getCollection("clientes");

        // 1. Saco una lista con los IDs de las clínicas de cada ciudad
        List<Integer> idsClinicas = new ArrayList<>();
        for (Document clinica : col_clinicas.find(eq("ciudad", ciudad))) {
            idsClinicas.add(clinica.getInteger("_id"));
        }

        // 2. Saco una lista con los IDs de los odontologos que trabajan en esas clinicas
        List<Integer> idsOdontologos = new ArrayList<>();
        for (Document odontologo : col_odontologos.find(in("id_clinica", idsClinicas))) {
            idsOdontologos.add(odontologo.getInteger("_id"));
        }

        // 3. Saco una lista con los IDs de los clientes de las citas REALIZADAS de esos odontólogos
        List<Integer> idsClientes = new ArrayList<>();
        for (Document cita : col_citas.find(and(in("id_odontologo", idsOdontologos),
                                            eq("estado", "REALIZADA")))) {
            Integer idCliente = cita.getInteger("id_cliente");
            
            if (!idsClientes.contains(idCliente)) {
                idsClientes.add(idCliente);
            }
        }

        // 4. Clientes con solo dni, nombre y apellidos en formato JSON
        List<String> listaJson = new ArrayList<>();
        for (Document cliente : col_clientes.find(in("_id", idsClientes))
                .projection(Projections.fields(
                        Projections.include("dni", "nombre", "apellidos"),
                        Projections.excludeId()))) {
            listaJson.add(cliente.toJson());
        }

        System.out.println("\n--- a) Clientes en formato JSON ---");
        for (String json : listaJson) {
            System.out.println(json);
        }

        // b) Del JSON a objetos Persona
        List<Persona> personas = new ArrayList<>();
        for (String json : listaJson) {
            Document d = Document.parse(json);
            Persona p = new Persona(d.getString("dni"),
                                    d.getString("nombre"),
                                    d.getString("apellidos"));
            personas.add(p);
        }

        System.out.println("\n--- b) Lista de Personas ---");
        for (Persona p : personas) {
            System.out.println(p);
        }

        client.close();
    }
}
