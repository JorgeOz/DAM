package ejemplos;

import java.io.*;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class SocioMongoDBFrom {

	public static void main(String[] args) {
		
		MongoCursor<Document> cursor = null;
		Document doc_socio = null;
		Bson bsonFilter = null;
		String socio = null;
		MongoClient conexionMongo = MongoClients.create();
		
		// Conectar con la MongoDB
		MongoDatabase baseDatos = conexionMongo.getDatabase("Biblioteca");
		MongoCollection colSocio = baseDatos.getCollection("Socios");

		try {
			
			// Buscar en MongoDB
			bsonFilter = Filters.eq("Nombre", "Num_socio");
			doc_socio = (Document) colSocio.find(bsonFilter).first();
			
			socio = doc_socio.toJson();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
