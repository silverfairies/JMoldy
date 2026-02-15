package an.argentum.jmoldy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import an.argentum.util.Pair;

public class SaveWriter {
    public static void save ( Project project ) {
        try {
            write ( project.getPath().resolve(".moldy").toFile(), map(project), ": " );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Pair<String, String>> map ( Project project ) {
        ArrayList<Pair<String, String>> target = new ArrayList<>();

        target.add(new Pair<String,String>("name", project.getName()));
        if ( project.getVersion() != null ) target.add(new Pair<String,String>("version", project.getVersion()));

        target.add(new Pair<String,String>("src", project.getSrcDirectory().toString()));
        target.add(new Pair<String,String>("lib", project.getLibDirectory().toString()));
        target.add(new Pair<String,String>("out", project.getOutDirectory().toString()));
        if ( project.getJarDirectory() != null ) target.add(new Pair<String,String>("jar", project.getJarDirectory().toString()));

        if ( project.getJarName() != null ) target.add(new Pair<String,String>("jarName", project.getJarName()));
        if ( project.getEntryPoint() != null ) target.add(new Pair<String,String>("entryPoint", project.getEntryPoint()));

        return target;
    }

    private static void write ( File file, ArrayList<Pair<String, String>> contents, String delimiter ) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        for ( Pair<String, String> pair : contents ) bw.write( pair.getKey() + delimiter + "\"" + pair.getValue() + "\"\n");
        bw.close();
    }
}
