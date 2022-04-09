import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.io.OutputStream;
import java.io.FileNotFoundException;

public class Server {

    public static void main(String[] args) throws IOException{
        Socket socketClient = null;
        ServerSocket serverSocket = null;
        FileInputStream fileInputStream = null;
        OutputStream outputStream = null;
        PrintWriter otc = null;
        BufferedReader inFromClient = null;
        BufferedWriter bufferedWriter = null;
//        String[][] chr = null;
        String fn = null;
        String user = null;
        String last = null;
        String user2;
        String first = null;
        String marks = null;

            try {
                serverSocket = new ServerSocket(9998);
                System.out.println("Server Waiting for Connection");
                socketClient = serverSocket.accept();
                if (socketClient.isConnected()) {
                    System.out.println("Connection is successful and waiting for commands");
                }
                inFromClient = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
                fn = inFromClient.readLine();
                user = inFromClient.readLine();

                try{
                    String filename = "/homes/Desktop/" + fn;
                    File file = new File(filename);
                    fileInputStream = new FileInputStream(file);
                    BufferedReader bufferReader = new BufferedReader(new FileReader(file));
                    String fileContent;
                    boolean found = false;
                    while((fileContent = bufferReader.readLine()) != null) {
                        String[] lines = fileContent.split(";");
                                user2 = lines[1];
                                last = lines[2];
                                first = lines[3];
                                marks = lines[4];
                                if ((user.equals(user2))) {
                                    outputStream = socketClient.getOutputStream();
                                    otc = new PrintWriter(outputStream, true);
                                    //otc.println("last: " + last + "\nfirst: " + first +
                                    //        "\nMarks: " + marks);
                                    otc.println("last: " + last + "  first: " + first + "  Marks: "+marks);
                                    found = true;
                                    break;
                                }
                    }
                    if (!found) {
                        System.out.println("InfoNotFoundException");
                    }
//                    outputStream = socketClient.getOutputStream();
//                    otc= new PrintWriter(outputStream, true);
//                    otc.println("last: " + last + "\nfirst: " + first +
//                            "\nMarks: " + marks);

                }catch (FileNotFoundException e){
                    System.out.println("File does not exist");
                    e.getStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                if(socketClient != null) socketClient.close();
                if(serverSocket != null) serverSocket.close();
                if(fileInputStream != null) fileInputStream.close();
                if(outputStream != null) outputStream.close();
            }

        }
    }

