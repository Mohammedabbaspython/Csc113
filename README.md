## 1. Person Hierarchy & Interfaces

* **`printRole()`**
    * **Logic:** Abstract in the `Person` class. Subclasses implement this to print their specific title (e.g., "Guest", "Manager"). When looping through a mixed `Person[]` array, calling this dynamically identifies who the system is dealing with (Polymorphism).
* **`getPrice()` (`Billable`)**
    * **Logic:** Interface contract. `Room` implements this by returning its flat `nightPrice`. `Booking` implements this by calculating and returning `room.getPrice() * numberOfNights`.

## 2. Room Class

* **`Room(String type, double nightPrice, int roomNumber)`**
    * **Logic:** Constructor that initializes the room details. It must automatically set `isAvailable = true` upon creation.
* **`bookRoom()` / `releaseRoom()`**
    * **Logic:** `bookRoom()` must first check if the room is already booked. If available, it changes the state to `false` and returns `true`. `releaseRoom()` does the reverse.
* **`toString()`**
    * **Logic:** Builds and returns a clean, user-friendly string (e.g., "Room 101 | Single | $150/night | Available: Yes") to be printed in the main menu UI.

## 3. Booking Class

* **`Booking(int bookingId, Guest guest, Room room, int numberOfNights, Employee bookedBy)`**
    * **Logic:** Strictly links the guest, room, and employee (Aggregation). It must force `isPaid` and `isCheckedOut` to `false` by default.
* **`payBill()`**
    * **Logic:** Checks if the bill is already paid. If not, sets `isPaid = true` and returns `true`. This prevents a guest from accidentally being charged twice.
* **`checkOut()`**
    * **Logic:** Sets `isCheckedOut = true` and *must* instantly call `room.releaseRoom()` on its assigned room object so the physical room can be rented to someone else.

## 4. Hotel Class (The Engine)

* **`addRoom(String type, double price)`**
    * **Logic:** Checks if `roomCount < maxRooms`. If there is space, it instantiates the `Room` object (Composition), places it in `rooms[roomCount]`, increments the counter, and returns `true`.
* **`addBooking(Guest guest, int numberOfNights, int roomNumber, Employee employee)`**
    * **Logic:** First checks if `bookingCount < maxBookings`. Then uses `findRoom()` to verify the room exists and `isAvailable`. If valid, it instantiates the `Booking` (Composition), calls `bookRoom()` on the room, adds it to the array, and increments the counter.
* **`cancelBooking(int bookingId)`**
    * **Logic:** Uses a loop to search the array and locate the record by its booking ID. If found, it calls `releaseRoom()` on the associated room, removes the booking from the array, and shifts the remaining array elements to the left to avoid `null` gaps.
* **`availableRooms()`**
    * **Logic:** Loops through `rooms` to count how many are available. It creates a new `Room[]` array of that exact count, populates it with only the available rooms, and returns the new array.
* **`findRoom(int roomNumber, int index)` & `findBooking(int bookingId, int index)`**
    * **Logic (Strict Recursion):** Base case 1: `index >= count` (returns `null`). Base case 2: object ID at current index matches target (returns the object). Recursive step: return the method calling itself with `index + 1`. No loops allowed!
* **`archiveCompleteBookings()`**
    * **Logic:** Loops through `bookings`. If a booking's `isCheckedOut` is `true` (which guarantees the bill is already paid), it adds the booking's `getPrice()` to `totalRevenue`, removes it from the array, and shifts the remaining elements down to free up capacity.
* **`hireEmployee(Employee employee)` / `fireEmployee(Employee employee)`**
    * **Logic:** `hire` checks array limits before adding. `fire` searches for the employee, removes them, and shifts the array elements to the left to maintain a contiguous list without empty spaces.



    ### GUI Component Summary

#### 1. Login Frame
**Architecture:** The initial `JFrame` serving as the access point to the system. It uses a simple layout to separate manager access from employee access.

*   **Employee Login Section:**
    *   `JLabel`: "Choose Employee Account:"
    *   `JComboBox`: A drop-down menu populated with the system's current employees.
    *   `JButton`: "Login as Employee" (Grabs the selected employee from the drop-down, sets the static session variable, hides this frame, and opens the Employee Menu).
*   **Manager Login Section:**
    *   `JLabel`: "Manager Access:"
    *   `JButton`: "Login as Manager" (Hides this frame and opens the Manager Menu).

#### 2. Manager Menu Frame
**Architecture:** A single `JFrame` displaying all manager actions simultaneously using a flat layout (e.g., `GridLayout`). All action results (success, failure, or data output) are displayed to the user via `JOptionPane` message dialogs. 

*   **Add New Room Section:**
    *   `JTextField`: Input for Room Type.
    *   `JTextField`: Input for Price per Night.
    *   `JButton`: "Add Room" (Triggers backend method, shows success/fail dialog, clears fields).
*   **Hire Employee Section:**
    *   `JComboBox`: Drop-down menu populated with candidates.
    *   `JButton`: "Hire Employee" (Triggers hire method for the selected person, shows dialog).
*   **Fire Employee Section:**
    *   `JComboBox`: Drop-down menu populated with current active employees.
    *   `JButton`: "Fire Employee" (Triggers fire method, shows dialog, removes them from the combo box).
*   **Archive Section:**
    *   `JButton`: "Archive Bookings" (Triggers archiving method and pops a dialog showing the total revenue).
*   **Navigation:**
    *   `JButton`: "Logout" (Wipes the static session variable, hides this frame, and re-opens the Login Frame).

#### 3. Employee Menu Frame
**Architecture:** A single `JFrame` acting as the reception desk control panel. It uses a flat layout (e.g., `GridLayout`) to display all daily operations simultaneously. To keep the interface clean and avoid extra windows, all search results, receipts, and confirmations are displayed via `JOptionPane` message dialogs.

*   **Create Booking Section:**
    *   `JComboBox`: Drop-down menu populated with currently available rooms.
    *   `JComboBox`: Drop-down menu populated with registered guests.
    *   `JTextField`: Input for Number of Nights.
    *   `JButton`: "Create Booking" (Triggers backend booking logic, shows success/fail dialog, and refreshes the available rooms drop-down).
*   **Search Room Section:**
    *   `JTextField`: Input for Room Number.
    *   `JButton`: "Search Room" (Retrieves room details and displays them in a pop-up dialog box).
*   **Cancel Booking Section:**
    *   `JTextField`: Input for Booking ID.
    *   `JButton`: "Cancel Booking" (Triggers cancellation method, shows confirmation dialog).
*   **Check-Out Section:**
    *   `JComboBox`: Drop-down menu populated with current active bookings or guests.
    *   `JButton`: "Check-Out & Pay" (Processes the check-out, displays the final bill amount in a dialog box, and clears the room).
*   **Navigation:**
    *   `JButton`: "Logout" (Wipes the static session variable to `null`, hides this frame, and re-opens the Login Frame).