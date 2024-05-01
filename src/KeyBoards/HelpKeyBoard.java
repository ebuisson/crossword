package KeyBoards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

//class to create a help keyboard for input to blue squares
public class HelpKeyBoard extends JDialog implements ActionListener {

    private JPanel keyPanel; //panel containing keys
    private int targetx; //coordinates of a specific square on PuzzleGrid
    private int targety;
    private String result; //user input
    private char correctLetter; //correct answer for a square
    private String randomLetters; //holds random letters

    //keys to be added to the keyboard
    private String[] firstSymbols = {"A", "Z", "E", "R", "T", "Y", "U", "I", "O", "P"};
    private String[] secondSymbols = {"Q", "S", "D", "F", "G", "H", "J", "K", "L", "M"};
    private String[] thirdSymbols = {"W", "X", "C", "V", "B", "N"};

    //extends JDialog which allows to easily create Frame with title
    //uses x and y coordinates, and correctLetter from PuzzleGrid class
    public HelpKeyBoard(JFrame parent, String title, int targetx, int targety, char correctLetter) {
        //constructor
        super(parent, title, true);
        this.targetx = targetx;
        this.targety=targety;
        this.correctLetter = correctLetter;
        this.keyPanel = new JPanel();

        //layout for panel
        this.keyPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        //for loop uses Random to pick 4 random characters and adds them to randomLetters variable
        //if random character = correctLetter, character is ignored and loop is entered an extra time
        String correctLetterString= String.valueOf(correctLetter);
        randomLetters = correctLetterString;
        int j;
        for (j = 0; j < 4; j++) {
            Random random = new Random();
            char A = (char) (random.nextInt(26) + 'a');
            if (correctLetterString.equalsIgnoreCase(String.valueOf(A))) {
                j=j-1;
            } else {randomLetters = randomLetters + A;
            }
        }

        //code to put buttons on keyboard
        //if statement only adds Action Listener to keys that have been randomly chosen + correct key
        int counter = 0;
        for (String symbol : this.firstSymbols) {
            JButton button = new JButton(symbol);
            if (randomLetters.toUpperCase().contains(symbol)) {
                button.addActionListener(this);
                //formatting of button
                button.setBackground(Color.blue);
                button.setForeground(Color.BLACK);
                button.setOpaque(true);
                button.setBorderPainted(false);
                button.setFocusPainted(false);
            }
            //counter adds buttons across each row
            c.gridx = counter;
            c.gridy = 0;
            this.keyPanel.add(button, c);
            counter++;
        }
        //same code is used for the next two rows on the keyboard
        counter = 0;
        for (String symbol : this.secondSymbols) {
            JButton button = new JButton(symbol);
            if (randomLetters.toUpperCase().contains(symbol)) {
                System.out.println(symbol);
                button.addActionListener(this);
                button.setBackground(Color.blue);
                button.setForeground(Color.BLACK); button.setOpaque(true);
                button.setBorderPainted(false);
                button.setFocusPainted(false);
            }
            c.gridx = counter;
            c.gridy = 1;
            this.keyPanel.add(button, c);
            counter++;
        }

        counter = 0;
        for (int i = 0; i <= 5; i++) {
            JButton button = new JButton(this.thirdSymbols[i]);
            if (randomLetters.toUpperCase().contains(thirdSymbols[i])) {
                System.out.println(thirdSymbols[i]);
                button.addActionListener(this);
                button.setBackground(Color.blue);
                button.setForeground(Color.BLACK);
                button.setOpaque(true);
                button.setBorderPainted(false);
                button.setFocusPainted(false);
            }
            c.gridx = counter;
            c.gridy = 2;
            this.keyPanel.add(button, c);
            counter++;
        }
        //adds the keyPanel to the Frame from JDialog
        getContentPane().add(this.keyPanel);
        //Frame is disposed when closed; size is adjusted
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }

    // the click on a button is captured; the text on the button is assigned to the String result with method setResult
    //String result is then accessed by method setBoardResult in PuzzleGrid through method showDialog()
    //keyboard is disposed after click is captured
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = ((JButton)e.getSource()).getText();
        HelpKeyBoard comp = (HelpKeyBoard) ((JButton)e.getSource()).getRootPane().getParent();
        comp.setResult(action);
        dispose();
    }
    //assigns result from action to variable result
    public void setResult(String result) {
        this.result = result;
    }

    //returns variable result to be used by PuzzleGrid class
    public String showDialog() {
        setVisible(true);
        return result;
    }

    public int getTargetx() {
        return targetx;
    }

    public void setTargetx(int targetx) {
        this.targetx = targetx;
    }

    public int getTargety() {
        return targety;
    }

    public void setTargety(int targety) {
        this.targety = targety;
    }

    public char getCorrectLetter() {
        return correctLetter;
    }

    public void setCorrectLetter(char correctLetter) {
        this.correctLetter = correctLetter;
    }
}




