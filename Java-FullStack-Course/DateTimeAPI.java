import java.util.Scanner;
import java.time.*;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class DateTimeAPI {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        Month month = date.getMonth();
        int dayofMonth = date.getDayOfMonth();
        LocalDate currentDate = LocalDate.now();
        System.out.println("Todays Date: " + LocalDate.now());
        System.out.println("Year: " + year);
        System.out.println("Month: " + month);
        System.out.println("Day: " + dayofMonth);

        System.out.println("Enter your birth date (mm-dd-yyyy): ");
        String birthDate = sc.nextLine();

        //this will format/parse the input
        LocalDate birthDob = LocalDate.parse(birthDate,formatter);
        LocalDate nextDOB = birthDob.withYear(currentDate.getYear());

        Period agPeriod = Period.between(birthDob, currentDate);
        int age = agPeriod.getYears();
        //int daysBetween = agPeriod.getDays;

        System.out.println("You are " + age + " years old");

        if(nextDOB.isBefore(currentDate))
        {
            nextDOB = nextDOB.plusYears(1);
        }
        long daysTill = ChronoUnit.DAYS.between(currentDate, nextDOB);

        System.out.println("Days till your birthday: " + daysTill);

    }
}