import java.awt.*;       
import java.awt.event.*; 
import javax.swing.*;    
import javax.swing.event.*;

/**
 * The MainPanel extends the JPanel class, and allows users to switch 
 * between the starting panel, the rules panel, and the board panel of 
 * the game by clicking on corresponding buttons. 
 *
 * @main authors (Caiqin Zhou, Jane Yang)
 * @version (12/13/18)
 */

public class MainPanel extends JPanel {
    private BridgeItGame game;    
    private CardLayout cardLayout = new CardLayout();

    private JPanel contentPanel, buttonPanel, 
    startPanel, rulesPanel, boardPanel;
    private JButton rules, playWFriend, playWComputer, exitButton, 
                    restartWFriend, restartWComputer;  

    public MainPanel(Image image, BridgeItGame myGame){ 
        game = myGame;  // an alias of the BridgeItGame input

        this.setLayout (new BorderLayout());
        this.setPreferredSize(new Dimension(1000, 750));

        // the MainPanel includes 2 panels (from top to bottom of screen): 
        // the contentPanel (shows either the startPanel, rulesPanel,  
        // or the boardPanel, depending on which button is clicked) 
        // the buttonPanel (allows users to switch the content in the 
        // contentPanel by clicking on different buttons)

        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        // CardLayout allows different panels to be displayed on 
        // button clicks
        contentPanel = new JPanel();
        contentPanel.setLayout(cardLayout);  

        startPanel = new StartPanel(image); // home screen
        rulesPanel = new RulesPanel();  // rules of the game
        boardPanel = new BoardPanel(myGame);    // game board         

        rules = new JButton("Rules");        
        playWFriend = new JButton("Play with a Friend");
        playWComputer = new JButton("Play with the Computer"); 
        exitButton = new JButton("Exit");          
        // restart buttons aren't displayed on startPanel 
        // will be added once user gets to boardPanel
        restartWFriend = new JButton("Play again with Friend"); 
        restartWComputer = new JButton("Play again with Computer");

        rules.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        playWFriend.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        exitButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));                
        playWComputer.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        restartWFriend.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));        
        restartWComputer.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));        
        
        ButtonListener listener = new ButtonListener();
        rules.addActionListener(listener);
        playWFriend.addActionListener(listener);
        playWComputer.addActionListener(listener);        
        exitButton.addActionListener(listener); 
        restartWFriend.addActionListener(listener);
        restartWComputer.addActionListener(listener);
        
        buttonPanel.add(rules);
        buttonPanel.add(playWFriend);
        buttonPanel.add(playWComputer);
        buttonPanel.add(exitButton);
        
        contentPanel.add(startPanel, "start");
        contentPanel.add(rulesPanel, "rules");
        contentPanel.add(boardPanel, "board");
        // display the startPanel (home page) as default
        cardLayout.show(contentPanel, "start"); 

        this.add(contentPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);        
    }

    private class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            if (event.getSource() == rules){   
                // System.out.println("rules pressed");
                cardLayout.show(contentPanel, "rules");
            } else if (event.getSource() == playWFriend || 
                       event.getSource() == playWComputer){    
                // System.out.println("playWFriend pressed");
                // remove the "rules" and "start" so users can't look 
                // at the rules or restart once they started a game
                buttonPanel.removeAll();
                buttonPanel.revalidate();
                buttonPanel.repaint();
                
                buttonPanel.add(restartWFriend); 
                buttonPanel.add(restartWComputer);                 
                buttonPanel.add(exitButton);
                
                if (event.getSource() == playWFriend){
                    game.isComputer = false;  
                } else if(event.getSource() == playWComputer){
                    game.isComputer = true;
                }
                
                // switch to display the game board
                cardLayout.show(contentPanel, "board");                
            } else if(event.getSource() == restartWFriend || 
                    event.getSource() == restartWComputer){
                // to restart a game, repain the game board
                game.removeAllEdge();
                game.setIsGameOver(false);   
                game.resetCurrentPlayer();
                
                if (event.getSource() == restartWFriend){
                    game.isComputer = false;  
                } else if(event.getSource() == restartWComputer){
                    game.isComputer = true;
                }     
                
                cardLayout.show(contentPanel, "board");                                
                boardPanel.repaint();
            } else if (event.getSource() == exitButton) {   
                // System.out.println("exitButton pressed");
                System.exit(0);
            } 
        }
    }
}
