import java.io.File;
import java.io.IOException;
import java.util.*;

public class KruGraph {
    private Vertex[] vertexArr;
    private ArrayList<MyEdge> edgeArr;
    private int vertexCount;
    private int edgeCount;

    public KruGraph(String graph_file)throws IOException{
        //TODO
        File file = new File(graph_file);
        Scanner sc = new Scanner(file);
        vertexCount = sc.nextInt();
        edgeCount = sc.nextInt();
        vertexArr = new Vertex[vertexCount + 1];
        for(int i =0; i < vertexCount + 1; i ++){
            if(i != 0) {
                vertexArr[i] = new Vertex(i);
            }
        }
        edgeArr = new ArrayList<MyEdge>();
        for(int i = 0;i < edgeCount; i ++){
            int begin = sc.nextInt();
            int end = sc.nextInt();
            int weight = sc.nextInt();
            edgeArr.add(new MyEdge(begin,end,weight));
        }
    }

    public PriorityQueue<MyEdge> kruskalMST(){
        PriorityQueue<MyEdge> result = new PriorityQueue<MyEdge>();
        int index = 0;
        int inde = 0;
        ArrayList<MyEdge> temp = edgeArr;
        Collections.sort(temp);
        while(index < vertexCount-1){
            MyEdge next = temp.get(inde++);
            Vertex x = find(vertexArr[next.getS()]);
            Vertex y = find(vertexArr[next.getD()]);
            if(!x.equals(y)){
                result.add(next);
                index++;
                union(x,y);
            }
        }

        return result;
    }
    public static Vertex find(Vertex x){
        if(x.getParent().equals(x)){
            return x;
        }else{
            x.updateParent(find(x.getParent()));
        }
        return x.getParent();
    } 

    public static boolean union(Vertex x, Vertex y){
        //TODO

        Vertex px = find(x);
        Vertex py = find(y);
        if(px.equals(py)){
            return false;
        }
        if(px.getSize() > py.getSize()){
            py.updateParent(px);
            x.updateParent(px);
            px.updateSize(px.getSize() + py.getSize());
        }else if(px.getSize() <= py.getSize()){
            px.updateParent(py);
            py.updateSize(px.getSize() + py.getSize());
        }

        return true;
    } //

    public static void printGraph(PriorityQueue<MyEdge> edgeList){
        int turn = edgeList.size();
        for (int i = 0; i < turn; i++) {
            MyEdge edge = edgeList.poll();
            int source = edge.getS();
            int dest = edge.getD();
            if(source > dest){
                int temp = source;
                source = dest;
                dest = temp;
            }
            System.out.println("from: " + source + " to: " + dest + " weight: " + edge.getWeight());
        }
    }

    public static void main(String[] args) throws IOException {
        KruGraph graph = new KruGraph("./src/localtestk1.txt");
        printGraph(graph.kruskalMST());
    }

}
