package repaso;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Ejercicio6Repaso {
	public static void main(String[] args) throws Exception {

        JSONParser parser = new JSONParser();
        JSONArray productos = (JSONArray) parser.parse(new FileReader("ficheros/productos.json"));

        System.out.println("EJERCICIO 6 - REPASO");
        System.out.println("====================");
        
        for (Object o : productos) {
            JSONObject p = (JSONObject) o;
            JSONObject ubicacion = (JSONObject) p.get("ubicacion");
            
            if (ubicacion.get("almacen").equals("Almacén 3") && ubicacion.get("pasillo").equals("Pasillo 3")) {
            	System.out.println("Nombre: " + p.get("nombre_producto"));
            	System.out.println("Tipo: " + p.get("tipo_producto"));
            }
        }
	}
}
