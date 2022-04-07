* Lec1. Intro to Cluster Coordination & Theory of Leader election.  
    * Terminology. 
        * Node: a **process** running on a dedicated machine. Edge between two nodes => communication between two peocesses
        * Cluster: **collection of computers/nodes** connected to each other. Nodes in the same cluster => working on same task (running same code)
    * How to Distribute tasks among nodes? --**Apache ZooKeeper**
        * Why it
            * is a distributed system itself 
            * typically runs in a cluster of an odd number of nodes (>=3)
            * Redendancy => lose one node but system still fully functional
        * Big Pic
            * Each node communicate with **Zookeeper Cluster** (the central server) instead of with each other
            * Tree + file System
                * each Znode is a hybrid between a file(can store any data inside) and a directory(can have children znodes)
                    * Persistent Znode: persists between sessions
                    * Ephemeral Znode: deleted when the session ends
        * 3 step leader-election distributed algorithm
            * Step 1. Every node that connects to Zookeeper volunteers to become a leader. Each node submitts its candidacy by adding a znode that represents itself under the election parent. (**Since zookeeper maintains the global order, it can name znode by its order of addition**)
            * Step 2. After each node finishes creating its znode, it will query the current children of the election parent. (Notice because of the order that zookeeper provides us, each node when querying the children of its election parent is guaranteed to see all the znodes created prior to its znode creation)
            * Step 3. If the znode that the current node created is the smallest number, it knows it's the leader. For whatever nodes that's not the leader, it's now waiting for instructions from the elected leader
            * Need to consider for failure. 
* Lec2. Zookeeper Server and Client.  
    * Clarification
      * Development - typically done on our local computer so that we can code, debug and test in the comfort of our IDE
      * QA on production like cluster
      * Deployment on the production env on the cloud
   * Setup
      * Create **logs** folder inside apache-zookeeper-3.6.3-bin
      * Rename **zoo_sample.cfg** to **zoo.cfg** under apache-zookeeper-3.6.3-bin/cnf where later during runtime zookeeper will look for in order to run as a stand-alone server
      * Modify **zoo.cfg**'s dataDir to point to **logs**. 
   * Start zookeeper server
      * navigate to **bin**
      * ./zkServer.sh start
   * Cmd line client 
      * navigate to **bin**
      * ./zkCli.sh 
      * Zookeeper Client makes znodes appear like files on our computer
   
           
