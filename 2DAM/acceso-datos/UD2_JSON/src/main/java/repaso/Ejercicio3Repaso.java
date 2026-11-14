package repaso;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;

public class Ejercicio3Repaso {

	public static void main(String[] args) throws Exception {

        JSONParser parser = new JSONParser();
        JSONArray productos = (JSONArray) parser.parse(new FileReader("ficheros/productos.json"));
        String nombreProducto;

        System.out.println("EJERCICIO 3 - REPASO");
        System.out.println("====================");
        
        for (Object o : productos) {
            JSONObject p = (JSONObject) o;

            if ("Pantalones Levi's 501".equals(p.get("nombre_producto"))) {
                System.out.println("Nombre: " + p.get("nombre_producto"));
                System.out.println("Precio: " + p.get("precio"));
                System.out.println("Stock: " + p.get("stock"));
                System.out.println("Tipo: " + p.get("tipo_producto"));

                JSONArray cats = (JSONArray) p.get("categorias");
                System.out.println("Categorías: " + cats);

                JSONObject ub = (JSONObject) p.get("ubicacion");
                System.out.println("Almacén: " + ub.get("almacen"));
                System.out.println("Pasillo: " + ub.get("pasillo"));
                System.out.println("Estantería: " + ub.get("estanteria"));
            }
        }
	}

}
