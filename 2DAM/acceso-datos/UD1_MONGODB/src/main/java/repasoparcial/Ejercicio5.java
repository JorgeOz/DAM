package repasoparcial;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

public class Ejercicio5 {
    public static void main(String[] args) {
    	
        MongoClient cliente = MongoClients.create();
        MongoDatabase db = cliente.getDatabase("Biblioteca");
        MongoCollection<Document> col_libros = db.getCollection("Libros");

        Bson filtro;
        MongoCursor<Document> cursor;

        filtro = Filters.gte("año_publicacion", 2000);
        cursor = col_libros.find(filtro).sort(Sorts.descending("año_publicacion")).projection(Projections.fields(Projections.include("titulo", "año_publicacion"), Projections.excludeId())).iterator();
        while (cursor.hasNext()) {
            Document d = cursor.next();
            System.out.println("Título: " + d.get("titulo") + " - " + "Año de publicación: " + d.get("año_publicacion"));
        }

        cliente.close();
    }
}
