package repasoparcial;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Ejercicio3 {
    public static void main(String[] args) {
    	
        MongoClient cliente = MongoClients.create();
        MongoDatabase db = cliente.getDatabase("Biblioteca");
        MongoCollection<Document> col_libros = db.getCollection("Libros");

        Bson filtro;
        MongoCursor<Document> cursor;

        filtro = Filters.lt("año_publicacion", 1990);
        cursor = col_libros.find(filtro).iterator();
        while (cursor.hasNext()) {
            Document d = cursor.next();
            System.out.println("Título: " + d.get("titulo"));
        }

        cliente.close();
    }
}
