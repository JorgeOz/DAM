package repaso;

import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Ejercicio14 {
    public static void main(String[] args) {
    	
        MongoClient cliente = MongoClients.create();
        MongoDatabase db = cliente.getDatabase("Biblioteca");
        MongoCollection<Document> col_libros = db.getCollection("Libros");

        Bson filtro;
        MongoCursor<Document> cursor;

		Document autor = null;
		
        filtro = Filters.or(Filters.eq("autor.nacionalidad", "Americano"), Filters.eq("editorial", "Planeta"));
        cursor = col_libros.find(filtro).iterator();
        while (cursor.hasNext()) {
            Document d = cursor.next();
            autor = (Document) d.get("autor");
            System.out.println(autor.get("nombre") + " " + autor.get("apellido") + " - " + d.get("titulo") + " - " + d.get("año_publicacion"));
        }

        cliente.close();
    }
}