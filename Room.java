public class Room implements Billable {
    private double nightPrice;
    private String type;
    private int roomNumber;
    private boolean isAvailable;

    public Room(String type, double nightPrice, int roomNumber) {
        this.type = type;
        this.nightPrice = nightPrice;
        this.roomNumber = roomNumber;
        this.isAvailable = true;
    }

    public boolean bookRoom() {
        if (!isAvailable) {
            return false;
        }

        isAvailable = false;
        return true;
    }

    public boolean releaseRoom() {
        if (isAvailable) {
            return false;
        }

        isAvailable = true;
        return true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return type;
    }

    @Override
    public double getPrice() {
        return nightPrice;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber +
                ", Type: " + type +
                ", Night Price: " + nightPrice +
                ", Available: " + isAvailable;
    }
}