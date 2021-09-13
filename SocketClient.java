import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SocketClient {
    private final String host;
    private final int port;
    
    private InetAddress address;
    private DatagramSocket socket;
    
    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;   
    }
    
    public void connect() throws Exception {
        this.address = InetAddress.getByName(this.host);
        this.socket = new DatagramSocket();
        
        this.socket.setSoTimeout(250);
    }
    
    public void disconnect() {
        this.socket.close();
    }
    
    public String send(String message) throws Exception {
        byte[] messageArray = message.getBytes();
        DatagramPacket request = new DatagramPacket(messageArray, messageArray.length, this.address, this.port);
        socket.send(request);
        
        byte[] buffer = new byte[512];
        DatagramPacket response = new DatagramPacket(buffer, buffer.length);
        socket.receive(response);
        
        return new String(buffer, 0, response.getLength());
    }
}
