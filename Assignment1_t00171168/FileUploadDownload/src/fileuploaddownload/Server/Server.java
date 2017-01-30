
import java.io.*;
import java.util.*;
import java.nio.file.*;

public class Server {

    private static final int serverPort = 1234;
    public static List<String> activeUsers = new LinkedList<String>();
    public static String serverFolderLocation = "c:\\ServerFolders\\";

    public static void main(String[] args) {

        File folder = null;
        String user = null;
        ArrayList<String> params;
        
        File serverFolder = new File(serverFolderLocation);
        serverFolder.mkdir();

        try {
            MyServerDatagramSocket mySocket = new MyServerDatagramSocket(serverPort);
            System.out.println("Server listening...");

            while (true) {  
                DatagramMessage request = mySocket.receiveMessageAndSender();
                params = extractParams(request.getMessage());
                String action = params.get(0).trim();

                String returnMessage;
                String fileName;
                System.out.println("ACTION: " + action);
                
                switch (action) {
                    case "LOGIN":
                        user = params.get(1).trim();
                        folder = new File(serverFolderLocation + user);
                        returnMessage = login(params.get(1).trim(), folder);
                        break;
                    case "LOGOUT":
                        System.err.println("USER TO LOGOUT: " + params.get(1).trim());
                        returnMessage = logout(params.get(1).trim());
                        break;
                    case "UPLOAD":
                        String fileAsString = request.getMessage().substring(request.getMessage().indexOf(params.get(2)));
                        fileName = params.get(1);
                        returnMessage = upload(folder, fileName, fileAsString);
                        break;
                    case "DOWNLOAD":
                        fileName = params.get(1).trim();
                        returnMessage = download(folder, fileName);
                        break;
                    default:
                        returnMessage = "Invalid action. Actions available - LOGIN, LOGOUT, UPLOAD and DOWNLOADs";
                        break;
                }

                mySocket.sendMessage(request.getAddress(), request.getPort(), returnMessage);
            }//end while
        }//end try
        catch (Exception ex) {
            ex.printStackTrace();
        }//end catch
    }//end main

    public static ArrayList<String> extractParams(String request) {
        StringTokenizer t = new StringTokenizer(request);
        ArrayList<String> params = new ArrayList<String>();
        String word = "";

        while (t.hasMoreTokens()) {
            word = t.nextToken();
            params.add(word);
        }

        return params;
    }

    public static String login(String username, File folder) {
        if (folder.exists()) {
            if (activeUsers.contains(username)) {
                return "401: " + username + " is already logged in.";
            } else {
                activeUsers.add(username);
                return "200: " + username + " has logged in";
            }
        } else {
            folder.mkdir();
            activeUsers.add(username);
            return "201: " + username + "'s folder has been created.";
        }
    }

    public static String logout(String username) {
        if (activeUsers.contains(username)) {
            activeUsers.remove(username);
            return "200: " + username + " has been successfully logged out.";
        } else {
            return "402: " + username + " cannot be logged out.";
        }
    }

    public static String upload(File folder, String fileName,String fileAsString) {

        File fileDestination = new File(folder, fileName);

        if (fileDestination.exists()) {
            return "405: The file " + fileName + " already exists in your folder.";
        } else {

            try {
                FileOutputStream stream = new FileOutputStream(fileDestination);
                stream.write(fileAsString.trim().getBytes());
                stream.close();
                return "200: File successfully uploaded.";
            } catch (Exception e) {
                System.out.println(e);
                return "An error occurred: " + e;
            }
        }
    }

    public static String download(File folder, String fileName) {
        File fileToBeDownloaded = new File(folder, fileName);
        boolean fileExists = fileToBeDownloaded.exists();
        StringBuilder builder = new StringBuilder();
        //check to see that the file exists in the current users folder
        if (fileExists) {
            try {
                //convert file to string so that it can be passed back to client
                FileInputStream fileStream = new FileInputStream(fileToBeDownloaded);
                int ch;
                while ((ch = fileStream.read()) != -1) {
                    builder.append((char) ch);
                }
            } catch (Exception e) {
                System.out.println(e);
                return e.toString();
            }
            return "200:" + builder;
        } else {
            return "404: File not found in folder.  ";
        }
    }
} // end class      
