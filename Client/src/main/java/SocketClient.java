import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {
    private static String HOST = "127.0.0.1";
    private static int PORT = 8080;

    public SocketClient() {
    }

    public static void main(String[] args)  {
            try {
                Client c=new Client(HOST,PORT);
                c.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}

class Client{
    private  String ip;
    private  int port;
    Socket socket;
    BufferedReader reader;
    BufferedWriter writer;
    Client(String ip,int port) throws IOException {
       this.ip=ip;
       this.port=port;
    }
    public void run() throws Exception{
        socket = new Socket(ip, port);
        System.out.println("connected");
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        while(true){
            Thread.sleep(1000);
            System.out.println("send");
            writer.write("client send message");
            writer.flush();
        }
    }
}