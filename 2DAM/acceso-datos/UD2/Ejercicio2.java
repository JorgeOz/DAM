package Ejercicios;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Ejercicio2 {

	public static void main(String[] args) {

		System.out.println("EJERCICIO 2 - Obtener el nombre y el precio de los productos de tipo <<Ropa>>");
		System.out.println("====================================================================");

		JSONParser parser = new JSONParser();
		JSONArray productos = null;
		JSONObject producto = null;
		String v_nombre = "";
		Double v_precio = 0.0;

		try {
			productos = (JSONArray) parser.parse(new FileReader("ficheros/productos.json"));

			for (Object produc : productos) {
				producto = (JSONObject) produc;
				v_nombre = (String) producto.get("nombre_producto");
				v_precio = (Double) producto.get("precio");

				if (producto.get("tipo_producto").equals("Ropa")) {
					System.out.println("- Nombre del producto: " + v_nombre + " || Precio del producto: " + v_precio);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}