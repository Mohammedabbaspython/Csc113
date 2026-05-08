import java.io.*;

public class Hotel implements Serializable {
    private String name;
    private Manager manager;
    private LinkedList employees;
    private int maxEmployees;
    private LinkedList rooms;
    private int roomIdCounter;
    private int maxRooms;
    private LinkedList bookings;
    private int bookingIdCounter;
    private int maxBookings;
    private double totalRevenue;

    // constructor
    public Hotel(String name, int maxRooms, int maxBookings, Manager manager, int maxEmployees) {
        this.name = name;
        this.manager = manager;
        this.employees = new LinkedList("Employees");
        this.maxEmployees = maxEmployees;
        this.rooms = new LinkedList("Rooms");
        this.maxRooms = maxRooms;
        this.roomIdCounter = 0;
        this.bookings = new LinkedList("Bookings");
        this.maxBookings = maxBookings;
        this.bookingIdCounter = 0;
        this.totalRevenue = 0;
    }

    // adds a new room to the hotel and assigns it a number
    public boolean addRoom(String type, double price) {
        if (rooms.size() >= maxRooms) {
            return false;
        }

        rooms.insertAtBack(new Room(type, price, ++roomIdCounter));
        saveToFile();
        return true;
    }

    // create a new booking and adds it to the bookings array
    public boolean addBooking(Guest guest, int numberOfNights, int roomNumber, Employee employee)
            throws RoomUnavailableException {
        if (bookings.size() >= maxBookings) {
            return false;
        }

        Room room = findRoom(roomNumber, 0);

        if (room == null || !room.getIsAvailable()) {
            throw new RoomUnavailableException("Room Unavailable");
        }

        room.bookRoom();

        Booking booking = new Booking(++bookingIdCounter, guest, room, numberOfNights, employee);
        bookings.insertAtBack(booking);

        saveToFile();
        return true;
    }

    // return an array of all the available rooms in the hotel
    public Room[] availableRooms() {
        int count = 0;
        Node current = rooms.getHead();
        
        // count available rooms in list
        while (current != null) {
            Room room = (Room) current.getData();
            if (room.getIsAvailable()) {
                count++;
            }
            current = current.getNext();
        }

        Room[] available = new Room[count];
        int j = 0;
        current = rooms.getHead();
        
        // collect available rooms into array
        while (current != null) {
            Room room = (Room) current.getData();
            if (room.getIsAvailable()) {
                available[j] = room;
                j++;
            }
            current = current.getNext();
        }

        return available;
    }

    // remove a booking from list and releases the room
    public boolean cancelBooking(int bookingId) {
        Node current = bookings.getHead();
        
        while (current != null) {
            Booking booking = (Booking) current.getData();
            if (booking.getBookingId() == bookingId) {
                if (booking.getIsCheckedOut()) {
                    return false;
                }
                booking.getRoom().releaseRoom();
                bookings.remove(booking);
                saveToFile();
                return true;
            }
            current = current.getNext();
        }

        return false;
    }

    // recursive method that find and return a room by looking for it using room
    // number

    public Room findRoom(int roomNumber, int index) {
        if (index >= rooms.size()) {
            return null;
        }

        Node current = rooms.getHead();
        
        // traverse to the current index
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        Room room = (Room) current.getData();
        if (room.getRoomNumber() == roomNumber) {
            return room;
        }

        return findRoom(roomNumber, index + 1);
    }

    // recursive method that find and return a booking by looking for it using
    // booking id
    public Booking findBooking(int bookingId, int index) {
        if (index >= bookings.size()) {
            return null;
        }

        Node current = bookings.getHead();
        
        // traverse to the current index
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        Booking booking = (Booking) current.getData();
        if (booking.getBookingId() == bookingId) {
            return booking;
        }

        return findBooking(bookingId, index + 1);
    }

    // returns all the bookings of a specific guest matching by guest id
    public Booking[] findGuestBookings(Guest guest) {
        int count = 0;
        Node current = bookings.getHead();
        
        // count guest bookings
        while (current != null) {
            Booking booking = (Booking) current.getData();
            if (booking.getGuest().getId() == guest.getId()) {
                count++;
            }
            current = current.getNext();
        }

        Booking[] result = new Booking[count];
        int j = 0;
        current = bookings.getHead();
        
        // collect guest bookings into array
        while (current != null) {
            Booking booking = (Booking) current.getData();
            if (booking.getGuest().getId() == guest.getId()) {
                result[j] = booking;
                j++;
            }
            current = current.getNext();
        }

        return result;
    }

    // remove booking from list if it is checked out and returns true
    public boolean archiveCompleteBookings() {
        boolean removed = false;
        Node current = bookings.getHead();

        while (current != null) {
            Booking booking = (Booking) current.getData();
            Node nextNode = current.getNext();
            
            if (booking.getIsCheckedOut()) {
                totalRevenue += booking.getPrice();
                bookings.remove(booking);
                removed = true;
                saveToFile();
            }
            current = nextNode;
        }

        return removed;
    }

    // getter
    public double getRevenue() {
        return totalRevenue;
    }

    // add employee to the list if he is not in it and returns true
    public boolean hireEmployee(Employee employee) {
        if (employees.size() >= maxEmployees) {
            return false;
        }
        // check if employee is already hired
        Node current = employees.getHead();
        while (current != null) {
            if (employee == current.getData()) {
                return false;
            }
            current = current.getNext();
        }

        employees.insertAtBack(employee);
        saveToFile();
        return true;
    }

    // remove employee if he is in the employees list and returns true
    public boolean fireEmployee(Employee employee) {
        Node current = employees.getHead();

        while (current != null) {
            if (current.getData().equals(employee)) {
                employees.remove(employee);
                saveToFile();
                return true;
            }
            current = current.getNext();
        }

        return false;
    }

    // string representation
    public String toString() {
        String result = "Hotel Name: " + name +
                "\nManager: " + manager +
                "\nTotal Revenue: " + totalRevenue +
                "\nRooms:\n";

        Node current = rooms.getHead();
        while (current != null) {
            result += current.getData() + "\n";
            current = current.getNext();
        }

        result += "Bookings:\n";
        current = bookings.getHead();
        while (current != null) {
            result += current.getData() + "\n";
            current = current.getNext();
        }

        result += "Employees:\n";
        current = employees.getHead();
        while (current != null) {
            result += current.getData() + "\n";
            current = current.getNext();
        }

        return result;
    }

    // getter
    public Employee[] getEmployees() {
        Employee[] hiredEmployees = new Employee[employees.size()];
        Node current = employees.getHead();
        int i = 0;
        
        // collect employees into array
        while (current != null) {
            hiredEmployees[i++] = (Employee) current.getData();
            current = current.getNext();
        }
        return hiredEmployees;
    }

    // save hotel object to file
    public void saveToFile() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("HRS.data"));
            out.writeObject(this);
            out.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    // load hotel object from file
    public static Hotel loadFromFile() {
        Hotel hotel = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("HRS.data"));
            hotel = (Hotel) in.readObject();
            in.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());

        }

        return hotel;

    }

}