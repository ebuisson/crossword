package Panels;

import KeyBoards.KeyBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//class to create panel at the bottom of Frame which checks if special word is correctly entered
public class CheckPanel {

    private String solution; //solution to the special word, from input given in PuzzleGrid
    private JPanel checkPanel; //contains buttons for the solution and a check button
    private JPanel blankPanel; //used for formatting the Frame
    private String[] resultCheck; //holds the characters entered in the solution buttons by the user
    private JFrame frame; //main frame which holds the panels

    public CheckPanel (String solution,JFrame frame) {
        //constructor
        this.solution = solution;
        this.frame = frame;

        createCheckString(solution.length());
        populateCheckPanel();
    }
    //method used when a square is clicked
    //obtains the letter selected by user on keyboard (passed as result) and assigns it to resultCheck String[]
    //int x informs the location of the square along the xaxis; the method assigns the result to the same location in resultCheck
    public void setCheckResult(int x, String result) {
        resultCheck[x] = result;
        frame.remove(this.checkPanel); //outdated Panel is removed from Frame
        populateCheckPanel(); //an updated Panel is populated to replace old Panel
    }
    //creates a blank string [] with the same length as the solution string to hold user input values
    public void createCheckString(int length) {
        resultCheck = new String [length];
        for (int i=0;i<length;i++) {
            resultCheck[i]= "";
        }
    }
    //method to create the Panel buttons and layout
    public void populateCheckPanel() {
        this.checkPanel = new JPanel();
        this.blankPanel = new JPanel();
        //set layout of both panels on Frame
        this.checkPanel.setLayout(new FlowLayout());
        this.checkPanel.setVisible(false);
        this.checkPanel.setPreferredSize(new Dimension(700,60));
        this.blankPanel.setPreferredSize(new Dimension(300,60));
        checkPanel.add(blankPanel);
        frame.getContentPane().add(this.checkPanel, BorderLayout.SOUTH);
        //for loop to create buttons = to length of solution String
        int i;
        for (i=0;i<solution.length();i++) {
            int finalI = i;
            int finalJ = 0; //no need for yaxis, single row of buttons
            JButton solButton = new JButton(resultCheck[i]); //creating a button and assigning the result value to the button title
            //action listener uses keyboard class to open a new keyboard when button is clicked and feed result to setCheckResult method
            solButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    KeyBoard click = new KeyBoard(frame, "KeyBoard",finalI,finalJ);
                    setCheckResult(finalI, click.showDialog());
                }
            });
            //formatting of buttons
            solButton.setPreferredSize(new Dimension(50, 50));
            solButton.setMargin(new Insets(-10, -10, -10, -10));
            solButton.setBackground(Color.GRAY);
            solButton.setForeground(Color.BLACK);
            solButton.setOpaque(true);
            solButton.setBorderPainted(false);
            solButton.setFocusPainted(false);
            //add button to the panel
            checkPanel.add(solButton);}
        //check button returns "that is correct" when resultCheck equals solution
        //returns "try again" if no match
        JButton checkButton = new JButton("Check");
        //formatting for button
        checkButton.setPreferredSize(new Dimension(70, 25));
        checkButton.setMargin(new Insets(-10, -10, -10, -10));
        //action listener loops through result check String[] to return a String
        //finalResult is compared to solution for a match
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String finalResult = "";
                int i = 0;
                for (i=0;i<resultCheck.length;i++) {
                    finalResult = finalResult+resultCheck[i];
                }
                if (finalResult.equalsIgnoreCase(solution)) {
                    JOptionPane.showConfirmDialog(null, "That is correct.", null, JOptionPane.OK_CANCEL_OPTION);
                }else {
                    JOptionPane.showConfirmDialog(null, "Try again.", null, JOptionPane.OK_CANCEL_OPTION);}
            }
        });
        this.checkPanel.add(checkButton);
        this.checkPanel.setVisible(true);
        //refresh the frame to make sure it displays correctly
        frame.invalidate();
        frame.validate();
        frame.repaint();
    }
}

