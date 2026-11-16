package ejercicios;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Ejercicio5 {

	public static void main(String[] args) throws Exception {
        JSONParser parser = new JSONParser();
        JSONArray productos = (JSONArray) parser.parse(new FileReader("ficheros/productos.json"));

        for (Object o : productos) {
            JSONObject p = (JSONObject) o;
            JSONObject ubicacion = (JSONObject) p.get("ubicacion");
            Double precio = ((Number) p.get("precio")).doubleValue();
            
            if (ubicacion.get("almacen").equals("Almacén 1") && precio < 15.0) {
            	System.out.println("Nombre: " + p.get("nombre_producto"));
            	System.out.println("Stock: " + p.get("stock"));
            }
        }
	}
}