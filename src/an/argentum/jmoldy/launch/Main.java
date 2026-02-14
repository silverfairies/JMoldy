package an.argentum.jmoldy.launch;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Launching...");
            Process process = new ProcessBuilder("java -cp lib/*:* an.argentum.jmoldy.Main".split(" "))
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .redirectInput(ProcessBuilder.Redirect.INHERIT)
            .start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
