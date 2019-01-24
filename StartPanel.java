import java.awt.*;       
import java.awt.event.*; 
import javax.swing.*;    
import javax.swing.event.*;

/**
 * StartPanel extends JPanel and represents the home page on the UI.
 *
 * @authors (Caiqin Zhou, Jane Yang)
 * @version (12/13/18)
 */

public class StartPanel extends JPanel {
    private Image image;    // background image
    // the 2 panels display the name of the game and the authors, respectively
    private JPanel top, bottom;     
    private JLabel title, authors;    

    public StartPanel(Image i){ 
        this.image = i;   // aliasing
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        top = new JPanel();
        bottom = new JPanel();         
        top.setOpaque(false);   // prevents the top and bottom panels
        bottom.setOpaque(false); // from covering the background image
        
        title = new JLabel("Bridg-It");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 150));
        title.setForeground(Color.white);
        authors = new JLabel("by Jane Yang, Yining Li, Caiqin Zhou");
        authors.setVerticalAlignment(JLabel.TOP);
        authors.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
        authors.setForeground(Color.white);
        
        top.add(title);
        bottom.add(authors);               
        
        this.add(Box.createRigidArea(new Dimension(0,120)));
        this.add(top);
        this.add(Box.createRigidArea(new Dimension(0,5)));        
        this.add(bottom);
    }

    @Override   
    protected void paintComponent(Graphics g) {
        // set the image as the background of the StartPanel
        super.paintComponent(g);
        // the size of image adjusts to size of the panel/window
        g.drawImage(image, 0,0, this.getWidth(), this.getHeight(), null);
    }
}
