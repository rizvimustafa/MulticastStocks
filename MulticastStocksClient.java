import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Date;
import java.util.Scanner;

public class MulticastStocksClient {

    private static final String MULTICAST_IP = "228.1.1.1";
    private static final int PORT = 5555;

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Which stock-index you want to monitor?");
            System.out.println("Applet only - type '1'");
            System.out.println("Googlet only - type '2'");
            System.out.println("Teslat only - type '3'");
            System.out.println("Metat only - type '4'");
            System.out.println("All stocks - type '5'");
            int option = scanner.nextInt();
            scanner.close();

            InetAddress multicastAddress = InetAddress.getByName(MULTICAST_IP);
            MulticastSocket multicastSocket = new MulticastSocket(PORT);
            multicastSocket.joinGroup(multicastAddress);
            System.out.println("You have chosen option: " + option);
            System.out.println("\nStock Values Multicast Client Starting on multicast-channel-IP: " + MULTICAST_IP);

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(packet);

                String data = new String(packet.getData(), packet.getOffset(), packet.getLength());
                String[] lines = data.split("\n");
                System.out.println("\ndata received from multicast-Channel-IP " + MULTICAST_IP);
                String time = (new Date()).toString();
                System.out.println(time);

                if (option == 5 || option == 1) {
                    System.out.println(lines[1]);
                }

                if (option == 5 || option == 2) {
                    System.out.println(lines[2]);
                }

                if (option == 5 || option == 3) {
                    System.out.println(lines[3]);
                }

                if (option == 5 || option == 4) {
                    System.out.println(lines[4]);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
