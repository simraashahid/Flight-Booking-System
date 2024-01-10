import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Passenger {
    private String username;
    private String password;
    public Passenger(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public boolean authenticate(String password) {
        return this.password.equals(password);
    }
}

class Flight {
    private String flightNumber;
    private String destination;
    private double fare;
    private int availableSeats;
    public Flight(String flightNumber, String destination, double fare, int availableSeats) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.fare = fare;
        this.availableSeats = availableSeats;
    }
    public String getFlightNumber() {
        return flightNumber;
    }
    public String getDestination() {
        return destination;
    }
    public double getFare() {
        return fare;
    }
    public int getAvailableSeats() {
        return availableSeats;
    }
    public void bookSeat() {
        if (availableSeats > 0) {
            availableSeats--;
            System.out.println("Seat booked successfully.");
        } else {
            System.out.println("Sorry, no available seats for this flight.");
        }
    }

    public void cancelBooking() {
        availableSeats++;
        System.out.println("Booking canceled. Seat available again.");
    }
}

class Booking {
    private String bookingId;
    private Flight flight;
    private Passenger passenger;
    public Booking(String bookingId, Flight flight, Passenger passenger) {
        this.bookingId = bookingId;
        this.flight = flight;
        this.passenger = passenger;
    }
    public String getBookingId() {
        return bookingId;
    }
    public Flight getFlight() {
        return flight;
    }
    public Passenger getPassenger() {
        return passenger;
    }
    public double calculateTotalFare() {
        return flight.getFare();
    }
}

class BoardingPass {
    private String boardingPassId;
    private Booking booking;

    public BoardingPass(String boardingPassId, Booking booking) {
        this.boardingPassId = boardingPassId;
        this.booking = booking;
    }
    public String getBoardingPassId() {
        return boardingPassId;
    }
    public Booking getBooking() {
        return booking;
    }
    public void printBoardingPass() {
        System.out.println("Boarding Pass for " + booking.getPassenger().getUsername());
        System.out.println("Flight: " + booking.getFlight().getFlightNumber());
        System.out.println("Destination: " + booking.getFlight().getDestination());
    }
}

class FlightBookingSystem {
    private static Map<String, Passenger> passengers = new HashMap<>();
    private static ArrayList<Flight> flights = new ArrayList<>();
    private static ArrayList<Booking> bookings = new ArrayList<>();
    private static ArrayList<BoardingPass> boardingPasses = new ArrayList<>();
    private static int bookingIdCounter = 1;
    private static int boardingPassIdCounter = 1;

    private static void initializeData() {
        // Initialize passengers
        passengers.put("user1", new Passenger("user1", "pass1"));
        passengers.put("user2", new Passenger("user2", "pass2"));

        // Initialize flights
        flights.add(new Flight("F001", "New York", 500.0, 50));
        flights.add(new Flight("F002", "Los Angeles", 450.0, 30));

        // For testing purposes, pre-book some seats
        bookings.add(new Booking("B001", flights.get(0), passengers.get("user1")));
        flights.get(0).bookSeat();
        bookings.add(new Booking("B002", flights.get(1), passengers.get("user2")));
        flights.get(1).bookSeat();
    }

    private static Passenger signIn(String username, String password) {
        Passenger passenger = passengers.get(username);
        if (passenger != null && passenger.authenticate(password)) {
            return passenger;
        }
        return null;
    }

    private static Passenger signUp(String username, String password) {
        if (!passengers.containsKey(username)) {
            Passenger newPassenger = new Passenger(username, password);
            passengers.put(username, newPassenger);
            return newPassenger;
        }
        return null;
    }

    private static void bookFlight(Passenger passenger) {
        System.out.println("Available Flights:");
        for (Flight flight : flights) {
            System.out.println("Flight " + flight.getFlightNumber() + ": " + flight.getDestination() +
                    ", Fare: $" + flight.getFare() + ", Available Seats: " + flight.getAvailableSeats());
        }

        System.out.print("Enter the flight number you want to book: ");
        String flightNumber = new Scanner(System.in).next();
        Flight selectedFlight = findFlight(flightNumber);

        if (selectedFlight != null && selectedFlight.getAvailableSeats() > 0) {
            selectedFlight.bookSeat();
            Booking newBooking = new Booking("B00" + bookingIdCounter++, selectedFlight, passenger);
            bookings.add(newBooking);
            System.out.println("Booking successful. Your booking ID is: " + newBooking.getBookingId());
        } else {
            System.out.println("Invalid flight number or no available seats. Please try again.");
        }
    }

