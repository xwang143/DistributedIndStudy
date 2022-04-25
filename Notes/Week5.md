* Lec 9. Network Communication
  * Multithreading vs Distributed Systems
    * Passing message between threads is easy, since all threads running in the context of the same application all have shared memory space or they could pass data to each other.  
    * In distribute systems, we dont have the shared memory anymore, the only efficient way for our nodes to communiate is to use the network
  * TCP / IP Network Model (Compare to Postal Service)
    * Four layers of abstractions -- each layer is getting survice from the layer underneath it and communicate wuth the same layer in another machine/station using the relavent protocals
    * Layer 1. Data Link (**lowest**)
      * is concerned with all the details with interfacing with the hardware and in charge of physical delivery of data between two points(two different hosts or a host and a router) over a single link
      * in charge of 
        * encapsulation of the data
        * flow control
        * error detection
        * error correction
        * etc... 
      * **Ethernet Protocol**
        * wraps data packets into frames and uses the devices as MAC addresses to deliver those packets from one device to another device 
      * equivalent to postal service logistic department that takes care of the postal service planes, trucks, postman scahedules and make sure the letters moves from one place to another on their way to the destination
    * Layer 2. Internet
      * in charge of delivering data across multiple networks and routing the packets from the source computer to the destination computer
      * only takes care of delivering a packet from one computer to another, but it doesnt know which application process the packet is intended for, nor does it know which process sent it or the where the response should go to
      * **Internet Protocol**
        * each device in the network is assigned with an IP address 
      * IP address is equivalent to address of the building each recipient is located
    * Layer 3. Transport 
      * in charge of delivering message from end to end from the process on one machine to the process on another machine
      * each end point/socket identify itself by 16 bit port
        * the listening port is chosen ahead of time by the destination application but the source port is generated at the sending time 
        * Using the port once the packet arrives at the destination computer the os knows which process the packet belongs to
      * port number is equivalent to the apartment number
      * 2 primary protocols
        * **User Datagram Protocol (UDP) **
          * Connectionless
          * Best effort -Unreliable
          * Messages can be lost, duplicated and repordered
          * based on a unit called Datagram which is limited in size.  
          * UDP is preferred when the speed and simplicity outweighs reliability
          * Example
          * allows broadcasting with allows a single computer to send a message to all the computers in the local network without knowing anything about where the comnputers are
            * sending debug messages to the distributed logging service
            * real time data stream service such as video or audio
            * online gaming 
        * **Transmission Control Protocol (TCP, most common)**
          * Reliable - Guarantees data delivery as sent, without any losses
          * Connectiom between 2 points
            * connection needs to be created before data is sent 
            * shut down in the end
          * Works as a plain stream of bytes, not distinguishing which byte belongs to which message
            * however in our case we want to send command, request and messages between nodes in cluster
            * and parsing messages out of a stream of bytes is very hard
            * we need to know hwere the message starts, ends and how the data is formatted
            * for that purpose we have the final layer -- application 
    * Layer 4. Application
      * Many different protocals already defined for us for different purposes
        * FDP (File Transfer Protocal): transfering files through the web
        * SMTP (Simple Mail Transfer Protocol): sending the receiving emails
        * DNS (Domain Name System): Translating host names into IP addresses
        * HTTP (Hypertext Transfer Protocol): Transmitting Hypermedia documents, video, sound, images...      
* Lec 10. HTTP for Communication in Distributed Systems.  
  * HTTP Fundamentals
    * HTTP was initially designed to serve contents from the web server to the user web browsers
    * However in our course, we will focus on the use of http in the distributed systems fro communication between compute nodes
    * Every http transactions have two parts
      * request from client node to server node
      * response from server node back to the client node (can be empty).  
  * HTTP Request Structure.  
    * method
     * get
       * safe - only retrieval action with no side effects 
       * idempotent - performing the underlying operation N times is equivalent to performing it only once 
       * does not contain a message body
       * Use cases: health check, distributed data retieval
     * post 
       * contains a message body(payload)
       * may have side effects, operation more complex
       * useful in communication between nodes
    * Path
     * Relative Path...Query String
    * Protocol Version
     * HTTP/1.1
       * loads resources one after the other, so if one resource cannot be loaded it blocks all the other resources behind it
       * Disadvantages
         * Creating a new connection for every request is expensive
         * The number of outgoing connections a client can maintain is limited by 
           * Number of ports 
           * The OS   
     * HTTP/2   
       * split data into binary-code messages and numbering these messages so that the client knows which stram each binary message belongs to.   
    * Headers
      * key-values strings: Header-Name: Value1; Value2; Value3
      * some headers are used only in requests or only in responses, and some are used in both
      * Allow the recipient to take actions before reading the message body
        * Memory Allocation
        * Skipping / Forwarding (Proxying)
      * HTTP/1.1 header - plain text key value pairs that can be easily inspected by tools like WIreshark
      * HTTP/2 - the headers are compressed
        * saves on payload side
        * hard to inspect / debug
    *  Message Body
       * can contain anything we want
       * the server and client have to agree on how to parse the data
       * can contain complex data objects    
   * HTTP Response Structure
     * status code instead of path
       * 200 Success
       * 400 Client Errors
       * 600 Server Errors
     * status message instad of method 
        
     
  
        
