package repasoparcial;

import org.bson.Document;
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
        MongoCollection<Document> col_libros = db.getCollection("Libros");
        MongoCollection<Document> col_prestamos = db.getCollection("Prestamos");
        MongoCollection<Document> col_socios = db.getCollection("Socios");

        // 1. Libros: de título a isbn (un solo documento -> first)
        Document libro = col_libros.find(Filters.eq("titulo", "Los pilares de la tierra")).first();
        String isbn = libro.getString("isbn");                       // "446854"

        // 2. Prestamos: los préstamos que llevan ese isbn (varios -> cursor)
        MongoCursor<Document> cursor_prestamos =
                col_prestamos.find(Filters.eq("libro.isbn", isbn)).iterator();

        while (cursor_prestamos.hasNext()) {
            // 3. De cada préstamo, el número de socio (texto)
            Document socio = (Document) cursor_prestamos.next().get("socio");
            String textoNum = socio.getString("num_socio");          // "1", luego "2"

            // 4. A número, porque en Socios es un número
            Integer numero = Integer.parseInt(textoNum);

            // 5. Socios: de número a nombre (un solo documento -> first)
            Document s = col_socios.find(Filters.eq("Num_socio", numero)).first();
            if (s != null) {
                System.out.println("Socio: " + s.get("Nombre"));
            }
        }

        cliente.close();
    }
}