    private static void cancelBooking(Passenger passenger) {
        System.out.println("Your Bookings:");
        for (Booking booking : bookings) {
            if (booking.getPassenger() == passenger) {
                System.out.println("Booking ID: " + booking.getBookingId() +
                        ", Flight: " + booking.getFlight().getFlightNumber() +
                        ", Destination: " + booking.getFlight().getDestination());
            }
        }

        System.out.print("Enter the booking ID you want to cancel: ");
        String bookingId = new Scanner(System.in).next();
        Booking bookingToCancel = findBooking(bookingId);

        if (bookingToCancel != null) {
            bookingToCancel.getFlight().cancelBooking();
            bookings.remove(bookingToCancel);
            System.out.println("Booking canceled successfully.");
        } else {
            System.out.println("Invalid booking ID. Please try again.");
        }
    }

    private static void viewBoardingPass(Passenger passenger) {
        System.out.println("Your Bookings:");
        for (Booking booking : bookings) {
            if (booking.getPassenger() == passenger) {
                BoardingPass boardingPass = new BoardingPass("BP00" + boardingPassIdCounter++, booking);
                boardingPasses.add(boardingPass);
                boardingPass.printBoardingPass();
            }
        }
    }

    private static Flight findFlight(String flightNumber) {
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                return flight;
            }
        }
        return null;
    }

    private static Booking findBooking(String bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId().equals(bookingId)) {
                return booking;
            }
        }
        return null;
    }
    private static void displayAvailableFlights() {
        System.out.println("Available Flights:");
        for (Flight flight : flights) {
            System.out.println("Flight " + flight.getFlightNumber() + ": " + flight.getDestination() +
                    ", Fare: $" + flight.getFare() + ", Available Seats: " + flight.getAvailableSeats());
        }
    }

    public static void main(String[] args) {
        initializeData();

        Scanner scanner = new Scanner(System.in);
        boolean isLoggedIn = false;
        Passenger loggedInPassenger = null;

        while (true) {
            if (!isLoggedIn) {
                System.out.println("1. Sign In");
                System.out.println("2. Sign Up");
                System.out.println("3. Exit");

                int choice = scanner.nextInt();

                if (choice == 1) {
                    System.out.print("Enter username: ");
                    String username = scanner.next();
                    System.out.print("Enter password: ");
                    String password = scanner.next();

                    loggedInPassenger = signIn(username, password);
                    if (loggedInPassenger != null) {
                        isLoggedIn = true;
                        System.out.println("Sign In successful. Welcome, " + loggedInPassenger.getUsername() + "!");
                    } else {
                        System.out.println("Invalid credentials. Please try again.");
                    }
                } else if (choice == 2) {
                    System.out.print("Enter username: ");
                    String username = scanner.next();
                    System.out.print("Enter password: ");
                    String password = scanner.next();

                    Passenger newPassenger = signUp(username, password);
                    if (newPassenger != null) {
                        isLoggedIn = true;
                        loggedInPassenger = newPassenger;
                        System.out.println("Sign Up successful. Welcome, " + loggedInPassenger.getUsername() + "!");
                    } else {
                        System.out.println("Username already exists. Please choose a different username.");
                    }
                } else if (choice == 3) {
                    System.out.println("Exiting Flight Booking System. Thank you!");
                    System.exit(0);
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("1. Book a Flight");
                System.out.println("2. Cancel Booking");
                System.out.println("3. View Boarding Pass");
                System.out.println("4. View Available Flights");
                System.out.println("5. Sign Out");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        bookFlight(loggedInPassenger);
                        break;
                    case 2:
                        cancelBooking(loggedInPassenger);
                        break;
                    case 3:
                        viewBoardingPass(loggedInPassenger);
                        break;
                    case 4:
                        displayAvailableFlights();
                        break;
                    case 5:
                        isLoggedIn = false;
                        loggedInPassenger = null;
                        System.out.println("Sign Out successful. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }
}