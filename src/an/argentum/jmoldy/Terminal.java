package an.argentum.jmoldy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

import an.argentum.util.FileEdit;
import an.argentum.util.TerminalReader;
import an.argentum.util.TextEdit;
public class Terminal extends TerminalReader {

    private Project project;
    private Path executingPath;
    private Aliases aliasList;

    public Terminal () {
        this.prefix = "M> ";
        this.aliasList = new Aliases();
    }

    protected boolean decode(String[] input) {
        switch (input[0]) {
            case "load":
                if ( input.length > 1 ) {
                    switch (input[1]) {
                        case "-here":
                            this.project = new Project(FileSystems.getDefault().getPath("."));
                            break;
                            
                        default:
                            this.project = new Project(FileSystems.getDefault().getPath(input[1]));
                            break;
                    }
                } else System.out.println("Specify directory to load.");
                return true;

            case "load2":
                this.project = new Project(FileSystems.getDefault().getPath(input[1]));
                return true;

            case "open":
                if ( input.length > 1 ) {
                    Path projectPath = this.aliasList.get(input[1]);
                    if ( projectPath != null ) this.project = new Project(projectPath); else System.out.println("No alias " + input[1] + " set.");
                } else System.out.println("Specify an alias.");
                return true;

            case "alias":
                if ( input.length > 1 ) {
                    switch (input[1]) {
                        case "new":
                            if ( input.length > 2 ) {
                                this.aliasList.create(new File(input[2]).toPath());
                            } else System.out.println("Specify a path.");
                            break;

                        case "list":
                            try {
                                Scanner fr = new Scanner(new File("aliases.txt"));
                                while ( fr.hasNextLine() ) System.out.println(fr.nextLine());
                                fr.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            break;
                    
                        default:
                            if ( project == null ) System.out.println("Specify argument new|list.");
                    }
                } else if ( project == null ) System.out.println("Specify argument new|list.");
                if ( project == null ) return true; else break;

            case "execute":
                if ( input.length > 1 ) {
                    TextEdit.execute(Arrays.copyOfRange(input, 1, input.length));
                } else System.out.println("Specify a prompt to run in therminal.");
                return true;

            case "echo":
                System.out.println(TextEdit.arrayToString(Arrays.copyOfRange(input, 1, input.length)));
                return true;

            case "exit":
                return false;
        }
        if (this.project != null) {
            switch (input[0]) {
                case "clear":
                    if ( input.length > 1 ) {
                        switch (input[1]) {
                            case "out":
                            case "jar":
                                FileEdit.clear(this.project.getPath().resolve(this.project.getDirectory(input[1])));
                                break;
                            default:
                                System.out.println("Specify out or jar directory as\nclear out|jar.");
                        }
                    } else System.out.println("Specify out or jar directory as\nclear out|jar.");
                    break;

                case "build":
                    if ( this.project.getJarDirectory() != null ) Execute.build(project);
                    else System.out.println("Target directory jar not specified.");
                    break;

                case "compile":
                    Execute.compile(this.project);
                    break;

                case "specify":
                    if ( input.length > 2 ) {
                        switch (input[1]) {
                            case "ep":
                            case "entry":
                            case "entryPoint":
                                this.project.setEntryPoint(input[2]);
                                break;
                            
                            case "jn":
                            case "jarName":
                                this.project.setJarName(input[2]);
                                break;

                            case "src":
                            case "out":
                            case "jar":
                            case "lib":
                                this.project.setPaths(input[1], input[2]);
                                break;

                            default:
                                System.out.println("Unknown variable.");
                        }
                    } else System.out.println("Specify a variable to set.");
                    break;

                case "alias":
                    if ( input.length > 1 ) {
                        switch (input[1]) {
                            case "this":
                                this.aliasList.create(project.getPath());
                                break;
                        
                            default:
                                System.out.println("Unexpected arguments.");
                        }
                    } else System.out.println("Not enough arguments!");
                    break;
            
                default:
                    System.out.println("Invalid input!");
            }
        } else System.out.println("No project loaded.");
        return true;
    }
}
