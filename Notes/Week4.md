* Lec7. Intro to Service Registry & Service Discovery
  * Service Discovery
    * What is it 
      * When a group of computers start up, the only device they're aware of is themselves, even if they're all connected to the same network
      * To form a logical cluster, different nodes need to find out about each other and commucate
    * static Configuration 
      * finds all nodes' addresses ahead of time and put them all in a single configuration file and distribute it to all nodes before we lauch the application. 
      * Limitations
        * if one node becomes unavailable or changes its address, the other nodes will still try to use the old address and will be never discover the new address of the node
        * if we want to expand the cluster, we have to re-generate the file and distribute the new file to all the nodes
    * Dynamic Configuration
      * Some companies still manage their clusters in a similar way, with some degree of automation
      * Everytime a new node is added - one central configuration is updated
      * An automated configuration management tool like Chef or Puppet, can pick up the configuration and distribute it among the nodes in the cluster
      * More dynamic but still involves a human to update the configuration
  * Service Registry with Zookeeper
    * start with a permanent znode called **/service_registry**, every node that joined the cluster will add an ephemeral sequential znode under the  **/service_registry**
    * each node puts its own address inside its znode
  * Service Discovery with Zookeeper
    * each node register a watcher on the **/service_registry** znode using the *getchildren()* method
    * then when it wants to read/use a particular node's address, the node needs to call *getData()* method to read the address data stored in the znode。
    * if there's any change in the cluster at any point, the node is going to get notified immediately with the *NodeChildrenChanged* event  
    * Leader / Workers Architecture
      * Workers dont need to know each other at all nor does the leader needs to register itself in the registry   
      * Workers will register themselves with the cluster
      * Only the leader will register for notifications
      * Leader will know about the state of the cluster at all times and diestribute the work accordingly
      * If a leader dies, the new leader will remove itself from the service regsitry and continue distributing the work
* Lec8. Service Registry & Discovery - Implementation
  * Storing Configuration / Address
    * We can store any configuration in the znode
    * We need to store the minimum data to allow communication within the cluster
    * The address we will store will be in the form of:
      * Host Name: Port example: http://127.0.0.1:8080*
    
    
    
    
