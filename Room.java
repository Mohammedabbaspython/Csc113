import java.io.Serializable;

public class Room implements Billable, Serializable {
    private double nightPrice;
    private String type;
    private int roomNumber;
    private boolean isAvailable;

    // constructor
    public Room(String type, double nightPrice, int roomNumber) {
        this.type = type;
        this.nightPrice = nightPrice;
        this.roomNumber = roomNumber;
        this.isAvailable = true;
    }

    // marks room as unavailable
    public boolean bookRoom() {
        if (!isAvailable) {
            return false;
        }

        isAvailable = false;
        return true;
    }

    // marks room as available
    public boolean releaseRoom() {
        if (isAvailable) {
            return false;
        }

        isAvailable = true;
        return true;
    }

    // getters
    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return type;
    }

    // returns the price of the night
    public double getPrice() {
        return nightPrice;
    }

    // getter
    public boolean getIsAvailable() {
        return isAvailable;
    }

    public String toString() {
        return "Room Number: " + roomNumber +
                ", Type: " + type +
                ", Night Price: " + nightPrice +
                ", Available: " + isAvailable;
    }
}