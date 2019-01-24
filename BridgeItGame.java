
/**
 * BridgeItGame Class represents one round of the Bridg-it game
 *
 * @author (Jane Yang, Yining Li, Caiqin Zhou)
 * @version 12/13/18
 */
import javafoundations.*;
import java.util.*;
import java.io.*;

public class BridgeItGame
{
    private Player player1, player2;
    // Use vectors to store the boundary Vertices that have been conencted
    private Vector<Vertex> connectedStart, connectedEnd; 
    private boolean isGameOver;
    private Player currentPlayer; 
    public boolean isComputer;
    private int winPlayerNum;

    /**
     * Constructor
     */
    public BridgeItGame(){
        player1 = new Player(1);
        player2 = new Player(2);
        currentPlayer = player1; // player 1 makes the first move
        isGameOver = false;
        isComputer = false;
        winPlayerNum = 0;
    }

    public void removeAllEdge(){
        for(int i = 0; i< player1.getBoard().getNumVertices(); i++){
            for(int j = 0; j< player1.getBoard().getNumVertices(); j++){
                player1.getBoard().removeEdge(player1.getBoard().getVertex(i),player1.getBoard().getVertex(j));
            }
        }
        for(int i = 0; i< player2.getBoard().getNumVertices(); i++){
            for(int j = 0; j< player2.getBoard().getNumVertices(); j++){
                player2.getBoard().removeEdge(player2.getBoard().getVertex(i),player2.getBoard().getVertex(j));
            }
        }
    }

    /**
     * move: add edge that corresponds to the user's mouse click if that move is valid
     * @param int inputX: the x-coordinate of the mouse click
     * @param int inputY: the y-coordinate of the mouse click
     */
    public void move(int inputX, int inputY){
        Edge targetEdge = new Edge();
        int currentIndex = 0;        
        int targetIndex = -1;
        boolean found  = false; // whether the mouse click has a nearby vertex

        if(!isGameOver){            
            // System.out.println("Current player: "+currentPlayer);
            // iterate through player1's vertices to look for the vertex that's the closest to 
            // where the mouse clicked
            while (!found && currentIndex < currentPlayer.getNumVertices()){
                Vertex currentVertex = currentPlayer.getBoard().getVertex(currentIndex);
                if(currentVertex.getRealm().contains(inputX,inputY)){
                    targetIndex = currentIndex;
                    targetEdge = findEdge(targetIndex, inputX, inputY);
                    found = true; // exit the while loop
                    // System.out.println("player1: xcord " + player1.getBoard().getVertex(currentIndex).getIDx()
                    // + "ycord"+ player1.getBoard().getVertex(currentIndex).getIDy() );
                }
                currentIndex++;
            }
            if(!found){
                // System.out.println("vertex not found");
            }
            // System.out.println("Printing target edge:" + targetEdge.getFirstV()+targetEdge.getSecondV());
            if(isValidMove(targetEdge.getFirstV(), targetEdge.getSecondV())){
                // add edge to current player's own board
                currentPlayer.addEdge(targetEdge.getFirstV(), targetEdge.getSecondV());
                // System.out.println("Start to checkwin");
                checkWin();
                // only changes turns after current player has made a valid move
                switchTurns();
                if(isComputer){
                    randomMove();
                }
                // System.out.println("Switched turns. Now currentPlayer: "+currentPlayer);
            }
        }
    }        
    /**
     * randomMove: allows the computer player to make a random move
     * 
     */
    public void randomMove(){
        Vertex currentVertex = new Vertex();
        for(int i = 0; i<currentPlayer.getNumVertices(); i++){
            currentVertex = currentPlayer.getBoard().getVertex(i);
            if(currentPlayer.getTopNeighbor(currentVertex)!= null &&
            !currentPlayer.getBoard().isEdge(currentPlayer.getTopNeighbor(currentVertex),currentVertex)
            &&isValidMove(currentPlayer.getTopNeighbor(currentVertex),currentVertex)){
                currentPlayer.addEdge(currentVertex, currentPlayer.getTopNeighbor(currentVertex));
                checkWin();
                switchTurns();
                return;
            }
            else if(currentPlayer.getBottomNeighbor(currentVertex)!= null 
            && !currentPlayer.getBoard().isEdge(currentPlayer.getBottomNeighbor(currentVertex),currentVertex)
            &&isValidMove(currentVertex,currentPlayer.getBottomNeighbor(currentVertex))){
                currentPlayer.addEdge(currentVertex, currentPlayer.getBottomNeighbor(currentVertex));
                checkWin();
                switchTurns();
                return;
            }

            else if(currentPlayer.getRightNeighbor(currentVertex)!= null 
            && !currentPlayer.getBoard().isEdge(currentPlayer.getRightNeighbor(currentVertex),currentVertex)
            &&isValidMove(currentPlayer.getBoard().getVertex(i), currentPlayer.getRightNeighbor(currentPlayer.getBoard().getVertex(i)))){
                currentPlayer.addEdge(currentVertex, currentPlayer.getRightNeighbor(currentVertex));
                checkWin();
                switchTurns();
                return;
            }
            else if(currentPlayer.getLeftNeighbor(currentVertex)!= null 
            && !currentPlayer.getBoard().isEdge(currentPlayer.getLeftNeighbor(currentVertex),currentVertex)
            && isValidMove(currentPlayer.getLeftNeighbor(currentVertex),currentVertex)){
                currentPlayer.addEdge(currentVertex, currentPlayer.getLeftNeighbor(currentVertex));
                checkWin();
                switchTurns();
                return;
            }
        }
    }       

