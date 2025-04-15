import java.util.Map;
import java.util.Objects;

public class MorseEncoder {
	private static final Morse morse = new Morse();

	static String Encode(String string) {
		StringBuilder ret = new StringBuilder();
		for(int i = 0;i < string.length();++i) {
			if(string.charAt(i) == ' ')
				ret.append(" ");
			else
				ret.append(morse.letterAndMorse.get(Character.toUpperCase(string.charAt(i)))).append(" ");
		}

		return ret.substring(0, ret.length() - 1);
	}

	static String Decode(String string) {
		String[] letters = string.split(" ");
		StringBuilder ret = new StringBuilder();

		for(String letter : letters) {
			for(Map.Entry<Character, String> code : morse.letterAndMorse.entrySet()) {
				if(Objects.equals(code.getValue(), letter)) {
					ret.append(code.getKey());
					break;
				}
			}
		}

		return ret.toString().charAt(0) + ret.substring(1).toLowerCase();
	}
}