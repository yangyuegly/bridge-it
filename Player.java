/**
 * The Player class creates a Player object that represents a general player
 *
 * @main authors (Jane Yang, Yining Li)
 * @version 12/13/18
 */
public class Player
{
    private String name;
    private int playerNum;
    private final int SPACE = 110;
    private AdjListsGraph<Vertex> board;//this Player's board

    /**
     * Constructor for objects of class Player
     */
    public Player (int num){
        playerNum = num;
        board = new AdjListsGraph<Vertex>();
        if(playerNum == 1){ 
            // create a 5*4 "grid" graph (w/ spaces b/t Vertices)
            // for player 1 whose path goes horizontally
            for(int i = 0; i<5; i ++){
                for(int j = 0; j<4; j++){
                    board.addVertex(new Vertex(SPACE * i + 255, SPACE * j + 100, i, j));
                }
            }
        }

        if(playerNum == 2){ 
            // create a 4*5 "grid" graph (w/ spaces b/t Vertices)
            // for player 2 whose path goes vertically
            for(int i = 0; i<4; i ++){
                for(int j = 0; j<5; j++){
                    board.addVertex(new Vertex(SPACE * i + 310, SPACE * j + 50, i, j));
                }
            }
        }

    }

    /**
     * getLeftNeighbor
     * @param Vertex target: the Vertex whose left neighbor is to be found
     * @return the left neighbor Vertex of the target
     */
    public Vertex getLeftNeighbor(Vertex target){
        if(target.getIDx() == 0){
            // System.out.println("no left neighbor");
            return null; //no left neighbor for the left-most vertices
        } else{
            int index = getVertexIndex(target.getIDx()-1, target.getIDy());
            return getVertex(index);
        }

    }

    /**
     * getRighttNeighbor
     * @param Vertex target: the Vertex whose right neighbor is to be found
     * @return the right neighbor Vertex of the target
     */    
    public Vertex getRightNeighbor(Vertex target){
        if(playerNum == 1){
            if(target.getIDx() == 4){
                // System.out.println("no right neighbor");
                return null; // no right neighbor for the right-most vertices
            }            
        }
        else{
            if(target.getIDx() == 3){
                // System.out.println("no right neighbor");
                return null;
            }         
        }
        int index = getVertexIndex(target.getIDx()+1, target.getIDy());
        return getVertex(index);
    }

    /**
     * getTopNeighbor
     * @param Vertex target: the Vertex whose top neighbor is to be found
     * @return the top neighbor Vertex of the target
     */
    public Vertex getTopNeighbor(Vertex target){

        if(target.getIDy() == 0){
            // System.out.println("player 1 no top neighbor");
            return null; //no top neighbor for the top-most vertices
        } 
        else{
            int index = getVertexIndex(target.getIDx(), target.getIDy()-1);
            return getVertex(index);
        }

    }

    /**
     * getBottomNeighbor
     * @param Vertex target: the Vertex whose bottom neighbor is to be found
     * @return the bottom neighbor Vertex of the target
     */
    public Vertex getBottomNeighbor(Vertex target){

        if(playerNum == 1){
            if(target.getIDy() == 3){
                // System.out.println("player 1 no bottom neighbor");
                return null; //no bottom neighbor for bottom-most vertices
            }        
        }
        else{
            if(target.getIDy() == 4){
                // System.out.println("player 2 no bottom neighbor");
                return null;
            }  
        }
        int index = getVertexIndex(target.getIDx(), target.getIDy()+1);
        return getVertex(index);

    }

    /**
     * The followings are getter methods for instance variables
     */
    public String getName()
    {
        return name;
    }

    public int getNum()
    {
        return playerNum;
    }

    public AdjListsGraph<Vertex> getBoard(){
        return board;
    }

    public int getNumVertices() { 
        return board.getNumVertices(); 
    }

    /**
     * getVertexIndex
     * @param int x: the x identifier of the given vertex
     * @param int y: the y identifier of the given vertex
     * @return int resultIndex: the index of the given vertex in the vertices vector
     */
    public int getVertexIndex(int x, int y){
        boolean found = false;
        int resultIndex = -1;
        int index = 0;
        //when a vertex with the given coordinates is not found, keep looking for
        //the target vertex
        while(!found && index<getNumVertices()){
            if(getVertex(index).getIDx() ==x && getVertex(index).getIDy() ==y){
                resultIndex = index; 
                found = true;
            }else{
                index++;
            }
        }
        return resultIndex;
    }

    /**
     * getVertex
     * @param int i: the index of the vertex to be found in the vector
     * @return: the Vertex
     */
    public Vertex getVertex(int i){
        return board.getVertex(i);
    }

    /**
     * addEdge
     * @param Vertex vertex1: the first Vertex
     * @param Vertex vertex2: the second Vertex
     */
    public void addEdge(Vertex vertex1, Vertex vertex2) {
        board.addEdge(vertex1,vertex2);
        // mark the two Vertices as connected
        setConnected(vertex1,true);
        setConnected(vertex2,true);    
    }


    /**
     * pathDFS
     * check whether a path exists between the start Vertex and the end Vertex
     * @param the start vertex
     * @param the end vertex 
     * @return boolean: whether a path has been found
     */
    public boolean pathDFS(Vertex start, Vertex end){
        return (board.pathDFS(start, end).size() > 0);
    }

    /**
     * setConnected
     * marks a vertex as connected or disconnected
     * @param whether the vertex has been connected 
     * @param the target vertex 
     */
    public void setConnected(Vertex target, boolean c){
        target.setConnected(c);
    }

    /**
     * toString
     * @return a formatted String representation of Player
     */
    public String toString(){
        return "Player" + playerNum;
    }
}
