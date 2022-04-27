* Lec 11. HTTP Server - Implementation&Custom Headers
  * Build Http server
    * two endpoints 
      * /status - Handles Get requests
        * reports the health of our application
      * /task - Handles Post requests  
        * calculates a product of a list of integers
        * send the results back to the client 
    * [implementation](https://github.com/xwang143/httpserver/blob/main/src/main/java/WebServer.java)
  * cURL tool for sending HTTP request
    * Using cURL we can test our HTTP server
    * Has many features and arguments
      * --request HTTP_METHOD
      * --header HEADER_LINE
      * --verbose(-v)
      * --data SOME_DATA
      * HTTP server address
    * How to run
      * open one terminal and run **java -jar target/http.server-1.0-SNAPSHOT-jar-with-dependencies.jar 8081** to start the server
      * open the second terminal and run **curl --request GET -v localhost:8081/something**
      * clear the screen and run **curl --request GET -v localhost:8081/status**
      * clear the screen and run **curl --request POST -v --data '50,100' localhost:8081/task**
      * clear the screen and run **curl --request POST -v --header "X-Test: true"  --data '50,100' localhost:8081/task**
      * clear the screen and run **curl --request POST -v --header "X-Debug: true"  --data '50,100' localhost:8081/task**。 


* Lec 12. Distributed Systems & Cloud Computing with Java.    
  * Build HTTP Client
    * HTTP Client 
      * We will use the built HTTP Client implementation which was added in JDK 11
      * THere are many other third party implementations for both HTTP client and server
    * Key Features for Performance
      * Support for sending HTTP requests asynchronously
        * Synchronous mode
          * once we send request to a serfver, our thread stays blocked until we either get response or an error from the server
          * and we can send the next request to the next server only the previous transaction is completed
          * This makes writing codes easy but we lose the abilioty to perform multiple tasks in parallel by different nodes
        * Asynchronous mode
          * we can send request to the server without suspending the thread waiting for its response
          * once we are done sending out all the requests, we wait until all the responses come back so we can aggregate them
          * *CompletableFuture <String> responseFuture1 = client.sendAsync(request1, ...) : String response1 = responseFuture1.join();*
      * Maintaining a connection pool to all the downstream HTTP servers     
          * if we are sending request to the same server over and over again, we need to make sure that our http client does not close the connection immediately after the response comes back from the server     
          * Otherwise we will pay unnecessary cost of establishing and closing the connection for every http transaction
          * if HTTP/2 is enabled on both HTTP Server and CLient, connection pooling is enabled by default
          * if one of the peers does not support HTTP/2, we need to make sure its enabled by default or enable it explicitly
          * JDK built-in HTTP Client supports
            * HTTP/2 and 
            * HTTP 1.1 connection pooling by default
          * In most 3rd party HTTP clients we need to enable connection pooling by setting "Keep Alive" to true
          * JDK Built-in HTTP Server deos NOT support HTTP/2(as of JDK11)
   * Send tasks from an HTTP client to multiple HTTP server workers
     * [implementation](https://github.com/xwang143/httpclient)
     * start the server on two ports 
       * *java -jar target/http.server-1.0-SNAPSHOT-jar-with-dependencies.jar 8081* and *java -jar target/http.server-1.0-SNAPSHOT-jar-with-dependencies.jar 8082*
     * start the client
       * *java -jar target/http.client-1.0-SNAPSHOT-jar-with-dependencies.jar *
 
 
          

 


 
