package impSinkAship;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StartupBustGUI {

    private GameHelper helper = new GameHelper();
    private ArrayList<Startup> startups = new ArrayList<>();
    private int numOfGuesses = 0;
    private JButton[][] cells = new JButton[7][7];
    private JLabel statusLabel = new JLabel("Sink poniez, hacqi, cabista!", SwingConstants.CENTER);
    private JLabel guessLabel = new JLabel("Guesses: 0", SwingConstants.CENTER);
    private static final String ALPHABET = "abcdefg";

    public StartupBustGUI() {
        setUpGame();
        buildGUI();
    }

    private void setUpGame() {
        Startup one = new Startup(); one.setName("poniez");
        Startup two = new Startup(); two.setName("hacqi");
        Startup three = new Startup(); three.setName("cabista");
        startups.add(one);
        startups.add(two);
        startups.add(three);
        for (Startup s : startups) s.setLocationCells(helper.placeStartup(3));
    }

    private void buildGUI() {
        JFrame frame = new JFrame("Startup Bust!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(440, 360);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(6, 6));

        // Grid
        JPanel gridPanel = new JPanel(new GridLayout(7, 7, 3, 3));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                String coord = ALPHABET.charAt(col) + String.valueOf(row);
                cells[row][col] = new JButton(coord);
                cells[row][col].setFont(new Font("SansSerif", Font.PLAIN, 11));
                final String guess = coord;
                cells[row][col].addActionListener(e -> processGuess(guess));
                gridPanel.add(cells[row][col]);
            }
        }

        // Status
        JPanel south = new JPanel(new GridLayout(2, 1));
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        guessLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        south.add(statusLabel);
        south.add(guessLabel);

        frame.add(new JLabel("Startup Bust — Sink all 3 ships!", SwingConstants.CENTER), BorderLayout.NORTH);
        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(south, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    private void processGuess(String guess) {
        if (startups.isEmpty())
            return;
        numOfGuesses++;
        guessLabel.setText("Guesses: " + numOfGuesses);
        String result = "miss";

        int col = ALPHABET.indexOf(String.valueOf(guess.charAt(0)));
        int row = Integer.parseInt(String.valueOf(guess.charAt(1)));
        JButton btn = cells[row][col];
        btn.setEnabled(false);

        for (Startup s : startups) {
            result = s.checkYourself(guess);
            if (result.equals("hit")) {
                btn.setBackground(Color.ORANGE);
                statusLabel.setText("HIT! Keep going!");
                break;
            }
            if (result.equals("kill")) {
                btn.setBackground(Color.RED);
                startups.remove(s);
                if (startups.isEmpty()) {
                    statusLabel.setText("ALL SUNK in " + numOfGuesses + " guesses!");
                    String msg = "All the ships are dead! Your stock is now worthless.\n\n";
                    if (numOfGuesses <= 18) {
                        msg += "It only took you " + numOfGuesses + " guesses.\n";
                        msg += "You got out before your options sank.";
                    } else {
                        msg += "Took you long enough. " + numOfGuesses + " guesses.\n";
                        msg += "Finished before you ran out of options.";
                    }
                    JOptionPane.showMessageDialog(null, msg);
                } else {
                    statusLabel.setText("SUNK! " + startups.size() + " ship(s) left!");
                }
                break;
            }
        }
        if (result.equals("miss")) {
            btn.setBackground(Color.CYAN);
            statusLabel.setText("Miss! Try again.");
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(StartupBustGUI::new);
    }
}

