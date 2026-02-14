package an.argentum.jmoldy;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
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

    public Project ( String name ) {
        this.name = name;
        this.ver = 0;
        this.packages = new ArrayList<>();
    }

    public Project ( Path path ) {
        this.path = path;
        this.name = this.path.toAbsolutePath().normalize().getFileName().toString();
        this.packages = new ArrayList<>();
        this.sourcePackages = generateSourcePackages();
        this.externalLibraries = generateExternalLibraries();
        this.sourceFiles = generateSourceFiles();
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
        if ( generate ) this.sourcePackages = generateSourcePackages();
        return this.sourcePackages;
    } 

    private File[] generateSourcePackages () {
        LinkedHashSet<File> packageHashSet = generateSourcePackages(this.path.resolve("src").toFile());
        File[] packagesArray = new File[packageHashSet.size()];
        return packageHashSet.toArray(packagesArray);
    }

    private LinkedHashSet<File> generateSourcePackages ( File path ) {
        File[] files = path.listFiles();
        LinkedHashSet<File> packageHashSet = new LinkedHashSet<>();
        for ( File each : files ) {
            if ( each.isDirectory() ) {
                for ( File file : generateSourcePackages( each ) ) packageHashSet.add(file);
            }
            else if ( each.getName().endsWith(".java") ) packageHashSet.add(each.getParentFile());
        }
        return packageHashSet;
    }

    public File[] getSourceFiles () {
        return getSourceFiles ( true );
    }

    public File[] getSourceFiles ( boolean generate ) {
        if ( generate ) this.sourceFiles = generateSourceFiles();
        return this.sourceFiles;
    }

    private File[] generateSourceFiles () {
        LinkedHashSet<File> packageHashSet = generateSourceFiles(this.path.resolve("src").toFile());
        File[] packagesArray = new File[packageHashSet.size()];
        return packageHashSet.toArray(packagesArray);
    }

    private LinkedHashSet<File> generateSourceFiles ( File path ) {
        File[] files = path.listFiles();
        LinkedHashSet<File> packageHashSet = new LinkedHashSet<>();
        for ( File each : files ) {
            if ( each.isDirectory() ) {
                for ( File file : generateSourceFiles( each ) ) packageHashSet.add(file);
            }
            else if ( each.getName().endsWith(".java") ) packageHashSet.add(each);
        }
        return packageHashSet;
    }

    public File[] getExternalLibraries () {
        return getExternalLibraries ( true );
    }

    public File[] getExternalLibraries ( boolean generate ) {
        if ( generate ) this.externalLibraries = generateExternalLibraries();
        return this.externalLibraries;
    }

    private File[] generateExternalLibraries () {
        LinkedHashSet<File> packageHashSet = generateExternalLibraries(this.path.resolve("lib").toFile());
        File[] packagesArray = new File[packageHashSet.size()];
        return packageHashSet.toArray(packagesArray);
    }

    private LinkedHashSet<File> generateExternalLibraries ( File path ) {
        File[] files = path.listFiles();
        LinkedHashSet<File> packageHashSet = new LinkedHashSet<>();
        for ( File each : files ) {
            if ( each.isDirectory() ) {
                for ( File file : generateExternalLibraries( each ) ) packageHashSet.add(file);
            }
            else if ( each.getName().endsWith(".jar") ) packageHashSet.add(each);
        }
        return packageHashSet;
    }
}
