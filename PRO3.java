import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalIPAddress {

    public static void main(String[] args) {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("Local IP address: " + localhost.getHostAddress());
            InetAddress local = InetAddress.getByName("www.Google.com");
            System.out.println("Ip Address Of Website:" + local.getHostAddress());
        } catch (UnknownHostException e) {
            System.out.println("Error: Could not find local IP address.");
            e.printStackTrace();
        }
    }
}
