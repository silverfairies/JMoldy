package an.argentum.jmoldy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import an.argentum.util.TextEdit;

public class SaveReader {
    
    public static HashMap<String, String> readFile ( File file ) throws FileNotFoundException {
        return map(getStrings(file), ":");
    }

    private static String[] getStrings ( File file ) throws FileNotFoundException {
        ArrayList<String> buffer = new ArrayList<>();
        Scanner read = new Scanner(file);
        while ( read.hasNextLine() ) buffer.add(read.nextLine());
        read.close();
        String[] output = new String[buffer.size()];
        output = buffer.toArray(output);
        return output;
    }

    private static HashMap<String, String> map ( String[] input, String regex ) {
        HashMap<String, String> output = new HashMap<>();
        for ( int i = 0; i < input.length; i++ ) {
            System.out.println(input[i]);
            output.put(input[i].split(regex)[0], TextEdit.getFirstString(input[i].split(regex)[1]));
        }
        return output;
    }
}
