package repaso;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;

public class Ejercicio2Repaso {

	public static void main(String[] args) throws Exception {
		
        JSONParser parser = new JSONParser();
        JSONArray productos = (JSONArray) parser.parse(new FileReader("ficheros/productos.json"));
        String nombre;
        Double precio;

        System.out.println("EJERCICIO 2 - REPASO");
        System.out.println("====================");
        
        for (Object o : productos) {
            JSONObject producto = (JSONObject) o;
            
            if (producto.get("tipo_producto").equals("Ropa")) {
            	nombre = (String)producto.get("nombre_producto");
            	precio = ((Number)producto.get("precio")).doubleValue();
            	System.out.println("Nombre: " + nombre);
            	System.out.println("Precio: " + precio);
            }
        }
	}
}
