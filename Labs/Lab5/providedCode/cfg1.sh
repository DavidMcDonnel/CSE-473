#!/bin/bash


# Configuration script for VM1 in CSE473 Lab 5

source ./vmhosts

# use 'ifconfig' to take data1 down
sudo ifconfig data1 down
# use 'ip' to turn off arp on data1
sudo ip link set data1 arp off
# use 'ifconfig' to bring data1 back up, assign an IP address and turn multicast off
sudo ifconfig data1 up
ip=$(ifconfig data1 | grep 'inet addr:' | cut -d: -f2 | awk '{ print $1 }')
sudo ip addr del $ip dev data1
sudo ip addr add 192.168.2.1 dev data1
sudo ifconfig data1 -multicast
# use 'route' to add a host route on device data1 for Rtr.3 (VM7)
sudo route add -host 192.168.3.1 dev data1
# use 'route' to add a net route for the network of Rtr.4 
sudo route add -net 192.168.4.0 netmask 255.255.255.0 gw 192.168.3.1
# use 'arp' to add arp entries for VM2, VM3 and VM7
sudo arp -s $VM2_IP $VM2_MAC
sudo arp -s $VM3_IP $VM3_MAC
sudo arp -s $VM7_IP $VM7_MAC
# use 'sysctl' to turn IPV4 forwarding on to make this a router/forwarder
sudo sysctl net.ipv4.ip_forward=1
# use 'sysctl' to turn off sending of icmp redirects on data1
sudo sysctl net.ipv4.conf.data1.accept_redirects=0
# use 'sysctl' to turn ipv6 off on data1
sudo sysctl net.ipv6.conf.data1.disable_ipv6=1
# use 'iptables' to append a rule to the OUTPUT chain 
# This rule should select packets with protocol icmp and an icmp-type of redirect.
# The action to take on the selected packets should be DROP
sudo iptables -A OUTPUT -p icmp --icmp-type redirect -j DROP
# use 'iptables' to append a rule like the one above to the FORWARD chain
sudo iptables -A FORWARD -p icmp --icmp-type redirect -j DROP
