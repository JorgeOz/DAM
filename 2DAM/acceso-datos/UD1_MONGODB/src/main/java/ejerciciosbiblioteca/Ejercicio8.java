package ejerciciosbiblioteca;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Ejercicio8 {
    public static void main(String[] args) {
    	
        MongoClient cliente = MongoClients.create();
        MongoDatabase db = cliente.getDatabase("Biblioteca");
        MongoCollection<Document> col_prestamos = db.getCollection("Prestamos");

        Bson filtro;
        MongoCursor<Document> cursor;

		
        filtro = Filters.eq("libro.isbn", "446854");
        cursor = col_prestamos.find(filtro).iterator();
        while (cursor.hasNext()) {
            Document d = cursor.next();
            System.out.println("Documento: " + d.toJson());
        }

        cliente.close();
    }
	
}
