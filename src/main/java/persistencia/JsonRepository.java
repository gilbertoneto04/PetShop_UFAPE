package persistencia;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import util.LocalDateTimeAdapter;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;

public class JsonRepository {
    private static final String CAMINHO_BASE = "src/main/resources/";

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    static {
        File pasta = new File(CAMINHO_BASE);
        if (!pasta.exists()) {
            pasta.mkdirs();
        }
    }

    private static <T> String getCaminhoArquivo(Class<T> classe) {
        return CAMINHO_BASE + classe.getSimpleName().toLowerCase() + ".json";
    }

    public static <T> List<T> carregarTodos(Class<T> classe) {
        String caminho = getCaminhoArquivo(classe);
        Type tipoLista = TypeToken.getParameterized(List.class, classe).getType();

        try (Reader reader = new FileReader(caminho)) {
            List<T> dados = gson.fromJson(reader, tipoLista);
            return dados != null ? dados : new ArrayList<>();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo JSON", e);
        }
    }

    public static <T> void salvarTodos(List<T> lista, Class<T> classe) {
        String caminho = getCaminhoArquivo(classe);
        Type tipoLista = TypeToken.getParameterized(List.class, classe).getType();

        try (Writer writer = new FileWriter(caminho)) {
            gson.toJson(lista, tipoLista, writer);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao escrever no arquivo JSON", e);
        }
    }

    public static <T> void adicionar(T entidade, Class<T> classe) {
        List<T> lista = carregarTodos(classe);
        lista.add(entidade);
        salvarTodos(lista, classe);
    }
}
