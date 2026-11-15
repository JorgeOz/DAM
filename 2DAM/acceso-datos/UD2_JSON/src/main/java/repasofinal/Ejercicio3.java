package repasofinal;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Ejercicio3 {
    public static void main(String[] args) throws Exception {

        JSONParser parser = new JSONParser();
        JSONArray productos = (JSONArray) parser.parse(new FileReader("ficheros/productos.json"));
        Double precio;
        Double maximo = 0.0;
        String nombre;

        for (Object o : productos) {
            JSONObject p = (JSONObject) o;
            precio = ((Number) p.get("precio")).doubleValue();
            nombre = (String) p.get("nombre");
            
            if (precio > maximo) {
            	maximo = precio;
            	System.out.println("Producto más caro: " + precio);
            	System.out.println("Precio: " + precio);
            }
            
        }
    }
}
