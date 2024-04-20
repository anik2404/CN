import java.util.Scanner;

public class IPAddressAnalyzer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter an IPv4 address: ");
        String ipAddress = scanner.nextLine();

        int[] octets = parseIpAddress(ipAddress);

        if (octets == null) {
            System.out.println("Invalid IPv4 address.");
            return;
        }

        String ipClass = determineClass(octets[0]);
        int networkId = calculateNetworkId(octets, ipClass);
        int hostId = calculateHostId(octets);

        System.out.println("IP address: " + ipAddress);
        System.out.println("Class: " + ipClass);
        System.out.println("Network ID: " + networkId);
        System.out.println("Host ID: " + hostId);
    }

    private static int[] parseIpAddress(String ipAddress) {
        String[] parts = ipAddress.split("\\.");
        if (parts.length != 4) {
            return null;
        }

        int[] octets = new int[4];
        for (int i = 0; i < 4; i++) {
            try {
                octets[i] = Integer.parseInt(parts[i]);
                if (octets[i] < 0 || octets[i] > 255) {
                    return null;
                }
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return octets;
    }

    private static String determineClass(int firstOctet) {
        if (firstOctet >= 0 && firstOctet <= 127) {
            return "Class A";
        } else if (firstOctet >= 128 && firstOctet <= 191) {
            return "Class B";
        } else if (firstOctet >= 192 && firstOctet <= 223) {
            return "Class C";
        } else {
            return "Invalid"; // Class D and E are not handled here
        }
    }

    private static int calculateNetworkId(int[] octets, String ipClass) {
        int networkId = 0;
        switch (ipClass) {
            case "Class A":
                networkId = (octets[0] << 24) | (octets[1] << 16);
                break;
            case "Class B":
                networkId = (octets[0] << 24) | (octets[1] << 16) | (octets[2] << 8);
                break;
            case "Class C":
                networkId = (octets[0] << 24) | (octets[1] << 16) | (octets[2] << 8) | octets[3];
                break;
        }
        return networkId;
    }

    private static int calculateHostId(int[] octets) {
        return (octets[0] << 24) | (octets[1] << 16) | (octets[2] << 8) | octets[3];
    }
}
