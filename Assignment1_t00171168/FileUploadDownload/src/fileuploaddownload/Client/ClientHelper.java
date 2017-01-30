import java.net.*;
import java.io.*;
import java.nio.file.*;

public class ClientHelper {

    private MyClientDatagramSocket mySocket;
    private InetAddress serverHost;
    private int serverPort;

    public ClientHelper() throws SocketException, UnknownHostException {

        this.serverHost = InetAddress.getByName("localhost");
        this.serverPort = Integer.parseInt("1234");

        // instantiates a datagram socket for both sending
        // and receiving data
        this.mySocket = new MyClientDatagramSocket();
    }

    public String login(String username) throws SocketException, IOException {
        mySocket.sendMessage(serverHost, serverPort, "LOGIN " + username);
        return mySocket.receiveMessage();
    }

    public String logout(String username) throws SocketException, IOException {
        mySocket.sendMessage(serverHost, serverPort, "LOGOUT " + username);
        return mySocket.receiveMessage();
    }

    public String upload(String filename, String path) throws SocketException, IOException {
        try {
            byte[] file = Files.readAllBytes(new File(path).toPath());
            System.out.print(file);
            mySocket.sendMessage(serverHost, serverPort, "UPLOAD " + filename + " " + new String(file));
            return mySocket.receiveMessage();
        } catch (FileNotFoundException e) {
            return "The file doesn't exist: " + path;
        } catch (IOException ex) {
            return ex.toString();
        } catch (Exception exc) {
            return exc.toString();
        }
    }

    public String download(String filename, String downloadPath) throws SocketException, IOException {
        
        mySocket.sendMessage(serverHost, serverPort, "DOWNLOAD " + filename);
        String message = mySocket.receiveMessage();

        File down = new File(downloadPath);
        if(message.substring(0,3).equals("200"))
        {
            try {
                File downloadLocation = new File(down, filename);
                Files.write(downloadLocation.toPath(), message.substring(4).trim().getBytes());

            } catch (IOException e) {
                System.out.print(e);
                return e.toString();
            } catch (Exception ex){
                System.err.println(ex);
                return ex.toString();
            }
        }
        return message;
    }

    public void done() throws SocketException {
        mySocket.close();
    }  //end done

} //end class
