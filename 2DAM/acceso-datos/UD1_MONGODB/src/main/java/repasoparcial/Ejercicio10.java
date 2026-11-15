package repasoparcial;

import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

public class Ejercicio10 {
    public static void main(String[] args) {
    	
        MongoClient cliente = MongoClients.create();
        MongoDatabase db = cliente.getDatabase("Biblioteca");
        MongoCollection<Document> col_libros = db.getCollection("Libros");

        // 1. INSERTAR: un Document con un subdocumento (autor) y un array (generos)
        List<String> generos = Arrays.asList("aventura", "fantasia");

        Document nuevo = new Document("isbn", "99999")
                .append("titulo", "Libro de prueba")
                .append("autor", new Document("nombre", "Ana").append("apellido", "Perez"))
                .append("editorial", "Planeta")
                .append("año_publicacion", 2026)
                .append("páginas", 300)
                .append("generos", generos);

        col_libros.insertOne(nuevo);
        System.out.println("Insertado");

        // 2. ACTUALIZAR: sumar 50 a las páginas (filtro + Updates.inc)
        UpdateResult upd = col_libros.updateOne(
                Filters.eq("isbn", "99999"),
                Updates.inc("páginas", 50));
        System.out.println("Modificados: " + upd.getModifiedCount());

        // 3. COMPROBAR: buscarlo y ver las páginas
        Document comprobado = col_libros.find(Filters.eq("isbn", "99999")).first();
        System.out.println("Páginas ahora: " + comprobado.get("páginas"));

        // 4. BORRAR
        DeleteResult res = col_libros.deleteOne(Filters.eq("isbn", "99999"));
        System.out.println("Borrados: " + res.getDeletedCount());

        cliente.close();
    }
}