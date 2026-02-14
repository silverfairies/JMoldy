package an.argentum.jmoldy;

import java.nio.file.Path;

class Library {
    
    private String name;
    private String projectName;
    private Path path;
    private int version;

    public Library ( Path path ) {
        this.path = path;
    }

    public String getName () {
        return this.name;
    }

}
