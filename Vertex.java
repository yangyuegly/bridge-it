
/**
 * The Vertex Class creates a single Vertex (a circle) on the 
 * Bridg-it gameboard.
 * A Vertex object has the following properties (instance variables):
 * 1. the (x,y) identifier on the Bridg-it graph, which looks like a 
 *    4*5 or 5*4 grid
 * 2. the position on the GUI canvas 
 * 3. the size of the corresponding circle on the GUI
 * 4. whether the Vertex has been used by the user (for forming an edge)
 * 5. the neighboring area of the Vertex on the GUI
 *
 * @main authors (Jane Yang, Yining Li)
 * @version (12/13/18)
 */

public class Vertex{
    // x and y are the identifiers of each vertex. 
    // e.g., (0, 0) is the identifier of the top left Vertex
    // xpos and ypos represent the position of the vertex on the GUI
    // e.g., (340, 200) is the position of the top left Vertex on the GUI
    // width and height are the same (the diameter of the Vertex)
    private int xpos, ypos, x, y, width, height ;
    
    // whether the Vertex has been connected (used to form an edge)    
    private boolean connected; 

    // the blank space between the center of two Vertices on the GUI    
    private final int SPACE = 130;
    
    // realm is the area associated with the Vertex, which includes
    // right, left, top, and bottom rectangular areas around the Vertex   
    private Area realm, rightArea, leftArea, topArea, bottomArea; 
    
    /**
     * Constructor for objects of class Vertex
     */
    public Vertex()
    {
        xpos = 0;
        ypos = 0;
        width = 50; // width and height represent the diameter of 
        height = 50; // each vertex/dot on the GUI 
        x = -1;
        y = -1;
        connected = false;

        realm = new Area(0,0,0,0);
        rightArea = new Area(0,0,0,0);
        leftArea = new Area(0,0,0,0);
        bottomArea = new Area(0,0,0,0);
        topArea = new Area(0,0,0,0);
    }

    public Vertex(int m, int n, int x, int y)    {
        width = 50; // diameter of the Vertex
        height = 50;
        this.x = x;
        this.y = y;
        connected = false; // the Vertex hasn't been used yet
        
        // xpos and ypos indicate the center of the vertex on the GUI        
        xpos = m + width/2;
        ypos = n + height/2;

        // Area(leftBorder, rightBorder, topBorder, bottomBorder)        
        rightArea = new Area(xpos + width/2, xpos + SPACE/2, 
                             ypos - height/2, ypos + height/2);
        leftArea = new Area(xpos - SPACE/2, xpos - width/2,
                            ypos - height/2, ypos + height/2);
        topArea = new Area(xpos - width/2, xpos + width/2, 
                           ypos - SPACE/2, ypos - height/2);
        bottomArea = new Area(xpos - width/2 , xpos + width/2, 
                              ypos + height/2, ypos + SPACE/2);
        realm = new Area(xpos - SPACE/2,xpos + SPACE/2, 
                         ypos - SPACE/2, ypos + SPACE/2);
    }
    
    /**
     * the following are getter methods for the instance variables 
     */    
    public Area getRealm(){
        return realm;
    }

    public int getXpos(){
        return xpos;
    }

    public int getYpos(){
        return ypos;
    }

    public int getIDx(){
        return x;
    }

    public int getIDy(){
        return y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public boolean getConnected(){
        return connected;
    }

    public Area getRightArea(){
        //System.out.println("getRightArea: " + rightArea.toString());
        return rightArea;
    }

    public Area getLeftArea(){
       // System.out.println("getLeftArea: " + leftArea.toString());
        return leftArea;
    }

    public Area getTopArea(){
       // System.out.println("getTopArea: " + topArea.toString());
        return topArea;
    }

    public Area getBottomArea(){
       // System.out.println("getBottomArea: " + bottomArea.toString());
        return bottomArea;
    }
    
    /**
     * setter for connected instance variable
     */
    public void setConnected(boolean c){
        connected= c;
    }
    
    /**
     * toString
     * @return String result: a String representation of a Vertex
     */
    public String toString(){
        String result = "";
        result += "Vertex: X: " + getIDx() + ": Y " + getIDy();
        return result;
    }
}
