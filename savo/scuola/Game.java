import java.util.Random;
import java.util.Scanner;

public class Game {
	private final Scanner scanner = new Scanner(System.in);
	private final Morse morse = new Morse();
	private int words;
	private int correct = 0;

	Game(int words) {
		this.words = words;
		Play();
	}

	private void Play() {
		while(words != 0) {
			int size = new Random().nextInt(17) + 1;
			StringBuilder code = new StringBuilder();
			for(int i = 0;i < size;++i) {
				code.append(morse.letterAndMorse.get((char) (65 + new Random().nextInt(26)))).append(" ");
			}

			System.out.println(code);

			String result = MorseEncoder.Decode(code.toString());
			String answer = scanner.nextLine();

			if(result.equalsIgnoreCase(answer)) {
				System.out.println("Corretto");
				correct++;
			}
			else {
				System.out.println("Sbagliato");
				System.out.println("Risposta corretta: " + result);
			}

			words--;
		}

		System.out.println("Corrette: " + correct + " su " + words);
	}
}
