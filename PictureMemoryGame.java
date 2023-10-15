import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;




public class PictureMemoryGame extends JFrame {
    private ArrayList<String> imagePaths;
    private ArrayList<String> cardImages;
    private int numberOfMatches;
    private JButton[] cardButtons;
    private int firstCardIndex = -1;
    private int secondCardIndex;
    private int moves; // Add moves tracking variable
    private JLabel movesLabel; // Label to display the number of moves
    private Timer gameTimer; // Timer to track the time
    private int seconds; // Elapsed time in seconds
    private JLabel timeLabel;


    public PictureMemoryGame() {
        setTitle("Picture Memory Game");
        setSize(800, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        imagePaths = new ArrayList<>();

        imagePaths.add("pkm1.png");
        imagePaths.add("pkm2.png");
        imagePaths.add("pkm3.png");
        imagePaths.add("pkm4.png");
        imagePaths.add("pkm5.png");
        imagePaths.add("pkm6.png");
        imagePaths.add("pkm7.png");
        imagePaths.add("pkm8.png");
        imagePaths.add("pkm1.png");
        imagePaths.add("pkm2.png");
        imagePaths.add("pkm3.png");
        imagePaths.add("pkm4.png");
        imagePaths.add("pkm5.png");
        imagePaths.add("pkm6.png");
        imagePaths.add("pkm7.png");
        imagePaths.add("pkm8.png");

        cardImages = new ArrayList<>();
        for (String imagePath : imagePaths) {
            cardImages.add("");
        }

        Collections.shuffle(imagePaths);
        Collections.shuffle(cardImages);

        JPanel cardPanel = new JPanel(new GridLayout(4, 4));
        cardButtons = new JButton[16];

        for (int i = 0; i < cardButtons.length; i++) {
            final int index = i;
            cardButtons[i] = new JButton();
            cardButtons[i].setIcon(new ImageIcon("pokemon.png"));
            cardButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleCardClick(index);
                }
            });
            cardPanel.add(cardButtons[i]);

        }
        add(cardPanel);

        moves = 0; // Initialize the moves counter
        movesLabel = new JLabel("Moves: 0");
        movesLabel.setHorizontalAlignment(JLabel.CENTER);
        add(movesLabel, BorderLayout.SOUTH);

        seconds = 0; // Initialize the elapsed time
        timeLabel = new JLabel("Time: 0 seconds");
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        add(timeLabel, BorderLayout.NORTH);

        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                timeLabel.setText("Time: " + seconds + " seconds");
            }
        });
        gameTimer.setRepeats(true);

    }


    private void handleCardClick(int index) {
        if (cardButtons[index].getIcon() == null) {
            return;
        }

        if (firstCardIndex == -1) {
            firstCardIndex = index;
            cardButtons[firstCardIndex].setIcon(new ImageIcon(imagePaths.get(index)));
            gameTimer.start();
        } else {
            secondCardIndex = index;
            cardButtons[secondCardIndex].setIcon(new ImageIcon(imagePaths.get(index)));
            moves++;
            movesLabel.setText("Moves: " + moves);

            // Check if the images on the first and second cards match
            if (imagePaths.get(firstCardIndex).equals(imagePaths.get(secondCardIndex))) {
                // The images match, so the cards disappear
                cardButtons[firstCardIndex].setEnabled(false);
                cardButtons[secondCardIndex].setEnabled(false);
                //cardButtons[firstCardIndex].setIcon(null);
                //cardButtons[secondCardIndex].setIcon(null);
                cardImages.set(firstCardIndex, null);
                cardImages.set(secondCardIndex, null);
                numberOfMatches++;


                int unmatchedCount = 0;
                for (String cardImage : cardImages) {
                    if (cardImage != null) {
                        unmatchedCount++;
                    }
                }

                if (unmatchedCount == 0) {
                    // Player has won
                    gameTimer.stop(); // Stop the timer when the game is won
                    JOptionPane.showMessageDialog(null, "Congrats! You Won in " + moves + " moves and " + seconds + " seconds!");
                    System.exit(0);
                }


                // if (numberOfMatches == imagePaths.size() / 2) {
                  //  gameTimer.stop(); // Stop the timer when the game is won
                  //  JOptionPane.showMessageDialog(null, "Congrats! You Won in " + moves + " moves and " + seconds + " seconds!");
                   // System.exit(0);
               // }
            } else {
                // The images don't match, so turn the cards face-down again after a delay
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cardButtons[firstCardIndex].setIcon(new ImageIcon("pokemon.png"));
                        cardButtons[secondCardIndex].setIcon(new ImageIcon("pokemon.png"));
                        firstCardIndex = -1;
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }

        }
    }



    public static void main (String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { new PictureMemoryGame().setVisible(true);}


        });
    }



}