import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    static Scanner scanner = new Scanner(System.in);
    static final int MAX_BOOKINGS = 100; // Maximum number of bookings
    static String[] bookingHistory = new String[MAX_BOOKINGS];
    static int bookingCount = 0;

    public static void main(String[] args) {
        configSeat();
    }

    static void configSeat() {
        System.out.println("-+".repeat(20));
        System.out.println("CSTAD HALL BOOKING SYSTEM");
        System.out.println("-+".repeat(20));
        System.out.print("> Config total rows in hall: ");
        int rows = Integer.parseInt(scanner.nextLine());
        System.out.print("> Config total seats per row in hall: ");
        int columns = Integer.parseInt(scanner.nextLine());

        String[][] seatsMorning = new String[rows][columns];
        String[][] seatsAfternoon = new String[rows][columns];
        String[][] seatsNight = new String[rows][columns];

        for (int a = 0; a < rows; a++) {
            char label = (char) ('A'+a);
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
                        String op = scanner.nextLine();
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
                                addToBookingHistory(seatToBook, stuId, hallName);
                                String[] parts = seatToBook.split(",");
                                for (String part : parts) {
                                    String[] seat = part.split("-");
                                    int row = seat[0].charAt(0) - 'A';
                                    int col = Integer.parseInt(seat[1]) - 1;
                                    if (row >= 0 && row < seatsMorning.length && col >= 0 && col < seatsMorning[0].length) {
                                        if (seatsMorning[row][col].contains("BO")){
                                            String fail = """
                                            +------------------------+
                                            # Seat(s) already booked.
                                            +------------------------+
                                            """;
                                            System.out.println(fail);
                                        }
                                        else {
                                            seatsMorning[row][col] = seatsMorning[row][col].replace("AV", "BO");
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
                            case "b" -> {
                                hallName = "B";
                                System.out.println("# Hall B (Afternoon Hall)");
                                printSeat(seatsAfternoon);
                                System.out.println("# INSTRUCTION");
                                System.out.println("# Single: C-1");
                                System.out.println("# Multiple: C-1,C-2");

//                                String seatBookingValidate = "C-\\d+(,C-\\d+)*";
//                                   String opValidate = scanner.nextLine().trim();
//                                Pattern pattern1 = Pattern.compile(seatBookingValidate);
//                                Matcher matcher1 = pattern1.matcher(opValidate);
                                System.out.print("> Please select available seat : ");
                                String seatToBook = scanner.nextLine().toUpperCase();
                                System.out.print("> Please enter student ID : ");
                                String stuId = scanner.nextLine();
                                addToBookingHistory(seatToBook, stuId, hallName);
                                String[] parts = seatToBook.split(",");
                                for (String part : parts) {
                                    String[] seat = part.split("-");
                                    int row = seat[0].charAt(0) - 'A';
                                    int col = Integer.parseInt(seat[1]) - 1;
                                    if (row >= 0 && row < seatsAfternoon.length && col >= 0 && col < seatsAfternoon[0].length) {
                                        if (seatsAfternoon[row][col].contains("BO")){
                                            String fail = """
                                            +------------------------+
                                            # Seat(s) already booked.
                                            +------------------------+
                                            """;
                                            System.out.println(fail);
                                        }
                                        else {
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

//                                String seatBookingValidate = "C-\\d+(,C-\\d+)*";
//                                String opValidate = scanner.nextLine().trim();
//                                Pattern pattern1 = Pattern.compile(seatBookingValidate);
//                                Matcher matcher1 = pattern1.matcher(opValidate);

                                System.out.print("> Please select available seat : ");
                                String seatToBook = scanner.nextLine().toUpperCase();
                                System.out.print("> Please enter student ID : ");
                                String stuId = scanner.nextLine();
                                addToBookingHistory(seatToBook, stuId, hallName);
                                String[] parts = seatToBook.split(",");
                                for (String part : parts) {
                                    String[] seat = part.split("-");
                                    int row = seat[0].charAt(0) - 'A';
                                    int col = Integer.parseInt(seat[1]) -1;
                                    if (row >= 0 && row < seatsNight.length && col >= 0 && col<seatsNight[0].length) {
                                        if (seatsNight[row][col].contains("BO")){
                                            String fail = """
                                            +------------------------+
                                            # Seat(s) already booked.
                                            +------------------------+
                                            """;
                                            System.out.println(fail);
                                        }
                                        else {
                                            seatsNight[row][col] = seatsNight[row][col].replace("AV", "BO");
                                            String success = """
                                            +------------------------+
                                            # Seat(s) booked successfully.
                                            +------------------------+
                                            """;
                                            System.out.println(success);
                                        }
                                    }
                                    else {
                                        System.out.println("Invalid seat selection : " + part);
                                    }
                                }
                            }
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
                        String confirmationValidate = "^[yYnN]$";
                        String opValidate = scanner.nextLine().trim();
                        Pattern pattern1 = Pattern.compile(confirmationValidate);
                        Matcher matcher1 = pattern1.matcher(opValidate);

                        if (matcher1.matches()) {
                            System.out.print("Do you want to reboot the hall? (Y/N): ");
                            String op = scanner.nextLine().toUpperCase();
                            switch (op.toLowerCase()){
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
    static void showMenu(){
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
    static void printSeat(String[][] seats){
        for (String[] seat : seats){
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
    static void rebootHall(String[][] seats){
        for (int row = 0; row< seats.length; row++) {
            for (int col = 0; col<seats[row].length; col++) {
                seats[row][col] = seats[row][col].replace("BO","AV");
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
        for (int i = 0; i < bookingCount; i++) {
            String[] parts = bookingHistory[i].split(", ");
            String seats = parts[0].split(": ")[1];
            String studentId = parts[1].split(": ")[1];
            String timestamp = parts[2].split(": ")[1];
            String hallName = parts[3].split(": ")[1];

            String formattedEntry = String.format("""
        -------------------------------------------
        #No: %d
        #SEATS: [%s]
        #HALL       #STU.ID         #CREATED AT
        Hall %s      %s           %s
        ------------------------------------------""", i + 1, seats, hallName, studentId, timestamp);
            System.out.println(formattedEntry);
        }
    }
}