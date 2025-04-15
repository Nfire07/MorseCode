import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MorseDecoder {
    private final static Map<String, String> alphaToMorse=new HashMap<>();

    public MorseDecoder() {
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

    }

    public static String morseToAlpha(String V) {
		for(Map.Entry<String, String> entry : alphaToMorse.entrySet()) {
			if(entry.getValue().equals(V)){
				return entry.getKey();
			}
		}
		return null;
    }
    
    public String decodeMorse(String morse) {
        String[] morseWords = morse.split("  "); 
        StringBuilder decodedMessage = new StringBuilder();

        for (String morseWord : morseWords) {
            String[] morseCharacters = morseWord.split(" "); 
            for (String morseCharacter : morseCharacters) {
                decodedMessage.append(morseToAlpha(morseCharacter));
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
    
    public String getRandomValue() {
    	return new ArrayList<String>(alphaToMorse.values()).get(new Random().nextInt(alphaToMorse.size())); 
    }

    
    
}
