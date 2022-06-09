import java.util.HashMap;
import java.util.Map;

public class Converter {

    private static String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
    private static String[] lettersUpcase = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    
    private static Map<String, Integer> numbers = new HashMap<String, Integer>() {{

        for(int i = 0; i < letters.length; i++) {
            put(letters[i], i+1);
        }

    }};

    private static Map<String, Integer> numbersUp = new HashMap<String, Integer>() {{

        for(int i = 0; i < letters.length; i++) {
            put(lettersUpcase[i], i+1);
        }

    }};

    public static boolean checarValidade(String letra) {

        boolean has = false;

        if(!(numbers.get(letra) == null)) {
            has = true;
        }

        if(!(numbersUp.get(letra) == null)) {
            has = true;

        }
        
        return has;

    }

    public static String intToString(int num) {
        return letters[num];
    }

    public static String intToStringUp(int num) {
        return lettersUpcase[num];
    }

    public static int stringToInt(String letter) {
        if(numbers.get(letter) == null)
            return numbersUp.get(letter);
        else
            return numbers.get(letter);
    }
    
}
