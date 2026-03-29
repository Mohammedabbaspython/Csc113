public class Booking implements Billable {

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

    public boolean payBill() {
        if (!isPaid) {
            isPaid = true;
            return true;
        }
        return false;
    }

    public boolean checkOut() {
        if (isPaid && !isCheckedOut) {
            isCheckedOut = true;
            room.releaseRoom();
            return true;
        }
        return false;
    }
    @Override
    public double getPrice() {
        return room.getPrice() * numberOfNights;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId +
                ", Guest: " + guest +
                ", Room: " + room.getRoomNumber() +
                ", Nights: " + numberOfNights +
                ", Paid: " + isPaid +
                ", Checked Out: " + isCheckedOut;
    }
}