
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;



public class Main {
	/*
	 this program will help you to understand and use morse code
	 it has 2 modalities
	 	-it show a morse value and require you the correct value
	 	-it show you a letter and require you to code it
	*/	
	public static final Dimension SCREEN = Toolkit.getDefaultToolkit().getScreenSize();
	public static final Color FOREGROUND_COLOR = Color.decode("#e6e1e1").brighter();
	public static final Color GREEN_BACKGROUND = Color.decode("#235c32");
	public static final Color YELLOW_BACKGROUND = Color.decode("#5c5323");
	public static final Color RED_BACKGROUND = Color.decode("#691f1f");
	public static final Color BACKGROUND = Color.decode("#212020").brighter();
	
	
	public static void menuScreen(JFrame window, WindowGraphics windowGraphics) {
		windowGraphics.removeAll();
		window.repaint();
        window.revalidate();
        
		String titleString = "Morse Trainer 1.0";
        JLabel title = new JLabel(titleString);
        title.setFont(new Font("Consolas",Font.BOLD,50));
        FontMetrics fontMetrics = title.getFontMetrics(title.getFont());
        
		title.setBounds(window.getWidth()/2-fontMetrics.stringWidth(titleString)/2,
					150,
					fontMetrics.stringWidth(titleString),
					fontMetrics.getHeight());
        
        title.setForeground(FOREGROUND_COLOR);
        
        	title.setFocusable(false);
        	title.setBorder(BorderFactory.createEmptyBorder());
        	windowGraphics.add(title);
        
        
        
        	JButton[] menuButtons = new JButton[4];
        
        
        	int padding=0;
        	for (int i = 0; i < menuButtons.length; i++) {
        		menuButtons[i]=new JButton();
        		menuButtons[i].setBounds(window.getWidth()/2-100,250+padding,200,50);
        		menuButtons[i].setForeground(FOREGROUND_COLOR);
        		menuButtons[i].setFont(new Font("Consolas",Font.BOLD,18));
        		menuButtons[i].setFocusable(false);
        		menuButtons[i].setBorder(BorderFactory.createEmptyBorder());
	        	
        		padding+=100;
        		windowGraphics.add(menuButtons[i]);			
		}

        
        menuButtons[0].setText("Decode Mode");
        menuButtons[0].setBackground(GREEN_BACKGROUND);
        menuButtons[0].addActionListener(e -> {
        	decodeModeScreen(window,windowGraphics);
        });
        
        menuButtons[1].setText("Encode Mode");
        menuButtons[1].setBackground(GREEN_BACKGROUND);
        menuButtons[1].addActionListener(e -> {
        	encodeModeScreen(window, windowGraphics);
        }); 
        
        menuButtons[2].setText("About Morse Code");
        menuButtons[2].setBackground(YELLOW_BACKGROUND);
        menuButtons[2].addActionListener(e -> {
        	aboutScreen(window, windowGraphics);
        }); 
        
        
        
        menuButtons[3].setText("Exit");
        menuButtons[3].setBackground(RED_BACKGROUND);
        menuButtons[3].addActionListener(e -> {window.dispose();System.exit(0);}); // makes the program exit when you click button exit

        addCreditLabel(window,windowGraphics);
        
	}
	
