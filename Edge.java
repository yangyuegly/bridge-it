/**
 * An Edge object consists of 2 vertices
 *
 * @author (Jane Yang)
 * @version (12/13/18)
 */
public class Edge{

    private Vertex v1, v2;
    /**
     * Constructor for objects of class Arc
     */
    public Edge(Vertex v1, Vertex v2)
    {
        this.v1 = v1;
        this.v2 = v2;
    }
    
    public Edge(){
        v1 = new Vertex();
        v2 = new Vertex();
    }
        
    public Vertex getFirstV(){
         return v1;
    }
    
     public Vertex getSecondV(){
         return v2;
    }   
}
