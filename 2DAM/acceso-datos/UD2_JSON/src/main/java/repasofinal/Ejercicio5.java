package repasofinal;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Ejercicio5 {
    public static void main(String[] args) throws Exception {

        JSONParser parser = new JSONParser();
        JSONArray productos = (JSONArray) parser.parse(new FileReader("ficheros/productos.json"));

        Iterator<?> it = productos.iterator();

        while (it.hasNext()) {
            JSONObject p = (JSONObject) it.next();
            long stock = ((Number) p.get("stock")).longValue();

            if (stock < 50) {
                it.remove();
            }
        }

        try (FileWriter file = new FileWriter("ficheros/productos_nuevo.json")) {
            file.write(productos.toJSONString());
        }
    }
}