#!/bin/bash


# Configuration script for VM2 in CSE473 Lab 5

source ./vmhosts

# use ifconfig to take data1 down
sudo ifconfig data1 down
# use 'ip' to turn off arp on data1
sudo ip link set data1 arp off
# use 'ifconfig' to bring data1 back up, assign an IP address and turn multicast off
sudo ifconfig data1 up
ip=$(ifconfig data1 | grep 'inet addr:' | cut -d: -f2 | awk '{ print $1 }')
sudo ip addr del $ip dev data1
sudo ip addr add 192.168.2.2 dev data1
sudo ifconfig data1 -multicast
# use 'arp' to add arp entries for VM1 and VM3 
sudo arp -s $VM1_IP $VM1_MAC
sudo arp -s $VM3_IP $VM3_MAC
# use 'route' to add a net route for the whole network to go to Rtr.2
sudo route add -net 192.168.2.0 netmask 0.0.0.0 gw 192.168.2.1
# use 'sysctl' to turn ipv6 off on data1
sudo sysctl net.ipv6.conf.data1.disable_ipv6=1
