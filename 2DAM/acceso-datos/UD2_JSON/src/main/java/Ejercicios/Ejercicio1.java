package Ejercicios;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Ejercicio1 {

	public static void main(String[] args) {

		System.out.println("EJERCICIO 1 - Obtener el nombre de todos los productos");
		System.out.println("=============================================");

		JSONParser parser = new JSONParser();
		JSONArray productos = null;
		JSONObject producto = null;
		String v_nombre = "";

		try {
			productos = (JSONArray) parser.parse(new FileReader("ficheros/productos.json"));

			for (Object produc : productos) {
				producto = (JSONObject) produc;
				v_nombre = (String) producto.get("nombre_producto");

				System.out.println("- Nombre del producto: " + v_nombre);
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