#!/bin/bash


if [ $# -eq 2 ]
then
   ONLUSER=$1
   PASSWORD=$2
else
  echo "Usage: $0 <onl username> <vm_password>"
  echo "Example: $0 johnsmith ftdly"
  exit 0
fi

# Sample sshpass command:
# sshpass -p zfsvw ssh -n ${ONLUSER}@${VMsmall1} hostname

source ~/.topology

VMHOSTS="vmhosts"
CFG="cfg1.sh"
sshpass -p ${PASSWORD} scp $VMHOSTS $CFG ${ONLUSER}@${VMsmall1}:~/

CFG="cfg2.sh"
sshpass -p ${PASSWORD} scp $VMHOSTS $CFG ${ONLUSER}@${VMsmall2}:~/

CFG="cfg3.sh"
sshpass -p ${PASSWORD} scp $VMHOSTS $CFG ${ONLUSER}@${VMsmall3}:~/

CFG="cfg4.sh"
sshpass -p ${PASSWORD} scp $VMHOSTS $CFG ${ONLUSER}@${VMsmall4}:~/

CFG="cfg5.sh"
sshpass -p ${PASSWORD} scp $VMHOSTS $CFG ${ONLUSER}@${VMsmall5}:~/

CFG="cfg6.sh"
sshpass -p ${PASSWORD} scp $VMHOSTS $CFG ${ONLUSER}@${VMsmall6}:~/

CFG="cfg7.sh"
sshpass -p ${PASSWORD} scp $VMHOSTS $CFG ${ONLUSER}@${VMsmall7}:~/
