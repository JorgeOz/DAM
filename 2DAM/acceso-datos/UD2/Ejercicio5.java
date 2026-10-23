package Ejercicios;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Ejercicio5 {

	public static void main(String[] args) {

		System.out.println("EJERCICIO 5 - Mostrar el nombre y y la ubicación completa de los productos con un stock menor de 50");
		System.out.println("==========================================================================================");

		JSONParser parser = new JSONParser();
		JSONArray productos = null;
		JSONObject producto = null;
		JSONObject ubicacion = null;
		Double v_stock = 0.0;

		try {
			productos = (JSONArray) parser.parse(new FileReader("ficheros/productos.json"));

			for (Object produc : productos) {
				producto = (JSONObject) produc;
				ubicacion = (JSONObject) producto.get("ubicacion");
				v_stock = (Double) producto.get("precio");

				if (v_stock < 50) {
					System.out.println("-- Nombre del producto: " + producto.get("nombre_producto"));
					System.out.println("-- Ubicación");
					System.out.println("   - Almacén: " + ubicacion.get("almacen"));
					System.out.println("   - Estantería: " + ubicacion.get("estanteria"));
					System.out.println("   - Pasillo: " + ubicacion.get("pasillo"));
					System.out.println();
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