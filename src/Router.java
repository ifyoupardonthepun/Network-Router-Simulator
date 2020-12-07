// Shrey Shah
// ID: 112693183
// Shrey.Shah@stonybrook.edu
// Homework #4
// CSE214
// R.04 James Finn

import java.util.*;

public class Router extends LinkedList<Packet>{

    //default constructor
    public Router() {
    }

    /**
     * adds a packet to the end of the router queue
     *
     * @param p
     * the packet to be added
     */
    public void enqueue(Packet p) {
        super.add(p);
    }

    /**
     * Removes and returns the Packet at the front of the router buffer.
     *
     * @return
     *     Returns the Packet removed from the queue.
     */
    public Packet dequeue() {
        return super.remove();
    }
    /**
     * Finds the router with the most free buffer space (contains the least Packets),
     * and returns the index of the router. The indexes start at 1 rather than 0.
     * If all router buffers are full, an exception is thrown.
     *
     * @param routers
     * collection of routers to search through.
     *
     * @return
     *  the index of the router with the smallest queue.
     *
     * @throws CongestedException
     * no routers have space in their queues
     */
    public static int sendPacketTo(Collection<Router> routers) throws CongestedException{
        int counter = 1;
        int index = counter;
        int routerBuffers = 0;
        if (routers.size() == 0) {
            System.out.printf("%nNo Routers instantiated%n");
            return 0;
        }
        for (Router r: routers) {
            if (r.size() < routerBuffers) {
                routerBuffers = r.size();
                index = counter;
            }
            if (counter == 1){
                routerBuffers = r.size();
            }
            counter++;
        }
        if (routerBuffers == Simulator.maxBufferSize){
            throw new CongestedException();
        }
        return index;
    }
}