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
    * **Logic:** Uses `findBooking()` to locate the record. If found, it calls `releaseRoom()` on the associated room, removes the booking from the array, and shifts the remaining array elements to the left to avoid `null` gaps.
* **`availableRooms()`**
    * **Logic:** Loops through `rooms` to count how many are available. It creates a new `Room[]` array of that exact count, populates it with only the available rooms, and returns the new array.
* **`findRoom(int roomNumber, int index)` & `findBooking(int bookingId, int index)`**
    * **Logic (Strict Recursion):** Base case 1: `index >= count` (returns `null`). Base case 2: object ID at current index matches target (returns the object). Recursive step: return the method calling itself with `index + 1`. No loops allowed!
* **`archiveCompleteBookings()`**
    * **Logic:** Loops through `bookings`. If a booking's `isPaid` AND `isCheckedOut` are both `true`, it adds the booking's `getPrice()` to `totalRevenue`, removes it from the array, and shifts the remaining elements down to free up capacity.
* **`hireEmployee(Employee employee)` / `fireEmployee(Employee employee)`**
    * **Logic:** `hire` checks array limits before adding. `fire` searches for the employee, removes them, and shifts the array elements to the left to maintain a contiguous list without empty spaces.