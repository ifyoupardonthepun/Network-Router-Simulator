// Shrey Shah
// ID: 112693183
// Shrey.Shah@stonybrook.edu
// Homework #4
// CSE214
// R.04 James Finn

public class Packet {
    public static int packetCount = 0;
    public int id;
    public int packetSize;
    public int timeArrive;
    public int timeToDest;


    /**
     *
     * @param size
     * the size of the packet.
     *
     * @param time
     * the arrival time of the packet.
     *
     */
    public Packet(int size, int time) {
        packetCount++;
        setId(packetCount);
        setPacketSize(size);
        setTimeArrive(time);
        setTimeToDest(packetSize/100);
    }
    /**
     * Returns the packetCount of the Packet.
     *
     * @return
     *     Returns the total number of Packets.
     */
    public static int getPacketCount() {
        return packetCount;
    }

    /**
     * Sets the PacketCount of Packet.
     *
     * @param count
     * The total number of packets.
     */
    public static void setPacketCount(int count) {
        packetCount = count;
    }

    /**
     * Returns the id of the Packet.
     *
     * @return
     * Returns the id of the packet.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of Packet.
     *
     * @param i
     * The ID of the packet to set.
     */
    public void setId(int i) {
        id = i;
    }

    /**
     * Returns the packetSize of the Packet.
     *
     * @return
     * Returns the size of the packet.
     */
    public int getPacketSize() {
        return packetSize;
    }

    /**
     * Sets the packetSize of Packet.
     *
     * @param s
     *     The size of the packet to set.
     */
    public void setPacketSize(int s) {
        packetSize = s;
    }

    /**
     * Returns the timeArrive of the Packet.
     *
     * @return
     * Returns the arrival time of the packet.
     */
    public int getTimeArrive() {
        return timeArrive;
    }

    /**
     * Sets the timeArrive of Packet.
     *
     * @param t
     *     The arrival time of the packet to set.
     */
    public void setTimeArrive(int t) {
        timeArrive = t;
    }

    /**
     * Returns the timeToDest of the Packet.
     *
     * @return
     *     Returns the time to the destination router of the packet.
     */
    public int getTimeToDest() {
        return timeToDest;
    }

    /**
     * Sets the timeToDest of Packet.
     *
     * @param t
     * The time to the destination router of the packet to set.
     */
    public void setTimeToDest(int t) {
        timeToDest = t;
    }

    /**
     * Returns a String representation of this Packet.
     *
     * @return
     * Returns the String representation of the packet.
     */
    public String toString() {
        return String.format("[%d, %d, %d]", id, timeArrive, timeToDest);
    }
}