        @SuppressWarnings("empty-statement")
	public static void decodeModeScreen(JFrame window, WindowGraphics windowGraphics) {
		windowGraphics.removeAll();
	    windowGraphics.repaint();
	    windowGraphics.revalidate();
	    
	    MorseDecoder morseDecoder = new MorseDecoder();
	    String[] character = new String[1];
	    while((character[0] = morseDecoder.getRandomValue()).equals(" "));
	    
	    
	    JButton backButton = new JButton("← Back");
	    backButton.setBounds(20, 20, 100, 30);
	    backButton.setBackground(Main.RED_BACKGROUND);
	    backButton.setForeground(Main.FOREGROUND_COLOR);
	    backButton.setFocusable(false);
	    backButton.setBorder(BorderFactory.createEmptyBorder());
	    backButton.addActionListener(e -> {
	        menuScreen(window, windowGraphics);
	    });
	    
	    windowGraphics.add(backButton);
	    
	    JLabel title = new JLabel("Decode");
	    title.setFont(new Font("Consolas", Font.BOLD, 30));
	    FontMetrics titleMetrics = title.getFontMetrics(title.getFont());
	    title.setBounds(window.getWidth() / 2 - titleMetrics.stringWidth(title.getText()) / 2, 30, titleMetrics.stringWidth(title.getText()), 40);
	    title.setForeground(Main.FOREGROUND_COLOR);
	    
	    windowGraphics.add(title);
	    
	    JTextField input = new JTextField();
	    input.setBounds(window.getWidth()/2-125,window.getHeight()/2+100,150,50);
	    input.setBorder(null);
	    input.setFont(new Font("Consolas", Font.BOLD, 40));
	    input.setHighlighter(null);
	    input.setHorizontalAlignment(JTextField.CENTER);
	    input.setBackground(Main.BACKGROUND.brighter().brighter());
	    input.setCaretColor(Main.FOREGROUND_COLOR);
	    input.setForeground(Main.FOREGROUND_COLOR);
	    input.addKeyListener(new KeyAdapter() {
	    	@Override
	        public void keyTyped(KeyEvent e) {
	            if (input.getText().length() >= 1) {
	                e.consume();
	            }
	            if (!(e.getKeyChar()+"").toUpperCase().matches("^[A-Z]$") && !(e.getKeyChar()+"").toUpperCase().matches("^[0-9]$")) {
	                e.consume(); 
	            }
	        }
	    });
	    
	    windowGraphics.add(input);
	    
	    JLabel codeViewer = new JLabel(character[0]);
	    codeViewer.setFont(new Font("Consolas", Font.BOLD, 80));
	    FontMetrics codeViewerMetrics = codeViewer.getFontMetrics(codeViewer.getFont());
	    codeViewer.setBounds(window.getWidth() / 2 - codeViewerMetrics.stringWidth(codeViewer.getText()) / 2, window.getHeight()/2-100, codeViewerMetrics.stringWidth(title.getText()), 100);
	    codeViewer.setForeground(Main.FOREGROUND_COLOR);
	    
	    windowGraphics.add(codeViewer);
	    
	    JButton submitButton = new JButton("Submit");
	    submitButton.setBounds(window.getWidth()/2-125+input.getWidth(),window.getHeight()/2+100,100,50);
	    submitButton.setBackground(Main.GREEN_BACKGROUND);
	    submitButton.setForeground(Main.FOREGROUND_COLOR);
	    submitButton.setFont(new Font("Consolas", Font.BOLD, 18));
	    submitButton.setFocusable(false);
	    submitButton.setBorder(BorderFactory.createEmptyBorder());
	    submitButton.addActionListener(e -> {
	    	if(morseDecoder.decodeMorse(character[0]).equals(input.getText().toUpperCase())) {
	    		while((character[0] = morseDecoder.getRandomValue()).equals(" "));
	    		codeViewer.setText(character[0]);
	    		input.setText("");
	    	}
	    	else {
	    		input.setText("");
	    	}
	    });
	    
	    windowGraphics.add(submitButton);
	    
	    addCreditLabel(window,windowGraphics);

	    windowGraphics.repaint();
	    windowGraphics.revalidate();
		
	}

