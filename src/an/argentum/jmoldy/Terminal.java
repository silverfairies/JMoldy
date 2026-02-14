package an.argentum.jmoldy;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;

import an.argentum.util.TerminalReader;
import an.argentum.util.TextEdit;
import an.argentum.jmoldy.*;
public class Terminal extends TerminalReader {

    private Project project;

    public Terminal () {
        this.prefix = "M> ";
    }

    protected boolean decode(String[] input) {
        switch (input[0]) {
            case "clear":
                if (this.project != null) {
                    if ( input.length > 1 ) {
                        switch (input[1]) {
                            case "out":
                            case "jar":
                                Execute.delete(project.getPath(), input[1]);
                                break;
                            default:
                                System.out.println("Specify out or jar directory as\nclear out|jar.");
                        }
                    } else System.out.println("Specify out or jar directory as\nclear out|jar.");
                } else System.out.println("No project loaded.");
                break;
            
            case "load":
                if ( input.length > 1 ) {
                    switch (input[1]) {
                        case "-here":
                            project = new Project(FileSystems.getDefault().getPath("."));
                            break;
                    
                        default:
                            project = new Project(FileSystems.getDefault().getPath(input[1]));
                            break;
                    }
                } else System.out.println("Specify directory to load or -this to load current directory.");
                break;

            case "build":
                Execute.build(project);
                break;

            case "compile":
                Execute.compile(project);
                break;

            case "specify":
                if ( input.length > 2 ) {
                    switch (input[1]) {
                        case "ep":
                        case "entry":
                            project.setEntryPoint(input[2]);
                            break;
                        
                        case "jn":
                        case "jar":
                            project.setJarName(input[2]);
                            break;

                        default:
                            System.out.println("Unknown variable.");
                    }
                } else System.out.println("Specify a variable to set.\nentry|jar");
                break;

            case "execute":
                if ( input.length > 1 ) {
                    TextEdit.execute(Arrays.copyOfRange(input, 1, input.length));
                } else System.out.println("Specify a prompt to run in therminal.");
                break;

            case "echo":
                System.out.println(TextEdit.arrayToString(Arrays.copyOfRange(input, 1, input.length)));
                break;

            case "exit":
                return false;
        
            default:
                System.out.println("Invalid input!");
        }
        return true;
    }
}
