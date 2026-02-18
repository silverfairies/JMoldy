package an.argentum.jmoldy;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;

import an.argentum.util.FileEdit;
import an.argentum.util.TerminalReader;
import an.argentum.util.TextEdit;
public class Terminal extends TerminalReader {

    private Project project;
    private Path executingPath;

    public Terminal () {
        this.prefix = "M> ";
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
                } else System.out.println("Specify directory to load or -this to load current directory.");
                return true;

            case "load2":
                this.project = new Project(FileSystems.getDefault().getPath(input[1]));
                return true;

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
        }
        if (this.project != null) {
            switch (input[0]) {
                case "clear":
                    if (this.project != null) {
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
                    } else System.out.println("No project loaded.");
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
        } else System.out.println("No project loaded.");
        return true;
    }
}
