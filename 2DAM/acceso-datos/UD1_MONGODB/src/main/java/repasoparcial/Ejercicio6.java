package repasoparcial;

import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Ejercicio6 {
    public static void main(String[] args) {
        MongoClient cliente = MongoClients.create();
        MongoDatabase db = cliente.getDatabase("Biblioteca");
        MongoCollection<Document> col_libros = db.getCollection("Libros");

        long cantidad = col_libros.countDocuments(Filters.eq("editorial", "Debolsillo"));
        System.out.println("Libros de Debolsillo: " + cantidad);

        cliente.close();
    }
}
