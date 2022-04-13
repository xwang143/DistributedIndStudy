* Lec 5. Waters, Triggers and Intro to Failure Detection
  * Watchers and Triggers
    * watcher is an object registered with zookeeper to get a notification event when a change happens  
    * we can register a watcher when we call the methods
      * public ZooKeeper(String connectString, int sessionTimeout, Watcher watcher) - Also takes a watcher
      * *getChildren(..., watcher)* - Get notified when the list of a znode's children changes
      * *getData(znodePath, watcher)* - Get notified if a znode's data gets modified
      * *exists(znodePath, watcher)* - Get notified if a znode gets deleted or created
      * the last 3 are different from the first as in **Watchers registered with these three methods are one-time triggers**
      * **if we want to get future notifications, we need to register the watcher again**
    * Demo
      * Add *watchTargetZnode* 
      * Update *process*
      * Start the application and launch zookeeper client **./zkCli.sh**
      * **ls /** gives us [election, zookeeper] at this moment since we haven't created any target znode
      * **create /target_znode "some test data" **
      * **set /target_znode "some new data"**
      * **create /target_znode/child_znode " "**
      * **deleteall /target_znode** to remove the target znode and all its children
  * Failure Detection with Zookeeper
    * Once the leader election completed, all nodes that are not the leaders can start watching the leader
    * if the leader dies and its znode gets deleted, then all the nodes in the cluster will get modified and then they can start their election of new leader
    * Works but suffer from **the Herd Effect**
      * When a large number of nodes are waiting for an event to occur
      * When the event occurs they all get notified and all wake up
      * Even though they call wake up trying to accomplish sth only one node can "succeed"
      * Can negatively impact the performance and can completely freeze the cluster
  * Leader Re-election Algorithm
    * After the initial leader election, each node is only going to watch the znode that comes right before it in the sequence of candidate znodes.
    * This way if the leader dies, the only node its going to be notified is its immediate successor
* Lec 6. Leader Reelection Implementation
  * Implementation:  
    * *reelectLeader* while loop
    * *NodeDeleted* case in *process* method
  * Inject Failures and test our cluster
    * start four terminals
    * run **java -jar leader.election-1.0-SNAPSHOT-jar-with-dependencies.jar** on each terminal one by one
    * kill the process on the first terminal, notice the second terminal takes the leader role
    * We do this one by one until only the 4th terminal isnt killed yet
    * If we restart the 1st terminal it will be watching the 4th terminal
   
      
    
     
