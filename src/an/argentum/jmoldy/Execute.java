package an.argentum.jmoldy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import an.argentum.util.TextEdit;

class Execute {

    public static void delete ( Path path, String directory ) {
        Path buffer = path.resolve(directory);
        for ( File each : buffer.toFile().listFiles()) delete(each);
    }

    private static void delete ( File file ) {
        if ( !file.isDirectory() || (file.isDirectory() && file.listFiles().length == 0) ) file.delete(); else {
            for(File each : file.listFiles()) {
                delete(each);
            }
            file.delete();
        }
    }

    public static void build ( Project project ) {
        String name = project.getJarName() != null ? project.getJarName() : project.getName() + ".jar";
        if ( project.getEntryPoint() != null )
            TextEdit.execute(new String("jar cfe " + name + " " + project.getEntryPoint() + " -C ../out/ .").split(" "), project.getPath().resolve("jar").toFile());
        else
            TextEdit.execute(new String("jar cf " + name + " -C ../out/ .").split(" "), project.getPath().resolve("jar").toFile());
        try {
            for ( File each : project.getPath().resolve("lib").toFile().listFiles() ) Files.copy(each.toPath(), project.getPath().resolve("jar").resolve("lib").resolve(each.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void compile ( Project project ) {
        StringBuilder command = new StringBuilder();
        command.append("javac ")
            .append("-d out ")
            .append("-cp ");
            for ( File file : project.getExternalLibraries() ) command.append(file.toPath().normalize().toString() + ":");
            command.append("*");
            for ( File file : project.getSourceFiles() ) command.append(" " + file.toPath().normalize().toString());
        System.out.println(command);
        TextEdit.execute(command.toString().split(" "), null);
    }

}
