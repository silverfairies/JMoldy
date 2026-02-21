package an.argentum.jmoldy;

import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class ProjectLoader {
    public static Project load ( Path path ) throws FileNotFoundException {
        if ( path.resolve(".moldy").toFile().exists() ) return new Project(path);
        
        throw new FileNotFoundException("The directory " + path.toAbsolutePath().normalize().toString() + " does not have a project file.");
    }

    public static Project load ( String path ) throws FileNotFoundException {
        return load(FileSystems.getDefault().getPath(path));
    }

    public static Project create ( String name, Templates template, Path path ) throws FileNotFoundException {
        if ( path.resolve(".moldy").toFile().exists() ) return new Project(path);
        if ( path.toFile().exists() ) return new Project(name, path, template);
        
        throw new FileNotFoundException("The directory " + path.toAbsolutePath().normalize().toString() + " does not exist.");
    }

    public static Project create ( String name, String template, String path ) throws FileNotFoundException {
        Templates templateBuffer = Templates.find(template);
        Path pathBuffer = FileSystems.getDefault().getPath(path);
        return create(name, templateBuffer, pathBuffer);
    }
}
