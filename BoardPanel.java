import java.awt.*;       
import java.awt.event.*; 
import javax.swing.*;    
import javax.swing.event.*;

/**
 * BoardPanel extends JPanel and displays the game board on the UI.
 * After a player makes a mover, the corresponding edge is added on the
 * panel.
 *
 * @main authors (Caiqin Zhou, Jane Yang)
 * @version (12/13/18)
 */

public class BoardPanel extends JPanel{
    public JLabel rules, whoseTurn;
    // the board panel display the dots, the msgPanel displays messages
    // which show which player's turn it is and whether anyone has won
    private JPanel board, msgPanel;
    private BridgeItGame myGame;
    private boolean computer; 

    // colors of the two players' dots
    private Color p1Color = new Color(255,204,204);
    private Color p2Color = new Color(192,192,192);    

    public BoardPanel(BridgeItGame myGame)
    {           
        computer = false;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
        this.myGame = myGame;   // aliasing

        msgPanel = new JPanel();
        msgPanel.setLayout(new BoxLayout(msgPanel, BoxLayout.Y_AXIS));
        msgPanel.setOpaque(false);      

        rules = new JLabel("To add a line between 2 dots, "
            + "click the space between them.");
        rules.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        rules.setAlignmentX(CENTER_ALIGNMENT);

        whoseTurn = new JLabel("Pink Player Please Make the First Move");
        whoseTurn.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        whoseTurn.setForeground(p1Color);
        whoseTurn.setAlignmentX(CENTER_ALIGNMENT);

        msgPanel.add(whoseTurn);        
        msgPanel.add(rules);

        //width and height are uniform for all vertices
        int width = myGame.getPlayer1().getBoard().getVertex(1).getWidth();
        int height = myGame.getPlayer1().getBoard().getVertex(1).getHeight();

        board = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);     // paint parent's background
                setBackground(Color.WHITE);  // set background color for this JPanel
                g.setColor(p1Color);    // set the drawing color                
                g.drawRect(230, 25, 540, 540);// boundary of the board 

                // draw player1's dots
                for(int i = 0; i<myGame.getPlayer1().getNumVertices(); i++){
                    Vertex currentVertex = myGame.getPlayer1().getBoard().getVertex(i);
                    int xpos = currentVertex.getXpos();
                    int ypos = currentVertex.getYpos();
                    g.drawOval(xpos-width/2, ypos-height/2, width, height);
                    g.fillOval(xpos-width/2, ypos-height/2, width, height);
                }

                // draw player2's dots
                g.setColor(p2Color); 
                for(int i = 0; i<myGame.getPlayer2().getNumVertices(); i++){
                    Vertex currentVertex = myGame.getPlayer2().getBoard().getVertex(i);
                    int xpos = currentVertex.getXpos();
                    int ypos = currentVertex.getYpos();
                    g.drawOval(xpos-width/2, ypos-height/2, width, height);
                    g.fillOval(xpos-width/2, ypos-height/2,width, height);
                }

                Graphics2D g2d = (Graphics2D)g;
                g2d.setStroke(new BasicStroke(10));

                // draw edges that player 1 have connected 
                for(int i = 0; i<myGame.getPlayer1().getNumVertices(); i++){
                    for(int j = 0; j<myGame.getPlayer1().getNumVertices(); j++){
                        g.setColor(p1Color); 
                        Vertex Vi = myGame.getPlayer1().getBoard().getVertex(i);
                        Vertex Vj = myGame.getPlayer1().getBoard().getVertex(j);
                        if(myGame.getPlayer1().getBoard().isEdge(Vi, Vj)){
                            g2d.drawLine(Vi.getXpos(),Vi.getYpos(),
                                Vj.getXpos(),Vj.getYpos());
                        }
                    }
                }

                // draw edges that player 2 have connected 
                for(int i = 0; i<myGame.getPlayer2().getNumVertices(); i++){
                    for(int j = 0; j<myGame.getPlayer1().getNumVertices(); j++){
                        g.setColor(p2Color); 
                        Vertex Vm = myGame.getPlayer2().getBoard().getVertex(i);
                        Vertex Vn = myGame.getPlayer2().getBoard().getVertex(j);                        
                        if(myGame.getPlayer2().getBoard().isEdge(Vm, Vn)){
                            g2d.drawLine(Vm.getXpos(),Vm.getYpos(),
                                Vn.getXpos(),Vn.getYpos());;
                        }
                    }
                }

            }     
        };

        board.setPreferredSize(new Dimension(1300, 800));
        board.addMouseListener(new myMouseListener());
        add(msgPanel);
        add(board);
    }

    public void setComputer(boolean c){
        computer = c;
    }

    private class myMouseListener implements MouseListener{  
        public void mouseClicked(MouseEvent event){  
            // System.out.println("Mouse clicked @ position x = "
            // + event.getX() + " y = " + event.getY());   
            int mouseX = event.getX();
            int mouseY = event.getY();
            // identifies the edge to be add based on user's mouse click
            // add the edge if it's a valid edge 
            Player pBefore = myGame.getCurrentPlayer();
            myGame.move(mouseX, mouseY);   
            Player pAfter = myGame.getCurrentPlayer();
            
            // if current player didn't change after making the move
            // then the move must have been invalid; display a msg
            // prompting the user to make another attempt                        
            if (pBefore.equals(pAfter)){
                rules.setText("The line you wanted to add was invalid. "
                    + "Please try something else. ");
            } else{ // move was valid
                rules.setText("To add a line between 2 dots, "
                    + "click the space between them.");
            }

            if (myGame.isPlayer1Turn()){    
                if (myGame.getIsGameOver()){ // player2 just ended the game
                    if (myGame.getWinnerNum() == 1){
                        whoseTurn.setForeground(p1Color);
                    } 
                    whoseTurn.setText("*** Player" + myGame.getWinnerNum()+ " Won! ***");
                    rules.setText("To start another round of the game, "
                                 + "please restart the program");
                } else{
                    whoseTurn.setText("Player 1's turn (pink player)");
                    whoseTurn.setForeground(p1Color);
                }
            }
            else {  // player 2's turn
                if (myGame.getIsGameOver()){
                    if (myGame.getWinnerNum() == 2){
                        whoseTurn.setForeground(p2Color);
                    }                     
                    whoseTurn.setText("*** Player" + myGame.getWinnerNum()+ " Won! ***");
                    rules.setText("To start another round of the game, "
                        + "please restart the program");                    
                } else{            
                    whoseTurn.setText("Player 2's turn (gray player)");
                    whoseTurn.setForeground(p2Color); 
                }
            }
            board.repaint();
        }

        public void mouseEntered(MouseEvent event){  
            // do nothing
        }

        public void mouseExited(MouseEvent event){ 
            // do nothing
        }

        public void mousePressed(MouseEvent event){  
            // do nothing
        }

        public void mouseReleased(MouseEvent event){  
            // do nothing
        }
    }

}
