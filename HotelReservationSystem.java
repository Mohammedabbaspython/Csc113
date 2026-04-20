import java.util.Scanner;

public class HotelReservationSystem {
    // initializing input Scanner
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        // menus options count constants
        final int MAIN_MENU_OPTIONS = 3;
        final int EMPLOYEE_MENU_OPTIONS = 5;
        final int MANAGER_MENU_OPTIONS = 6;

        // option selecting variable
        int choice;

        //
        Manager hotelManager = new Manager("Mohamed", 100, 20000, 5000);
        Hotel hotel = new Hotel("HIJ Inn", 500, 2000, hotelManager, 10);
        Person[] people = new Person[10];
        Room[] availableRooms;
        Room room;
        Employee employee;
        Guest guest;
        Booking booking;
        Booking[] bookings;
        String type;
        double price;

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

        Guest[] guests = { (Guest) people[5], (Guest) people[6], (Guest) people[7], (Guest) people[8],
                (Guest) people[9] };
        
        Employee [] employees = {(Employee) people[1], (Employee) people[2], (Employee) people[3], (Employee) people[4]};
        // welcome message
        System.out.println("Welcome");
        do {
            // main menu
            displayMainMenu();
            // choice
            choice = takeChoiceInput(MAIN_MENU_OPTIONS);

            // Employee menu
            if (choice == 1) {
                if (hotel.getEmployees().length == 0) {
                    System.out.println("Hotel doesn't have employees");
                    continue;
                }

                System.out.println("Choose account:");

                for (int i = 1; i <= hotel.getEmployees().length; i++) {
                    System.out.printf("\t%d. name: %s, id: %d\n", i, hotel.getEmployees()[i - 1].getName(),
                            hotel.getEmployees()[i - 1].getId());
                }
                employee = hotel.getEmployees()[takeChoiceInput(hotel.getEmployees().length) - 1];

                do {
                    // Employee menu
                    displayEmployeeMenu();
                    // choice
                    choice = takeChoiceInput(EMPLOYEE_MENU_OPTIONS);
                    if (choice == 1) {
                        availableRooms = hotel.availableRooms();
                        if (availableRooms.length == 0) {
                            System.out.println("No available Rooms");
                            continue;
                        }
                        System.out.println("Available Rooms:");
                        for (int i = 1; i <= availableRooms.length; i++) {
                            System.out.printf("\t%d. Room number: %d type: %s price per night: %.2f\n", i,
                                    availableRooms[i - 1].getRoomNumber(), availableRooms[i - 1].getRoomType(),
                                    availableRooms[i - 1].getPrice());
                        }
                        room = availableRooms[takeChoiceInput(availableRooms.length) - 1];

                        System.out.println("Choose Guest: ");
                        for (int i = 1; i <= guests.length; i++) {
                            System.out.printf("\t%d. name: %s, id: %d, phone number: %s\n", i, guests[i - 1].getName(),
                                    guests[i - 1].getId(), guests[i - 1].getPhoneNumber());
                        }
                        guest = guests[takeChoiceInput(guests.length) - 1];

                        System.out.println("Enter the number of nights (min 1- max 30):");
                        
                        if (hotel.addBooking(guest, takeChoiceInput(30), room.getRoomNumber(), employee)) {
                            System.out.println("Success");
                        }
                        else {
                            System.out.println("Failed!");
                        }

                    } else if (choice == 2) {
                        room = null;
                        System.out.println("Enter room Number: ");
                        room = hotel.findRoom(input.nextInt(), 0);

                        if (room == null) {
                            System.out.println("Room not found!");
                        }
                        else {
                            System.out.println(room);
                        }

                    } else if (choice == 3) {
                        booking = null;
                        System.out.println("Enter booking id to cancel: ");
                        booking = hotel.findBooking(input.nextInt(), 0);

                        if (booking == null) {
                            System.out.println("Booking not found!");
                        }
                        else {
                            System.out.println(booking);
                            System.out.println("Are you sure you want to cancel the booking?y/n");

                            if (input.next().charAt(0) == 'y') {
                                if (hotel.cancelBooking(booking.getBookingId())){
                                    System.out.println("Success");
                                } else {
                                    System.out.println("Failed");
                                }
                            }
                        }

                    } else if (choice == 4) {
                        System.out.println("Choose Guest: ");
                        for (int i = 1; i <= guests.length; i++) {
                            System.out.printf("\t%d. name: %s, id: %d, phone number: %s\n", i, guests[i - 1].getName(),
                            guests[i - 1].getId(), guests[i - 1].getPhoneNumber());
                        }
                        guest = guests[takeChoiceInput(guests.length) - 1];
                        
                        bookings = hotel.findGuestBookings(guest);
                        if (bookings.length == 0){
                            System.out.println("No bookings");
                            continue;
                        }
                        for (int i = 1; i <= bookings.length; i++) {
                            System.out.println("\t" + i + ". " + bookings[i-1]);
                        }
                        
                        booking = bookings[takeChoiceInput(bookings.length) -1 ];

                        

                        System.out.println("The total amount is " + booking.getPrice() + "sar, pay with: \n\t1. card. \n\t2. cash");
                        takeChoiceInput(2);
                        if (booking.payBill() && booking.checkOut()){
                                System.out.println("Success");
                            } else {
                                System.out.println("Failed");
                            }
                    }
                } while (choice != 5);

            }
            // Manager menu
            else if (choice == 2) {
                do {
                    // display manager menu
                    displayManagerMenu();

                    choice = takeChoiceInput(MANAGER_MENU_OPTIONS);
                    
                    if (choice == 1) {
                        System.out.print("Enter room type (only one word): ");
                        type = input.next();
                        System.out.print("Enter room price per night: ");
                        price = input.nextDouble();

                        if (hotel.addRoom(type, price)){
                                    System.out.println("Success");
                                } else {
                                    System.out.println("Failed");
                                }
                    }
                    else if (choice == 2){
                        System.out.println("Select an employee: ");
                        for (int i = 1; i <= employees.length; i++) {
                            System.out.printf("%d. %s Salary: %.2f\n", i, employees[i - 1].toString(), employees[i - 1].getSalary());
                        }
                        employee = employees[takeChoiceInput(employees.length) -1 ];

                        if (hotel.hireEmployee(employee)) {
                            System.out.println("Success");
                        } else {
                            System.out.println("Failed");
                        }
                    }
                    else if (choice == 3) {
                        System.out.println("Select an employee: ");
                        for (int i = 1; i <= employees.length; i++) {
                            System.out.printf("%d. %s Salary: %.2f\n", i, employees[i - 1].toString(), employees[i - 1].getSalary());
                        }
                        employee = employees[takeChoiceInput(employees.length) -1 ];

                        if (hotel.fireEmployee(employee)) {
                            System.out.println("Success");
                        } else {
                            System.out.println("Failed");
                        }
                    }
                    else if (choice == 4) {
                        System.out.println("Archiving.....");
                        if (hotel.archiveCompleteBookings()) {
                            System.out.println("Success");
                        } else {
                            System.out.println("Failed");
                        }

                        System.out.printf("Hotel total revenue is %.2f sar", hotel.getRevenue());
                    }
                    else if (choice == 5) {
                        System.out.println("People registered in the system: ");
                        for (int i = 0; i < people.length; i++) {
                            System.out.printf("%d. %s Role: ", i + 1, people[i].toString());
                            people[i].printRole();
                        }
                    }

                } while (choice != 6);

            }
        }while(choice!=3);

    }

    // main menu
    public static void displayMainMenu() {
        // displaying main menu options
        System.out.println("Select from the main menu options:");
        System.out.println("\t 1 Log in as an employee");
        System.out.println("\t 2 Log in as a manager");
        System.out.println("\t 3 Exit");
    }

    // employee menu
    public static void displayEmployeeMenu() {
        // displaying employee menu options
        System.out.println("Select from the options:");
        System.out.println("\t 1 Create a New Booking");
        System.out.println("\t 2 Search for a Room");
        System.out.println("\t 3 Cancel a Booking");
        System.out.println("\t 4 Check-Out a Guest");
        System.out.println("\t 5 Logout");
    }

    // manager menu
    public static void displayManagerMenu() {
        // displaying employee menu options
        System.out.println("Select from the options:");
        System.out.println("\t 1 Add a New Room to Hotel");
        System.out.println("\t 2 Hire a New Employee");
        System.out.println("\t 3 Fire an Employee");
        System.out.println("\t 4 Archive Completed Bookings And View Revenue");
        System.out.println("\t 5 Print System Role Roster");
        System.out.println("\t 6 Logout");
    }

    // handles input
    public static int takeChoiceInput(int numOfOptions) {
        String raw;

        while (true) {
            System.out.print("Choose an option: ");
            raw = input.next();

            // validate that all characters are digits
            boolean validDigits = true;
            for (int i = 0; i < raw.length(); i++) {
                if (!Character.isDigit(raw.charAt(i))) {
                    validDigits = false;
                    break;
                }
            }

            if (!validDigits || raw.length() == 0) {
                System.out.println("\t Invalid input. Please enter an integer between 1 and " + numOfOptions);
                continue;
            }

            int value = 0;
            for (int i = 0; i < raw.length(); i++) {
                value = value * 10 + (raw.charAt(i) - '0');
            }

            if (value >= 1 && value <= numOfOptions) {
                return value;
            }

            // invalid input message
            System.out.println("\t Invalid input. Please enter an integer between 1 and " + numOfOptions);
        }
    }

}
