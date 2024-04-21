import ipaddress
def calculate_subnet_info(ip_address,subnet):
    try:
        network=ipaddress.ip_interface(f"{ip_address}/{subnet}")
    except(valueError,ipaddress.AddressValueError)as e:
        raise ValueError(f"Invalid IP Address or Subnet:{e}")
    subnet_mask=str(network.netmask)
    subnetwork_address=str(network.network)
    return subnet_mask,subnetwork_address
#Driver Code
ip_address=input("Enter an IP Address:")
subnet=int(input("Enter the subnet:"))
try:
    subnet_mask,subnetwork_address=calculate_subnet_info(ip_address,subnet)
    print(f"Subnet Mask:{subnet_mask}")
    print(f"Subnetwork Address:{subnetwork_address}")
except ValueError as e:
    printf(f"Error:{e}")