import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

// To be a watcher, a class needs to implements Watcher interface and include process method
public class LeaderElection implements Watcher {
    private static final String ZOOKEEPER_ADDRESS= "localhost:2181";
    private static final int SESSION_TIMEOUT = 3000;
    private static final String ELECTION_NAMESPACE = "/election";
    private ZooKeeper zooKeeper;
    private String currentZnodeName;

    public static void main(String [] args) throws IOException, InterruptedException, KeeperException {
        LeaderElection leaderElection = new LeaderElection();
        System.out.println(leaderElection);
        leaderElection.connectToZookeeper();
        leaderElection.volunteerForLeadership();
        leaderElection.electLeader();
        leaderElection.run();
        leaderElection.close();
        System.out.println(("Disconnected from Zookeeper, exiting application"));
    }

    public void volunteerForLeadership() throws KeeperException, InterruptedException {
        String znodePrefix = ELECTION_NAMESPACE + "/c_";
        String znodeFullPath = zooKeeper.create(znodePrefix, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        System.out.println("znode name " + znodeFullPath);
        this.currentZnodeName = znodeFullPath.replace("/election/", "");
    }

    public void electLeader() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren(ELECTION_NAMESPACE, false);

        Collections.sort(children);
        String smallestChild = children.get(0);

        if (smallestChild.equals(currentZnodeName)) {
            System.out.println("I am the leader");
            return;
        }

        System.out.println("I am not the leader, " + smallestChild + " is the leader");
    }

    public void connectToZookeeper() throws IOException {
        // register an event handler and pass to the zookeeper as a watcher project
        this.zooKeeper = new ZooKeeper(ZOOKEEPER_ADDRESS, SESSION_TIMEOUT, this);
        System.out.println("Created zookeeper object");
    }

    public void run() throws InterruptedException{
        synchronized ( zooKeeper){
            zooKeeper.wait();
        }
    }

    public void close() throws InterruptedException{
        zooKeeper.close();
    }

    // This method will be called by the zookeeper library on a separate thread whenever there's a new event coming from
    // the zookeeper server
    @Override
    public void process(WatchedEvent event){
        switch(event.getType()){
            // general zookeeper connection events dont have any type
            case None:
                if (event.getState() == Event.KeeperState.SyncConnected){
                    System.out.println("Successfully connected to Zookeeper");
                }else{
                    synchronized (zooKeeper){
                        System.out.println("Disconnected from Zookeeper Event");
                        zooKeeper.notifyAll();
                    }
                }
        }
    }
}
