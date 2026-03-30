public class Hotel {
    private String name;
    private Manager manager;
    private Employee[] employees;
    private int employeesCount;
    private Room[] rooms;
    private Booking[] bookings;
    private int roomCount;
    private int bookingCount;
    private double totalRevenue;

    public Hotel(String name, int maxRooms, int maxBookings, Manager manager, int maxEmployees) {
        this.name = name;
        this.manager = manager;
        this.employees = new Employee[maxEmployees];
        this.rooms = new Room[maxRooms];
        this.bookings = new Booking[maxBookings];
        this.employeesCount = 0;
        this.roomCount = 0;
        this.bookingCount = 0;
        this.totalRevenue = 0;
    }

    public boolean addRoom(String type, double price) {
        if (roomCount >= rooms.length) {
            return false;
        }

        rooms[roomCount] = new Room(type, price, roomCount + 1);
        roomCount++;
        return true;
    }

    public boolean addBooking(Guest guest, int numberOfNights, int roomNumber, Employee employee) {
        if (bookingCount >= bookings.length) {
            return false;
        }

        Room room = findRoom(roomNumber, 0);

        if (room == null || !room.isAvailable()) {
            return false;
        }

        room.bookRoom();

        Booking booking = new Booking(bookingCount + 1, guest, room, numberOfNights, employee);
        bookings[bookingCount] = booking;
        bookingCount++;

        totalRevenue += booking.getPrice();
        return true;
    }

    public Room[] availableRooms() {
        int count = 0;

        for (int i = 0; i < roomCount; i++) {
            if (rooms[i] != null && rooms[i].isAvailable()) {
                count++;
            }
        }

        Room[] available = new Room[count];
        int j = 0;

        for (int i = 0; i < roomCount; i++) {
            if (rooms[i] != null && rooms[i].isAvailable()) {
                available[j] = rooms[i];
                j++;
            }
        }

        return available;
    }

    public boolean cancelBooking(int bookingId) {
        int index = -1;

        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getBookingId() == bookingId) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return false;
        }

        bookings[index].getRoom().releaseRoom();
        totalRevenue -= bookings[index].getPrice();

        for (int i = index; i < bookingCount - 1; i++) {
            bookings[i] = bookings[i + 1];
        }

        bookings[bookingCount - 1] = null;
        bookingCount--;

        return true;
    }

    public Room findRoom(int roomNumber, int index) {
        if (index >= roomCount) {
            return null;
        }

        if (rooms[index].getRoomNumber() == roomNumber) {
            return rooms[index];
        }

        return findRoom(roomNumber, index + 1);
    }

    public Booking findBooking(int bookingId, int index) {
        if (index >= bookingCount) {
            return null;
        }

        if (bookings[index].getBookingId() == bookingId) {
            return bookings[index];
        }

        return findBooking(bookingId, index + 1);
    }

    public Booking[] findGuestBookings(Guest guest) {
        int count = 0;

        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getGuest() == guest) {
                count++;
            }
        }

        Booking[] result = new Booking[count];
        int j = 0;

        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getGuest() == guest) {
                result[j] = bookings[i];
                j++;
            }
        }

        return result;
    }

    public boolean archiveCompleteBookings() {
        boolean removed = false;

        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getIsCheckedOut()) {
                for (int j = i; j < bookingCount - 1; j++) {
                    bookings[j] = bookings[j + 1];
                }

                bookings[bookingCount - 1] = null;
                bookingCount--;
                i--;
                removed = true;
            }
        }

        return removed;
    }

    public double getRevenue() {
        return totalRevenue;
    }

    public boolean hireEmployee(Employee employee) {
        if (employeesCount >= employees.length) {
            return false;
        }

        employees[employeesCount] = employee;
        employeesCount++;
        return true;
    }

    public boolean fireEmployee(Employee employee) {
        int index = -1;

        for (int i = 0; i < employeesCount; i++) {
            if (employees[i] == employee) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return false;
        }

        for (int i = index; i < employeesCount - 1; i++) {
            employees[i] = employees[i + 1];
        }

        employees[employeesCount - 1] = null;
        employeesCount--;

        return true;
    }

    @Override
    public String toString() {
        String result = "Hotel Name: " + name +
                "\nManager: " + manager +
                "\nTotal Revenue: " + totalRevenue +
                "\nRooms:\n";

        for (int i = 0; i < roomCount; i++) {
            result += rooms[i] + "\n";
        }

        result += "Bookings:\n";
        for (int i = 0; i < bookingCount; i++) {
            result += bookings[i] + "\n";
        }

        result += "Employees:\n";
        for (int i = 0; i < employeesCount; i++) {
            result += employees[i] + "\n";
        }

        return result;
    }
}