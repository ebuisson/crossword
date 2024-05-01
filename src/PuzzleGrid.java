import KeyBoards.HelpKeyBoard;
import KeyBoards.KeyBoard;
import Panels.CheckPanel;
import Panels.CluesPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

//main class
//creates puzzle grid and calls other classes for check and clues panel, and help keyboard and keyboard
public class PuzzleGrid {

    private String fileName; //file containing grid layout and clues, provided by user
    private String solution; //solution to special word, provided by user
    private JFrame frame; //Frame containing all panels
    private JPanel crosswordPanel; //panel containing crossword grid
    private String[][] board; //contains square types for the grid
    private String[][] resultBoard; // contains results for each square on the grid from user input
    private int[] dimensions = {0,0}; //dimensions of the crossword grid

    public PuzzleGrid(String fileName, String solution) {
        //constructor
        this.fileName = fileName;
        this.solution = solution;

        //calls method readDimensions which grabs the first line from the file and assigns the values to int[] dimensions
        try {
            dimensions = readDimensions(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.board = new String[dimensions[0]][dimensions[1]];
        //creates the result board array using the dimensions obtained above
        createResultArray(dimensions[0], dimensions[1]);

        this.frame = new JFrame();
        //formatting for the frame
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(1100, 750));
        frame.setTitle("Swedish-style crossword puzzle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        //adding panels from CluesPanel and CheckPanel classes to the Frame
        CluesPanel CluesPanel = new CluesPanel(fileName, frame);
        CheckPanel CheckPanel = new CheckPanel(solution,frame);

        readCrossWord();
        populateCrossWordPanel();
    }
    //assigns a new result to the resultBoard in the correct location on the array
    //outdated panel is removed and an updated panel is created with populateCrossWordPanel() method
    public void setBoardResult(int y, int x, String result) {
        resultBoard[y][x] = result;
        frame.remove(this.crosswordPanel);
        populateCrossWordPanel();
    }
    //method to assign each square type to the board[][] array using input file
    public int[] readDimensions (String fileName){
        int[] dimensions = {0, 0};
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            //parses the first line of the file, which contains the dimensions of the puzzle
            //splits the line by space
            dimensions[0] = Integer.parseInt(line.split(" ")[0]);
            dimensions[1] = Integer.parseInt(line.split(" ")[1]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dimensions;
    }

    //creates a ResultArray with correct dimensions
    public void createResultArray (int width, int height) {
        resultBoard = new String[height][width];
        for (int i = 0; i < height; i ++) {
            for (int j = 0; j < width; j ++) {
                resultBoard[i][j] = "";
            }
        }
    }
    //assign the square types to each row in board by reading through each line in the file and splitting the lines by space
    //only 13 rows are read, skipping the first row, by making i<dimension of crossword grid in for loop
    public void readCrossWord() {
        int x = dimensions[0];
        String[] splitLine = null;
        try {
            LineNumberReader ln = new LineNumberReader(new FileReader(fileName));
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                int i;
                for (i = 0; i < x; i++) {
                    splitLine = (line.split(" "));
                    board[i] = splitLine;
                    line = br.readLine();
                }
                break; //break out of while loop once 13 lines are read
            }
        } catch (IOException e) {
            e.printStackTrace();
        }}
    //method to add buttons and results to the crosswordPanel using board[][] and resultBoard[][]
    //the x and y indices for each button are used to keep track of location in both arrays
    public void populateCrossWordPanel() {
        int x = dimensions[0]; //assigning the x dimension
        int y = dimensions[1]; //assigning the y dimension
        this.crosswordPanel = new JPanel();
        this.crosswordPanel.setLayout(new GridLayout(x, y, 3, 3));
        this.crosswordPanel.setVisible(false);
        frame.getContentPane().add(this.crosswordPanel, BorderLayout.CENTER);
        int i;
        int j;
        //looping through each index in the array and assigning a listener and a title (result) to the buttons
        for (i = 0; i < x; i++) {
            for (j = 0; j < y; j++) {
                //uses if statements to assign different properties to the buttons depending on their square type
                if (board[i][j].equals("O")) {
                    JButton Obutton = new JButton(resultBoard[i][j]); //creates a button and assigns letter chosen by the user to the title of the button
                    int finalI = i;
                    int finalJ = j;
                    //assigns an action listener that uses the class KeyBoard to open a keyboard and record the chosen letter
                    Obutton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            KeyBoard test = new KeyBoard(frame, "KeyBoard", finalI, finalJ);
                            setBoardResult(finalI, finalJ, test.showDialog()); //assigns the new result for that location on the board to resultBoard[i][j]
                        }
                    });
                    //formatting of the button
                    Obutton.setPreferredSize(new Dimension(12, 12));
                    Obutton.setMargin(new Insets(-10, -10, -10, -10)); //margins are reduced to display the title on small buttons
                    Obutton.setForeground(Color.BLACK);
                    Obutton.setBackground(Color.WHITE);
                    Obutton.setOpaque(true);
                    Obutton.setBorderPainted(false);
                    Obutton.setFocusPainted(false);
                    crosswordPanel.add(Obutton);
                } else if (board[i][j].equals("X")) { //the black squares do not have an action listener
                    JButton Xbutton = new JButton(resultBoard[i][j]);
                    Xbutton.setPreferredSize(new Dimension(12, 12));
                    Xbutton.setBackground(Color.BLACK);
                    Xbutton.setForeground(Color.BLACK);
                    Xbutton.setOpaque(true);
                    Xbutton.setBorderPainted(false);
                    Xbutton.setFocusPainted(false);
                    crosswordPanel.add(Xbutton);
                } else if (board[i][j].equals("S")) {
                    JButton Sbutton = new JButton(resultBoard[i][j]);
                    int finalI = i;
                    int finalJ = j;
                    Sbutton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            KeyBoard test = new KeyBoard(frame, "KeyBoard", finalI, finalJ);
                            setBoardResult(finalI, finalJ, test.showDialog());
                        }
                    });
                    Sbutton.setPreferredSize(new Dimension(12, 12));
                    Sbutton.setMargin(new Insets(-10, -10, -10, -10));
                    Sbutton.setBackground(Color.GRAY);
                    Sbutton.setForeground(Color.BLACK);
                    Sbutton.setOpaque(true);
                    Sbutton.setBorderPainted(false);
                    Sbutton.setFocusPainted(false);
                    crosswordPanel.add(Sbutton);
                } else if (board[i][j].startsWith("H")) {
                    JButton Hbutton = new JButton(resultBoard[i][j]);
                    int finalI = i;
                    int finalJ = j;
                    Hbutton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //the help squares use the HelpKeyBoard class to select 5 options on the keyboard
                            HelpKeyBoard test = new HelpKeyBoard(frame, "KeyBoard", finalI, finalJ,board[finalI][finalJ].charAt(2));
                            setBoardResult(finalI, finalJ, test.showDialog());
                        }
                    });
                    Hbutton.setPreferredSize(new Dimension(12, 12));
                    Hbutton.setMargin(new Insets(-10, -10, -10, -10));
                    Hbutton.setBackground(Color.blue);
                    Hbutton.setForeground(Color.BLACK);
                    Hbutton.setOpaque(true);
                    Hbutton.setBorderPainted(false);
                    Hbutton.setFocusPainted(false);
                    crosswordPanel.add(Hbutton);
                } else {
                    System.out.println("The square type character is not valid."); //Error to user if a square is assigned an unknown square type
                    break;
                }
            }
        }

        this.crosswordPanel.setVisible(true);
        //refresh the frame to make sure it displays correctly
        frame.invalidate();
        frame.validate();
        frame.repaint();
    }
    //main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PuzzleGrid("src/puzzle-1-adjusted.txt","southpark");
            }
        });}

    //getter and setter
    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}
    
