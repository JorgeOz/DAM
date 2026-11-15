package repasofinal;

import java.io.FileReader;
import java.io.FileWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Ejercicio4 {
    public static void main(String[] args) throws Exception {

        JSONParser parser = new JSONParser();
        JSONArray productos = (JSONArray) parser.parse(new FileReader("ficheros/productos.json"));
        String tipoProducto;
        Double precio;
        Double nuevoPrecio;
        
        for (Object o : productos) {
            JSONObject p = (JSONObject) o;
            tipoProducto = (String) p.get("tipo_producto");
            precio = ((Number) p.get("precio")).doubleValue();
            
             if (tipoProducto.equals("Deportes")) {
            	 nuevoPrecio = precio * 1.1;
            	 p.put("precio", nuevoPrecio);
             }
        }
        
        try (FileWriter file = new FileWriter("ficheros/productos_nuevo.json")) {
            file.write(productos.toJSONString());
        }
    } 
}