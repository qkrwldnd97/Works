
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
import java.io.*;
public class Threader extends Thread {

    Main main;
    BufferedReader input= null;
    InputStreamReader reader = null;
    BufferedReader in = null;
    PrintWriter writer = null;
    String name = null;
    FileReader fr = null;
    ArrayList<String> list = new ArrayList<String>();
    BufferedReader in2 = null;
    //PrintWriter write = null;
    FileWriter text = null;
    FileReader read = null;
    BufferedReader in3 = null;
    //ArrayList<String> list = new ArrayList<String>();

    //2.1
    public Threader(Socket socket, Main server) {
        try {
            reader = new InputStreamReader(socket.getInputStream());
            input = new BufferedReader(reader);
            writer = new PrintWriter(socket.getOutputStream(), true);
            main = server;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void run() {

        String name = "";
        String code = "";
        String pass = "";

        //register
        while (true) {
            try {
                String newUser = input.readLine();
                if (newUser == null) {
                    break;
                }
                String[] a = newUser.split("--");

                try {
                    code = a[0];
                    name = a[1];
                    //name = newUser1.substring(0, newUser1.indexOf("-"));
                    System.out.println(name);

                    pass = a[2];
                    //String pass = newUser1.substring(newUser1.indexOf("-")+2);
                    System.out.println(pass);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                //check code
                if (code.equals("CREATENEWUSER")) {
                    writer.println(serverRegister(a[0], a[1], a[2]));
                } else if (code.equals("LOGIN")) {
                    writer.println(serverLogin(a[0], a[1], a[2]));
                } else if (code.equals("STARTNEWGAME")) {
                    writer.println(newGame(a[0], a[1]));
                } else if (code.equals("JOINGAME")) {
                    writer.println(join(a[0], a[1], a[2]));
                } else if (code.equals("ALLPARTICIPANTSHAVEJOINED")) {
                    launch(a[2]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Register
    public String serverRegister(String code, String name, String pass) {

        String mess = "";

        //form
        if (!code.equals("CREATENEWUSER")) {
            mess = "RESPONSE--CREATENEWUSER--INVALIDMESSAGEFORMAT";
        }

        //id check
        if (name.isEmpty()) {
            return "RESPONSE--CREATENEWUSER--INVALIDUSERNAME";
        }
        if (!(name.length() < 10)) {
            return "RESPONSE--CREATENEWUSER--INVALIDUSERNAME";
        }
        for (int i = 0; i < name.length(); i++) {
            try {
                Integer.parseInt(name.charAt(i) + "");
                continue;
            } catch (Exception e) {
                if (!((name.charAt(i) >= 'A' && name.charAt(i) <= 'Z') || (name.charAt(i) >= 'a' && name.charAt(i) <= 'z') || (name.charAt(i) == '_'))) {
                    return "RESPONSE--CREATENEWUSER--INVALIDUSERNAME";
                }
            }
        }
        //pass check
//        if (pass.isEmpty()) {
//            return "RESPONSE--LOGIN--INVALIDPASSWORD";
//        }
//        if (!(pass.length() < 10)) {
//            return "RESPONSE--LOGIN--INVALIDPASSWORD";
//        }
//        if (!((pass.matches(".*[a-zA-Z]+.*")) || pass.contains("#") || pass.contains("&") || pass.contains("$") || pass.contains("*"))) {
//            return "RESPONSE--LOGIN--INVALIDPASSWORD";
//        }
//        boolean digit = true;
//        for (int i = 0; i < pass.length(); i++) {
//            try {
//                Integer.parseInt(pass.charAt(i) + "");
//                digit = false;
//                break;
//            } catch (Exception e) {
//                continue;
//            }
//        }
//            if (!digit) {
//                return "RESPONSE--CREATENEWUSER--INVALIDUSERPASSWORD";
//            }

            //check userdatabase.txt
            //FileReader reader= null;
            boolean check = true;
            ArrayList<String> list = new ArrayList<String>();
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new FileReader("/homes/src/UserDatabase.txt"));
                while (check) {
                    String data = bufferedReader.readLine();
                    if (data == null) {
                        check = false;
                    }
                    list.add(data);
                }
            } catch (Exception e) {
                System.out.println("No such a file or direction found");
            } finally {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            for (int i=0; i<list.size()+1;i++) {
//                if (!list.get(i).contains(name)) {
//                    continue;
//                } else {
//                    return "RESPONSE--CREATENEWUSER--USERALREADYEXISTS";
//                }
//            }


            // Success
            {
                try {
                    File file = new File("/homes/src/UserDatabase.txt");
//                    FileOutputStream output = new FileOutputStream(file);
//                    PrintWriter writer1 = new PrintWriter(output, true);
                    FileWriter writer3= new FileWriter(file,true);
                    BufferedWriter bw = new BufferedWriter(writer3);
                    PrintWriter out = new PrintWriter(bw);

                    String str = "";
                    for (int i = 0; i < list.size(); i++) {
                        if (!(str.equals("r"))) {
                            str += "\r\n" + list.get(i);
                        } else {
                            str = list.get(i);
                        }
                    }

                    if (!(str.equals(""))) {
                        str ="\r\n"+ name + ":" + pass + ":0:0:0";
                    } else {
                        str = name + ":" + pass + ":0:0:0";
                    }

                    System.out.println(str);
                   // writer1.println(str);
                    //writer3.write(str);
                   // writer1.close();
                   // writer3.close();
                    out.println(str);
                    out.close();
                        return "RESPONSE--CREATENEWUSER--SUCCESS";


                } catch (Exception e) {
                   e.printStackTrace();
                }
                //mess = "RESPONSE--CREATENEWUSER--SUCCESS";
            }
        return mess;
    }

    //2.3
    public String serverLogin(String code, String name, String pass) {

        String mess = null;

        // check format
        if (!code.equals("LOGIN")) {
            mess = "RESPONSE--LOGIN--INVALIDMESSAGEFORMAT";
        }

        // unknown user
        //reading UserDatabase and check username is in it.
        FileReader reader = null;
        ArrayList<String> list = new ArrayList<String>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("/homes/src/UserDatabase.txt"));
            while (true) {
                String data = bufferedReader.readLine();
                if (data == null) {
                    break;
                }
                list.add(data);
            }
        } catch (Exception e) {
            System.out.println("can't find file");
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
            }
        }

        boolean isHas = false;
        int a = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains(name)) {
                isHas = true;
                a = i;
                break;
            }
        }
        if ((!isHas) || (a == -1)) {
            return "RESPONSE--LOGIN--UNKNOWNUSER";
        }

        if (!(list.get(a).contains(pass))) {
            return "RESPONSE--LOGIN--INVALIDUSERPASSWORD";
        }

        // Invalid user pass

        // User already logged in
//        main.arrayList.add(name); //when serverLogin
//
//        boolean isLoggedIn = false;
//        for (int i = 0; i < main.arrayList.size(); i++) {
//            if (main.arrayList.get(i).equals(name)) {
//                isLoggedIn = true;
//                break;
//            }
//        }
//        if (!isLoggedIn) {
//            //user is not logged in
//            mess = "RESPONSE--LOGIN--USERALREADYLOGGEDIN";
//        }

        // SUCCESS
        // Randomly generate 10 length token
        else {
            String token = "";
            for (int i = 0; i < 10; i++) {
                Random r = new Random();
                int ab = r.nextInt(25) + 97;
                token = token + (char) ab;
            }

            this.name = name;
            mess = "RESPONSE--LOGIN--SUCCESS--" + token;
            main.arrayList.add(name); //when serverLogin

            boolean isLoggedIn = false;
            for (int i = 0; i < main.arrayList.size(); i++) {
                if (main.arrayList.get(i).equals(name)) {
                    isLoggedIn = true;
                    break;
                }
            }
            if (!isLoggedIn) {
                //user is not logged in
                mess = "RESPONSE--LOGIN--USERALREADYLOGGEDIN";
            }

        }
        return mess;
    }


    //// 2.4 Start a new game ////
    public String newGame(String code, String userToken) {

        String mess = null;

        try {

            // Unknown user

            // Invalid user pass

            // User already logged in

            //success
            // Randomly generate 3 length token
            String token = "";
            for (int i = 0; i < 3; i++) {
                Random r = new Random();
                int ab = r.nextInt(25) + 97;
                token = token + (char) ab;
            }
            mess = "RESPONSE--STARTNEWGAME--SUCCESS--" + token;
            main.players.put(token, new ArrayList<PrintWriter>());
            main.players.get(token).add(writer);
            main.leaders.put(token, writer);
            main.tokens.add(token);
            return mess;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mess;
    }


    //////////////////  2_5_JoinaGame //////////////////
    public String join(String code, String userToken, String gameToken) {
        String mess = null;

        // User not logged in

        boolean login = false;
        for (int i = 0; i < main.arrayList.size(); i++) {
            if (main.arrayList.get(i).contains(name)) {
                login = true;
                break;
            }
        }
        if (!login) {
            //user is not logged in : invalid user token
            mess= "RESPONSE--JOINGAME--USERNOTLOGGEDIN";
        }

        // game key not found
//        if (!(gameToken.equals("") || gameToken == null)) {
//            return  "RESPONSE--JOINGAME--GAMEKEYNOTFOUND";
//        }
//        if (gameToken.length() > 9) {
//            return "RESPONSE--JOINGAME--GAMEKEYNOTFOUND";
//        }
        // failure
        // success
//        else {
//            mess = "NEWPARTICIPANT--JOINGAME--SUCCESS--" + gameToken;
//        }

        boolean hasToken = false;

        for (int i = 0; i < main.tokens.size(); i++) {
            if (main.tokens.get(i).equals(gameToken)) {
                hasToken = true;
                break;
            }
        }

        if (hasToken) {
            main.players.get(gameToken).add(writer);
            PrintWriter print = main.leaders.get(gameToken);
            print.println("NEWPARTICIPANT--" + name + "--" + "0");

            mess="RESPONSE--JOINGAME--SUCCESS--" + gameToken;
        }
        return mess;
    }

    public void launch(String token) {
//        try {
//            String suggest = input.readLine();
//            String[] ab=suggest.split("--");
//            String sug = ab[3];
//            for(int i=0; i<Main.players.size(); i++) {
//                //ArrayList<PrintWriter> a=Main.players.get(i);
//                Main.suggestions.put(sug, Main.players.get(i));
//            }
//        } catch(Exception e){
//        }
        for (int i = 0; i < main.players.get(token).size(); i++) {
            main.players.get(token).get(i).println("NEWGAMEWORD--A group of zebras--a dazzle");
        }
    }
    //2.8



    //2_6_LaunchGame

    //2_7_Send New Game Word

    //2_8_Collect players' suggestions


    //2_9_Send Round Options

    //2_10_Collect Players' Choice

    //2_11_Apply Game Logic and Send Result

    //2_12_Go to the next round

    //2_13_Quit game

}






























/*public class Threader extends Thread {

    Main main;

    InputStreamReader reader = null;
    BufferedReader input = null;
    PrintWriter writer = null;
    public Threader(Socket socket, Main main) {
        try {
            reader = new InputStreamReader(socket.getInputStream());
            input = new BufferedReader(reader);
            writer = new PrintWriter(socket.getOutputStream(), true);
            this.main = main;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {

        String name = "";
        // Register New User
        try {
            String newUser = input.readLine();
            String[] a = newUser.split("--");


            //check KEYWORD to check status
            if (a[0].equals("CREATENEWUSER")) {
                //writer.println(createNewUser());
            } else if (a[0].equals("LOGIN")) {
                //writer.println(serverLogin());
            }


            String newUser1 = newUser.substring(15);
            System.out.println(newUser1);
            name = newUser1.substring(0, newUser1.indexOf("-"));
            System.out.println(name);
            String pass = newUser1.substring(newUser1.indexOf("-")+2);
            System.out.println(pass);


            // check format
            if(!a[0].equals("CREATENEWUSER")){
                writer.println("RESPONSE--CREATENEWUSER--INVALIDMESSAGEFORMAT");
            }

            // username condition check
            if(name.equals(null)){
                writer.println("RESPONSE--CREATENEWUSER--INVALIDUSERNAME");
            }
            if(name.length() > 9){
                writer.println("RESPONSE--CREATENEWUSER--INVALIDUSERNAME");
            }

            for (int i = 0; i < name.length(); i++){
                try {
                    Integer.parseInt(name.charAt(i) + "");
                    continue;
                } catch (Exception e) {
                    if (!((name.charAt(i) >= 'A' && name.charAt(i) <= 'Z') || (name.charAt(i) >= 'a' && name.charAt(i) <= 'z'))) {
                        writer.println("RESPONSE--CREATENEWUSER--INVALIDUSERNAME");
                    }
                }

            }

            // pass condition check
            if(pass.equals(null)){
                writer.println("RESPONSE--CREATENEWUSER--INVALIDUSERPASSWORD");
            }
            if(pass.length() > 9){
                writer.println("RESPONSE--CREATENEWUSER--INVALIDUSERPASSWORD");
            }

            for (int i = 0; i < pass.length(); i++){
                try {
                    Integer.parseInt(pass.charAt(i) + "");
                    continue;
                } catch (Exception e) {
                    if (!( (pass.charAt(i) == '*') || (pass.charAt(i) == '$') || (pass.charAt(i) == '&') || (pass.charAt(i) == '#') || (pass.charAt(i) >= 'A' && pass.charAt(i) <= 'Z') || (pass.charAt(i) >= 'a' && pass.charAt(i) <= 'z'))) {
                        writer.println("RESPONSE--CREATENEWUSER--INVALIDPASSWORD");
                    }
                }
            }

            // at least one uppercase letter
            boolean isUpperCase = false;
            for (int i = 0; i < pass.length(); i++){
                if ((pass.charAt(i)>='A' && pass.charAt(i)<='Z')){
                    isUpperCase = true;
                    break;
                }
            }
            if (!isUpperCase){
                writer.println("RESPONSE--CREATENEWUSER--INVALIDUSERPASSWORD");
            }

            // at least one digit
            boolean digit = false;
            for (int i = 0; i < pass.length(); i++){
                try {
                    Integer.parseInt(pass.charAt(i) + "");
                    digit = true;
                    break;
                } catch (Exception e) {
                    continue;
                }
            }
            if (!digit){
                writer.println("RESPONSE--CREATENEWUSER--INVALIDUSERPASSWORD");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //reading UserDatabase and check username is in it.
        FileReader reader = null;
        ArrayList<String> list = new ArrayList<String>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("/Users/kimeric/Desktop/Purdue/Academic/CS180/CS180Fall16/PROJECT03_2/UserDatabase.txt"));
            while (true) {
                String data = bufferedReader.readLine();
                if (data == null) {
                    break;
                }
                list.add(data);
            }
        } catch (Exception e) {
            System.out.println("can't find file");
        }
        finally {
            try{
                reader.close();
            }
            catch (Exception e){
            }
        }

        boolean isHas = false;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains(name)) {
                isHas = true;
                break;
            }
        }

        if (!isHas) {
            writer.println("RESPONSE--CREATENEWUSER--USERALREADYEXISTS");
        }

        // success
        writer.println("RESPONSE--CREATENEWUSER--SUCCESS");



        ////// User Login /////
//        public String serverLogin() {
        try {
            String a = input.readLine();
            String newUser = input.readLine();
            String[] b = newUser.split("--");

            // check format
            if(!b[0].equals("LOGIN")){
                writer.println("RESPONSE--LOGIN--INVALIDMESSAGEFORMAT");
            }

            // unknown user
            //invaliduserpassword
            //user already logged in

            //success
            //randomly generate 10 length toeken
            String token = "";
            for (int i = 0; i < 10; i++) {
                Random r = new Random();
                int ab = r.nextInt(25) + 97;
                token = token + (char) ab;
            }
            writer.println("RESPONSE--LOGIN--SUCCESS--" + token);

            main.list.add(name); //when serverLogin

            boolean isLoggedIn = false;
            for (int i = 0; i < main.list.size(); i++) {
                if (main.list.get(i).equals(name)) {
                    isLoggedIn = true;
                    break;
                }
            }

            if (!isLoggedIn) {
                //user is not logged in
                writer.println("RESPONSE--LOGIN--USERALREADYLOGGEDIN");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
//            return "RESPONSE--LOGIN--SUCCESS--XXuKQrVDw";
//        }

        //// 2.4 Start a new game ////
        try {
            String a = input.readLine();
            String newUser = input.readLine();
            String[] b = newUser.split("--");
            writer.println("RESPONSE--STARTNEWGAME--USERNOTLOGGEDIN");
            writer.println("RESPONSE--STARTNEWGAME--FAILURE");
            writer.println("RESPONSE--STARTNEWGAME--SUCCESS");


            // unknown user
            //invaliduserpassword
            //user already logged in

            //success
            *//* TODO: randomly generate 10 length toekn *//*
            //String token =
            //writer.println("RESPONSE--LOGIN--SUCCESS--" + token);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
