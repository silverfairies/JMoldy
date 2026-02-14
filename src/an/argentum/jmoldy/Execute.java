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
        StringBuilder command = new StringBuilder();
        if ( project.getEntryPoint() != null )
            command.append("jar cfe ")
                .append(name + " ")
                .append(project.getEntryPoint())
                .append(" -C ")
                .append(project.getPath()
                    .resolve(project.getJarDirectory())
                    .relativize(project.getPath().resolve(project.getOutDirectory()))
                    .toString() + "/ .");
        else
            command.append("jar cf ")
                .append(name)
                .append(" -C ")
                .append(project.getPath()
                    .resolve(project.getJarDirectory())
                    .relativize(project.getPath().resolve(project.getOutDirectory()))
                    .toString() + "/ .");
        
        System.out.println(command);
        TextEdit.execute(command.toString().split(" "), project.getPath().resolve(project.getJarDirectory()).toFile());
        try {
            for ( File each : project.getPath().resolve(project.getLibDirectory()).toFile().listFiles() ) Files.copy(each.toPath(), project.getPath().resolve(project.getJarDirectory()).resolve("lib").resolve(each.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void compile ( Project project ) {
        StringBuilder command = new StringBuilder();
        command.append("javac ")
            .append("-d " + project.getPath().resolve(project.getOutDirectory()).toString() + " ")
            .append("-cp ");
            for ( File file : project.getExternalLibraries() ) command.append(file.toPath().normalize().toString() + ":");
            command.append("*");
            for ( File file : project.getSourceFiles() ) command.append(" " + file.toPath().normalize().toString());
        System.out.println(command);
        TextEdit.execute(command.toString().split(" "), null);
    }

}
