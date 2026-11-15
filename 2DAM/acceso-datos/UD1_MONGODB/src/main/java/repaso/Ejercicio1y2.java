package repaso;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Ejercicio1y2 {
    public static void main(String[] args) {
    	
        try (MongoClient cliente = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase db = cliente.getDatabase("Biblioteca");
            MongoCollection<Document> libros = db.getCollection("Libros");

            // Ejercicio 1: el primer libro
            Document primero = libros.find().first();
            System.out.println(primero.toJson());

            // Ejercicio 2: todos los libros
            for (Document d : libros.find()) {
                System.out.println(d.getString("titulo"));
            }
        }
    }
}