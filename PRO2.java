import java.util.Scanner;

public class CIDRAnalyzer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a CIDR address (e.g., 192.168.1.0/24): ");
        String cidrAddress = scanner.nextLine();

        int[] ipAddress = parseCIDRAddress(cidrAddress);

        if (ipAddress == null) {
            System.out.println("Invalid CIDR address.");
            return;
        }

        int cidrPrefix = getCidrPrefix(cidrAddress);
        if (cidrPrefix == -1) {
            System.out.println("Invalid CIDR address.");
            return;
        }

        int networkId = calculateNetworkId(ipAddress, cidrPrefix);
        int hostId = calculateHostId(ipAddress, cidrPrefix);

        System.out.println("CIDR address: " + cidrAddress);
        System.out.println("Network ID: " + networkId);
        System.out.println("Host ID: " + hostId);
    }

    private static int[] parseCIDRAddress(String cidrAddress) {
        String[] parts = cidrAddress.split("/");
        if (parts.length != 2) {
            return null;
        }

        try {
            int[] ipOctets = parseIpAddress(parts[0]);
            int prefix = Integer.parseInt(parts[1]);
            if (ipOctets == null || prefix < 0 || prefix > 32) {
                return null;
            }
            return ipOctets;
        } catch (NumberFormatException e) {
            return null;
        }
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

    private static int getCidrPrefix(String cidrAddress) {
        String[] parts = cidrAddress.split("/");
        if (parts.length != 2) {
            return -1;
        }

        try {
            return Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static int calculateNetworkId(int[] ipAddress, int cidrPrefix) {
        int networkId = 0;
        for (int i = 0; i < 4; i++) {
            int mask = (cidrPrefix > 8 * i) ? 0xFF : ((1 << (8 - (cidrPrefix % 8))) - 1); // Create subnet mask based on prefix
            networkId |= (ipAddress[i] & mask);
        }
        return networkId;
    }

    private static int calculateHostId(int[] ipAddress, int cidrPrefix) {
        int hostId = 0;
        for (int i = 0; i < 4; i++) {
            int mask = (cidrPrefix > 8 * i) ? 0 : ((1 << (8 - (cidrPrefix % 8))) - 1); // Invert subnet mask for host bits
            hostId |= (ipAddress[i] & mask);
        }
        return hostId;
    }
}
