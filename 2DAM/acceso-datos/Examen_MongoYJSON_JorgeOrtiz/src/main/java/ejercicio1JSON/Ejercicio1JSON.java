package ejercicio1JSON;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Ejercicio1JSON {
    public static void main(String[] args) throws Exception {
    	
        JSONParser parser = new JSONParser();
        JSONArray incidencias = (JSONArray) parser.parse(new FileReader("ficheros/incidencias.json"));
        boolean hay = false;
        
    	System.out.println("INCIDENCIAS RESUELTAS:");
    	System.out.println("----------------------");
    	
        for (Object o : incidencias) {
            JSONObject p = (JSONObject) o;
            JSONObject equipo = (JSONObject) p.get("equipo");
            JSONArray piezasUtilizadas = (JSONArray) p.get("piezasUtilizadas");
            
            if ("Carlos".equals(((JSONObject) p.get("tecnico")).get("nombre"))
                    && "Martinez Lopez".equals(((JSONObject) p.get("tecnico")).get("apellidos"))
                    && "RESUELTA".equals(p.get("estado"))) {
            	
                System.out.println("Descripción: " + p.get("descripcion"));
                System.out.println("Modelo del equipo afectado: " + equipo.get("modelo"));
                System.out.println("Fecha de registro: " + p.get("fechaRegistro"));
                System.out.println("Tipo de pieza utilizado: " + piezasUtilizadas + "\n");
                
                hay = true;
            }
        }
        
        if (!hay) {
        	System.out.println("No hay resultados");
        }
    }
}
