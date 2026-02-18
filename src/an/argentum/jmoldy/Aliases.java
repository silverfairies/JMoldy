package an.argentum.jmoldy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

import an.argentum.util.SaveReader;
import an.argentum.util.SaveWriter;

public class Aliases {
    private HashMap<String, Path> map;

    public Aliases () {
        if ( new File("aliases.txt").exists() ) {
            try {
                this.map = load();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                new File("aliases.txt").createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.map = new HashMap<>();
        }
    }

    public boolean create ( Path path ) {
        if ( path.resolve(".moldy").toFile().exists() ) {
            String name;
            try {
                name = SaveReader.readFile(path.resolve(".moldy").toFile()).get("name");
                this.map.put(name, path);
                SaveWriter.append(new File("aliases.txt"), name, path.toString());
                System.out.println("Alias " + name + " created.");
            } catch (IOException e) {
                return false;
            }
            return true;
        }
        System.out.println("No project initialized at path.");
        return false;
    }

    public boolean create ( String name, Path path ) {
        if ( path.resolve(".moldy").toFile().exists() ) {
            this.map.put(name, path);
            try {
                SaveWriter.append(new File("aliases.txt"), name, path.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Alias " + name + " created.");
            return true;
        }
        System.out.println("No project initialized at path.");
        return false;
    }

    public Path get ( String name ) {
        return this.map.get(name);
    }

    private HashMap<String, Path> load () throws FileNotFoundException {
        HashMap<String, Path> output = new HashMap<>();
        HashMap<String, String> buffer = SaveReader.readFile(new File("aliases.txt"));
        for ( String key : buffer.keySet() ) output.put(key, new File(buffer.get(key)).toPath());
        return output;
    }

    private void save () throws IOException {
        HashMap<String, String> buffer = new HashMap<>();
        for ( String key : this.map.keySet() ) buffer.put(key, new File(buffer.get(key)).toString());
        SaveWriter.save(new File("aliases.txt"), buffer);
    }
}