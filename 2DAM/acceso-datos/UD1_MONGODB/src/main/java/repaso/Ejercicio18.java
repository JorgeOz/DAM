package repaso;

import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Ejercicio18 {
    public static void main(String[] args) {
        MongoClient cliente = MongoClients.create();
        MongoDatabase db = cliente.getDatabase("Biblioteca");
        MongoCollection<Document> col_libros = db.getCollection("Libros");
        MongoCollection<Document> col_prestamos = db.getCollection("Prestamos");
        MongoCollection<Document> col_socios = db.getCollection("Socios");

        // PASO 1: Libros -> sacar el isbn de «El Quijote» (un solo documento: first)
        Document libro = col_libros.find(Filters.eq("titulo", "El Quijote")).first();
        String isbn = libro.getString("isbn");                    // "456779"

        // PASO 2: Prestamos -> préstamos que contienen ese isbn (varios: cursor + while)
        MongoCursor<Document> cursor_prestamos =
                col_prestamos.find(Filters.eq("libro.isbn", isbn)).iterator();   // 2 préstamos

        while (cursor_prestamos.hasNext()) {

            // PASO 3: sacar el subdocumento "socio" y su num_socio (texto)
            Document socio = (Document) cursor_prestamos.next().get("socio");
            String cad = socio.getString("num_socio");            // "1", y luego "2"

            // PASO 4: pasarlo a número
            Integer numero = Integer.parseInt(cad);               // 1, y luego 2

            // PASO 5: Socios -> buscar por Num_socio (un solo documento: first)
            Document s = col_socios.find(Filters.eq("Num_socio", numero)).first();

            if (s != null) {
                System.out.println("Socio: " + s.get("Nombre"));
            }
        }

        cliente.close();
    }
}