    /**
     * findEdge (helper): determine which one of the four edges (starting from a target vertex) 
     *                    that the user clicked in the GUI
     * @param int targetIndex: the index of the target vertex
     * @param int inputX: the x-coordinate of the mouse click
     * @param int inputY: the y-coordinate of the mouse click
     * @return an Edge that corresponds to where the user clicked 
     */
    private Edge findEdge(int targetIndex, int inputX, int inputY){
        Edge result = new Edge();
        Vertex neighbor = new Vertex();
        // System.out.println("reached findEdge");
        Vertex targetVertex = currentPlayer.getBoard().getVertex(targetIndex);
        if(targetVertex.getRightArea().contains(inputX,inputY)){
            neighbor = currentPlayer.getRightNeighbor(targetVertex);
            result = new Edge(targetVertex, neighbor);
            // System.out.println("right neighbor: " + neighbor);
        }
        if(targetVertex.getLeftArea().contains(inputX,inputY)){
            neighbor = currentPlayer.getLeftNeighbor(targetVertex);
            result = new Edge(neighbor, targetVertex);
            // System.out.println("left neighbor: " + neighbor);
        }
        if(targetVertex.getTopArea().contains(inputX,inputY)){
            neighbor = currentPlayer.getTopNeighbor(targetVertex);
            result = new Edge(neighbor, targetVertex);
            // System.out.println("top neighbor: "+ neighbor);
        }
        if(targetVertex.getBottomArea().contains(inputX,inputY)){
            neighbor = currentPlayer.getBottomNeighbor(targetVertex);
            result = new Edge(targetVertex,neighbor);
            // System.out.println("bottom neighbor: "+ neighbor);
        }
        return result;
    }

    /**
     * isMyEdge (helper)
     * @param Vertex m1, Vertex m2
     * @param the player that is making the move
     * @return boolean: whether two Vertices have been connected by the
     *                  current player
     */
    private boolean isMyEdge(Vertex m1, Vertex m2){
        System.out.println("isMyEdge: " + currentPlayer.getBoard().isEdge(m1, m2));
        return (currentPlayer.getBoard().isEdge(m1, m2));
    }

    /**
     * isBoundary (helper)
     * @param Vertex m1
     * @param Vertex m2
     * @return boolean: whether the two vertices are on the boundary of the
     *                  current player's board
     */
    private boolean isBoundary(Vertex m1, Vertex m2){
        // if the given vertices are on the boundary, then they can always be connected
        // (the other player couldn't have blocked the path)
        if(isPlayer1Turn()){
            if(m1.getIDx() == 0 && m2.getIDx() == 0
            ||m1.getIDx() == 4 && m2.getIDx() == 4){
                // System.out.println("is player1's boundary");
                return true;
            }
        }
        else{   // player2's turn
            if (m1.getIDy() == 0 && m2.getIDy() == 0
            ||m1.getIDy() == 4 && m2.getIDy() == 4){
                // System.out.println("is player2's boundary");
                return true;
            }
        }
        // System.out.println("not  boundary");
        return false;
    }

