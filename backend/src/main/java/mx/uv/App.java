package mx.uv;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.UUID;
import com.google.gson.*;

/**
 * Hello world!
 *
 */
public class App 
{
    static Gson gson = new Gson();
    
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        //port(80);
        port(getHerokuAssignedPort());

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });
        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        get("/usuarios", (request, response)->{
            response.type("application/json");
            return gson.toJson(DAO.dameUsuarios());
        });

        post("/registro", (request, response)->{
            response.type("application/json");
            String payload = request.body();
            Usuario usuario = gson.fromJson(payload, Usuario.class);
            
            String id = String.format("%04d", (int) (Math.random() * 10000));
            usuario.setId(id);
            String msg = DAO.crearUsuario(usuario);
            System.out.println("n "+usuario.getNombre());
            System.out.println("p "+usuario.getContraseña());
            System.out.println("i "+usuario.getId());
            System.out.println("c "+usuario.getCompras());

            JsonObject respuesta = new JsonObject();
            respuesta.addProperty("msj", "Se creo el usuario");
            respuesta.addProperty("id", id);
            return gson.toJson(respuesta);
        });
    }
    
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }
}