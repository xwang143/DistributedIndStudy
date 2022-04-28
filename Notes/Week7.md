* Lec13. Message Delivery Semantics in Distributed Systems
  * Server Failure Scenarios.  
    * Unlike a simple method invocation on a single machine
    * Many things can happen after the client sent a request, that are outside of the client's control
    * Network Failure: right before the server respond to the client, the network communication breaks
    * Server Crash right after the execution
    * Server Crash before execution
    * Server crash before ereceiving the request
  * At Most Once Semanntics
    * The client send a request to the server only once
    * if the server
      * never recieves a response
      * Or crashes right before it sends back the response
    * the client will never rediliver the request to the server   
    * Worst case: the action never performed by the server
    * Best case: the action is perfomed only once
    * Use Caases:
      * Messages to a logging or monitoring service
      * Sending promotional emails/notificatioins to users
  * At least Once Semantics
    * works only for idempotent operations
      * Idempotent operations 
        * examples: read the first line in a file...Update the status of a user to "active"...Delete a record by ID 
        * performing those operations multiple times will yield the same result
      * Non-Idempotent operations
        * examples: appending a line to a file...Incrementing a metric in a database...Withdrawing money from a user's account
        * How to deal
          * Orders service generate sequence_key for every billing service, that **sequence_key**is sent to every request together with a boolean value telling blling service whether the current request **is_retry** or not   
          * the sequence_key number will be stored in a database as one transaction end with an actual update with the **new_user_balance**
          * if the request was send to the billing service but the billing service didnt respond, then the order service will send the retry request with the same sequencee key as well as the is_retry filled with true
          * When the billing service receives this meesage, it will first check which sequence key is recorded in the DB from the last operation. 
            * If the sequence key recorded in the DB is different from the one it just received, that means the billing service didnt secussfully perform the first attempt of this request, the billing service will perform the account update and save info in the DB
            * If same, doesnt need to anything but simply respond to the orders service to acknowledge their request
       
