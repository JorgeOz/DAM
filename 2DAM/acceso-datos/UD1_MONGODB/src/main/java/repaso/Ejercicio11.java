package repaso;

import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Ejercicio11 {
    public static void main(String[] args) {
        MongoClient cliente = MongoClients.create();
        MongoDatabase db = cliente.getDatabase("Biblioteca");
        MongoCollection<Document> col_libros = db.getCollection("Libros");

        Bson filtro;
        MongoCursor<Document> cursor;

        filtro = Filters.and(Filters.gte("año_publicacion", 2010), Filters.lte("año_publicacion", 2020));
        cursor = col_libros.find(filtro).iterator();
        while (cursor.hasNext()) {
            Document d = cursor.next();
            System.out.println(d.get("titulo") + " - " + d.get("año_publicacion") + " - " + d.get("páginas"));
        }

        cliente.close();
    }
}