    /**
     * isBlocked (helper)
     * @param Vertex m1
     * @param Vertex m2
     * @return boolean: whether the opponent player has formed an edge 
     *                  that blocks the edge that the user wants to form
     *                  using the given vertices
     */
    private boolean isBlocked(Vertex m1, Vertex m2){
        Vertex target2 = new Vertex();
        Vertex target1 = new Vertex();

        //when the current player tries to connect a horizontal edge
        if(isHorizontal(m1,m2)){
            if(isPlayer1Turn()){
                //find the corresponding vertices on the opponent's board
                target1 = player2.getVertex(player2.getVertexIndex(m1.getIDx(),m1.getIDy()));
                target2 = player2.getBottomNeighbor(target1);
            }
            else{   // player2's turn
                target2 = player1.getVertex(player1.getVertexIndex(m2.getIDx(),m2.getIDy()));
                target1 = player1.getTopNeighbor(target2);
            }
        }
        //when the current player tries to connect a vertical edge
        else{
            if(isPlayer1Turn()){
                //find the potential blocking vertex in the opponent's board
                target2 = player2.getVertex(player2.getVertexIndex(m2.getIDx(),m2.getIDy()));
                target1 = player2.getLeftNeighbor(target2);
            }
            else{   // player2's turn
                target1 = player1.getVertex(player1.getVertexIndex(m1.getIDx(),m1.getIDy()));
                target2 = player1.getRightNeighbor(target1);
            }

        }

        // System.out.println("target1: " + target1);
        // System.out.println("target2: " + target2);

        return getOpponent(currentPlayer).getBoard().isEdge(target1,target2);
    }

    /**
     * isValidMove
     * @param Vertex m1
     * @param Vertex m2
     * @return boolean: whether the two vertices can be connected. 
     * This method uses three helper methods (isMyEdge, isBoundary, isBlocked)
     */
    public boolean isValidMove(Vertex m1, Vertex m2){
        if (isMyEdge(m1, m2))
            return false;
        if (isBoundary(m1, m2))
            return true;
        return (!isBlocked(m1, m2));
    }

    /**
     * return the opponent of the current player
     * @return the opponent
     * @param the current player
     */
    public Player getOpponent(Player curr){
        if(curr.equals(player1)){
            return player2;
        }
        else
            return player1;
    }

