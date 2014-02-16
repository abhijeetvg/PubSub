PubSub
======

## Overview

This is publish subscribe system (PubSub) that uses two forms of communication: basic messages using UDP and Java RMI for publishing and subsscribing. The PubSub system will allow the publishing of simple formatted “articles”. 

## Authors

* [Abhijeet Gaikwad](https://github.com/abhijeetvg)
* [Prashant Chaudhary](https://github.com/prashantc29)

## Building Project

Steps:

* Install pom: `mvn install`
* Compile: `mvn compile`
* Create package: `mvn package`
  * This will create tar distribution in `dist/target/`

* Generate eclipse project files: `mvn eclipse:eclipse`
  * This project can be now imported in eclipse.

## Starting RMI Registry

* To start RMI registry on server host you need to set the `CLASSPATH` first. 
* The classpath should include the path of the `jar` that contains the `Communicate` interface and the `PubSubService\_Stub`. This is the `common` and `server` jar.
* Eg. `export CLASSPATH=export CLASSPATH="/home/prashant/workspace/PubSub/common/target/common-1.0-SNAPSHOT.jar:/home/prashant/workspace/PubSub/server/target/server-1.0-SNAPSHOT.jar"`
* Run `rmiregistry` now.

## Setting up Security policy

* Create a security policy file.
* Eg security.policy. Contents of this file:
`grant {
// Allow everything for now
permission java.security.AllPermission;
};`

## Starting client (on Linux)

* Go to the pubsub-1.0-SNAPSHOT/: `cd  dist/target/pubsub-1.0-SNAPSHOT/`
* Run: `java -Djava.security.policy="<SECURITY POLICY FILE>" -cp ./client-1.0-SNAPSHOT.jar:./server-1.0-SNAPSHOT.jar:./common-1.0-SNAPSHOT.jar edu.umn.pubsub.client.Client <IP ADDRESS OF THE SERVER>`
* This will give you the client command line prompt.

## Starting server (on Linux)

* Go to the pubsub-1.0-SNAPSHOT/: `cd  dist/target/pubsub-1.0-SNAPSHOT/`
* Run: `java -Djava.security.policy="<SECURITY POLICY FILE>" -cp ./client-1.0-SNAPSHOT.jar:./server-1.0-SNAPSHOT.jar:./common-1.0-SNAPSHOT.jar edu.umn.pubsub.server.Server <IP ADDRESS OF THE SERVER>`
