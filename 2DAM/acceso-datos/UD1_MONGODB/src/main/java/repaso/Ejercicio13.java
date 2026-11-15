package repaso;

import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Ejercicio13 {
    public static void main(String[] args) {
        MongoClient cliente = MongoClients.create();
        MongoDatabase db = cliente.getDatabase("Biblioteca");
        MongoCollection<Document> col_socios = db.getCollection("Socios");

        Bson filtro;
        MongoCursor<Document> cursor;

        filtro = Filters.eq("Num_socio", 2);
        cursor = col_socios.find(filtro).iterator();
        while (cursor.hasNext()) {
            Document s = cursor.next();
            System.out.println("Nombre: " + s.get("Nombre"));
        }

        cliente.close();
    }
}
