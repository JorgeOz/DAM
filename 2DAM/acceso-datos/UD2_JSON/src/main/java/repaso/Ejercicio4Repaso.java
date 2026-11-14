package repaso;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Ejercicio4Repaso {
	public static void main(String[] args) throws Exception {

        JSONParser parser = new JSONParser();
        JSONArray productos = (JSONArray) parser.parse(new FileReader("ficheros/productos.json"));
        Double precio;
        
        System.out.println("EJERCICIO 4 - REPASO");
        System.out.println("====================");
        
        for (Object o : productos) {
            JSONObject p = (JSONObject) o;
            JSONObject ubicacion = (JSONObject)p.get("ubicacion");
            precio = ((Number) p.get("precio")).doubleValue();
            
            if (ubicacion.get("almacen").equals("Almacén 1") && precio < 1500.00) {
            	System.out.println("Nombre: " + p.get("nombre_producto"));
            	System.out.println("Stock: " + p.get("stock"));
            }
        }
	}
}
