package an.argentum.jmoldy;

import java.util.HashMap;

public enum Templates {
    BLANK ( "blank", new HashMap<>() ),
    STANDARD ( "standard", new HashMap<>() );

    private String name;
    private HashMap<String, String> contents;

    Templates ( String name, HashMap<String, String> contents ) {
        this.name = name;
        this.contents = contents;
    }

    public HashMap<String, String> getMap () {
        return this.contents;
    }

    public boolean is (String search) {
        return search.equals(this.name);
    }

    public String getName () {
        return this.name;
    }

    public static Templates find ( String search ) {
        for (Templates each : Templates.values()) {
            if ( each.is(search) ) return each;
        }
        return null;
    }
}
