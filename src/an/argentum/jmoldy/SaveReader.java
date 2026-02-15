package an.argentum.jmoldy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import an.argentum.util.Pair;
import an.argentum.util.TextEdit;

public class SaveReader {
    
    public static HashMap<String, String> readFile ( File file ) throws FileNotFoundException {
        return map(getStrings(file));
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

    private static HashMap<String, String> map ( String[] input ) {
        HashMap<String, String> output = new HashMap<>();
        LinePair[] buffer = new LinePair[input.length];
        for ( int i = 0; i < input.length; i++ ) {
            System.out.println(input[i]);
            buffer[i] = new LinePair(input[i], ":");
        }
        for ( int i = 0; i < buffer.length; i++ ) output.put(buffer[i].getKey(), buffer[i].getValue());
        return output;
    }

    static class LinePair extends Pair<String, String> {

        public LinePair ( String input, String regex ) {
            super(input.split(regex)[0], TextEdit.getFirstString(input.split(regex)[1]));
        }

    }
}
