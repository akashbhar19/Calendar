package _calendar;

import java.util.*;
import java.time.LocalDateTime;

/**
 * This Driver tests all the method in the date class
 * 
 * @Akash Bhardwaj
 * @version 4/16/2016
 */
public class Driver {
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		LocalDateTime now = LocalDateTime.now();
		int month = now.getMonthValue(), date = now.getDayOfMonth(), year = now.getYear(), temp, daysInYear = 365;
		Date currentDate = new Date(month, date, year), firstDate, secondDate;
		Date date1 = new Date(1, 1, 2011);
		Date date2;
		Date date3;
		Date date4 = new Date(5, 4, 2014);
		Date date5 = new Date(6, 5, 2015);
		Date date6 = new Date(7, 6, 2016);
		Date date7 = new Date(8, 7, 2017);
		Date date8 = new Date(8, 7, 2017);
		Date date9 = new Date(9, 9, 2019);
		if(currentDate.getLeapYearStatus()) {
			daysInYear = 366;
		}
		int checker = 0;
		
		while(checker != 8) {
			month = currentDate.getMonth();
			date = currentDate.getDay();
			year = currentDate.getYear();
			System.out.println("Welcome to Simple Calendar!");
			System.out.println("Read and choose from one of the following options");
			System.out.println("1.) Run through tests");
			System.out.println("2.) View current date");
			System.out.println("3.) Reset current date");
			System.out.println("4.) Find day of year");
			System.out.println("5.) Subtract days from current date");
			System.out.println("6.) Add days to current date");
			System.out.println("7.) Test the number of days between two given days");
			System.out.println("8.) Quit");
			checker = keyboard.nextInt();
			if(checker == 1) {
				// testing the subtract method
				System.out.println(" Testing the substract method, by substracting 25 days from the first date");
				date2 = date1.subtract(25);

				System.out.println(date2);

				// testing the add method
				System.out.println(" Testing the add method, by adding 155 days from the  date");
				date3 = date1.subtract(155);

				System.out.println(date3);
				// testing the long method
				System.out.println(" Testing the method that setspp the date to long format");
				System.out.println(date1.getDate('l'));

				// testing the long method
				System.out
						.println(" Testing the method that sets the date to long format that uses the getDayOfWeek() method ");
				System.out.println(date3.getDate('l'));
				// testing the short method
				System.out.println(" Testing the method that sets back to short format with date ");
				System.out.println(date3.getDate('s'));
				// testing the daysBetween method
				System.out.println(
						" Testing the days between method by checking the days between the first date and the third date");
				System.out.println(date1.daysBetween(date3));
				// testing the daysBetween
				System.out.println(
						" Testing the days between method by checking the days between the third date and the first date");
				System.out.println(date3.daysBetween(date1));

				System.out.println(
						" Testing the overloaded days between method by checking the days between the third date and a date who's parameters are inputted in the method");
				System.out.println(date3.daysBetween(5, 21, 2016));
				// testing the equals method
				System.out.println(
						" Testing the equals method by checking by checking if the fifth date is equal to the sixth date ");
				System.out.println(date5.equals(date6));
				// testing the compareTo method
				System.out.println(" Testing the compareTo method by checking by comparing the eighth date to the ninth date ");
				System.out.println(date8.compareTo(date9));

				System.out.println(" Testing the compareTo method by checking by comparing the ninth date to the eighth date ");
				System.out.println(date9.compareTo(date8));
				// testing the toString method
				System.out.println(" Testing the toString method with the first date ");
				System.out.println(date1.toString(1));

				// it accounts for leap years
			}
			else if(checker == 2) {
				System.out.println(currentDate.toString(1));
				System.out.println(currentDate.toString(2));
			}
			else if(checker == 3) {
				System.out.println("Enter new month (1-12): ");
				month = keyboard.nextInt();
				System.out.println("Enter new day (1-31): ");
				date = keyboard.nextInt();
				System.out.println("Enter new year (YYYY): ");
				year = keyboard.nextInt();
				currentDate = new Date(month, date, year);
				currentDate.setDayOfWeek();
				System.out.println("Your new date is " + currentDate.toString(2));
			}
			else if(checker == 4) {
				System.out.println("The current date is " + currentDate.getDayOfYear() + " out of " + daysInYear);
			}
			else if(checker == 5) {
				System.out.println("Your current date is " + currentDate.toString(1));
				System.out.println("How many days would you like to subtract? ");
				temp = keyboard.nextInt();
				System.out.println(currentDate.subtract(temp).toString(1) + " is " + temp + " days before " + currentDate.toString(1));
			}
			else if(checker == 6) {
				System.out.println("Your current date is " + currentDate.toString(1));
				System.out.println("How many days would you like to add? ");
				temp = keyboard.nextInt();
				System.out.println(currentDate.add(temp).toString(1) + " is " + temp + " days after " + currentDate.toString(1));
			}
			else if(checker == 7) {
				System.out.println("Please enter first dates month to be compared (if you want to use current date type 0)");
				temp = keyboard.nextInt();
				if(temp != 0) {
					month = temp;
					System.out.println("Please enter first dates day to be compared");
					date = keyboard.nextInt();
					System.out.println("Please enter first dates year to be compared");
					year = keyboard.nextInt();
					firstDate = new Date(month, date, year);
				}
				else {
					firstDate = currentDate;
				}
				System.out.println("Please enter second dates month to be compared");
				month = keyboard.nextInt();
				System.out.println("Please enter second dates day to be compared");
				date = keyboard.nextInt();
				System.out.println("Please enter second dates year to be compared");
				year = keyboard.nextInt();
				secondDate = new Date(month, date, year);
				System.out.println("The number of days between " + firstDate.toString(1) + " and " + secondDate.toString(1) + " is " + firstDate.daysBetween(secondDate));
			}
			else
				if(checker != 8) {
					System.out.println("Command not recognized. Please try again.");
				}
		}
		System.out.println("Have a nice day!");
		
	}
}