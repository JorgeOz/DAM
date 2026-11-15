package repaso;

import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Ejercicio9 {
    public static void main(String[] args) {
    	
        MongoClient cliente = MongoClients.create();
        MongoDatabase db = cliente.getDatabase("Biblioteca");
        MongoCollection<Document> col_prestamos = db.getCollection("Prestamos");
        MongoCollection<Document> col_socios = db.getCollection("Socios");

        Bson filtro;
        MongoCursor<Document> cursor_prestamos;

        // Paso 1: préstamos del isbn 456779
        filtro = Filters.eq("libro.isbn", "456779");
        cursor_prestamos = col_prestamos.find(filtro).iterator();

        while (cursor_prestamos.hasNext()) {
            // Paso 2: sacar el subdocumento "socio" del préstamo
            Document socio = (Document) cursor_prestamos.next().get("socio");
            String cad = socio.getString("num_socio");

            // Paso 3: pasarlo a número
            Integer numero = Integer.parseInt(cad);

            // Paso 4: buscar ese número en Socios (first() devuelve un Document)
            Document s = (Document) col_socios.find(Filters.eq("Num_socio", numero)).first();
            System.out.println("Nombre: " + s.get("Nombre"));
        }

        cliente.close();
    }
}
