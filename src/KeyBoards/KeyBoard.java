package KeyBoards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//class to create a regular keyboard for input to white and grey squares
public class KeyBoard extends JDialog implements ActionListener {

    private JPanel keyPanel; //panel containing keys
    private int targetx; //coordinates of a specific square on PuzzleGrid
    private int targety;
    private String result; //user input

    //keys to be added to the keyboard
    private String[] firstSymbols = {"A", "Z", "E", "R", "T", "Y", "U", "I", "O", "P"};
    private String[] secondSymbols = {"Q", "S", "D", "F", "G", "H", "J", "K", "L", "M"};
    private String[] thirdSymbols = {"W", "X", "C", "V", "B", "N"};

    //uses x and y coordinates of square on board to add input in correct location
    //extends JDialog which allows to easily create Frame with title
    public KeyBoard(JFrame parent, String title, int targetx, int targety) {
        //constructor
        super(parent, title, true);
        this.targetx = targetx;
        this.targety = targety;
        this.keyPanel = new JPanel();

        //layout of the keyboard panel
        this.keyPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        //code to put buttons on keyboard by row
        int counter = 0; //counter used to cycle through each position on x axis
        for (String symbol : this.firstSymbols) {
            JButton button = new JButton(symbol);
            button.addActionListener(this); //event listener is added to each button
            c.gridx = counter;
            c.gridy = 0;
            this.keyPanel.add(button, c);
            counter++;
        }

        counter = 0;
        for (String symbol : this.secondSymbols) {
            JButton button = new JButton(symbol);
            button.addActionListener(this);
            c.gridx = counter;
            c.gridy = 1;
            this.keyPanel.add(button, c);
            counter++;
        }

        counter = 0;
        for (int i = 0; i <= 5; i++) {
            JButton button = new JButton(this.thirdSymbols[i]);
            button.addActionListener(this);
            c.gridx = counter;
            c.gridy = 2;
            this.keyPanel.add(button, c);
            counter++;
        }
        //this adds the panel with the buttons to the Frame from JDialog
        getContentPane().add(this.keyPanel);

        //sets preference to dispose of the frame when closed; arranges size of panel on frame with pack()
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }

    // the click on a button is captured; the text on the button is assigned to the String result with method setResult
    //String result is then accessed by method setBoardResult in PuzzleGrid through method showDialog()
    //keyboard is disposed after click is captured
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = ((JButton)e.getSource()).getText();
        KeyBoard comp = (KeyBoard) ((JButton)e.getSource()).getRootPane().getParent();
        comp.setResult(action);
        dispose();
    }
    //assigns a String to the variable result
    public void setResult (String result){
        this.result = result;
    }
    //returns String result when called
    public String showDialog() {
        setVisible(true);
        return result;
    }
}