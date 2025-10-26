# Week 2, Part 1 - Remote Procedure Call Warm-up 
## Hands-on exercise to experience remote invocation and to contrast RMI with gRPC (in a later week). You’ll build and run a small calculator service.

---
## Get the code 
SSH -> `git clone git@github.com:mertrasna/distributed-systems.git`
HTTPS -> `git clone https://github.com/mertrasna/distributed-systems.git`
Or just download the zip

## How to Compile 

Open two terminals
Terminal A - compile & run the server
`cd part1/server`
`javac *.java`
`java Server`
Terminal B - compile & run the client
`cd part1/client`
`javac *.java`
`java Client`

On the first run, the calls to sum2 / product2 should fail with a serialization error related to Args.

