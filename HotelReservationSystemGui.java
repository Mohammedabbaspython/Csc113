import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HotelReservationSystemGui {
    // for data
    public static Hotel hotel;
    public static Person[] people;
    public static Guest[] guests;
    public static Employee[] employees;

    public static Employee loggedInEmployee;

    // frames
    public static JFrame loginFrame;
    public static JFrame managerFrame;
    public static JFrame employeeFrame;

    public static void main(String[] args) {
        // setup data
        setupData();

        showLoginFrame();

    }

    public static void setupData() {
        Manager hotelManager = new Manager("Mohamed", 100, 20000, 5000);

        hotel = Hotel.loadFromFile();

        if (hotel == null) {
            hotel = new Hotel("HIJ Inn", 50, 200, hotelManager, 10);
        }

        people = new Person[10];
        people[0] = hotelManager;
        people[1] = new Employee("Ahmed", 101, 3000);
        people[2] = new Employee("Fatima", 102, 3000);
        people[3] = new Employee("Sara", 103, 3000);
        people[4] = new Employee("Omar", 104, 3000);
        people[5] = new Guest("Ali", 105, "0501234567");
        people[6] = new Guest("Layla", 106, "0502345678");
        people[7] = new Guest("Hassan", 107, "0503456789");
        people[8] = new Guest("Mona", 108, "0504567890");
        people[9] = new Guest("Karim", 109, "0505678901");

        guests = new Guest[] { (Guest) people[5], (Guest) people[6], (Guest) people[7], (Guest) people[8],
                (Guest) people[9] };
        employees = new Employee[] { (Employee) people[1], (Employee) people[2], (Employee) people[3],
                (Employee) people[4] };
    }

    public static void initLoginFrame() {
        loginFrame = new JFrame("Hotel Reservation System - Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 300);
        loginFrame.setLayout(new FlowLayout());
        loginFrame.setResizable(false);

        // Employee Login Section
        JLabel empTitle = new JLabel("--- Employee Access ---");
        JLabel empLabel = new JLabel("Choose Employee Account:");

        JComboBox<Employee> empComboBox = new JComboBox<>(hotel.getEmployees());
        JButton empLoginBtn = new JButton("Login as Employee");

        empLoginBtn.addActionListener(new EmployeeLoginListener(empComboBox));

        loginFrame.add(empTitle);
        loginFrame.add(empLabel);
        loginFrame.add(empComboBox);
        loginFrame.add(empLoginBtn);

        // Manager Login Section
        JLabel mgrTitle = new JLabel("--- Manager Access ---");
        JButton mgrLoginBtn = new JButton("Login as Manager");

        mgrLoginBtn.addActionListener(new ManagerLoginListener());

        loginFrame.add(mgrTitle);
        loginFrame.add(mgrLoginBtn);

    }

    public static void initManagerFrame() {
        managerFrame = new JFrame("Manager Menu");
        managerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        managerFrame.setSize(600, 400);
        managerFrame.setLayout(new FlowLayout());
        managerFrame.setResizable(false);

        // Add Room Section
        JLabel addRoomTitle = new JLabel("--- Add New Room ---");
        JTextField typeField = new JTextField(10);
        JTextField priceField = new JTextField(10);
        JButton addRoomBtn = new JButton("Add Room");
        addRoomBtn.addActionListener(new AddRoomListener(typeField, priceField));

        managerFrame.add(addRoomTitle);
        managerFrame.add(new JLabel("Type:"));
        managerFrame.add(typeField);
        managerFrame.add(new JLabel("Price:"));
        managerFrame.add(priceField);
        managerFrame.add(addRoomBtn);

        // Hire Employee Section
        JLabel hireTitle = new JLabel("--- Hire Employee ---");
        JComboBox<Employee> hireComboBox = new JComboBox<>(employees);
        JButton hireBtn = new JButton("Hire Employee");

        // Fire Employee Section
        JLabel fireTitle = new JLabel("--- Fire Employee ---");
        JComboBox<Employee> fireComboBox = new JComboBox<>(hotel.getEmployees());
        JButton fireBtn = new JButton("Fire Employee");

        hireBtn.addActionListener(new HireEmployeeListener(hireComboBox, fireComboBox));
        fireBtn.addActionListener(new FireEmployeeListener(fireComboBox));

        managerFrame.add(hireTitle);
        managerFrame.add(hireComboBox);
        managerFrame.add(hireBtn);

        managerFrame.add(fireTitle);
        managerFrame.add(fireComboBox);
        managerFrame.add(fireBtn);

        // Archive Bookings Section
        JLabel archiveTitle = new JLabel("--- Archive Complete Bookings ---");
        JButton archiveBtn = new JButton("Archive Bookings");
        archiveBtn.addActionListener(new ArchiveBookingsListener());

        managerFrame.add(archiveTitle);
        managerFrame.add(archiveBtn);

        // Logout Section
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(new LogoutListener());
        managerFrame.add(logoutBtn);
    }

    public static void initEmployeeFrame() {
        employeeFrame = new JFrame("Employee Menu");
        employeeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        employeeFrame.setSize(600, 500);
        employeeFrame.setLayout(new FlowLayout());
        employeeFrame.setResizable(false);

        // Create Booking Section
        JLabel createTitle = new JLabel("--- Create Booking ---");
        JComboBox<Room> roomComboBox = new JComboBox<>(hotel.availableRooms());
        JComboBox<Guest> guestComboBox = new JComboBox<>(guests);
        JTextField nightsField = new JTextField(5);
        JButton createBookingBtn = new JButton("Create Booking");
        createBookingBtn.addActionListener(new CreateBookingListener(roomComboBox, guestComboBox, nightsField));

        employeeFrame.add(createTitle);
        employeeFrame.add(new JLabel("Room:"));
        employeeFrame.add(roomComboBox);
        employeeFrame.add(new JLabel("Guest:"));
        employeeFrame.add(guestComboBox);
        employeeFrame.add(new JLabel("Nights:"));
        employeeFrame.add(nightsField);
        employeeFrame.add(createBookingBtn);

        // Search Room Section
        JLabel searchTitle = new JLabel("--- Search Room ---");
        JTextField searchRoomField = new JTextField(10);
        JButton searchRoomBtn = new JButton("Search Room");
        searchRoomBtn.addActionListener(new SearchRoomListener(searchRoomField));

        employeeFrame.add(searchTitle);
        employeeFrame.add(new JLabel("Room Number:"));
        employeeFrame.add(searchRoomField);
        employeeFrame.add(searchRoomBtn);

        // Cancel Booking Section
        JLabel cancelTitle = new JLabel("--- Cancel Booking ---");
        JTextField cancelBookingField = new JTextField(10);
        JButton cancelBookingBtn = new JButton("Cancel Booking");
        cancelBookingBtn.addActionListener(new CancelBookingListener(cancelBookingField, roomComboBox));

        employeeFrame.add(cancelTitle);
        employeeFrame.add(new JLabel("Booking ID:"));
        employeeFrame.add(cancelBookingField);
        employeeFrame.add(cancelBookingBtn);

        // Check-Out Section
        JLabel checkoutTitle = new JLabel("--- Check-Out ---");
        JComboBox<Guest> checkoutGuestBox = new JComboBox<>(guests);
        JComboBox<Booking> checkoutBookingBox = new JComboBox<>();
        JButton fetchBookingsBtn = new JButton("Fetch Bookings");
        fetchBookingsBtn.addActionListener(new FetchBookingsListener(checkoutGuestBox, checkoutBookingBox));

        JButton checkoutBtn = new JButton("Check-Out");
        checkoutBtn.addActionListener(new CheckOutListener(checkoutBookingBox, roomComboBox));

        employeeFrame.add(checkoutTitle);
        employeeFrame.add(new JLabel("Guest:"));
        employeeFrame.add(checkoutGuestBox);
        employeeFrame.add(fetchBookingsBtn);
        employeeFrame.add(checkoutBookingBox);
        employeeFrame.add(checkoutBtn);

        // Logout Section
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(new LogoutListener());
        employeeFrame.add(logoutBtn);
    }

    public static void showLoginFrame() {
        if (managerFrame != null)
            managerFrame.setVisible(false);
        if (employeeFrame != null)
            employeeFrame.setVisible(false);

        if (loginFrame != null) {
            // removing the frame with old data
            loginFrame.dispose();
        }
        initLoginFrame();
        loginFrame.setVisible(true);
    }

    public static void showManagerFrame() {
        loginFrame.setVisible(false);
        if (employeeFrame != null)
            employeeFrame.setVisible(false);

        if (managerFrame != null) {
            // removing the frame with old data
            managerFrame.dispose();
        }
        initManagerFrame();
        managerFrame.setVisible(true);
    }

    public static void showEmployeeFrame() {
        loginFrame.setVisible(false);
        if (managerFrame != null)
            managerFrame.setVisible(false);

        if (employeeFrame != null) {
            // removing the frame with old data
            employeeFrame.dispose();
        }
        initEmployeeFrame();
        employeeFrame.setVisible(true);
    }
}

// listeners classes

class EmployeeLoginListener implements ActionListener {
    private JComboBox<Employee> empComboBox;

    public EmployeeLoginListener(JComboBox<Employee> empComboBox) {
        this.empComboBox = empComboBox;
    }

    public void actionPerformed(ActionEvent e) {
        Employee[] hiredEmployees = HotelReservationSystemGui.hotel.getEmployees();
        if (hiredEmployees.length == 0 || empComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(HotelReservationSystemGui.loginFrame,
                    "No employees available. Manager must hire an employee first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        HotelReservationSystemGui.loggedInEmployee = (Employee) empComboBox.getSelectedItem();
        HotelReservationSystemGui.showEmployeeFrame();
    }
}

class ManagerLoginListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        HotelReservationSystemGui.showManagerFrame();
    }
}

class AddRoomListener implements ActionListener {
    private JTextField typeField;
    private JTextField priceField;

    public AddRoomListener(JTextField typeField, JTextField priceField) {
        this.typeField = typeField;
        this.priceField = priceField;
    }

    public void actionPerformed(ActionEvent e) {
        try {
            String type = typeField.getText();
            double price = Double.parseDouble(priceField.getText());

            if (HotelReservationSystemGui.hotel.addRoom(type, price)) {
                JOptionPane.showMessageDialog(HotelReservationSystemGui.managerFrame, "Room added successfully!");
                typeField.setText("");
                priceField.setText("");
            } else {
                JOptionPane.showMessageDialog(HotelReservationSystemGui.managerFrame, "Failed to add room.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(HotelReservationSystemGui.managerFrame, "Invalid price format.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

class HireEmployeeListener implements ActionListener {
    private JComboBox<Employee> hireComboBox;
    private JComboBox<Employee> fireComboBox;

    public HireEmployeeListener(JComboBox<Employee> hireComboBox, JComboBox<Employee> fireComboBox) {
        this.hireComboBox = hireComboBox;
        this.fireComboBox = fireComboBox;
    }

    public void actionPerformed(ActionEvent e) {
        Employee emp = (Employee) hireComboBox.getSelectedItem();
        if (emp != null) {
            if (HotelReservationSystemGui.hotel.hireEmployee(emp)) {
                JOptionPane.showMessageDialog(HotelReservationSystemGui.managerFrame,
                        "Employee " + emp.getName() + " hired successfully!");
                fireComboBox.addItem(emp);
            } else {
                JOptionPane.showMessageDialog(HotelReservationSystemGui.managerFrame,
                        "Failed to hire or already hired.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

class FireEmployeeListener implements ActionListener {
    private JComboBox<Employee> fireComboBox;

    public FireEmployeeListener(JComboBox<Employee> fireComboBox) {
        this.fireComboBox = fireComboBox;
    }

    public void actionPerformed(ActionEvent e) {
        Employee emp = (Employee) fireComboBox.getSelectedItem();
        if (emp != null) {
            if (HotelReservationSystemGui.hotel.fireEmployee(emp)) {
                JOptionPane.showMessageDialog(HotelReservationSystemGui.managerFrame,
                        "Employee " + emp.getName() + " fired successfully!");
                fireComboBox.removeItem(emp);
            } else {
                JOptionPane.showMessageDialog(HotelReservationSystemGui.managerFrame, "Failed to fire employee.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

class ArchiveBookingsListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        boolean archived = HotelReservationSystemGui.hotel.archiveCompleteBookings();
        String msg;
        if (archived) {
            msg = "Bookings archived successfully.";
        } else {
            msg = "No completed bookings to archive.";
        }

        msg += "\nHotel Total Revenue: " + HotelReservationSystemGui.hotel.getRevenue() + " sar";
        JOptionPane.showMessageDialog(HotelReservationSystemGui.managerFrame, msg);
    }
}

class LogoutListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        HotelReservationSystemGui.loggedInEmployee = null;
        HotelReservationSystemGui.showLoginFrame();
    }
}

class CreateBookingListener implements ActionListener {
    private JComboBox<Room> roomComboBox;
    private JComboBox<Guest> guestComboBox;
    private JTextField nightsField;

    public CreateBookingListener(JComboBox<Room> roomComboBox, JComboBox<Guest> guestComboBox, JTextField nightsField) {
        this.roomComboBox = roomComboBox;
        this.guestComboBox = guestComboBox;
        this.nightsField = nightsField;
    }

    public void actionPerformed(ActionEvent e) {
        Room room = (Room) roomComboBox.getSelectedItem();
        Guest guest = (Guest) guestComboBox.getSelectedItem();
        if (room == null || guest == null) {
            JOptionPane.showMessageDialog(HotelReservationSystemGui.employeeFrame, "Please select a room and a guest.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int nights = Integer.parseInt(nightsField.getText());
            if (HotelReservationSystemGui.hotel.addBooking(guest, nights, room.getRoomNumber(),
                    HotelReservationSystemGui.loggedInEmployee)) {
                JOptionPane.showMessageDialog(HotelReservationSystemGui.employeeFrame, "Booking created successfully!");
                roomComboBox.removeItem(room);
                nightsField.setText("");
            } else {
                JOptionPane.showMessageDialog(HotelReservationSystemGui.employeeFrame, "Failed to create booking.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(HotelReservationSystemGui.employeeFrame, "Invalid number of nights.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (RoomUnavailableException ex) {
            JOptionPane.showMessageDialog(HotelReservationSystemGui.employeeFrame, ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

class SearchRoomListener implements ActionListener {
    private JTextField searchRoomField;

    public SearchRoomListener(JTextField searchRoomField) {
        this.searchRoomField = searchRoomField;
    }

    public void actionPerformed(ActionEvent e) {
        try {
            int roomNumber = Integer.parseInt(searchRoomField.getText());
            Room room = HotelReservationSystemGui.hotel.findRoom(roomNumber, 0);
            if (room != null) {
                JOptionPane.showMessageDialog(HotelReservationSystemGui.employeeFrame, room.toString(), "Room Details",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(HotelReservationSystemGui.employeeFrame, "Room not found.",
                        "Room Details",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(HotelReservationSystemGui.employeeFrame, "Invalid room number.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        searchRoomField.setText("");
    }
}

class CancelBookingListener implements ActionListener {
    private JTextField cancelBookingField;
    private JComboBox<Room> roomComboBox;

    public CancelBookingListener(JTextField cancelBookingField, JComboBox<Room> roomComboBox) {
        this.cancelBookingField = cancelBookingField;
        this.roomComboBox = roomComboBox;
    }

    public void actionPerformed(ActionEvent e) {
        try {
            int bookingId = Integer.parseInt(cancelBookingField.getText());
            Booking booking = HotelReservationSystemGui.hotel.findBooking(bookingId, 0);
            if (booking != null) {
                int confirm = JOptionPane.showConfirmDialog(HotelReservationSystemGui.employeeFrame,
                        "Cancel booking for " + booking.getGuest().getName() + "?", "Confirm",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (HotelReservationSystemGui.hotel.cancelBooking(bookingId)) {
                        JOptionPane.showMessageDialog(HotelReservationSystemGui.employeeFrame, "Booking cancelled.");
                        roomComboBox.addItem(booking.getRoom());
                        cancelBookingField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(HotelReservationSystemGui.employeeFrame,
                                "Failed to cancel booking.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(HotelReservationSystemGui.employeeFrame, "Booking not found.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(HotelReservationSystemGui.employeeFrame, "Invalid booking ID.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

class FetchBookingsListener implements ActionListener {
    private JComboBox<Guest> checkoutGuestBox;
    private JComboBox<Booking> checkoutBookingBox;

    public FetchBookingsListener(JComboBox<Guest> checkoutGuestBox, JComboBox<Booking> checkoutBookingBox) {
        this.checkoutGuestBox = checkoutGuestBox;
        this.checkoutBookingBox = checkoutBookingBox;
    }

    public void actionPerformed(ActionEvent e) {
        Guest g = (Guest) checkoutGuestBox.getSelectedItem();
        if (g != null) {
            Booking[] gBookings = HotelReservationSystemGui.hotel.findGuestBookings(g);
            checkoutBookingBox.removeAllItems();
            for (Booking b : gBookings) {
                checkoutBookingBox.addItem(b);
            }
            if (gBookings.length == 0) {
                JOptionPane.showMessageDialog(HotelReservationSystemGui.employeeFrame,
                        "No bookings found for this guest.");
            }
        }
    }
}

class CheckOutListener implements ActionListener {
    private JComboBox<Booking> checkoutBookingBox;
    private JComboBox<Room> roomComboBox;

    public CheckOutListener(JComboBox<Booking> checkoutBookingBox, JComboBox<Room> roomComboBox) {
        this.checkoutBookingBox = checkoutBookingBox;
        this.roomComboBox = roomComboBox;
    }

    public void actionPerformed(ActionEvent e) {
        Booking b = (Booking) checkoutBookingBox.getSelectedItem();
        if (b != null) {
            if (!b.getIsCheckedOut() && b.payBill() && b.checkOut()) {
                JOptionPane.showMessageDialog(HotelReservationSystemGui.employeeFrame,
                        "Check-out successful. Total paid: " + b.getPrice() + " sar");
                roomComboBox.addItem(b.getRoom());;
                checkoutBookingBox.removeItem(b);
                HotelReservationSystemGui.hotel.saveToFile();
            } else {
                JOptionPane.showMessageDialog(HotelReservationSystemGui.employeeFrame,
                        "Failed to check-out or already checked out.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(HotelReservationSystemGui.employeeFrame, "Please fetch and select a booking.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
