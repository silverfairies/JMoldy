package an.argentum.jmoldy;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedHashSet;

class Project {

    
    private String name;
    private ArrayList<Library> packages;
    private int ver;
    private Path path;
    private String entryPoint;
    private String jarName;
    private File[] sourcePackages;
    private File[] sourceFiles;
    private File[] externalLibraries;
    private Path srcDirectory;
    private Path libDirectory;
    private Path outDirectory;
    private Path jarDirectory;


    public Project ( String name ) {
        this.name = name;
        this.ver = 0;
        this.packages = new ArrayList<>();
    }

    public Project ( Path path ) {
        this.path = path;
        this.name = this.path.toAbsolutePath().normalize().getFileName().toString();
        // this.packages = new ArrayList<>();

        this.srcDirectory = new File("src").toPath();
        this.libDirectory = new File("lib").toPath();
        this.outDirectory = new File("out").toPath();
        this.jarDirectory = new File("jar").toPath();

        this.sourcePackages = getSourcePackages();
        this.externalLibraries = getExternalLibraries();
        this.sourceFiles = getSourceFiles();
        System.out.println(name + " loaded.");
    }

    public String getName () {
        return this.name;
    }

    public Path getPath () {
        return this.path;
    }

    public void setEntryPoint ( String input ) {
        this.entryPoint = input;
    }

    public String getEntryPoint () {
        return this.entryPoint;
    }

    public void setJarName ( String input ) {
        this.jarName = input;
    }

    public String getJarName () {
        return this.jarName;
    }



    public File[] getSourcePackages () {
        return getSourcePackages ( true );
    }

    public File[] getSourcePackages ( boolean generate ) {
        if ( generate ) this.sourcePackages = generateFileArray( srcDirectory, ".java", true);
        return this.sourcePackages;
    }

    public File[] getSourceFiles () {
        return getSourceFiles ( true );
    }

    public File[] getSourceFiles ( boolean generate ) {
        if ( generate ) this.sourceFiles = generateFileArray( srcDirectory, ".java", false);
        return this.sourceFiles;
    }

    public File[] getExternalLibraries () {
        return getExternalLibraries ( true );
    }

    public File[] getExternalLibraries ( boolean generate ) {
        if ( generate ) this.externalLibraries = generateFileArray(libDirectory, ".jar", false);
        return this.externalLibraries;
    }

    private File[] generateFileArray ( Path directory, String suffix, boolean directoriesOnly ) {
        LinkedHashSet<File> fileHashSet = generateFileArrayStep(this.path.resolve(directory).toFile(), suffix, directoriesOnly);
        File[] fileArray = new File[fileHashSet.size()];
        return fileHashSet.toArray(fileArray);
    }

    private LinkedHashSet<File> generateFileArrayStep ( File path, String suffix, boolean directoriesOnly ) {
        File[] files = path.listFiles();
        LinkedHashSet<File> fileHashSet = new LinkedHashSet<>();
        for ( File each : files ) {
            if ( each.isDirectory() ) {
                for ( File file : generateFileArrayStep(each, suffix, directoriesOnly) ) fileHashSet.add(file);
            }
            else if ( each.getName().endsWith(suffix) ) {
                if ( directoriesOnly ) fileHashSet.add(each.getParentFile()); else fileHashSet.add(each);
            }
        }
        return fileHashSet;
    }



    public void setPaths ( String directory, String path ) {
        switch (directory) {
            case "src":
                this.srcDirectory = new File(path).toPath();
                break;

            case "lib":
                this.libDirectory = new File(path).toPath();
                break;
        
            case "out":
                this.outDirectory = new File(path).toPath();
                break;
        
            case "jar":
                this.jarDirectory = new File(path).toPath();
                break;
        
            default:
                throw new InputMismatchException(directory + " not a parameter");
        }
    }

    public Path getOutDirectory () {
        return this.outDirectory;
    }

    public Path getSrcDirectory () {
        return this.srcDirectory;
    }

    public Path getJarDirectory () {
        return this.jarDirectory;
    }

    public Path getLibDirectory () {
        return this.libDirectory;
    }

    public Path getDirectory ( String directory ) {
        switch (directory) {
            case "src":
                return this.getSrcDirectory();

            case "lib":
                return this.getLibDirectory();
        
            case "out":
                return this.getOutDirectory();
        
            case "jar":
                return this.getJarDirectory();

            default:
                throw new InputMismatchException(directory + " not a parameter");
        }
    }
}
