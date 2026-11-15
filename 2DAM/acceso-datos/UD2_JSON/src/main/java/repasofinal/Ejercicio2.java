package repasofinal;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Ejercicio2 {
    public static void main(String[] args) throws Exception {

        JSONParser parser = new JSONParser();
        JSONArray productos = (JSONArray) parser.parse(new FileReader("ficheros/productos.json"));
        JSONArray categorias;
        String nombre;
        
        for (Object o : productos) {
            JSONObject p = (JSONObject) o;
            nombre = (String) p.get("nombre_producto");
            categorias = (JSONArray) p.get("categorias");
            
            for (Object categoria : categorias) {
            	if (categoria.equals("Hogar") || categoria.equals("Deportes")) {
            		System.out.println("Nombre: " + nombre);
            	}
            }
        }
    }
}
