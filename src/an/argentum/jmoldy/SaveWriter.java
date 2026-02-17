package an.argentum.jmoldy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class SaveWriter {
    public static void save ( Project project ) throws IOException {
        write ( project.getPath().resolve(".moldy").toFile(), map(project), ": " );
    }

    private static HashMap<String, String> map ( Project project ) {
        HashMap<String, String> target = new HashMap<>();

        target.put("name", project.getName());
        if ( project.getVersion() != null ) target.put("version", project.getVersion());

        target.put("src", project.getSrcDirectory().toString());
        target.put("lib", project.getLibDirectory().toString());
        target.put("out", project.getOutDirectory().toString());
        if ( project.getJarDirectory() != null ) target.put("jar", project.getJarDirectory().toString());

        if ( project.getJarName() != null ) target.put("jarName", project.getJarName());
        if ( project.getEntryPoint() != null ) target.put("entryPoint", project.getEntryPoint());

        return target;
    }

    private static void write ( File file, HashMap<String, String> contents, String delimiter ) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        for ( String key: contents.keySet() ) bw.write( key + delimiter + "\"" + contents.get(key) + "\"\n");
        bw.close();
    }
}
