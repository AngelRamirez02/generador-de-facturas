package direccion;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class ObtenerDireccion {
    
    public String municipio;
    public String estado ;
    public String[] colonias;
   
    public ObtenerDireccion(String codigoPostal) throws Exception {
        // Crear la solicitud HTTP
        //6bae8f3fe1mshaa056e464b2f3bap16a4cfjsnb4fe8f7318df
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://mexico-zip-codes.p.rapidapi.com/codigo_postal/" + codigoPostal))
                .header("x-rapidapi-key", "6bae8f3fe1mshaa056e464b2f3bap16a4cfjsnb4fe8f7318df")
                .header("x-rapidapi-host", "mexico-zip-codes.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        // Enviar la solicitud y obtener la respuesta
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        // Crear un StringBuilder para almacenar la respuesta
        StringBuilder respuesta = new StringBuilder();
        respuesta.append(response.body());
        System.out.println(respuesta);
        this.municipio = obtenerMunicipio(respuesta.toString());
        this.estado = obtenerEstado(respuesta.toString());
        this.colonias = obtenerColonias(respuesta.toString());
    }
    
        // Función para obtener el municipio
    public static String obtenerMunicipio(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.getString("municipio");
    }

    // Función para obtener el estado
    public static String obtenerEstado(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.getString("estado");
    }

    // Función para obtener las colonias
    public static String[] obtenerColonias(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray coloniasArray = jsonObject.getJSONArray("colonias");
        String[] colonias = new String[coloniasArray.length()];
        for (int i = 0; i < coloniasArray.length(); i++) {
            colonias[i] = coloniasArray.getString(i);
        }
        return colonias;
    }
}
