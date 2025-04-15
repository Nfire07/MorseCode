import java.util.HashMap;
import java.util.Map;

public class Morse {
	Map<Character, String> letterAndMorse = new HashMap<>();

	Morse() {
		letterAndMorse.put('A', "._");
		letterAndMorse.put('B', "_...");
		letterAndMorse.put('C', "_._.");
		letterAndMorse.put('D', "_..");
		letterAndMorse.put('E', ".");
		letterAndMorse.put('F', ".._.");
		letterAndMorse.put('G', "__.");
		letterAndMorse.put('H', "....");
		letterAndMorse.put('I', "..");
		letterAndMorse.put('J', ".___");
		letterAndMorse.put('K', "_._");
		letterAndMorse.put('L', "._..");
		letterAndMorse.put('M', "__");
		letterAndMorse.put('N', "_.");
		letterAndMorse.put('O', "___");
		letterAndMorse.put('P', ".__.");
		letterAndMorse.put('Q', "__._");
		letterAndMorse.put('R', "._.");
		letterAndMorse.put('S', "...");
		letterAndMorse.put('T', "_");
		letterAndMorse.put('U', ".._");
		letterAndMorse.put('V', "..._");
		letterAndMorse.put('W', ".__");
		letterAndMorse.put('X', "_.._");
		letterAndMorse.put('Y', "_.__");
		letterAndMorse.put('Z', "__..");
		letterAndMorse.put('0', "_____");
		letterAndMorse.put('1', ".____");
		letterAndMorse.put('2', "..___");
		letterAndMorse.put('3', "...__");
		letterAndMorse.put('4', "...._");
		letterAndMorse.put('5', ".....");
		letterAndMorse.put('6', "____.");
		letterAndMorse.put('7', "___..");
		letterAndMorse.put('8', "__...");
		letterAndMorse.put('9', "_....");
		letterAndMorse.put(' ', " ");
	}
}