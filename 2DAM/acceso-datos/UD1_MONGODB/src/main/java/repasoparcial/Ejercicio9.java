package repasoparcial;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Ejercicio9 {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        MongoClient cliente = MongoClients.create();
        MongoDatabase db = cliente.getDatabase("Biblioteca");
        MongoCollection<Document> col_libros = db.getCollection("Libros");
        MongoCollection<Document> col_prestamos = db.getCollection("Prestamos");
        MongoCollection<Document> col_socios = db.getCollection("Socios");

        // 1. Socios: de nombre a número (un solo documento -> first)
        Document socio = col_socios.find(Filters.eq("Nombre", "Carlos Cacho")).first();
        int numero = ((Number) socio.get("Num_socio")).intValue();   // 2

        // 2. Prestamos: los préstamos de ese socio. En Prestamos el número es TEXTO
        MongoCursor<Document> cursor_prestamos = col_prestamos
                .find(Filters.eq("socio.num_socio", String.valueOf(numero))).iterator();

        // Un Set no admite repetidos: un libro prestado dos veces sale una sola vez
        Set<String> isbns = new LinkedHashSet<String>();

        while (cursor_prestamos.hasNext()) {
            // 3. "libro" es un array de subdocumentos {isbn: ...}
            List<Document> lista = (List<Document>) cursor_prestamos.next().get("libro");
            for (Document l : lista) {
                isbns.add(l.getString("isbn"));
            }
        }

        // 4. Libros: de cada isbn a su título
        for (String isbn : isbns) {
            Document libro = col_libros.find(Filters.eq("isbn", isbn)).first();
            if (libro != null) {
                System.out.println("Libro: " + libro.get("titulo"));
            }
        }

        cliente.close();
    }
}
