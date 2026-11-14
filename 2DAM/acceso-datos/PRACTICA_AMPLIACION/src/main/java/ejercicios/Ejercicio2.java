package ejercicios;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Ejercicio2 {
    public static void main(String[] args) throws Exception {
    	
        // 1. Leer el fichero con Gson (en UTF-8)
        JsonParser parser = new JsonParser();
        JsonArray platos = parser.parse(
            new InputStreamReader(new FileInputStream("Platos.json"), "UTF-8")
        ).getAsJsonArray();

        try (MongoClient cliente = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase db = cliente.getDatabase("Cocina");
            MongoCollection<Document> coleccion = db.getCollection("platos");

            // 2. Cada plato -> texto JSON -> Document -> insertOne
            for (JsonElement e : platos) {
                Document doc = Document.parse(e.toString());
                coleccion.insertOne(doc);
            }
        }

        System.out.println("Platos insertados: " + platos.size());
    }
}