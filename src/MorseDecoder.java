import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MorseDecoder {
    private final Map<String, String> alphaToMorse;
    private final Map<String, String> morseToAlpha;

    public MorseDecoder() {
    	 alphaToMorse = new HashMap<>();
         alphaToMorse.put("A", ".-");
         alphaToMorse.put("B", "-...");
         alphaToMorse.put("C", "-.-.");
         alphaToMorse.put("D", "-..");
         alphaToMorse.put("E", ".");
         alphaToMorse.put("F", "..-.");
         alphaToMorse.put("G", "--.");
         alphaToMorse.put("H", "....");
         alphaToMorse.put("I", "..");
         alphaToMorse.put("J", ".---");
         alphaToMorse.put("K", "-.-");
         alphaToMorse.put("L", ".-..");
         alphaToMorse.put("M", "--");
         alphaToMorse.put("N", "-.");
         alphaToMorse.put("O", "---");
         alphaToMorse.put("P", ".--.");
         alphaToMorse.put("Q", "--.-");
         alphaToMorse.put("R", ".-.");
         alphaToMorse.put("S", "...");
         alphaToMorse.put("T", "-");
         alphaToMorse.put("U", "..-");
         alphaToMorse.put("V", "...-");
         alphaToMorse.put("W", ".--");
         alphaToMorse.put("X", "-..-");
         alphaToMorse.put("Y", "-.--");
         alphaToMorse.put("Z", "--..");
         alphaToMorse.put("0", "-----");
         alphaToMorse.put("1", ".----");
         alphaToMorse.put("2", "..---");
         alphaToMorse.put("3", "...--");
         alphaToMorse.put("4", "....-");
         alphaToMorse.put("5", ".....");
         alphaToMorse.put("6", "-....");
         alphaToMorse.put("7", "--...");
         alphaToMorse.put("8", "---..");
         alphaToMorse.put("9", "----.");
         alphaToMorse.put(" ", " ");

         morseToAlpha = new HashMap<>();
         morseToAlpha.put(".-", "A");
         morseToAlpha.put("-...", "B");
         morseToAlpha.put("-.-.", "C");
         morseToAlpha.put("-..", "D");
         morseToAlpha.put(".", "E");
         morseToAlpha.put("..-.", "F");
         morseToAlpha.put("--.", "G");
         morseToAlpha.put("....", "H");
         morseToAlpha.put("..", "I");
         morseToAlpha.put(".---", "J");
         morseToAlpha.put("-.-", "K");
         morseToAlpha.put(".-..", "L");
         morseToAlpha.put("--", "M");
         morseToAlpha.put("-.", "N");
         morseToAlpha.put("---", "O");
         morseToAlpha.put(".--.", "P");
         morseToAlpha.put("--.-", "Q");
         morseToAlpha.put(".-.", "R");
         morseToAlpha.put("...", "S");
         morseToAlpha.put("-", "T");
         morseToAlpha.put("..-", "U");
         morseToAlpha.put("...-", "V");
         morseToAlpha.put(".--", "W");
         morseToAlpha.put("-..-", "X");
         morseToAlpha.put("-.--", "Y");
         morseToAlpha.put("--..", "Z");
         morseToAlpha.put("-----", "0");
         morseToAlpha.put(".----", "1");
         morseToAlpha.put("..---", "2");
         morseToAlpha.put("...--", "3");
         morseToAlpha.put("....-", "4");
         morseToAlpha.put(".....", "5");
         morseToAlpha.put("-....", "6");
         morseToAlpha.put("--...", "7");
         morseToAlpha.put("---..", "8");
         morseToAlpha.put("----.", "9");
         morseToAlpha.put(" ", " ");
    }

    public String decodeMorse(String morse) {
        String[] morseWords = morse.split("  "); 
        StringBuilder decodedMessage = new StringBuilder();

        for (String morseWord : morseWords) {
            String[] morseCharacters = morseWord.split(" "); 
            for (String morseCharacter : morseCharacters) {
                decodedMessage.append(morseToAlpha.get(morseCharacter));
            }
            decodedMessage.append(" ");  
        }

        return decodedMessage.toString().trim(); 
    }

    
    public String encodeText(String text) {
        StringBuilder encodedMessage = new StringBuilder();
        text = text.toUpperCase(); 

        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            String morseChar = alphaToMorse.get(String.valueOf(character));
            if (morseChar != null) {
                encodedMessage.append(morseChar).append(" ");
            }
        }

        return encodedMessage.toString().trim();
    }

    public String[][] getKeyValueFromMap() {
        String[][] keyValue = new String[alphaToMorse.size()][2];
        int i = 0;

        for (Map.Entry<String, String> entry : alphaToMorse.entrySet()) {
            keyValue[i][0] = entry.getKey();
            keyValue[i][1] = entry.getValue();
            i++;
        }

        return keyValue;
    }
    
    public String getRandomKey() {
    	return new ArrayList<String>(alphaToMorse.keySet()).get(new Random().nextInt(alphaToMorse.size())); 
    }

    
    
}
