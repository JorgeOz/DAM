package repaso;

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

public class Ejercicio20 {
    public static void main(String[] args) {
        MongoClient cliente = MongoClients.create();
        MongoDatabase db = cliente.getDatabase("Biblioteca");
        MongoCollection<Document> col_libros = db.getCollection("Libros");
        
        Bson filtro;
        MongoCursor<Document> cursor;

        Document autor;
        
        filtro = Filters.eq("editorial", "Planeta");      
        cursor = col_libros.find(filtro).sort(Sorts.ascending("año_publicacion")).projection(Projections.fields(Projections.include("titulo", "autor"), Projections.excludeId())).iterator();
        while (cursor.hasNext()) {
            Document d = cursor.next();
            autor = (Document) d.get("autor");
            System.out.println(d.get("titulo") + " - " + autor.get("nombre") + " " + autor.get("apellido"));
        }

        cliente.close();
    }
}
