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

    // constructor
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

    // adds a new room to the hotel and assigns it a number
    public boolean addRoom(String type, double price) {
        if (roomCount >= rooms.length) {
            return false;
        }

        rooms[roomCount] = new Room(type, price, roomCount + 1);
        roomCount++;
        return true;
    }

    // create a new booking and adds it to the bookings array
    public boolean addBooking(Guest guest, int numberOfNights, int roomNumber, Employee employee) throws RoomUnavailableException {
        if (bookingCount >= bookings.length) {
            return false;
        }

        Room room = findRoom(roomNumber, 0);

        if (room == null || !room.getIsAvailable()) {
            throw new RoomUnavailableException("Room Unavailable");
        }

        room.bookRoom();

        Booking booking = new Booking(bookingCount + 1, guest, room, numberOfNights, employee);
        bookings[bookingCount] = booking;
        bookingCount++;

        return true;
    }

    // return an array of all the available rooms in the hotel
    public Room[] availableRooms() {
        int count = 0;

        for (int i = 0; i < roomCount; i++) {
            if (rooms[i] != null && rooms[i].getIsAvailable()) {
                count++;
            }
        }

        Room[] available = new Room[count];
        int j = 0;

        for (int i = 0; i < roomCount; i++) {
            if (rooms[i] != null && rooms[i].getIsAvailable()) {
                available[j] = rooms[i];
                j++;
            }
        }

        return available;
    }

    // remove a booking from list and releases the room
    public boolean cancelBooking(int bookingId) {
        int index = -1;

        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getBookingId() == bookingId) {
                index = i;
                break;
            }
        }

        if (index == -1 || bookings[index].getIsCheckedOut()) {
            return false;
        }

        bookings[index].getRoom().releaseRoom();

        for (int i = index; i < bookingCount - 1; i++) {
            bookings[i] = bookings[i + 1];
        }

        bookings[bookingCount - 1] = null;
        bookingCount--;

        return true;
    }

    // recursive method that find and return a room by looking for it using room number

    public Room findRoom(int roomNumber, int index) {
        if (index >= roomCount) {
            return null;
        }

        if (rooms[index].getRoomNumber() == roomNumber) {
            return rooms[index];
        }

        return findRoom(roomNumber, index + 1);
    }

    // recursive method that find and return a booking by looking for it using booking id
    public Booking findBooking(int bookingId, int index) {
        if (index >= bookingCount) {
            return null;
        }

        if (bookings[index].getBookingId() == bookingId) {
            return bookings[index];
        }

        return findBooking(bookingId, index + 1);
    }

    // returns all the bookings of a specific guest
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

    // remove booking from list if it is checked out and returns true
    public boolean archiveCompleteBookings() {
        boolean removed = false;

        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getIsCheckedOut()) {
                totalRevenue += bookings[i].getPrice();
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

    // getter
    public double getRevenue() {
        return totalRevenue;
    }

    // add employee to the list if he is not in it and returns true
    public boolean hireEmployee(Employee employee) {
        if (employeesCount >= employees.length) {
            return false;
        }
        // check if employee is already hired
        for (int i = 0; i < employeesCount; i++) {
            if (employee == employees[i]) {
                return false;
            }
        }

        employees[employeesCount] = employee;
        employeesCount++;
        return true;
    }

    // remove employee if he is in the employees list and returns true
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

    // string representation
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

    // getter
    public Employee[] getEmployees() {
        Employee[] hiredEmployees = new Employee[employeesCount];
        for (int i = 0; i < employeesCount; i++){
            hiredEmployees[i] = employees[i];
        }
        return hiredEmployees;
    }
}