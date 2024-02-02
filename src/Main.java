import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    static final int MAX_BOOKINGS = 100;
    static String[] bookingHistory = new String[MAX_BOOKINGS];
    static int bookingCount = 0;

    public static void main(String[] args) {
        configSeat();
    }

    static void configSeat() {
        System.out.println("-+".repeat(20));
        System.out.println("CSTAD HALL BOOKING SYSTEM");
        System.out.println("-+".repeat(20));
        int rows = getInput("Config total rows in hall ");
        int columns = getInput("Config total seats per row in hall");

        String[][] seatsMorning = new String[rows][columns];
        String[][] seatsAfternoon = new String[rows][columns];
        String[][] seatsNight = new String[rows][columns];

        for (int a = 0; a < rows; a++) {
            char label = (char) ('A' + a);
            for (int b = 0; b < columns; b++) {
                seatsMorning[a][b] = "\t|" + label + "-" + (b + 1) + "::AV" + "|\t";
                seatsAfternoon[a][b] = "\t|" + label + "-" + (b + 1) + "::AV" + "|\t";
                seatsNight[a][b] = "\t|" + label + "-" + (b + 1) + "::AV" + "|\t";
            }
        }

        do {
            showMenu();
            System.out.print("> Select menu: ");
            String regex = "[A-Fa-fCc]";
            String option = scanner.nextLine().trim();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(option);
            if (matcher.matches()) {
                switch (option.toLowerCase()) {
                    case "a" -> {
                        System.out.print("> Please select show time (A | B | C) : ");
                        String regexABC = "[A-Ca-c]";
                        String op = scanner.nextLine().trim();
                        Pattern pattern1 = Pattern.compile(regexABC);
                        Matcher matcher1 = pattern1.matcher(op);
                        if (matcher1.matches()) {
                            String hallName;
                            switch (op.toLowerCase()) {
                                case "a" -> {
                                    hallName = "A";
                                    System.out.println("# Hall A (Morning Hall)");
                                    printSeat(seatsMorning);
                                    System.out.println("# INSTRUCTION");
                                    System.out.println("# Single: C-1");
                                    System.out.println("# Multiple: C-1,C-2");

                                    System.out.print("> Please select available seat : ");
                                    String seatToBook = scanner.nextLine();
                                    System.out.print("> Please enter student ID : ");
                                    String stuId = scanner.nextLine();
                                    System.out.print("Do you want to reboot the hall? (Y/N): ");
                                    String confirmationValidate = "^[yYnN]$";
                                    String opValidate = scanner.nextLine().trim();
                                    Pattern patternYn = Pattern.compile(confirmationValidate);
                                    Matcher matcherYn = patternYn.matcher(opValidate);
                                    if (matcherYn.matches()) {
                                        switch (opValidate.toUpperCase()) {
                                            case "Y" -> {
                                                addToBookingHistory(seatToBook, stuId, hallName);
                                                String success = """
                                                        +------------------------+
                                                        # Seat(s) booked successfully.
                                                        +------------------------+""";
                                                System.out.println(success);
                                            }
                                            case "N" -> {
                                                System.out.println("--------------------");
                                                System.out.println("!!Booking canceled!!");
                                                System.out.println("--------------------");
                                            }
                                        }
                                    }
                                    String[] parts = seatToBook.split(",");
                                    for (String part : parts) {
                                        String[] seat = part.split("-");
                                        int row = seat[0].toUpperCase().charAt(0) - 'A';
                                        int col = Integer.parseInt(seat[1]) - 1;
                                        if (row >= 0 && row < seatsMorning.length && col >= 0 && col < seatsMorning[0].length) {
                                            if (seatsMorning[row][col].contains("BO")) {
                                                String fail = """
                                                        +------------------------+
                                                        # Seat(s) already booked.
                                                        +------------------------+""";
                                                System.out.println(fail);
                                            }
                                        } else {
                                            System.err.println("Invalid seat selection: " + part);
                                        }
                                    }
                                }
                                case "b" -> {
                                    hallName = "B";
                                    System.out.println("# Hall B (Afternoon Hall)");
                                    printSeat(seatsAfternoon);
                                    System.out.println("# INSTRUCTION");
                                    System.out.println("# Single: C-1");
                                    System.out.println("# Multiple: C-1,C-2");

                                    System.out.print("> Please select available seat : ");
                                    String seatToBook = scanner.nextLine().toUpperCase();
                                    System.out.print("> Please enter student ID : ");
                                    String stuId = scanner.nextLine();
                                    System.out.print("Do you want to reboot the hall? (Y/N): ");
                                    String confirmationValidate = "^[yYnN]$";
                                    String opValidate = scanner.nextLine().trim();
                                    Pattern patternYn = Pattern.compile(confirmationValidate);
                                    Matcher matcherYn = patternYn.matcher(opValidate);
                                    if (matcherYn.matches()) {
                                        switch (opValidate.toUpperCase()) {
                                            case "Y" -> addToBookingHistory(seatToBook, stuId, hallName);
                                            case "N" -> {
                                                System.out.println("--------------------");
                                                System.out.println("!!Booking canceled!!");
                                                System.out.println("--------------------");
                                            }
                                        }
                                    }
                                    String[] parts = seatToBook.split(",");
                                    for (String part : parts) {
                                        String[] seat = part.split("-");
                                        int row = seat[0].toUpperCase().charAt(0) - 'A';
                                        int col = Integer.parseInt(seat[1]) - 1;
                                        if (row >= 0 && row < seatsAfternoon.length && col >= 0 && col < seatsAfternoon[0].length) {
                                            if (seatsAfternoon[row][col].contains("BO")) {
                                                String fail = """
                                                        +------------------------+
                                                        # Seat(s) already booked.
                                                        +------------------------+
                                                        """;
                                                System.out.println(fail);
                                            } else {
                                                seatsAfternoon[row][col] = seatsAfternoon[row][col].replace("AV", "BO");
                                                String success = """
                                                        +------------------------+
                                                        # Seat(s) booked successfully.
                                                        +------------------------+
                                                        """;
                                                System.out.println(success);
                                            }
                                        } else {
                                            System.out.println("Invalid seat selection: " + part);
                                        }
                                    }

                                }
                                case "c" -> {
                                    hallName = "C";
                                    System.out.println("# Hall C (Night Hall");
                                    printSeat(seatsNight);
                                    System.out.println("# INSTRUCTION");
                                    System.out.println("# Single: C-1");
                                    System.out.println("# Multiple: C-1,C-2");

                                    System.out.print("> Please select available seat : ");
                                    String seatToBook = scanner.nextLine().toUpperCase();
                                    System.out.print("> Please enter student ID : ");
                                    String stuId = scanner.nextLine();

                                    System.out.print("Do you want to reboot the hall? (Y/N): ");
                                    String confirmationValidate = "^[yYnN]$";
                                    String opValidate = scanner.nextLine().trim();
                                    Pattern patternYn = Pattern.compile(confirmationValidate);
                                    Matcher matcherYn = patternYn.matcher(opValidate);
                                    if (matcherYn.matches()) {
                                        switch (opValidate.toUpperCase()) {
                                            case "Y" -> addToBookingHistory(seatToBook, stuId, hallName);
                                            case "N" -> {
                                                System.out.println("--------------------");
                                                System.out.println("!!Booking canceled!!");
                                                System.out.println("--------------------");
                                            }
                                        }
                                    }

                                    String[] parts = seatToBook.split(",");
                                    for (String part : parts) {
                                        String[] seat = part.split("-");
                                        int row = seat[0].toUpperCase().charAt(0) - 'A';
                                        int col = Integer.parseInt(seat[1]) - 1;
                                        if (row >= 0 && row < seatsNight.length && col >= 0 && col < seatsNight[0].length) {
                                            if (seatsNight[row][col].contains("BO")) {
                                                String fail = """
                                                        +------------------------+
                                                        # Seat(s) already booked.
                                                        +------------------------+
                                                        """;
                                                System.out.println(fail);
                                            } else {
                                                seatsNight[row][col] = seatsNight[row][col].replace("AV", "BO");
                                                String success = """
                                                        +------------------------+
                                                        # Seat(s) booked successfully.
                                                        +------------------------+
                                                        """;
                                                System.out.println(success);
                                            }
                                        } else {
                                            System.out.println("Invalid seat selection : " + part);
                                        }
                                    }
                                }
                            }
                        } else {
                            System.err.println("Invalid input. Please try again...");
                        }
                    }
                    case "b" -> {
                        System.out.println("#".repeat(60));
                        System.out.println("#H Hall A (Morning Hall)");
                        printSeat(seatsMorning);
                        System.out.println("#".repeat(60));
                        System.out.println("# Hall B (Afternoon Hall)");
                        printSeat(seatsAfternoon);
                        System.out.println("#".repeat(60));
                        System.out.println(" Hall C (Night Hall)");
                        printSeat(seatsNight);
                    }
                    case "c" -> showTime();
                    case "d" -> {
                        System.out.print("Do you want to reboot the hall? (Y/N): ");
                        String confirmationValidate = "^[yYnN]$";
                        String opValidate = scanner.nextLine().trim();
                        Pattern pattern1 = Pattern.compile(confirmationValidate);
                        Matcher matcher1 = pattern1.matcher(opValidate);

                        if (matcher1.matches()) {
                            switch (opValidate.toLowerCase()) {
                                case "n" -> {
                                    String fail = """
                                            +------------------------+
                                            # Hall reboot failed.
                                            +------------------------+
                                            """;
                                    System.out.println(fail);
                                }
                                case "y" -> {
                                    rebootHall(seatsMorning);
                                    rebootHall(seatsAfternoon);
                                    rebootHall(seatsNight);
                                    String success = """
                                            +------------------------+
                                            # Hall reboot successfully.
                                            +------------------------+
                                            """;
                                    System.out.println(success);
                                    if (bookingCount == 0){
                                        String noHistoryMessage = """
                                                            -----------------------------------
                                                                There is no booking history.
                                                            -----------------------------------""";
                                        System.out.println(noHistoryMessage);
                                    }
                                }
                            }
                        } else {
                            System.err.println("Invalid input. Please try again...");
                        }
                    }
                    case "e" -> showHistory();
                    case "f" -> System.exit(0);
                }
            } else {
                System.err.println("!!!Invalid option! Please try again...");
            }
        } while (true);
    }


    static int getInput(String message) {
        while (true) {
            System.out.print("> " + message + ": ");
            String input = scanner.nextLine().trim();
            Pattern pattern = Pattern.compile("^[1-9]\\d*$");
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                return Integer.parseInt(input);
            } else {
                System.err.println("Invalid input. Please enter a positive integer...");
            }
        }
    }

    static void showMenu() {
        System.out.println("+".repeat(60));
        System.out.println("[[ Application menu ]]");
        System.out.println("(A) Booking");
        System.out.println("(B) Hall");
        System.out.println("(C) Showtime");
        System.out.println("(D) Reboot Hall");
        System.out.println("(E) History");
        System.out.println("(F) Exit");
        System.out.println("+".repeat(60));
    }

    static void printSeat(String[][] seats) {
        for (String[] seat : seats) {
            for (String s : seat) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
    }

    static void showTime() {
        String showTime = """
                =-------------------------------=
                # Daily Showtime of CSTAD hall:
                # A) Morning (10:00AM - 12:30PM)
                # B) Afternoon (3:00PM - 5:30PM)
                # C) Night (7:00PM - 9:30PM)
                =-------------------------------=""";
        System.out.println(showTime);
    }

    static void rebootHall(String[][] seats) {
        for (int row = 0; row < seats.length; row++) {
            for (int col = 0; col < seats[row].length; col++) {
                seats[row][col] = seats[row][col].replace("BO", "AV");
            }
        }
    }

    static void addToBookingHistory(String seats, String studentId, String hallName) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String bookingDetails = "Seat(s): " + seats + ", Student ID: " + studentId + ", Timestamp: " + timestamp + ", Hall: " + hallName;
        bookingHistory[bookingCount++] = bookingDetails;
    }

    static void showHistory() {
        System.out.println("Booking History:");
        if (bookingCount == 0) {
            System.out.println("==============================");
            System.out.println("There is no booked seat yet...");
            System.out.println("==============================");
        }
        for (int i = 0; i < bookingCount; i++) {
            String[] parts = bookingHistory[i].split(", ");
            String formattedEntry = getString(parts, i);
            System.out.println(formattedEntry);
        }
    }
    private static String getString(String[] parts, int i) {
        String seats = parts[0].split(": ")[1];
        String studentId = parts[1].split(": ")[1];
        String timestamp = parts[2].split(": ")[1];
        String hallName = parts[3].split(": ")[1];
        return String.format("""
                -------------------------------------------
                #No: %d
                #SEATS: [%s]
                #HALL       #STU.ID         #CREATED AT
                Hall %s      %s           %s
                ------------------------------------------""", i + 1, seats, hallName, studentId, timestamp);
    }
}