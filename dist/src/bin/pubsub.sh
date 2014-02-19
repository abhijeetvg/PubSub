#!/bin/bash

exec_path=`follow $0`
bin=`dirname ${exec_path}`
bin=`cd ${bin} && pwd`

PROJ_HOME=${bin}/..
policy_file=${bin}/rmi_security.policy

for f in ${PROJ_HOME}/*.jar; do
  CLASSPATH=$f:$CLASSPATH;
done

export CLASSPATH;

if [ $1 == "server" ]
  echo Starting Server
  java -D'java.security.policy=${policy_file}' -cp $CLASSPATH edu.umn.pubsub.server.Server "$@"
elif [ $1 == "client" ]
  echo Starting Client
  java -D'java.security.policy=${policy_file}' -cp $CLASSPATH edu.umn.pubsub.server.Server "$@"
else
  echo Usage:
  echo ./pubsub.sh <client | server> <client_options | server_options>
  echo
  echo client_options: <rmi_server_ip> <rmi_server_port> <udp_host> <udp_port>
  echo server_options: <rmi_server_ip> <rmi_server_port>
  echo
  echo E.g.: ./pubsub.sh server 0.0.0.0 1099
fi