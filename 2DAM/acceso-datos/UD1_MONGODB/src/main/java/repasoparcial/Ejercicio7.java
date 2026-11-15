package repasoparcial;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Ejercicio7 {
    public static void main(String[] args) {
        MongoClient cliente = MongoClients.create();
        MongoDatabase db = cliente.getDatabase("Biblioteca");
        MongoCollection<Document> col_prestamos = db.getCollection("Prestamos");

        long cantidad = col_prestamos.countDocuments(Filters.eq("socio.num_socio", "1"));
        System.out.println("Préstamos del Socio 1: " + cantidad);

        cliente.close();
    }
}