	public static void aboutScreen(JFrame window, WindowGraphics windowGraphics) {
		windowGraphics.removeAll();
		windowGraphics.repaint();
		windowGraphics.revalidate();
	
		windowGraphics.setLayout(null);
	
		JLabel title = new JLabel("About Morse Code");
		title.setFont(new Font("Consolas", Font.BOLD, 30));
		FontMetrics titleMetrics = title.getFontMetrics(title.getFont());
		title.setBounds(window.getWidth() / 2 - titleMetrics.stringWidth(title.getText()) / 2, 30, titleMetrics.stringWidth(title.getText()), 40);
		title.setForeground(Main.FOREGROUND_COLOR);
		windowGraphics.add(title);
	
		String[] columnNames = {"Letter", "Morse"};
		MorseDecoder morseDecoder = new MorseDecoder();
		JTable table = new JTable(new DefaultTableModel(morseDecoder.getKeyValuesFromMap(), columnNames));
	
		table.setRowHeight(30);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setFillsViewportHeight(true);
		table.setEnabled(false);
		table.setFont(new Font("Consolas", Font.BOLD, 20));
		table.setForeground(Color.WHITE);
		table.setBackground(Main.BACKGROUND);
		table.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 30));
		table.getTableHeader().setForeground(Main.FOREGROUND_COLOR);
		table.getTableHeader().setBackground(Main.BACKGROUND.darker());
		table.setGridColor(Main.FOREGROUND_COLOR.darker().darker().darker());
		table.getTableHeader().setReorderingAllowed(false);
	
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
	
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
			@Override
			protected JButton createIncreaseButton(int orientation) {
				JButton button = new JButton();
				button.setPreferredSize(new Dimension(0, 0));
				button.setVisible(false);
				return button;
			}
	
			@Override
			protected JButton createDecreaseButton(int orientation) {
				JButton button = new JButton();
				button.setPreferredSize(new Dimension(0, 0));
				button.setVisible(false);
				return button;
			}
	
			@Override
			protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {}
	
			@Override
			protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {}
		});
	
		int tableX = 20;
		int tableY = 100;
		int tableWidth = window.getWidth() - 40; 
		int tableHeight = window.getHeight() - 150;
	
		scrollPane.setBounds(tableX, tableY, tableWidth, tableHeight);
	
		windowGraphics.add(scrollPane);
	
		JButton backButton = new JButton("← Back");
		backButton.setBounds(20, 20, 100, 30);
		backButton.setBackground(Main.RED_BACKGROUND);
		backButton.setForeground(Main.FOREGROUND_COLOR);
		backButton.setFocusable(false);
		backButton.setBorder(BorderFactory.createEmptyBorder());
		backButton.addActionListener(e -> {
			windowGraphics.removeAll();
			menuScreen(window, windowGraphics);
			window.repaint();
			window.revalidate();
		});
		windowGraphics.add(backButton);
	
		addCreditLabel(window, windowGraphics);
	
		window.repaint();
		window.revalidate();
	}
	
	
	

    @SuppressWarnings("empty-statement")
	public static void encodeModeScreen(JFrame window,WindowGraphics windowGraphics) {
		windowGraphics.removeAll();
	    windowGraphics.repaint();
	    windowGraphics.revalidate();
	    
	    MorseDecoder morseDecoder = new MorseDecoder();
	    String[] character = new String[1];

	    while((character[0] = morseDecoder.getRandomKey()).equals(" "));
	    
	    JButton backButton = new JButton("← Back");
	    backButton.setBounds(20, 20, 100, 30);
	    backButton.setBackground(Main.RED_BACKGROUND);
	    backButton.setForeground(Main.FOREGROUND_COLOR);
	    backButton.setFocusable(false);
	    backButton.setBorder(BorderFactory.createEmptyBorder());
	    backButton.addActionListener(e -> {
	        menuScreen(window, windowGraphics);
	    });
	    
	    windowGraphics.add(backButton);
	    
	    JLabel title = new JLabel("Encode");
	    title.setFont(new Font("Consolas", Font.BOLD, 30));
	    FontMetrics titleMetrics = title.getFontMetrics(title.getFont());
	    title.setBounds(window.getWidth() / 2 - titleMetrics.stringWidth(title.getText()) / 2, 30, titleMetrics.stringWidth(title.getText()), 40);
	    title.setForeground(Main.FOREGROUND_COLOR);
	    
	    windowGraphics.add(title);
	    
	    JTextField input = new JTextField();
	    input.setBounds(window.getWidth()/2-125,window.getHeight()/2+100,150,50);
	    input.setBorder(null);
	    input.setFont(new Font("Consolas", Font.BOLD, 40));
	    input.setHighlighter(null);
	    input.setHorizontalAlignment(JTextField.CENTER);
	    input.setBackground(Main.BACKGROUND.brighter().brighter());
	    input.setCaretColor(Main.FOREGROUND_COLOR);
	    input.setForeground(Main.FOREGROUND_COLOR);
	    input.addKeyListener(new KeyAdapter() {
	    	@Override
	        public void keyTyped(KeyEvent e) {
	            if (input.getText().length() >= 5) {
	                e.consume();
	            }
	            if (e.getKeyChar() != '.' && e.getKeyChar() != '-') {
	                e.consume(); 
	            }
	        }
	    });
	    
	    windowGraphics.add(input);
	    
	    JLabel codeViewer = new JLabel(character[0]);
	    codeViewer.setFont(new Font("Consolas", Font.BOLD, 80));
	    FontMetrics codeViewerMetrics = codeViewer.getFontMetrics(codeViewer.getFont());
	    codeViewer.setBounds(window.getWidth() / 2 - codeViewerMetrics.stringWidth(codeViewer.getText()) / 2, window.getHeight()/2-100, codeViewerMetrics.stringWidth(title.getText()), 100);
	    codeViewer.setForeground(Main.FOREGROUND_COLOR);
	    
	    windowGraphics.add(codeViewer);
	    
	    JButton submitButton = new JButton("Submit");
	    submitButton.setBounds(window.getWidth()/2-125+input.getWidth(),window.getHeight()/2+100,100,50);
	    submitButton.setBackground(Main.GREEN_BACKGROUND);
	    submitButton.setForeground(Main.FOREGROUND_COLOR);
	    submitButton.setFont(new Font("Consolas", Font.BOLD, 18));
	    submitButton.setFocusable(false);
	    submitButton.setBorder(BorderFactory.createEmptyBorder());
	    submitButton.addActionListener(e -> {
	    	if(morseDecoder.encodeText(character[0]).equals(input.getText())) {
	    		while((character[0] = morseDecoder.getRandomKey()).equals(" "));
	    		codeViewer.setText(character[0]);
	    		input.setText("");
	    	}
	    	else {
	    		input.setText("");
	    	}
	    });
	    
	    windowGraphics.add(submitButton);
	    
	    addCreditLabel(window,windowGraphics);

	    windowGraphics.repaint();
	    windowGraphics.revalidate();
	}
	

	
	public static void windowInitialization(JFrame window, WindowGraphics windowGraphics) {
		window.setBounds(SCREEN.width/2-400,SCREEN.height/2-400,800,800);
        window.setResizable(false);
        window.setUndecorated(true);
        window.setLayout(null);
        window.getContentPane().setBackground(BACKGROUND);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowGraphics.setBounds(0,0,window.getWidth(),window.getHeight());
		windowGraphics.setBackground(BACKGROUND);
		windowGraphics.setLayout(null);
		window.add(windowGraphics);
	}
	
	public static void addCreditLabel(JFrame window,WindowGraphics windowGraphics) {
		String credit = "--Made by Nfire07--";
		JLabel creditLabel = new JLabel(credit);
		creditLabel.setBackground(BACKGROUND);
		creditLabel.setForeground(FOREGROUND_COLOR);
		creditLabel.setHorizontalAlignment(JLabel.CENTER);
		creditLabel.setFont(new Font("Consolas",Font.BOLD,18));
		FontMetrics creditMetrics = creditLabel.getFontMetrics(creditLabel.getFont());
		creditLabel.setBounds(window.getHeight()-creditMetrics.stringWidth(credit)-20,window.getWidth()-50,creditMetrics.stringWidth(credit),50);
		windowGraphics.add(creditLabel);
	}
	
	public static void main(String[] args) {
        JFrame window = new JFrame("Morse Trainer 1.0");
        
        WindowGraphics windowGraphics = new WindowGraphics();
        
        windowInitialization(window,windowGraphics);
        
        menuScreen(window,windowGraphics);
        
        window.setVisible(true);
    }

}
