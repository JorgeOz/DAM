package repaso;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Ejercicio8Repaso {
	public static void main(String[] args) throws Exception {

        JSONParser parser = new JSONParser();
        JSONArray productos = (JSONArray) parser.parse(new FileReader("ficheros/productos.json"));

        for (Object o : productos) {
            JSONObject p = (JSONObject) o;
            Double precio = ((Number) p.get("precio")).doubleValue();
            Long stock = ((Number) p.get("stock")).longValue();
            
            if (precio > 100 && stock < 100) {
            	System.out.println("Nombre: " + p.get("nombre_producto"));
            	System.out.println("Precio: " + precio);
            	System.out.println("Stock: " + stock);
            }
        }
	}
}
