import java.io.Serializable;

public class Booking implements Billable, Serializable {

    private int bookingId;
    private Guest guest;
    private Room room;
    private int numberOfNights;
    private boolean isPaid;
    private boolean isCheckedOut;
    private Employee bookedBy;

    public Booking(int bookingId, Guest guest, Room room, int numberOfNights, Employee bookedBy) {
        this.bookingId = bookingId;
        this.guest = guest;
        this.room = room;
        this.numberOfNights = numberOfNights;
        this.bookedBy = bookedBy;
        this.isPaid = false;
        this.isCheckedOut = false;
    }
    // getters
    public Room getRoom() {
        return room;
    }

    public Guest getGuest() {
        return guest;
    }

    public Employee getEmployee() {
        return bookedBy;
    }

    public boolean getIsPaid() {
        return isPaid;
    }

    public boolean getIsCheckedOut() {
        return isCheckedOut;
    }

    public int getBookingId() {
        return bookingId;
    }

    // marks booking as paid
    public boolean payBill() {
        if (!isPaid) {
            isPaid = true;
            return true;
        }
        return false;
    }

    // marks booking as checked out and releases the room
    public boolean checkOut() {
        if (isPaid && !isCheckedOut) {
            isCheckedOut = true;
            room.releaseRoom();
            return true;
        }
        return false;
    }
    
    // the returns the amount of the booking bill
    public double getPrice() {
        return room.getPrice() * numberOfNights;
    }

    // string representation of booking
    public String toString() {
        return "Booking ID: " + bookingId +
                ", Guest: " + guest +
                ", Room: " + room.getRoomNumber() +
                ", Nights: " + numberOfNights +
                ", Paid: " + isPaid +
                ", Checked Out: " + isCheckedOut;
    }
}