package Ejercicios;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Ejercicio3 {

	public static void main(String[] args) {

		System.out.println("EJERCICIO 3 - Mostrar toda la informacón del <<Pantalones Levi's 501>>");
		System.out.println("=============================================================");

		JSONParser parser = new JSONParser();
		JSONArray productos = null;
		JSONObject producto = null;

		try {
			productos = (JSONArray) parser.parse(new FileReader("ficheros/productos.json"));

			for (Object produc : productos) {
				producto = (JSONObject) produc;

				if (producto.get("nombre_producto").equals("Pantalones Levi's 501")) {
					System.out.println(producto.toJSONString());
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