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
      * clear the screen and run **curl --request POST -v --header "X-Debug: true"  --data '50,100' localhost:8081/task**
 
