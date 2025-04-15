import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException; // Necessario per gestione errori file
import java.nio.charset.StandardCharsets; // Per specificare codifica file
import java.nio.file.Files; // Nuova API per file
import java.nio.file.NoSuchFileException; // Errore specifico file non trovato
import java.nio.file.Path;
import java.nio.file.Paths; // Per creare percorsi file
import java.util.*;
import java.util.List;
import java.util.stream.Collectors; // Per elaborare le linee lette

class MorseTrainerGUI extends JFrame implements ActionListener {

	// Mappa per la traduzione da carattere a Morse
	private final Map<Character, String> morseMap;
	// Lista di parole da cui pescare (ora caricata da file)
	private List<String> wordList; // Rimuovi 'final' perché viene inizializzata nel metodo
	private final Random random;

	// Componenti GUI
	private JLabel morseLabel;
	private JTextField inputField;
	private JButton submitButton;
	private JLabel feedbackLabel;
	private JLabel progressLabel;

	// Stato del gioco
	private String currentWord;
	private int wordsToPractice;
	private int wordsDone;
	private int correctCount;
	private int incorrectCount;
	private boolean isInfinite;

	// Costanti per la spaziatura Morse
	private static final String LETTER_SPACE = "   ";
	private static final String WORD_FILE = "./src/parole.txt"; // Nome del file delle parole

