package Panels;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//class to create text panel to the left of Frame containing the clues from the input file
public class CluesPanel {

    private String nextLine; //a line of text from the input file
    private JFrame frame; //frame containing the clue panel
    private String fileName; //fileName from PuzzleGrid user input


    public CluesPanel(String fileName, JFrame frame) {
        //constructor
        this.fileName = fileName;
        this.frame = frame;
        JPanel panel = new JPanel();
        JTextPane text = new JTextPane();
        //add text to panel and panel to frame
        panel.setPreferredSize(new Dimension(350, 700));
        panel.add(text);
        frame.getContentPane().add(panel, BorderLayout.WEST);

        //buffered reader used to go through txt file line by line
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine())!=null) {
                //lines of text not belonging to clues are skipped over, first line of interest begins with "across"
                if (line.equals("ACROSS")) {
                    appendString (line+"\n", text); //append string to text area with a new line character
                    //for all subsequent lines, the number of digit characters are added and lines with >3 characters are set to bold
                    //this makes lines starting with a year bold
                    while ((line = br.readLine())!=null) {
                        int numberOfNumbers = 0;
                        for (int i = 2; i < line.length(); i++) {
                            if (Character.isDigit(line.charAt(i))) {
                                numberOfNumbers++;}}
                        if (numberOfNumbers > 3) {
                            appendBoldString (line+"\n", text);
                            System.out.println(line);
                        } else {
                            //if a line doesn't have more than 3 digit characters, line is appended as is
                            appendString (line+"\n", text);
                        }
                    }
                }}}
        catch (IOException e) {
            e.printStackTrace();
        }}
    //method to append a string to the text area
    public void appendString (String nextLine, JTextPane text) {
        StyledDocument document = (StyledDocument) text.getDocument();
        try {
            //no attributes are used, string appended as is
            document.insertString(document.getLength(), nextLine, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    //method to append a string to the text area and make it bold
    public void appendBoldString(String nextLine, JTextPane text) {
        StyledDocument document = (StyledDocument) text.getDocument();
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setBold(attributes, true);
        try {
            document.insertString(document.getLength(), nextLine, attributes);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    //getter and setter methods
    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getNextLine() {
        return nextLine;
    }

    public void setNextLine(String nextLine) {
        this.nextLine = nextLine;
    }
}