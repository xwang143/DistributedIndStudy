* Lec3. Zookeeper Client Threading Model & Java API
  * Threading Model. 
    * Application's start code in main method is executed on the **main thread**    
    * When Zookeeper object is created, two additional threads are created
      * Event Thread   
        * handles all the zookeeper clients status changing events). 
          * Connection (KeeperState.SyncConnected)
          * Disconnection (KeeperState.Disconnected)
        * custom znode Watchers and Triggers we subscribe to
        * events are excuted on Event Thread in order of their arrival from the zookeeper 
      * IO Thread (we don't directly interract with it)
        * handles all the network communication with Zookeeper servers 
        * handles Zookeeper requests and responses
        * responds to pings
        * session management
        * session timeouts
  * Java API (intellij)
    * **pom.xml** is the configuration file for maven project.
      * Add <dependencies> and <build>
    * **LeaderElection class**
      * To be a watcher, a class needs to implements Watcher interface and include process method
    * Create **log4j.properties** under main/resources
* Lec 4. Leader Election Implementation 
  * **volunteerForLeadership** method added
  * **electLeader** method added
  * add **maven-assembly-plugin** points to **mainClass** into **.xml**
  * run *mvn clean package* which will build & package our code with its dependencies in a single executable jar file
  * Open 4 windows side by side and run *java -jar <jar_file_name>*
