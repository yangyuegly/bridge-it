
/**
 * The Area Class represents a rectangular area on the GUI
 * Its instance variables are the coordinates of its border
 *
 * @main authors (Jane Yang, Yining Li)
 * @version 12/13/18
 */
public class Area{
    private int leftBorder,rightBorder,topBorder,bottomBorder; 

    /**
     * Constructor for objects of class Area
     */
    public Area(int lb, int rb, int tb, int bb){
        // a rectangular area is defined by its four boundaries
        leftBorder = lb;
        rightBorder = rb; 
        topBorder = tb; 
        bottomBorder = bb;
    }
    
    /**
     * The followings are getters for the instance variables
     */
    public int getLeftBorder(){
        return leftBorder;
    }

    public int getRightBorder(){
        return rightBorder;
    }

    public int getTopBorder(){
        return topBorder;
    }

    public int getBottomBorder(){
        return bottomBorder;
    }
    
    /**
     * contains
     * @param int mouseX: the x-coordinate of the mouse click
     * @param int mouseY: the y-coordinate of the mouse click
     * @return a boolean: true if the mouse click lies in the Area, 
     * and false otherwise
     */
    public boolean contains(int mouseX, int mouseY){
        return (mouseX<=rightBorder && mouseX>=leftBorder 
                && mouseY>=topBorder && mouseY<=bottomBorder);
             
    }
    
    /**
     * toString
     * @return a formatted String representation of the Area
     */
    public String toString(){
        String result = "";
        result = "leftBorder: " + leftBorder + "rightBorder: " + rightBorder + "topBorder: " + topBorder + "bottomBorder " + bottomBorder;
        return result;
    }

}
