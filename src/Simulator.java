// Shrey Shah
// ID: 112693183
// Shrey.Shah@stonybrook.edu
// Homework #4
// CSE214
// R.04 James Finn

import java.util.*;

public class Simulator {
    public static final int MAX_PACKETS = 3;
    static int maxBufferSize;
    static int numIntRouters;

// beginning of the program
    public static void main(String[] args) {
        Router dispatcher = new Router();
        Collection<Router> routers = new ArrayList<>();
        Scanner stdin = new Scanner(System.in);

        System.out.print("Would you like to begin a simulation? (yes/no): ");
        String cont = stdin.nextLine();
        while (!cont.equalsIgnoreCase("NO")) {
            System.out.println("Starting simulator...");
            System.out.print("Please input the number of Intermediate routers: ");
            numIntRouters = stdin.nextInt();
            for (int i = 0; i < numIntRouters; i++) {
                routers.add(new Router());
            }
                System.out.print("Please input the arrival probability of a packet: ");
                double arrivalProb = stdin.nextDouble();
                System.out.print("Please input the maximum buffer size of a router: ");
                maxBufferSize = stdin.nextInt();
                System.out.print("Please input the minimum size of a packet: ");
                int minPacketSize = stdin.nextInt();
                System.out.print("Please input the maximum size of a packet: ");
                int maxPacketSize = stdin.nextInt();
                System.out.print("Please input the bandwidth size: ");
                int bandwidth = stdin.nextInt();
                System.out.print("Please input the simulation duration: ");
                int duration = stdin.nextInt();
                Packet.setPacketCount(0);
                simulate(dispatcher, routers, arrivalProb, minPacketSize, maxPacketSize, bandwidth, duration);
                stdin.nextLine();
                System.out.print("Do you want to try another simulation? (yes/no): ");
                cont = stdin.nextLine();
            }
        System.out.print("Program terminating successfully...");
        stdin.close();
    }

    /**
     * helper method that can generate a random number between minVal and maxVal,
     * inclusively.
     * @param minVal
     * the minimum value
     *
     * @param maxVal
     * the maximum value
     *
     * @return
     * Return that randomly generated number.
     *
     */
    private static int randInt(int minVal, int maxVal) {
        return (int) (Math.random() * (maxVal - minVal + 1) + minVal);
    }

    /**
     *
     * @param dispatcher
     * Level 1 router
     *
     * @param routers
     * Level 2 routers
     *
     * @param arrivalProb
     * the probability of a new packet arriving at the Dispatcher.
     *
     * @param minPacketSize
     * the minimum size of a Packet
     *
     * @param maxPacketSize
     * the maximum size of a Packet
     *
     * @param bandwidth
     * the maximum number of Packets the Destination router can accept at a given simulation unit
     *
     * @param duration
     * the number of simulation units
     *
     */
    public static void simulate(Router dispatcher, Collection<Router> routers, double arrivalProb, int minPacketSize, int maxPacketSize, int bandwidth, int duration) {
        int packetsDropped = 0;
        int totalServiceTime = 0;
        int totalPacketsArrived = 0;

        for (int time = 1; time <= duration; time++) {
            System.out.printf("%nTime: %d %n", time);
            for (int i = 0; i < MAX_PACKETS; i++) {
                if (Math.random() < arrivalProb) {
                    Packet.setPacketCount(Packet.getPacketCount() + 1);
                    Packet arrivedPacket = new Packet(randInt(minPacketSize, maxPacketSize), time);
                    dispatcher.add(arrivedPacket);
                    System.out.printf("Packet %d arrives at dispatcher with size %d.%n", arrivedPacket.getId(), arrivedPacket.getPacketSize());
                }
            }
            int index = 1;
            while (!(dispatcher.isEmpty())) {
                try {
                    for (Router r : routers) {
                        if (!dispatcher.isEmpty()) {
                            if (Router.sendPacketTo(routers) == index) {
                                assert dispatcher.peek() != null;
                                System.out.printf("Packet %d sent to Router %d.%n", dispatcher.peek().getId(), index);
                                r.enqueue(dispatcher.dequeue());
                            }
                        }
                        index++;
                    }
                    index = 1;
                } catch (CongestedException e) {
                    packetsDropped++;
                    System.out.printf("Network is congested. Packet %d is dropped.%n", dispatcher.dequeue().getId());
                }
            }
            int band = 0;
            for (Router r : routers) {
                Packet processPacket = r.peek();
                if (processPacket != null) {
                    if (processPacket.getTimeArrive() != time ) {
                        if (!(band == bandwidth) && processPacket.getTimeArrive() == 1) {
                            processPacket.setTimeToDest(processPacket.getTimeToDest() - 1);
                        }
                    }
                    if (processPacket.getTimeToDest() <= 0) {
                        if (band != bandwidth) {
                            System.out.printf("Packet %d has successfully reached its destination: +%d %n",
                                    processPacket.getId(), (time - r.dequeue().getTimeArrive()));
                            totalServiceTime += (time - processPacket.getTimeArrive());
                            totalPacketsArrived++;
                            band++;
                        }else
                            packetsDropped++;
                    }
                }
            }
            index = 1;
            for (Router r : routers) {
                System.out.println("R" + index + ":" + r.toString());
                index++;
            }
        }
        double average = (double) totalServiceTime / totalPacketsArrived;
        System.out.printf("%nSimulation ending...%n");
        System.out.println("Total service time: " + totalServiceTime);
        System.out.println("Total packets served: " + totalPacketsArrived);
        System.out.printf("Average service time per packet: %.2f%n", average);
        System.out.println("Total packets dropped: " + packetsDropped);
    }
}