	public MorseTrainerGUI() {
		super("Impara il Codice Morse");
		morseMap = new HashMap<>();
		random = new Random();
		wordList = new ArrayList<>(); // Inizializza lista vuota

		initializeMorseMap();
		if (!initializeWordList()) { // Carica la lista di parole da file
			// Se il caricamento fallisce e non ci sono parole di default, esci.
			JOptionPane.showMessageDialog(this,
					"Errore critico nel caricamento delle parole.\nIl programma verrà chiuso.",
					"Errore File", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		// Se la lista è vuota dopo il tentativo di caricamento, esci
		if (wordList.isEmpty()) {
			JOptionPane.showMessageDialog(this,
					"Nessuna parola trovata nel file '" + WORD_FILE + "' o nel fallback.\nIl programma verrà chiuso.",
					"Errore Parole", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		if (!askNumberOfWords()) {
			System.exit(0);
		}

		setupGUI();
		generateNewWord();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void initializeMorseMap() {
		// (Identico a prima - omesso per brevità)
		morseMap.put('A', ".-"); morseMap.put('B', "-..."); morseMap.put('C', "-.-.");
		morseMap.put('D', "-.."); morseMap.put('E', "."); morseMap.put('F', "..-.");
		morseMap.put('G', "--."); morseMap.put('H', "...."); morseMap.put('I', "..");
		morseMap.put('J', ".---"); morseMap.put('K', "-.-"); morseMap.put('L', ".-..");
		morseMap.put('M', "--"); morseMap.put('N', "-."); morseMap.put('O', "---");
		morseMap.put('P', ".--."); morseMap.put('Q', "--.-"); morseMap.put('R', ".-.");
		morseMap.put('S', "..."); morseMap.put('T', "-"); morseMap.put('U', "..-");
		morseMap.put('V', "...-"); morseMap.put('W', ".--"); morseMap.put('X', "-..-");
		morseMap.put('Y', "-.--"); morseMap.put('Z', "--..");
		morseMap.put('0', "-----"); morseMap.put('1', ".----"); morseMap.put('2', "..---");
		morseMap.put('3', "...--"); morseMap.put('4', "....-"); morseMap.put('5', ".....");
		morseMap.put('6', "-...."); morseMap.put('7', "--..."); morseMap.put('8', "---..");
		morseMap.put('9', "----.");
	}

	// Modificato per caricare da file
	private boolean initializeWordList() {
		try {
			Path path = Paths.get(WORD_FILE);
			System.out.println("Tentativo di caricamento parole da: " + path.toAbsolutePath()); // Log percorso
			List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			wordList = lines.stream()
					.map(String::trim) // Rimuove spazi bianchi inizio/fine
					.filter(line -> !line.isEmpty() && line.matches("^[a-zA-Z]+$")) // Filtra linee vuote e non solo alfabetiche
					.map(String::toUpperCase) // Converte in maiuscolo
					.collect(Collectors.toList()); // Colleziona in una nuova lista

			if (wordList.isEmpty()) {
				System.out.println("Attenzione: Il file '" + WORD_FILE + "' è vuoto o non contiene parole valide.");
				addDefaultWords(); // Aggiunge parole di default se il file è vuoto
				if(wordList.isEmpty()) { // Se anche le default falliscono (improbabile qui)
					return false;
				}
				JOptionPane.showMessageDialog(this,
						"Il file '" + WORD_FILE + "' è vuoto o non contiene parole valide.\nUsando lista di parole di default.",
						"Attenzione", JOptionPane.WARNING_MESSAGE);
			} else {
				System.out.println("Caricate " + wordList.size() + " parole da " + WORD_FILE);
			}
			return true;

		} catch (NoSuchFileException e) {
			System.err.println("Errore: File non trovato: " + WORD_FILE);
			JOptionPane.showMessageDialog(this,
					"File delle parole '" + WORD_FILE + "' non trovato.\nAssicurati che sia nella stessa cartella del programma.\nUsando lista di parole di default.",
					"Errore File", JOptionPane.WARNING_MESSAGE);
			addDefaultWords(); // Usa parole di default come fallback
			return !wordList.isEmpty(); // Ritorna true se le parole di default sono state aggiunte

		} catch (IOException e) {
			System.err.println("Errore durante la lettura del file: " + e.getMessage());
			JOptionPane.showMessageDialog(this,
					"Errore durante la lettura del file '" + WORD_FILE + "'.\n" + e.getMessage() + "\nUsando lista di parole di default.",
					"Errore I/O", JOptionPane.ERROR_MESSAGE);
			addDefaultWords(); // Usa parole di default come fallback
			return !wordList.isEmpty(); // Ritorna true se le parole di default sono state aggiunte
		}
	}

	// Metodo helper per aggiungere parole di default in caso di errore file
	private void addDefaultWords() {
		System.out.println("Aggiunta lista di parole di default.");
		wordList.clear(); // Assicura che la lista sia vuota prima di aggiungere
		wordList.add("CIAO"); wordList.add("MONDO"); wordList.add("JAVA");
		wordList.add("CODICE"); wordList.add("MORSE"); wordList.add("TRENO");
		wordList.add("LUCE"); wordList.add("SOLE"); wordList.add("MARE");
		wordList.add("CASA"); wordList.add("LIBRO"); wordList.add("STUDIO");
	}


	private boolean askNumberOfWords() {
		// (Identico a prima - omesso per brevità)
		boolean validInput = false;
		while (!validInput) {
			String input = JOptionPane.showInputDialog(
					this,
					"Quante parole vuoi praticare? (Inserisci 0 o lascia vuoto per infinito)",
					"Impostazione Sessione",
					JOptionPane.QUESTION_MESSAGE
			);

			if (input == null) { return false; }

			if (input.trim().isEmpty() || input.equals("0")) {
				isInfinite = true;
				wordsToPractice = -1;
				validInput = true;
				System.out.println("Modalità infinita selezionata.");
			} else {
				try {
					wordsToPractice = Integer.parseInt(input.trim());
					if (wordsToPractice > 0) {
						isInfinite = false;
						validInput = true;
						System.out.println("Praticherai " + wordsToPractice + " parole.");
					} else {
						JOptionPane.showMessageDialog(this, "Per favore, inserisci un numero positivo, 0 o lascia vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
					}
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(this, "Input non valido. Inserisci un numero.", "Errore", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		wordsDone = 0; correctCount = 0; incorrectCount = 0;
		return true;
	}

	private void setupGUI() {
		// (Identico a prima - omesso per brevità)
		setLayout(new BorderLayout(10, 10));

		JPanel topPanel = new JPanel(new GridLayout(2, 1));
		progressLabel = new JLabel("Parola 1 / " + (isInfinite ? "∞" : wordsToPractice), SwingConstants.CENTER);
		feedbackLabel = new JLabel("Inserisci la traduzione e premi Invio o clicca 'Invia'", SwingConstants.CENTER);
		feedbackLabel.setForeground(Color.BLUE);
		topPanel.add(progressLabel);
		topPanel.add(feedbackLabel);
		add(topPanel, BorderLayout.NORTH);

		morseLabel = new JLabel("...", SwingConstants.CENTER);
		morseLabel.setFont(new Font("Monospaced", Font.BOLD, 24));
		add(morseLabel, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel(new FlowLayout());
		inputField = new JTextField(20);
		submitButton = new JButton("Invia");

		inputField.addActionListener(this);
		submitButton.addActionListener(this);

		bottomPanel.add(new JLabel("Traduci:"));
		bottomPanel.add(inputField);
		bottomPanel.add(submitButton);
		add(bottomPanel, BorderLayout.SOUTH);

		((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

	private void generateNewWord() {
		if (!isInfinite && wordsDone >= wordsToPractice) {
			showStats();
			return;
		}

		// Scegli una parola casuale dalla lista (assicurati che la lista non sia vuota)
		if (wordList == null || wordList.isEmpty()) {
			morseLabel.setText("ERRORE: Lista parole vuota!");
			inputField.setEnabled(false);
			submitButton.setEnabled(false);
			return;
		}
		currentWord = wordList.get(random.nextInt(wordList.size())).toUpperCase(); // Assicurati sia maiuscola
		String currentMorse = wordToMorse(currentWord);

		morseLabel.setText(currentMorse);

		// Aggiorna etichetta progresso
		if (!isInfinite) {
			progressLabel.setText("Parola " + (wordsDone + 1) + " / " + wordsToPractice);
		} else {
			progressLabel.setText("Parola " + (wordsDone + 1) + " / ∞");
		}

		// Resetta il feedback solo se necessario (non alla prima parola)
		if (wordsDone > 0 || !feedbackLabel.getText().contains("Inserisci la traduzione")) {
			feedbackLabel.setText(" "); // Pulisce feedback precedente
			feedbackLabel.setForeground(Color.BLUE);
		}

		// Pulisci e metti focus sull'input (assicurati sia abilitato)
		inputField.setText("");
		if (inputField.isEnabled()) { // Metti focus solo se abilitato
			inputField.requestFocusInWindow();
		}


		pack(); // Riadatta la finestra se necessario
	}

	private String wordToMorse(String word) {
		// (Identico a prima - omesso per brevità)
		StringBuilder morseBuilder = new StringBuilder();
		for (int i = 0; i < word.length(); i++) {
			char character = word.charAt(i);
			String morseChar = morseMap.get(character);
			// Carattere non trovato
			morseBuilder.append(Objects.requireNonNullElse(morseChar, "?"));
			if (i < word.length() - 1) {
				morseBuilder.append(LETTER_SPACE);
			}
		}
		return morseBuilder.toString();
	}

	// Modificato per gestire abilitazione/disabilitazione input
	private void checkAnswer() {
		String userAnswer = inputField.getText().trim().toUpperCase();

		// Se l'utente non ha inserito nulla, non fare nulla (o dare un feedback)
		if (userAnswer.isEmpty()) {
			feedbackLabel.setText("Per favore, inserisci una traduzione.");
			feedbackLabel.setForeground(Color.ORANGE.darker());
			inputField.requestFocusInWindow(); // Rimetti focus per comodità
			return; // Non procedere oltre
		}

		// Controlla la risposta
		if (userAnswer.equals(currentWord)) {
			feedbackLabel.setText("Corretto!");
			feedbackLabel.setForeground(Color.GREEN.darker());
			correctCount++;
		} else {
			feedbackLabel.setText("Sbagliato! La parola era: " + currentWord);
			feedbackLabel.setForeground(Color.RED);
			incorrectCount++;
		}
		wordsDone++;

		// Disabilita input PRIMA di avviare il timer
		inputField.setEnabled(false);
		submitButton.setEnabled(false);

		// Passa alla prossima parola o mostra le statistiche dopo una pausa
		if (!isInfinite && wordsDone >= wordsToPractice) {
			// Se è l'ultima parola, mostra le statistiche dopo la pausa
			Timer endTimer = new Timer(1500, _ -> showStats());
			endTimer.setRepeats(false);
			endTimer.start();
		} else {
			// Altrimenti, prepara la prossima parola dopo la pausa
			Timer nextWordTimer = new Timer(1500, _ -> {
				// Riabilita input APPENA PRIMA di generare la nuova parola
				inputField.setEnabled(true);
				submitButton.setEnabled(true);
				generateNewWord(); // Genera e mostra la nuova parola
				inputField.requestFocusInWindow(); // Metti focus sul campo riabilitato
			});
			nextWordTimer.setRepeats(false); // Esegui solo una volta
			nextWordTimer.start();
		}
	}

	private void showStats() {
		// Assicurati che i campi siano disabilitati all'inizio delle statistiche
		inputField.setEnabled(false);
		submitButton.setEnabled(false);
		morseLabel.setText("Sessione Completata!");

		double percentage = 0.0;
		if (wordsDone > 0) {
			percentage = ((double) correctCount / wordsDone) * 100;
		}

		String statsMessage = String.format(
				"Sessione Terminata!\n\nParole totali: %d\nCorrette: %d\nSbagliate: %d\nPercentuale successo: %.2f%%\n\nVuoi ricominciare?",
				wordsDone, correctCount, incorrectCount, percentage
		);

		int choice = JOptionPane.showConfirmDialog(
				this,
				statsMessage,
				"Statistiche Finali",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE
		);

		if (choice == JOptionPane.YES_OPTION) {
			if (askNumberOfWords()) { // Chiedi di nuovo il numero
				// Riabilita i campi per la nuova sessione
				inputField.setEnabled(true);
				submitButton.setEnabled(true);
				feedbackLabel.setText("Nuova sessione iniziata.");
				feedbackLabel.setForeground(Color.BLUE);
				generateNewWord();
			} else {
				System.exit(0);
			}
		} else {
			System.exit(0);
		}
	}

	// Modificato per controllare se l'input è abilitato
	@Override
	public void actionPerformed(ActionEvent e) {
		// Ignora l'azione se i campi sono disabilitati (durante la pausa)
		if (!inputField.isEnabled()) {
			return;
		}
		checkAnswer(); // Procede al controllo della risposta
	}
}

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(MorseTrainerGUI::new);
	}
}