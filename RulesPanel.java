import java.awt.*;       // Using AWT's Graphics and Color
import java.awt.event.*; // Using AWT event classes and listener interfaces
import javax.swing.*;    // Using Swing's components and containers
import javax.swing.event.*;

/**
 * RulesPanel extends JPanel and include JLabels that display the
 * rules of the game on the UI.
 *
 * @main author (Caiqin Zhou)
 * @version (12/13/18)
 */
public class RulesPanel extends JPanel{
    private JLabel title, rule1, rule2, rule3, rule4, rule5, rule6, rule7, rule8, rule9;
    private Color bgColor = new Color(229,255,204);
    public RulesPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setBackground(bgColor);
        title = new JLabel("Rules");
        title.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));  

        rule1 = new JLabel("Bridg-It is a two-player game.");
        rule2 = new JLabel("Invite a friend to come play with you!");        
        rule3 = new JLabel("The game board includes two sets of dots, each set belongs to one player.");
        rule4 = new JLabel("Players take turns to connect two adjacent dots of their own color to form a line.");
        rule5 = new JLabel("       - A bridge can only be built horizontally or vertically.");
        rule6 = new JLabel("       - Adjacent dots are considered to be dots directly above, below, to the right, or to the left of another dot with the same color.");  
        rule7 = new JLabel("       - A bridge that crosses an existing bridge can’t be built.");        
        rule8 = new JLabel("The goal for one player is to build a bridge from left to right, and the goal for the other player is to build a bridge from top to bottom.");
        rule9 = new JLabel("The player who first creates a bridge that connects their opposite edges of the board wins.");   
        rule1.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));  
        rule2.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));  
        rule3.setFont(new Font("Comic Sans MS", Font.PLAIN, 15)); 
        rule4.setFont(new Font("Comic Sans MS", Font.PLAIN, 15)); 
        rule5.setFont(new Font("Comic Sans MS", Font.PLAIN, 15)); 
        rule6.setFont(new Font("Comic Sans MS", Font.PLAIN, 15)); 
        rule7.setFont(new Font("Comic Sans MS", Font.PLAIN, 15)); 
        rule8.setFont(new Font("Comic Sans MS", Font.PLAIN, 15)); 
        rule9.setFont(new Font("Comic Sans MS", Font.PLAIN, 15)); 
        
        title.setAlignmentX(CENTER_ALIGNMENT);
        add(title);
        add(rule1);
        add(rule2);
        add(rule3);
        add(rule4); 
        add(rule5);
        add(rule6);
        add(rule7);
        add(rule8);
        add(rule9);        
    }    
    
}
