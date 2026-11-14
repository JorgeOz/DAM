package repaso;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;

public class Ejercicio1Repaso {

	public static void main(String[] args) throws Exception {
		
        JSONParser parser = new JSONParser();
        JSONArray productos = (JSONArray) parser.parse(new FileReader("ficheros/productos.json"));
        String nombreProducto;

        System.out.println("EJERCICIO 1 - REPASO");
        System.out.println("====================");
        
        for (Object o : productos) {
            JSONObject producto = (JSONObject) o;
            nombreProducto = (String)producto.get("nombre_producto");
            System.out.println("Nombre del producto: " + nombreProducto);
        }
	}

}
