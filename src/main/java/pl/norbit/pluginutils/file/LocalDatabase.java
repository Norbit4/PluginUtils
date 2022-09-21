package pl.norbit.pluginutils.file;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.Primitives;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class LocalDatabase <T>{
    private final Class<T> c;
    @Getter
    private final String directoryPath;
    private Gson gson;
    private File file;

    private void initDatabase(String directoryPath) throws IOException {
        file = new File(directoryPath);

        gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .setPrettyPrinting()
                .create();

        if(!file.exists()){
            Files.createDirectory(Paths.get(file.getPath()));
        }
    }

    @SneakyThrows
    public LocalDatabase(String directoryPath, Class<T> c) {
        this.c = c;
        this.directoryPath = directoryPath;

        initDatabase(directoryPath);
    }

    public void saveObject(String name, Object o) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(directoryPath + "/" + name + ".json"));

        writer.write(gson.toJson(o));
        writer.flush();
        writer.close();
    }

    public T getObject(String name) throws IOException {

        for (File f : Objects.requireNonNull(file.listFiles())) {

            if(f.getName().equals(name + ".json")){
                BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(f.getAbsolutePath()));
                Object o = gson.fromJson(bufferedReader, c);

                bufferedReader.close();
                return Primitives.wrap(c).cast(o);
            }
        }
        return null;
    }

    public boolean objectExist(Object oWanted) throws IOException {

        for (File f : Objects.requireNonNull(file.listFiles())) {

            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(f.getAbsolutePath()));

            Object o = gson.fromJson(bufferedReader, c);

            bufferedReader.close();
            if(o.equals(oWanted)){
                return true;
            }
        }
        return false;
    }

    public boolean objectExist(String objectName){

        for (File f : Objects.requireNonNull(file.listFiles())) {

            if(f.getName().equals(objectName + ".json")){
                return true;
            }
        }
        return false;
    }

    public List<T> getAllObjects() throws IOException {
        List<T> objectsSet = new ArrayList<>();

        File[] files = file.listFiles();

        for (File f : Objects.requireNonNull(files)) {

            BufferedReader reader = new BufferedReader(new FileReader(f.getAbsolutePath()));

            T o = gson.fromJson(reader, c);
            reader.close();
            objectsSet.add(o);
        }
        return objectsSet;
    }

    public void clear() throws IOException {

        if(file.exists()) {
            FileUtils.deleteDirectory(file);
            Files.createDirectory(Paths.get(file.getPath()));
        }
    }

    public void deleteObject(String name){
        File file = new File(directoryPath + "/" + name + ".json");

        if(file.exists()){
            file.delete();
        }
    }
}
