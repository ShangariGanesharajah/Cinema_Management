import java.util.Scanner;
import java.util.regex.Pattern;

public class w2055152_CinemaManagement {
    private static final int ROWS = 3;  // Number of rows in the cinema
    private static final int SEATS = 16; // Number of seats per row
    private static int[][] seats = new int[ROWS][SEATS]; // Seat management array
    private static Ticket[] tickets = new Ticket[ROWS * SEATS]; // Array of sold tickets
    private static int ticketCount = 0; // Counter for sold tickets

    public static void main(String[] args) {
        System.out.println("\nWelcome to The London Lumiere");
        mainMenu();
    }

    private static void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("---------------------------");
            System.out.println("Please select an option:");
            System.out.println("1. Buy Ticket");
            System.out.println("2. Cancel Ticket");
            System.out.println("3. Print Seating Area");
            System.out.println("4. Find First Available Seat");
            System.out.println("5. Print Tickets Information and Total Sales");
            System.out.println("6. Search Ticket");
            System.out.println("7. Sort Tickets by Price");
            System.out.println("8. Exit");
            System.out.println("--------------------------");
            System.out.print("Enter your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number between 1 and 8: ");
                scanner.next();
            }
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    buyTicket();
                    break;
                case 2:
                    cancelTicket();
                    break;
                case 3:
                    printSeatingArea();
                    break;
                case 4:
                    findFirstAvailable();
                    break;
                case 5:
                    print_tickets_info();
                    break;
                case 6:
                    searchTicket();
                    break;
                case 7:
                    sortTickets();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 8);
        scanner.close();
    }

    private static void buyTicket() {
        Scanner scanner = new Scanner(System.in);
        int row = -1;
        int seat = -1;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Enter row number (1-" + ROWS + "): ");
            if (scanner.hasNextInt()) {
                row = scanner.nextInt() - 1; // Adjust to zero-based indexing
                if (isValidRow(row)) {
                    validInput = true;
                } else {
                    System.out.println("Invalid row number. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }

        validInput = false;
        while (!validInput) {
            System.out.print("Enter seat number (1-" + SEATS + "): ");
            if (scanner.hasNextInt()) {
                seat = scanner.nextInt() - 1; // Adjust to zero-based indexing
                if (isValidSeat(seat)) {
                    validInput = true;
                } else {
                    System.out.println("Invalid seat number. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }

        if (seats[row][seat] == 0) {
            scanner.nextLine(); // Consume the newline
            System.out.println("Enter details for the ticket holder:");

            String name = inputValidString(scanner, "Enter the First Name: ");
            String surname = inputValidString(scanner, "Enter the Surname: ");
            String email = inputValidEmail(scanner, "Enter valid Email: ");

            try {
                Person person = new Person(name, surname, email);
                double price = calculatePrice(row);
                Ticket ticket = new Ticket(row, seat, price, person);
                tickets[ticketCount++] = ticket;
                seats[row][seat] = 1;

                System.out.println("The seat at Row " + (row + 1) + ", Seat " + (seat + 1) + " has been booked.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("This seat is not available.");
        }
    }


    private static void cancelTicket() {
        Scanner scanner = new Scanner(System.in);
        int row = -1;
        int seat = -1;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Enter row number (1-" + ROWS + "): ");
            if (scanner.hasNextInt()) {
                row = scanner.nextInt() - 1;
                if (isValidRow(row)) {
                    validInput = true;
                } else {
                    System.out.println("Invalid row number. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }

        validInput = false;
        while (!validInput) {
            System.out.print("Enter seat number (1-" + SEATS + "): ");
            if (scanner.hasNextInt()) {
                seat = scanner.nextInt() - 1;
                if (isValidSeat(seat)) {
                    validInput = true;
                } else {
                    System.out.println("Invalid seat number. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }

        if (seats[row][seat] == 1) {
            seats[row][seat] = 0;
            removeTicket(row, seat);
            System.out.println("The seat at Row " + (row + 1) + ", Seat " + (seat + 1) + " has been cancelled.");
        } else {
            System.out.println("This seat is already available.");
        }
    }

    private static void printSeatingArea() {
        System.out.println("\nSeating Area:");

        System.out.println("******************************************************************");
        System.out.println("*                            Screen                              *");
        System.out.println("******************************************************************");

        // Print seat numbers header
        System.out.print(" ");
        for (int j = 1; j <= SEATS; j++) {
            System.out.print(String.format("%02d  ", j));
        }
        System.out.println();

        // Print seating arrangement
        for (int i = 0; i < ROWS; i++) {
            System.out.print("R" + (i + 1) + "  ");
            for (int j = 0; j < SEATS; j++) {
                System.out.print(seats[i][j] == 0 ? "O   " : "X   ");
            }
            System.out.println();
        }

        System.out.println();
    }

    private static void findFirstAvailable() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < SEATS; j++) {
                if (seats[i][j] == 0) {
                    System.out.println("First available seat is at Row " + (i + 1) + ", Seat " + (j + 1));
                    return;
                }
            }
        }
        System.out.println("No available seats.");
    }

    private static boolean isValidRow(int row) {
        return row >= 0 && row < ROWS;
    }

    private static boolean isValidSeat(int seat) {
        return seat >= 0 && seat < SEATS;
    }

    private static String inputValidString(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty() || !input.matches("[a-zA-Z]+")) {
                System.out.println("Invalid input. Please enter alphabetic characters only.");
            }
        } while (input.isEmpty() || !input.matches("[a-zA-Z]+"));
        return input;
    }

    private static String inputValidEmail(Scanner scanner, String prompt) {
        String input;
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!emailPattern.matcher(input).matches()) {
                System.out.println("Invalid email format. Please try again.");
            }
        } while (!emailPattern.matcher(input).matches());
        return input;
    }

    private static double calculatePrice(int row) {
        // Sample price calculation based on row
        // Modify this logic as per your requirements
        switch (row) {
            case 0: return 12.0; // Row 1
            case 1: return 10.0; // Row 2
            case 2: return 8.0; // Row 3
            default: return 10.0;
        }
    }

    private static void print_tickets_info() {
        if (ticketCount == 0) {
            System.out.println("No tickets sold yet.");
            return;
        }

        System.out.println("\nTickets Information:");
        double totalSales = 0;
        for (int i = 0; i < ticketCount; i++) {
            Ticket ticket = tickets[i];
            System.out.println("Ticket " + (i + 1) + ":");
            ticket.printInfo();
            totalSales += ticket.getPrice();
        }
        System.out.println("Total Sales: £" + totalSales);
    }

    private static void searchTicket() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter row number: ");
        int row = scanner.nextInt() - 1;
        System.out.print("Enter seat number: ");
        int seat = scanner.nextInt() - 1;
        boolean found = false;
        for (int i = 0; i < ticketCount; i++) {
            if (tickets[i].getRow() == row && tickets[i].getSeat() == seat) {
                System.out.println("Ticket found: ");
                tickets[i].printInfo();
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No ticket found for row " + (row + 1) + " and seat " + (seat + 1));
        }
    }

    private static void sortTickets() {
        if (ticketCount > 0) {
            java.util.Arrays.sort(tickets, 0, ticketCount, java.util.Comparator.comparingDouble(Ticket::getPrice));
            System.out.println("Tickets sorted by price.");

            // Optionally, print out the sorted tickets to verify
            for (int i = 0; i < ticketCount; i++) {
                tickets[i].printInfo();
            }
        } else {
            System.out.println("No tickets to sort.");
        }
    }


    private static void removeTicket(int row, int seat) {
        for (int i = 0; i < ticketCount; i++) {
            if (tickets[i].getRow() == row && tickets[i].getSeat() == seat) {
                tickets[i] = tickets[--ticketCount];
                tickets[ticketCount] = null;
                return;
            }
        }
    }
}
