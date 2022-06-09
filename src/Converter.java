import java.util.HashMap;
import java.util.Map;

public class Converter {

    private static String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
    
    private static Map<String, Integer> numbers = new HashMap<String, Integer>() {{

        for(int i = 0; i < letters.length; i++) {
            put(letters[i], i+1);
        }

    }};

    public static String intToString(int num) {
        return letters[num];
    }

    public static int stringToInt(String letter) {
        return numbers.get(letter);
    }
    
}
