#!/bin/bash


# Configuration script for VM5 in CSE473 Lab 5

source ./vmhosts
sudo ifconfig data1 down
sudo ip link set data1 arp off
sudo ifconfig data1 up
ip=$(ifconfig data1 | grep 'inet addr:' | cut -d: -f2 | awk '{ print $1 }')
sudo ip addr del $ip dev data1
sudo ip addr add 192.168.4.2 dev data1

sudo ifconfig data1 -multicast
# use 'arp' to add arp entries for VM4 and VM6 
sudo arp -s $VM4_IP $VM4_MAC
sudo arp -s $VM6_IP $VM6_MAC
# use 'route' to add a net route for the whole network to go to Rtr.4
sudo route add -net 192.168.4.0 netmask 0.0.0.0 gw 192.168.4.1
# use 'sysctl' to turn ipv6 off on data1
sudo sysctl net.ipv6.conf.data1.disable_ipv6=1
