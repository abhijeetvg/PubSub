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



