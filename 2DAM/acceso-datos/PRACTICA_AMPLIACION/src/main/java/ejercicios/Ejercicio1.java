package ejercicios;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

public class Ejercicio1 {
    public static void main(String[] args) {
    	
        Gson gson = new Gson();
        ArrayList<Persona> lista = new ArrayList<>();

        try (MongoClient cliente = MongoClients.create("mongodb://localhost:27017")) {
        	
            MongoDatabase db = cliente.getDatabase("Concesionari");
            MongoCollection<Document> personas = db.getCollection("Personas");

            // 1. Buscar las personas de London
            FindIterable<Document> resultado = personas.find(eq("City", "London"));

            // 2. Cada Document -> texto JSON -> objeto Persona
            for (Document doc : resultado) {
                String json = doc.toJson();
                Persona p = gson.fromJson(json, Persona.class);
                lista.add(p);
            }
        }

        // 3. Recorrer el ArrayList y mostrar
        for (Persona p : lista) {
            System.out.println(p);
        }
    }
}
