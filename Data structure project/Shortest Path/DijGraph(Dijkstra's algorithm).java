import java.io.File;
import java.io.IOException;
import java.util.*;

public class DijGraph {
    static int MAXWEIGHT = 10000000;//The weight of edge will not exceed this number
    private Node[] nodeArr;//The vertices set in the graph
    private int nodeCount;//number of total vertices
    private int edgeCount;//number of total edges

    //Two option for the DijGraph constructor
    //Option 0 is used to build graph with for part 1: implementation for Dijkstra
    //Option 1 is used to build graph with for part 2: simple application of Dijkstra
    public DijGraph(String graph_file, int option)throws IOException{
        if (option == 0){
            File file = new File(graph_file);
            Scanner sc = new Scanner(file);
            nodeCount = sc.nextInt();
            edgeCount = sc.nextInt();
            nodeArr = new Node[nodeCount + 1];
            for(int i =0; i < nodeCount + 1; i ++){
                if(i != 0) {
                    nodeArr[i] = new Node(i);
                }
            }
            for(int i = 0;i < edgeCount; i ++){
                int begin = sc.nextInt();
                int end = sc.nextInt();
                int weight = sc.nextInt();
                nodeArr[begin].addEdge(end, weight);
                nodeArr[end].addEdge(begin,weight);
            }
        }
        else if (option == 1){
            File file = new File(graph_file);
            Scanner sc = new Scanner(file);
            nodeCount = sc.nextInt();
            edgeCount = sc.nextInt();
            nodeArr = new Node[nodeCount + 1];
            for(int i =0; i < nodeCount + 1; i ++){
                if(i != 0){
                    nodeArr[i]= new Node(i, sc.next());
                }
            }
            for(int i = 0;i < edgeCount; i ++){
                String begin = sc.next();
                String end = sc.next();
                int weight = sc.nextInt();
                Node beginNode = findByName(begin);
                Node endNode = findByName(end);
                beginNode.addEdge(endNode.getNodeNumber(), weight);
                endNode.addEdge(beginNode.getNodeNumber(),weight);
            }
        }

    }

    //Finding the single source shortest distances by implementing dijkstra.
    //Using min heap to find the next smallest target
    public  Dist[] dijkstra( int source){
        Dist[] result = new Dist[nodeCount +1];
        Dist[] current = new Dist[nodeCount];
        Boolean[] check = new Boolean[nodeCount+1];
        for(int i=0; i<nodeCount; i++){
            if(i != source-1) {
                current[i] = new Dist(nodeArr[i+1].getNodeNumber(), Integer.MAX_VALUE);
                check[i+1] = false;
            }else{
                Dist temp = new Dist(nodeArr[source].getNodeNumber(), 0);
                insert(current, temp, i);
                check[i+1] = false;
            }
        }
        result[source] = new Dist(source, 0);
        int num = nodeCount;
        while(isempty(current) > 0){
            Dist temp = extractMin(current, num--);
            int tempvertex = temp.getNodeNumber();
            int index = indexof(nodeArr, tempvertex);
            for(Map.Entry<Integer, Integer> a: nodeArr[index].getEdges().entrySet()){
                int v = a.getKey();
                int weight = a.getValue();
                int inde = indexof(current, v);
                int newbee = temp.getDist() + weight;
                if(!check[v] && newbee < current[inde].getDist() && newbee >= 0){
//                    current[inde].updateDist(newbee);
                    insert(current, new Dist(v, newbee), inde);
                    Dist ttemp = new Dist(v, newbee);
                    result[v] = ttemp;
                }
            }
            check[tempvertex] = true;
        }
        return result;
    }
    private int indexof(Node[] a, int b){
        for(int i=1; i<a.length; i++){
            if(a[i].getNodeNumber() == b){
                return i;
            }
        }
        return 0;
    }
    private int indexof(Dist[] a, int b){
        for(int z=1; a[z] != null ; z++){
            if(a[z].getNodeNumber() == b){
                return z;
            }
        }
        return 0;
    }



    //Find the vertex by the location name
    public Node findByName(String name){
        for (int x =1; x < nodeCount + 1; x++){
            if(nodeArr[x].getLocation().equals(name)){
                return nodeArr[x];
            }
        }
        return null;
    }

    //Implement insertion in min heap
    //first insert the element to the end of the heap
    //then swim up the element if necessary
    //Set it as static as always
    public static void insert(Dist [] arr, Dist value, int index){
            arr[index] = value;
            arr = heapify(arr, index);
    }
    private static Dist[] heapify(Dist[] arr, int index){

        if (index > 0 && arr[index].getDist() < arr[(index - 1) / 2].getDist()) {
                swap(arr, index, (index - 1) / 2);
                heapify(arr, (index - 1) / 2);
            }

        return arr;
    }
    private static int isempty(Dist[] a){
        int count=0;
        for(int i=0; i<a.length; i++){
            if(a[i] != null){
                count++;
            }
        }
        return count;
    }

    public static void swap(Dist []arr, int index1, int index2){
        Dist temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    //Extract the minimum element in the min heap
    //replace the last element with the root
    //then do minheapify
    //Set it as static as always
    public static Dist extractMin (Dist[] arr, int size){
        Dist min;
        if(arr[0] == null){
            min = arr[1];
            arr[1] = arr[size];
            arr[size] = null;
            size--;
            arr = heapifyroot(arr, 1, size);
        }else{
            min = arr[0];
            arr[0] = arr[size-1];
            arr[size-1] = null;
            size--;
            arr = heapifyroot(arr, 0, size);
        }

        return min;
    }
    private static Dist[] heapifyroot(Dist[] arr, int index, int size){

        int left = 2 * index +1, right = 2 * index + 2, temp = index;
            if (left < size && arr[left].getDist() < arr[index].getDist()) {
                temp = left;
            }
            if (right < size && arr[right].getDist() < arr[temp].getDist()) {
                temp = right;
            }
            if (temp != index) {
                swap(arr, index, temp);
                heapifyroot(arr, temp, size);
            }

        return arr;
    }

    //This will print the shortest distance result
    //The output format will be what we expect to pass the test cases
    public static void printResult(Dist[] result, int source){
        for(int x = 1;  x < result.length; x++){
            if(x != source){
                System.out.println(result[x].getNodeNumber() + " " +result[x].getDist());
            }
        }
    }

    public static void main(String[] args)throws IOException {
        DijGraph graph = new DijGraph("./src/localtest1.txt", 0);
        Dist[] result  = graph.dijkstra(7);
        printResult(result, 7);
    }
}
