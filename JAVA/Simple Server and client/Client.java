import javax.xml.crypto.Data;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        Socket socket = null;
        DataInputStream stream=null;

        try{
            socket = new Socket("localhost", 9998);
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            stream=new DataInputStream(socket.getInputStream());

            System.out.printf("Filename: ");
            String filename = bufferedReader.readLine();
            printWriter.println(filename);

            System.out.printf("Username: ");
            String username = bufferedReader.readLine();
            printWriter.println(username);

            String receiveMessage = stream.readLine();
            System.out.println(receiveMessage);
            if(receiveMessage.equals("InfoNotFoundException")){
                throw new InfoNotFoundException("InfoNotFoundException: " +
                        "Your information is not in our file");
            }

        }catch (FileNotFoundException e){
            System.out.println("File does not exist");
            e.getStackTrace();
        }catch (InfoNotFoundException e){
            System.out.println("Your information is not in out file");
            e.getStackTrace();
        }finally {
            if(socket != null) socket.close();
            if(printWriter != null) printWriter.close();
            if(bufferedReader != null) bufferedReader.close();
            if(stream != null) stream.close();
        }
    }
}
