package ejerciciosclase;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

public class EjerciciosBiblioteca {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		// Variables
		String cad;
		String isbn;

		// Cursores donde se guardan los documentos de cada colección
		MongoCursor<Document> cursor_libros = null;
		MongoCursor<Document> cursor_prestamos = null;
		MongoCursor<Document> cursor_socios = null;
		MongoCursor<Document> cursor = null;

		Document libro = null;
		Document autor = null;
		Document socio = null;
		Document doc = null;

		// Objetos para manejar filtros
		Bson bsonFilter = null;
		Bson bsonFilterEntre = null;

		// Conectamos a la BD (sin parámetros: localhost:27017)
		MongoClient mongoCliente = MongoClients.create();

		// Conectar a Biblioteca
		MongoDatabase baseDatos = mongoCliente.getDatabase("Biblioteca");
		MongoCollection<Document> col_libros = baseDatos.getCollection("Libros");
		MongoCollection<Document> col_prestamos = baseDatos.getCollection("Prestamos");
		MongoCollection<Document> col_socios = baseDatos.getCollection("Socios");

		try {
			System.out.println("\n---------");
			System.out.println("EJEMPLO A");
			System.out.println("---------");

			cursor_libros = col_libros.find().iterator();

			// Recorrer la colección libros y listar los títulos
			while (cursor_libros.hasNext()) {
				System.out.println(cursor_libros.next().get("titulo"));
			}

			// Obtener el primer elemento de una colección

			System.out.println("\n---------");
			System.out.println("EJEMPLO B");
			System.out.println("---------");

			libro = col_libros.find().first();
			System.out.println("El primer libro " + libro.toJson());
			System.out.println("---------");

			// Obtener del primer libro el isbn
			System.out.println(" - El isbn del primer libro es: " + libro.get("isbn"));

			// Obtener del primer libro el nombre del autor (es un subdocumento)
			autor = (Document) libro.get("autor");
			System.out.println(" - El nombre del autor del primer libro es: " + autor.get("nombre"));

			// =============
			// == FILTROS ==
			// =============

			// Mostrar los libros de año de edición mayor o igual que 2000

			System.out.println("\n---------");
			System.out.println("EJEMPLO C - FILTROS");
			System.out.println("---------");

			bsonFilter = Filters.gte("año_publicacion", 2000);
			cursor_libros = col_libros.find(bsonFilter).iterator();

			System.out.println("Libros publicados >= 2000:");
			System.out.println("---------");

			while (cursor_libros.hasNext()) {
				System.out.println(cursor_libros.next().toJson());
			}

			// Mostrar los libros cuyo año de publicación sea mayor de 2000 y menor que 2020

			System.out.println("\n---------");
			System.out.println("EJEMPLO D - FILTROS");
			System.out.println("---------");

			bsonFilterEntre = Filters.and(Filters.gt("año_publicacion", 2000), Filters.lt("año_publicacion", 2020));
			cursor_libros = col_libros.find(bsonFilterEntre).iterator();

			System.out.println("Libros publicados entre 2000 y 2020:");
			System.out.println("---------");

			while (cursor_libros.hasNext()) {
				System.out.println(cursor_libros.next().toJson());
			}

			// EJERCICIO 6 - Préstamos con 2 libros

			System.out.println("\n---------");
			System.out.println("Ejercicio 6 - Préstamos con dos libros");
			System.out.println("---------");

			// "libro" es un array en Prestamos: size cuenta sus elementos
			bsonFilter = Filters.size("libro", 2);
			cursor_prestamos = col_prestamos.find(bsonFilter).iterator();

			while (cursor_prestamos.hasNext()) {
				System.out.println(cursor_prestamos.next().toJson());
			}

			// EJERCICIO 7 - Títulos de libros cuyo autor se apellida Follet

			System.out.println("\n---------");
			System.out.println("Ejercicio 7 - Títulos de los libros cuyo autor se apellida Follet");
			System.out.println("---------");

			// "autor" es un subdocumento: se accede con punto
			bsonFilter = Filters.eq("autor.apellido", "Follet");
			cursor_libros = col_libros.find(bsonFilter).iterator();

			while (cursor_libros.hasNext()) {
				System.out.println("- " + cursor_libros.next().get("titulo"));
			}

			// EJERCICIO 8 - Documentos de los préstamos del libro con ISBN 446854

			System.out.println("\n---------");
			System.out.println("Ejercicio 8 - Documentos de los préstamos del libro con ISBN 446854");
			System.out.println("---------");

			// "libro" es un array de subdocumentos: libro.isbn busca en todos sus elementos
			bsonFilter = Filters.eq("libro.isbn", "446854");
			cursor_prestamos = col_prestamos.find(bsonFilter).iterator();

			while (cursor_prestamos.hasNext()) {
				System.out.println(cursor_prestamos.next().toJson());
			}

			// EJEMPLO E - Mostrar el título del libro 446854 solo si tiene préstamos
			// (versión con countDocuments)
			// OJO: un cursor nunca es null, aunque no tenga resultados. Por eso hay que
			// contar los documentos o usar hasNext().

			System.out.println("\n---------");
			System.out.println("EJEMPLO E");
			System.out.println("---------");

			long numPrestamos = col_prestamos.countDocuments(Filters.eq("libro.isbn", "446854"));

			if (numPrestamos > 0) {
				// En Libros el campo es "isbn" directamente (no "libro.isbn")
				bsonFilter = Filters.eq("isbn", "446854");
				cursor_libros = col_libros.find(bsonFilter).iterator();

				System.out.println("Título del libro con ISBN 446854:");
				while (cursor_libros.hasNext()) {
					System.out.println(cursor_libros.next().get("titulo"));
				}
			} else {
				System.out.println("Este ISBN 446854 no tiene préstamos");
			}

			// EJEMPLO F - Lo mismo, pero con hasNext()

			System.out.println("\n---------");
			System.out.println("EJEMPLO F");
			System.out.println("---------");

			bsonFilter = Filters.eq("libro.isbn", "446854");
			cursor_prestamos = col_prestamos.find(bsonFilter).iterator();

			if (cursor_prestamos.hasNext()) {
				bsonFilter = Filters.eq("isbn", "446854");
				cursor_libros = col_libros.find(bsonFilter).iterator();

				System.out.println("Título del libro con ISBN 446854");
				System.out.println("---------");

				while (cursor_libros.hasNext()) {
					System.out.println(cursor_libros.next().get("titulo"));
				}
			} else {
				System.out.println("Este ISBN 446854 no tiene préstamos");
			}

			// EJERCICIO 9 - Nombre de los socios que han tenido prestado el libro con ISBN
			// 456779

			System.out.println("\n---------");
			System.out.println("Ejercicio 9 - Nombre de los socios que han tenido prestado el libro con ISBN 456779");
			System.out.println("---------");

			bsonFilter = Filters.eq("libro.isbn", "456779");
			cursor_prestamos = col_prestamos.find(bsonFilter).iterator();

			while (cursor_prestamos.hasNext()) {
				socio = (Document) cursor_prestamos.next().get("socio");

				// En Prestamos el número de socio es TEXTO ("1"); en Socios es NÚMERO (1)
				cad = socio.getString("num_socio");
				Integer numero_socio = Integer.parseInt(cad);

				bsonFilter = Filters.eq("Num_socio", numero_socio);
				socio = col_socios.find(bsonFilter).first();

				if (socio != null) {
					System.out.println("Nombre: " + socio.get("Nombre"));
				}
			}

			// =========================================================================================================

			// EJERCICIO 17 - Mostrar título de los libros escritos por Asimov

			System.out.println("\n---------");
			System.out.println("Ejercicio 17 - Mostrar título de los libros escritos por Asimov");
			System.out.println("---------");

			bsonFilter = Filters.eq("autor.apellido", "Asimov");

			// Ordenados por título de forma ascendente
			cursor_libros = col_libros.find(bsonFilter).sort(Sorts.ascending("titulo")).iterator();

			while (cursor_libros.hasNext()) {
				System.out.println("Lista ordenada por titulo: " + cursor_libros.next().toJson());
			}

			// Proyección (de los libros de Asimov, solo título y año de publicación)

			cursor_libros = col_libros.find(bsonFilter).projection(
					Projections.fields(Projections.include("titulo", "año_publicacion"), Projections.excludeId()))
					.iterator();

			System.out.println("\nProyección:");
			System.out.println("------------");
			while (cursor_libros.hasNext()) {
				System.out.println("Lista proyección: " + cursor_libros.next().toJson());
			}

			// Proyección y ordenado

			cursor_libros = col_libros.find(bsonFilter).sort(Sorts.ascending("titulo")).projection(
					Projections.fields(Projections.include("titulo", "año_publicacion"), Projections.excludeId()))
					.iterator();

			System.out.println("\nProyección ordenada:");
			System.out.println("------------");
			while (cursor_libros.hasNext()) {
				System.out.println("Lista proyección ordenada: " + cursor_libros.next().toJson());
			}

			// Excluir de lo que devuelve la editorial

			cursor_libros = col_libros.find(bsonFilter).sort(Sorts.ascending("titulo"))
					.projection(Projections.fields(Projections.exclude("editorial"), Projections.excludeId()))
					.iterator();

			System.out.println("\nSin editorial:");
			System.out.println("------------");
			while (cursor_libros.hasNext()) {
				System.out.println("Lista sin editorial: " + cursor_libros.next().toJson());
			}

			// =========================================================================================================

			// EJERCICIO 18 - Nombre de los socios que se han llevado prestado el libro de
			// título «El Quijote»

			System.out.println("\n---------");
			System.out.println(
					"Ejercicio 18 - Nombre de los socios que se han llevado prestado el libro de título «El Quijote»");
			System.out.println("---------");

			// 1) Buscar el libro por título en LIBROS (el campo es "titulo", no
			// "libro.titulo")
			bsonFilter = Filters.eq("titulo", "El Quijote");
			cursor_libros = col_libros.find(bsonFilter).iterator();

			while (cursor_libros.hasNext()) {

				isbn = cursor_libros.next().getString("isbn");

				// 2) Buscar en PRESTAMOS los préstamos que contienen ese isbn
				bsonFilter = Filters.eq("libro.isbn", isbn);
				cursor_prestamos = col_prestamos.find(bsonFilter).iterator();

				while (cursor_prestamos.hasNext()) {
					socio = (Document) cursor_prestamos.next().get("socio");

					// 3) El número de socio en Prestamos es texto: convertir a número
					cad = socio.getString("num_socio");
					Integer numero = Integer.parseInt(cad);

					// 4) Buscar el nombre en SOCIOS
					bsonFilter = Filters.eq("Num_socio", numero);
					socio = col_socios.find(bsonFilter).first();

					if (socio != null) {
						System.out.println("Socio: " + socio.get("Nombre"));
					}
				}
			}

			// =========================================================================================================

			// EJERCICIO 19 - Mostrar el título de los libros que tiene prestados el socio
			// con nombre «Javier Garcia»

			System.out.println("\n---------");
			System.out.println("Ejercicio 19 - Títulos de los libros prestados al socio «Javier Garcia»");
			System.out.println("---------");

			// 1) Buscar el socio por nombre en SOCIOS
			bsonFilter = Filters.eq("Nombre", "Javier Garcia");
			cursor_socios = col_socios.find(bsonFilter).iterator();

			while (cursor_socios.hasNext()) {

				// Num_socio es un número en Socios
				Integer num = ((Number) cursor_socios.next().get("Num_socio")).intValue();

				// 2) En PRESTAMOS el número de socio es texto: convertir a String
				bsonFilter = Filters.eq("socio.num_socio", String.valueOf(num));
				cursor_prestamos = col_prestamos.find(bsonFilter).iterator();

				while (cursor_prestamos.hasNext()) {

					// 3) "libro" es un array de subdocumentos con el isbn
					List<Document> listaLibros = (List<Document>) cursor_prestamos.next().get("libro");

					for (Document l : listaLibros) {
						isbn = l.getString("isbn");

						// 4) Buscar el título en LIBROS
						libro = col_libros.find(Filters.eq("isbn", isbn)).first();

						if (libro != null) {
							System.out.println("- " + libro.get("titulo"));
						}
					}
				}
			}

			// EJERCICIO 21 - Mostrar el título y la editorial (proyección) de los libros
			// ordenados por número de páginas

			System.out.println("\n---------");
			System.out.println("Ejercicio 21 - Título y editorial de los libros ordenados por número de páginas");
			System.out.println("---------");

			// Se incluye "páginas" en la proyección para poder ver el orden.
			// Para ordenar de mayor a menor: Sorts.descending("páginas")
			cursor = col_libros.find().projection(
					Projections.fields(Projections.include("titulo", "editorial", "páginas"), Projections.excludeId()))
					.sort(Sorts.ascending("páginas")).iterator();

			while (cursor.hasNext()) {
				System.out.println("Libro: " + cursor.next().toJson());
			}

			// EJERCICIO 22 - Mostrar el título de los libros que tienen algún préstamo

			System.out.println("\n---------");
			System.out.println("Ejercicio 22 - Título de los libros que tienen algún préstamo");
			System.out.println("---------");

			// Un Set no admite repetidos: así un libro prestado varias veces sale una sola
			// vez
			Set<String> isbnsPrestados = new LinkedHashSet<String>();

			cursor = col_prestamos.find().iterator();
			while (cursor.hasNext()) {
				List<Document> lib = (List<Document>) cursor.next().get("libro");
				for (Document l : lib) {
					isbnsPrestados.add(l.getString("isbn"));
				}
			}

			for (String i : isbnsPrestados) {
				libro = col_libros.find(Filters.eq("isbn", i)).first();

				// first() devuelve null si ese isbn no existe en Libros
				if (libro != null) {
					System.out.println("- " + libro.get("titulo"));
				}
			}

			// =========================================================================================================

			// INSERTAR, ACTUALIZAR Y BORRAR
			// Se hace sobre un libro de PRUEBA (isbn 12345) para no tocar los datos reales.
			// Al final se borra, así que el programa se puede ejecutar varias veces.

			System.out.println("\n---------");
			System.out.println("Insertar, actualizar y borrar");
			System.out.println("---------");

			// Insertar
			List<String> generos = new ArrayList<String>();
			generos.add("aventura");
			generos.add("fantasia");
			generos.add("juvenil");

			libro = new Document("isbn", "12345").append("titulo", "El día de la trilla")
					.append("autor",
							new Document("nombre", "Rebeca").append("apellido", "Yarros").append("nacionalidad",
									"americana"))
					.append("editorial", "Planeta").append("año_publicacion", 2026).append("páginas", 900)
					.append("genero", generos);

			col_libros.insertOne(libro);
			System.out.println("Libro de prueba insertado");

			// Actualizar un documento (updateOne): poner páginas a 1200
			// OJO: en Libros el campo es "isbn" (no "libro.isbn", que es de Prestamos)
			bsonFilter = Filters.eq("isbn", "12345");
			UpdateResult upd = col_libros.updateOne(bsonFilter, new Document("$set", new Document("páginas", 1200)));
			System.out.println("Actualizados (updateOne): " + upd.getModifiedCount());

			// Actualizar varios documentos (updateMany) con el helper Updates: sumar 100
			// páginas
			upd = col_libros.updateMany(bsonFilter, Updates.inc("páginas", 100));
			System.out.println("Actualizados (updateMany): " + upd.getModifiedCount());

			// Comprobar el resultado
			System.out.println("Ahora: " + col_libros.find(bsonFilter).first().toJson());

			// Borrar (deleteMany borra todos los que cumplan el filtro; deleteOne solo el
			// primero)
			DeleteResult res = col_libros.deleteMany(bsonFilter);
			System.out.println("Borrados: " + res.getDeletedCount());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoCliente.close();
		}
	}
}
