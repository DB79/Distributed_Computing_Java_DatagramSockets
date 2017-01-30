
import java.net.*;
import java.io.*;
import java.nio.file.*;
import java.nio.Buffer;

public class MyClientDatagramSocket extends DatagramSocket {

    static final int MAX_LEN = 100000;

    MyClientDatagramSocket() throws SocketException {
        super();
    }

    MyClientDatagramSocket(int portNo) throws SocketException {
        super(portNo);
    }

    public void sendMessage(InetAddress receiverHost, int receiverPort, String message) throws IOException {
        byte[] sendBuffer = message.getBytes();
        DatagramPacket datagram = new DatagramPacket(sendBuffer, sendBuffer.length, receiverHost, receiverPort);
        this.send(datagram);
    } // end sendMessage

    public void sendMessage(InetAddress receiverHost, int receiverPort, String message, File newUploadFile) throws IOException {

        byte[] messageBuffer = message.getBytes();
        byte[] file = Files.readAllBytes(newUploadFile.toPath());

        byte[] destination = new byte[messageBuffer.length + file.length];
        System.arraycopy(messageBuffer, 0, destination, 0, messageBuffer.length);
        System.arraycopy(file, 0, destination, messageBuffer.length, file.length);

        DatagramPacket datagram = new DatagramPacket(destination, destination.length, receiverHost, receiverPort);

        this.send(datagram);
    }

    public String receiveMessage() throws IOException {
        byte[] receiveBuffer = new byte[MAX_LEN];
        DatagramPacket datagram = new DatagramPacket(receiveBuffer, MAX_LEN);

        this.receive(datagram);
        String message = new String(receiveBuffer);
        return message;
    } //end receiveMessage
} //end class