    /**
     * getCurrentPlayer
     * @return currentPlayer: the current player
     */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }
    
    /**
     * resetCurrentPlayer (helper): to player1
     */
    public void resetCurrentPlayer(){
        currentPlayer = player1;
    }    

    /**
     * isPlayer1Turn
     */
    public boolean isPlayer1Turn(){
        return currentPlayer.equals(player1);
    }

    /**
     * isHorizontal (helper)
     * @return true if horizontal and false if vertical
     */
    private boolean isHorizontal(Vertex m1, Vertex m2){
        // System.out.println("m1: " + m1 + " m2: " + m2);
        return(m1.getIDy() == m2.getIDy());
    }

    /**
     * isNeighbor (helper): check whether two vertices are physically adjacent
     * @return whether the vertices are adjacent
     * @param vertex m1, m2: the two Vertices that
     * @param vertex 2
     */
    public boolean isNeighbor(Vertex m1, Vertex m2){
        // System.out.println("isneighbor m2 xcord: "  + m2.getIDx());
        int x1 = m1.getIDx();
        int y1 = m1.getIDy();
        int x2 = m2.getIDx();
        int y2 = m2.getIDy();

        return ((x1==x2 && Math.abs(y1-y2)==1) || (y1==y2 && Math.abs(x1-x2)==1));
    }

    /**
     * checkWin: check after each move if the current player is winning
     * @param the player who just played
     */
    public boolean checkWin(){

        //first check the necessary conditions for the current player to win: whether the vertices on the borders are connected
        if(isPossibleWin()){
            for(int i = 0; i<connectedStart.size();i++){
                for(int j = 0; j<connectedEnd.size(); j++){
                    // System.out.println("checkwin method reached");
                    if(currentPlayer.pathDFS(connectedStart.elementAt(i),connectedEnd.elementAt(j))) {
                        // System.out.println("current player: " + currentPlayer + " is winning: true"); 
                        winPlayerNum = currentPlayer.getNum();
                        isGameOver = true;
                        return true;                       
                    }
                }
            }
        }
        // System.out.println("current player: " + currentPlayer + " is winning: false");
        return false;
    }

    /**
     * switchTurns (helper): switch two players' turns
     */
    private void switchTurns(){
        Player temp = getOpponent(currentPlayer);
        currentPlayer = temp;
    }

    /**
     * isPossibleWin: check after each move if the current player has potential to win
     * by checking whether the vertices on the borders are connected
     * if the current player number is 1
     *      startConnected indicates whether the leftmost vertices are connected
     *      endConnected indicates whether the rightmost vertices are connected
     * if the current player number is 2
     *      startConnected indicates whether the topmost vertices are connected
     *      endConnected indicates whether the bottommost vertices are connected
     *      
     * @return boolean if vertices on the borders are connected
     */
    public boolean isPossibleWin(){
        boolean startConnected, endConnected;
        startConnected = endConnected = false;
        int currentIndex = 0;
        // System.out.println("is possible reached");
        connectedStart = new Vector<Vertex>();
        connectedEnd = new Vector<Vertex>();
        if(isPlayer1Turn()){
            for(int i = 0; i<4; i ++){
                currentIndex = currentPlayer.getVertexIndex(0,i);
                //check if a vertex on the leftmost border is connected
                if(currentPlayer.getBoard().getVertex(currentIndex).getConnected()){
                    startConnected = true;
                    //if the vertex on the border is connected, add it to the potential starting
                    //vertices
                    // System.out.println("Start connected " + startConnected);
                    connectedStart.add(currentPlayer.getBoard().getVertex(currentIndex));
                }
                currentIndex = currentPlayer.getVertexIndex(4,i);
                if(currentPlayer.getBoard().getVertex(currentIndex).getConnected()){
                    endConnected = true;
                    // System.out.println("End connected " + endConnected);
                    connectedEnd.add(currentPlayer.getBoard().getVertex(currentIndex));
                }
            }
        }
        else{
            for(int i = 0; i<4; i ++){
                currentIndex = currentPlayer.getVertexIndex(i,0);
                if(currentPlayer.getBoard().getVertex(currentIndex).getConnected()){
                    startConnected = true;
                    // System.out.println("Start connected " + startConnected);
                    connectedStart.add(currentPlayer.getBoard().getVertex(currentIndex));
                }
                currentIndex = currentPlayer.getVertexIndex(i,4);
                if(currentPlayer.getBoard().getVertex(currentIndex).getConnected()){
                    endConnected = true;
                    // System.out.println("End connected " + endConnected);
                    connectedEnd.add(currentPlayer.getBoard().getVertex(currentIndex));
                }
            }
        }
        return startConnected && endConnected;
    }

    /**
     * The followings are instance variable getters
     */
    public Player getPlayer1(){
        return player1;
    }

    public Player getPlayer2(){
        return player2;
    }

    public boolean getIsGameOver(){
        return isGameOver;
    }
    
    public int getWinnerNum(){
        return winPlayerNum;
    }
    
    /**
     * Instance variable setter
     */
    public void setIsGameOver(boolean c){
        isGameOver = false;
    }

    public static void main(String[] args){
        BridgeItGame test = new BridgeItGame();

        int currentIndex = test.player1.getVertexIndex(0,0);
        int currentIndex2 = test.player1.getVertexIndex(1,0);
        int currentIndex3 = test.player2.getVertexIndex(0,0);
        int currentIndex4 = test.player2.getVertexIndex(1,0);
        Vertex v1 = test.player1.getBoard().getVertex(currentIndex);
        Vertex v2 = test.player1.getBoard().getVertex(currentIndex2);
        Vertex v3 = test.player2.getBoard().getVertex(currentIndex3);
        Vertex v4 = test.player2.getBoard().getVertex(currentIndex4);

        // System.out.println("currentIndex " +currentIndex);
        // System.out.println("currentIndex2 " +currentIndex2);

        test.player1.addEdge(v1, v2);
        // System.out.println(test.isValidMove(v3,v4));
        // System.out.println(test.verticalOrHorizontal(v3,v4,test.player1));

    }
}
