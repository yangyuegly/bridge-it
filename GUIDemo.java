import java.awt.*;       // Using AWT's Graphics and Color
import java.awt.event.*; // Using AWT event classes and listener interfaces
import javax.swing.*;    // Using Swing's components and containers
import javax.swing.event.*;
/**
 * Driver class that runs the game
 *
 * @main authors (Caiqin Zhou, Jane Yang)
 * @version (12/13/18)
 */
public class GUIDemo{
    
    public static void main (String[] args)
    {
        boolean startNewGame = false; 
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image background = toolkit.getImage("background.jpg"); 
        JFrame frame = new JFrame ("Demo");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        BridgeItGame myGame = new BridgeItGame();
        
        MainPanel myPanel = new MainPanel(background, myGame);
        frame.getContentPane().add(myPanel);
        
        frame.pack();
        frame.setVisible(true);
    }

}
