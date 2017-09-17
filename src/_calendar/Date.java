package _calendar;


/**
 * The Date class holds the fields and methods that determine the property of a date
 * 
 * @author Akash Bhardwaj
 * @version 4/14/2016
 */
public class Date
{
    //fields
    private int month, day, year, dayOfYear, weekOfYear;
    private boolean leapYearStatus;
    private String dayOfWeek;

    
    /**
     * Date Constructor - this default constructor sets all of the values to zero or false
     *
     */
    public Date()
    {
        // initialize instance variables
        month = 0;
        day = 0;
        year = 0;
        leapYearStatus = false;
        dayOfYear = this.dayOfYear();
        this.setDayOfWeek();
        this.setWeekOfYear();
    }

    /**
     * Date Constructor - this alternate constructor takes parameters from a user to create
     * a Date object with the users choice of month, day and year
     *
     * @param month - A parameter that references the integer between 1-12 of month
     * @param day A parameter - A parameter that references the integer between 1-31 of day
     * @param year A parameter - A parameter that references the integer of a year
     */
    public Date(int month, int day, int year)
    {
        //Set all fields to parameter values
        this.month = month;
        this.day = day;
        this.year = year;
        //Use the fields to set other variables
        dayOfYear = this.dayOfYear();
        this.updateLeapYearStatus();
        this.setDayOfWeek();
        this.setWeekOfYear();
    }

    /**
     * Date Constructor - this copy constructor takes a date and its fields and copies them into the
     * fields of a new object, therefore creating a copy
     *
     * @param date - A parameter that references the date object
     */
    public Date(Date date)
    {
        //Set all fields to values from the Date object from the parameter
        this.month = date.month;
        this.day = date.day;
        this.year = date.year;
        //Use the fields to seet other variables
        dayOfYear = this.dayOfYear();
        this.updateLeapYearStatus();
        dayOfWeek = date.getDayOfWeek();
        weekOfYear = date.getWeekOfYear();
    }
    
    /**
     * Method add - adds the number of days the user wants to add to a date
     *
     * @param numDays - A parameter that references the number of days the user wants to add to the existing
     * date
     * @return - The return value is computed by the day of the year and the year and is converted to a Date object
     * using the dayOfYearToDate() method
     */
    public Date add(int numDays)
    {
        //Create a new year variable and day of year variable so that the original ones remain unchanged
        int newYear = year, newDayOfYear = this.dayOfYear();
        //Create a new leap year status so that the original does not change
        boolean newLeapYearStatus = this.updateLeapYearStatus(newYear);
        //Add the number of days and the day of year to get the number of days from the first day of that year
        numDays += newDayOfYear;
        //If this number is greater than 365 then the date will be in the following year
        while(numDays > 365)
        {
            //If the current year is a leap year, different operations need to be performed on the date
            if(newLeapYearStatus)
            {
                //If it's after February 29th the extra day does not need to be considered when adding a year
                if(newDayOfYear >= 60)
                {
                    numDays -= 365;
                }
                //If it is before February 29th the extra day has to be accounted for otherwise the new date will be a day behind
                else
                {
                    numDays -= 366;
                }
            }
            //If the current year is not a leap year then if the following year is a leap year, an extra day may need to be added
            else
            {
                /*
                 * If next year is a leap year and the day is after March 1st, the extra day will be acounted for
                 * Otherwise the regular 365 will be added
                 */
                if(this.updateLeapYearStatus(newYear + 1))
                {
                    //Add normal number of days if not after March 1st
                    if(newDayOfYear < 60)
                    {
                        numDays -= 365;
                    }
                    //If it is after March 1st add 366 to account for February 29th of the following year
                    else
                    {
                        numDays -= 366;
                    }
                }
                //If it's between two regular non-leap years, no conditions have to be checked and 365 can be added
                else
                {
                    numDays -= 365;
                }
            }
            //Once the right number of days have been accounted for, a year has to be added
            newYear += 1;
            //The leap year status needs to be updated to maintain proper checking and conditions
            newLeapYearStatus = this.updateLeapYearStatus(newYear);
        }
        //Once number of days is less than 365, the date will be within that day. So number of days is equal to day of year
        newDayOfYear = numDays;        
        newLeapYearStatus = this.updateLeapYearStatus(newYear);
        //Return the date
        return this.dayOfYearToDate(newDayOfYear, newYear, newLeapYearStatus);
    }

    /**
     * Method subtract - subtracts the number of days the user wants to remove from a date
     *
     * @param numDays - A parameter that references the number of days the user wants to subtract from the existing
     * date
     * @return The return value is computed by the day of the year and the year and is converted to a Date object
     * using the dayOfYearToDate() method
     */
    public Date subtract(int numDays)
    {
        //Create a new year variable and day of year variable so that the original ones remain unchanged
        int newYear = year, newDayOfYear = this.dayOfYear();
        //Create a new leap year status so that the original does not change
        boolean newLeapYearStatus = this.updateLeapYearStatus(newYear);
        //While number of days is greater than 365 the years will decrease by one
        while(numDays > 365)
        {
            //Condition if the year is a leap year
            if(newLeapYearStatus)
            {
                //If the date is greater than 60 then February 29th has to be accounted for
                if(newDayOfYear > 60)
                {
                    numDays = numDays - 366;
                }
                //Otherwise February 29th does not matter
                else
                {
                    numDays = numDays - 365;
                }
            }
            //If the year is not a leap year
            else
            {
                //If the previous year is a leap year then the February 29th in the previous year will have to be accounted for
                if(this.updateLeapYearStatus(newYear - 1))
                {
                    //If the day is greater than or equal to March 1st, just subtract the normal number of days
                    if(newDayOfYear >= 60)
                    {
                        numDays = numDays - 365;
                    }
                    //Otherwise February 29th will be in between the two dates and the extra day will have to be accounted for
                    else
                    {
                        numDays = numDays - 366;
                    }
                }
                //If the years are two regular non-leap years then nothing has to be checked for and 365 can be subtracted
                else
                {
                    numDays = numDays - 365;
                }
            }
            //Subtract one year after the right number of days have been accounted for
            newYear -= 1;
            //The leap year status needs to be updated to maintain proper checking and conditions
            newLeapYearStatus = this.updateLeapYearStatus(newYear);
        }
        //If number of days is less than the new day of year that means the date will be within the same yeaer
        if(numDays < newDayOfYear)
        {
            //If it's a leap year status then an extra day need to be accounted for
            if(newLeapYearStatus)
            {
                //If the day is before February 29th subtraction will be normal
                if(newDayOfYear < 60)
                {
                    //Subtract the number of days from the day of year to get final day of year
                    newDayOfYear -= numDays;
                    //The date can then be returned based on the updated day of year and year
                    return this.dayOfYearToDate(newDayOfYear, newYear, newLeapYearStatus);
                }
                //Otherwise subtract and check if the new date is before February 29th
                else
                {
                    newDayOfYear -= numDays;
                    //If it is before it then 1 will be added to the date
                    if(newDayOfYear < 59)
                    {
                        newDayOfYear += 1;
                    }
                    //The date can then be returned based on the updated day of year and year
                    return this.dayOfYearToDate(newDayOfYear, newYear, newLeapYearStatus);
                }
            }
            //If the date is within the same year and the year is not a leap year then nothing needs to be checked
            else
            {
                //Subtract the number of days
                newDayOfYear -= numDays;
                //The date can then be returned based on the updated day of year and year
                return this.dayOfYearToDate(newDayOfYear, newYear, newLeapYearStatus);// Kaushik was here
            }
        }
        //If the number of days is greater then it will be in the previous year
        else
        {
            //Go a year back
            newYear -= 1;
            //Update the leap year status for the new year
            newLeapYearStatus = this.updateLeapYearStatus(newYear);
            //Subtract the day of year from the number of days, this is the number of days before Deceomber 31st of the previous year
            numDays -= newDayOfYear;
            //If it's a leap year then December 31st will be the 366th day
            if(newLeapYearStatus)
            {
                newDayOfYear = 366;
            }
            //Otherwise it will be 365
            else
            {
                newDayOfYear = 365;
            }
            //Then the number of days can be subtracted from the new day of the year
            newDayOfYear -= numDays;
            //The date can then be returned based on the updated day of year and year
            return this.dayOfYearToDate(newDayOfYear, newYear, newLeapYearStatus);
        }
    }

    /**
     * Method daysBetween - this overloaded method returns the days between two dates
     *
     * @param other - A parameter other is the second date object that the user wants to compare
     * @return - The return value is the number of days between the two dates
     */
    public int daysBetween(Date other)
    {
        //Initialize counter to get the years between the dates, initialize daysCounted to be days between two dates within the same year
        int counter = 0, daysCounted = 0;
        //Set thisYear to the year of the current object and the year of the other object to otherYear
        int thisYear = this.getYear(), otherYear = other.getYear();
        //Set thisMonth to the month of the current object and the month of the other object to otherMonth
        int thisMonth = this.getMonth(), otherMonth = other.getMonth();
        //Set thisDayOfYear to the day of the current object and the day of the other object to otherDayOfYear
        int thisDayOfYear = this.getDayOfYear(), otherDayOfYear = other.getDayOfYear();
        //Makea leap year status for both of the dates
        boolean thisLeapYearStatus = this.updateLeapYearStatus(thisYear);
        boolean otherLeapYearStatus = this.updateLeapYearStatus(otherYear);
        //While both of the dates are not in the same year, a years worth of days will need to be added to the counter
        while (thisYear != otherYear)
        {
            //This code only works by comparing the larger date to the smaller date
            //Therefore if thisYear is greater than other year then numbers will be added to other year
            if(thisYear > otherYear)
            {
                //If the smaller year is a leap year the add 366 if less than February 29th to account for the extra day
                if(otherLeapYearStatus)
                {
                    if(otherDayOfYear < 60)
                    {
                        counter += 366;
                    }
                    else
                    {
                        counter += 365;
                    }
                }
                //If not a leap year then if the following year is a leap year the day will need to be accounted for
                else
                {
                    //If next year is a leap year, then add 366 if the day os after March 1st, to account for February 29th
                    if(this.updateLeapYearStatus(otherYear + 1))
                    {
                        if(otherDayOfYear < 60)
                        {
                            counter += 365;
                        }
                        else
                        {
                            counter += 366;
                        }
                    }
                    //If neither this year or next year is a leap year then just add 365 days
                    else
                    {
                        counter += 365;
                    }
                }
                //Increment the year after the day has been accounted for
                otherYear = otherYear + 1;
                //Update the leap year status after the year has been incremented
                otherLeapYearStatus = this.updateLeapYearStatus(otherYear);
            }
            //This condition is if the otherYear is greater than thisYear
            else
            {
                //If the smaller year is a leap year the add 366 if less than February 29th to account for the extra day
                if(thisLeapYearStatus)
                {
                    if(thisDayOfYear < 60)
                    {
                        counter += 366;
                    }
                    else
                    {
                        counter += 365;
                    }
                }
                //If not a leap year then if the following year is a leap year the day will need to be accounted for
                else
                {
                    if(this.updateLeapYearStatus(thisYear + 1))
                    {
                        if(thisDayOfYear < 60)
                        {
                            counter += 365;
                        }
                        else
                        {
                            counter += 366;
                        }
                    }
                    //If neither this year or next year is a leap year then just add 365 days
                    else
                    {
                        counter += 365;
                    }
                }
                //Increment the year after the day has been accounted for
                thisYear = thisYear + 1;
                //Update the leap year status after the year has been incremented
                thisLeapYearStatus = this.updateLeapYearStatus(thisYear);
            }
        }
        //The two dates are now within 365 days of each other and the days between the two new dates will now be accounted for
        if(thisLeapYearStatus)
        {
            //If the day of year is greater than the second
            if(thisDayOfYear > otherDayOfYear)
            {
                //If the two years are different, then count the days between and return counter + difference plus one if February 29th is between or without the one if that date is not between the two
                if(this.getYear() > other.getYear())
                {
                    if(otherDayOfYear < 60 && thisDayOfYear > 60)
                    {
                        daysCounted = counter + 1 + (thisDayOfYear - otherDayOfYear);
                    }
                    else
                    {
                        daysCounted = counter + (thisDayOfYear - otherDayOfYear);
                    }
                }
                //If the second day of year is greater than the first, then subtraction will need to occur because there are two dates with one greater day of year but lower year
                else
                {
                    if(otherDayOfYear < 60 && thisDayOfYear > 60)
                    {
                        daysCounted = counter + 1 - (thisDayOfYear - otherDayOfYear);
                    }
                    else
                    {
                        daysCounted = counter - (thisDayOfYear - otherDayOfYear);
                    }
                }
            }
            //If the other year is greater then everything will be reverse in this case
            else
            {
                //If the two years are different, then count the days between and return counter + difference plus one if February 29th is in between or without the one if that date is not between the two
                if(this.getYear() < other.getYear())
                {
                    if(thisDayOfYear < 60 && otherDayOfYear > 60)
                    {
                        daysCounted = counter + 1 + (otherDayOfYear - thisDayOfYear);
                    }
                    else
                    {
                        daysCounted = counter + (otherDayOfYear - thisDayOfYear);
                    }
                }
                //If the second day of year is greater than the first, then subtraction will need to occur because there are two dates with one greater day of year but lower year
                else
                {
                    if(thisDayOfYear < 60 && otherDayOfYear > 60)
                    {
                        daysCounted = counter + 1 - (otherDayOfYear - thisDayOfYear);
                    }
                    else
                    {
                        daysCounted = counter - (otherDayOfYear - thisDayOfYear);
                    }
                }
            }
        }
        //If the year is not a leap year then the same cass will happen but the one day February 29th will not have to be accounted for between the non-leap years
        else
        {
            if(this.getYear() > other.getYear())
            {
                if(thisDayOfYear > otherDayOfYear)
                {
                    daysCounted = counter + (thisDayOfYear - otherDayOfYear);
                }
                else
                {
                    daysCounted = counter - (otherDayOfYear - thisDayOfYear);
                }
            }
            else
            {
                if(thisDayOfYear > otherDayOfYear)
                {
                    daysCounted = counter - (thisDayOfYear - otherDayOfYear);
                }
                else
                {
                    daysCounted = counter + (otherDayOfYear - thisDayOfYear);
                }
            }
        }
        //Check for if daysCounted is negative and return the positive version of it
        if(daysCounted < 0)
        {
            return daysCounted * -1;
        }
        else
        {
            return daysCounted;
        }
    }
    
    /**
     * Method daysBetween - this overloaded method returns the days between two dates. The difference is that the parameters are a month, day, and a year instead of a date object holding those fields
     *
     * @param month - A parameter that references the users value of the month
     * @param day - A parameter that references the users value of the day
     * @param year - A parameter that references the users value of the year
     * @return - The return value is the number of days between the two dates
     */
    public int daysBetween(int month, int day, int year)
    {
        int counter = 0, daysCounted = 0;
        int thisYear = this.getYear(), otherYear = year;
        int thisDayOfYear = this.getDayOfYear(), otherDayOfYear = this.dayOfYear(month, day, year);
        boolean thisLeapYearStatus = this.updateLeapYearStatus(thisYear);
        boolean otherLeapYearStatus = this.updateLeapYearStatus(otherYear);
        //While both of the dates are not in the same year, a years worth of days will need to be added to the counter
        while (thisYear != otherYear)
        {
            //This code only works by comparing the larger date to the smaller date
            //Therefore if thisYear is greater than other year then numbers will be added to other year
            if(thisYear > otherYear)
            {
                //If the smaller year is a leap year the add 366 if less than February 29th to account for the extra day
                if(otherLeapYearStatus)
                {
                    if(otherDayOfYear < 60)
                    {
                        counter += 366;
                    }
                    else
                    {
                        counter += 365;
                    }
                }
                //If not a leap year then if the following year is a leap year the day will need to be accounted for
                else
                {
                    //If next year is a leap year, then add 366 if the day os after March 1st, to account for February 29th
                    if(this.updateLeapYearStatus(otherYear + 1))
                    {
                        if(otherDayOfYear < 60)
                        {
                            counter += 365;
                        }
                        else
                        {
                            counter += 366;
                        }
                    }
                    //If neither this year or next year is a leap year then just add 365 days
                    else
                    {
                        counter += 365;
                    }
                }
                //Increment the year after the day has been accounted for
                otherYear = otherYear + 1;
                //Update the leap year status after the year has been incremented
                otherLeapYearStatus = this.updateLeapYearStatus(otherYear);
            }
            //This condition is if the otherYear is greater than thisYear
            else
            {
                //If the smaller year is a leap year the add 366 if less than February 29th to account for the extra day
                if(thisLeapYearStatus)
                {
                    if(thisDayOfYear < 60)
                    {
                        counter += 366;
                    }
                    else
                    {
                        counter += 365;
                    }
                }
                //If not a leap year then if the following year is a leap year the day will need to be accounted for
                else
                {
                    if(this.updateLeapYearStatus(thisYear + 1))
                    {
                        if(thisDayOfYear < 60)
                        {
                            counter += 365;
                        }
                        else
                        {
                            counter += 366;
                        }
                    }
                    //If neither this year or next year is a leap year then just add 365 days
                    else
                    {
                        counter += 365;
                    }
                }
                //Increment the year after the day has been accounted for
                thisYear = thisYear + 1;
                //Update the leap year status after the year has been incremented
                thisLeapYearStatus = this.updateLeapYearStatus(thisYear);
            }
        }
        //The two dates are now within 365 days of each other and the days between the two new dates will now be accounted for
        if(thisLeapYearStatus)
        {
            //If the day of year is greater than the second
            if(thisDayOfYear > otherDayOfYear)
            {
                //If the two years are different, then count the days between and return counter + difference plus one if February 29th is between or without the one if that date is not between the two
                if(this.getYear() > year)
                {
                    if(otherDayOfYear < 60 && thisDayOfYear > 60)
                    {
                        daysCounted = counter + 1 + (thisDayOfYear - otherDayOfYear);
                    }
                    else
                    {
                        daysCounted = counter + (thisDayOfYear - otherDayOfYear);
                    }
                }
                //If the second day of year is greater than the first, then subtraction will need to occur because there are two dates with one greater day of year but lower year
                else
                {
                    if(otherDayOfYear < 60 && thisDayOfYear > 60)
                    {
                        daysCounted = counter + 1 - (thisDayOfYear - otherDayOfYear);
                    }
                    else
                    {
                        daysCounted = counter - (thisDayOfYear - otherDayOfYear);
                    }
                }
            }
            //If the other year is greater then everything will be reverse in this case
            else
            {
                //If the two years are different, then count the days between and return counter + difference plus one if February 29th is in between or without the one if that date is not between the two
                if(this.getYear() < year)
                {
                    if(thisDayOfYear < 60 && otherDayOfYear > 60)
                    {
                        daysCounted = counter + 1 + (otherDayOfYear - thisDayOfYear);
                    }
                    else
                    {
                        daysCounted = counter + (otherDayOfYear - thisDayOfYear);
                    }
                }
                //If the second day of year is greater than the first, then subtraction will need to occur because there are two dates with one greater day of year but lower year
                else
                {
                    if(thisDayOfYear < 60 && otherDayOfYear > 60)
                    {
                        daysCounted = counter + 1 - (otherDayOfYear - thisDayOfYear);
                    }
                    else
                    {
                        daysCounted = counter - (otherDayOfYear - thisDayOfYear);
                    }
                }
            }
        }
        //If the year is not a leap year then the same cass will happen but the one day February 29th will not have to be accounted for between the non-leap years
        else
        {
            if(this.getYear() > year)
            {
                if(thisDayOfYear > otherDayOfYear)
                {
                    daysCounted = counter + (thisDayOfYear - otherDayOfYear);
                }
                else
                {
                    daysCounted = counter - (otherDayOfYear - thisDayOfYear);
                }
            }
            else
            {
                if(thisDayOfYear > otherDayOfYear)
                {
                    daysCounted = counter - (thisDayOfYear - otherDayOfYear);
                }
                else
                {
                    daysCounted = counter + (otherDayOfYear - thisDayOfYear);
                }
            }
        }
        //Check for if daysCounted is negative and return the positive version of it
        if(daysCounted < 0)
        {
            return daysCounted * -1;
        }
        else
        {
            return daysCounted;
        }
    }

    /**
     * Method getDate - this method returns a displayable date based upon the 
     *
     * @param format A parameter
     * @return The return value
     */
    public String getDate(char format)
    {
        //Add the day of week, the month, day and the year and return the string if long form is asked
        if(format == 'l')
        {
            return this.getDayOfWeek() + ", " + this.numMonthToWordMonth(month) + " " + day + ", " + year;
        }
        //If s then use the toString method to display the shortened term
        else if(format == 's')
        {
            return this.toString();
        }
        //If the wrong letter is typed, return invalid choice
        else
        {
            return "Invalid choice";
        }
    }
    
    /**
     * Method toString - this method returns the short date as a displayable string
     *
     * @return The return value is a string representation of the object
     */
    public String toString(int x)
    {
    		if(x == 2) {
    			return dayOfWeek + ", " + numMonthToWordMonth(month) + " " + day + ", " + year;
    		}
        return month + "/" + day + "/" + year;
    }

    /**
     * Method equalsTo - this method returns true or false based upon whether or not the two dates are equal
     *
     * @param anotherDate - A parameter that references another date object
     * @return The return value is a boolean that shows whether or not the dates are the same
     */
    public boolean equalsTo(Date anotherDate)
    {
        //If the month, day and year field of both date objects are the same, return true
        if(this.getMonth() == anotherDate.getMonth() && this.getDay() == anotherDate.getDay() && this.getYear() == anotherDate.getYear())
        {
            return true;
        }
        //Otherwise false
        else
        {
            return false;
        }
    }

    /**
     * Method compareTo - this method returns an integer base upon the dates relationships to each other
     *
     * @param anotherDate - A parameter that references a second date object
     * @return The return value is one if the first date is greater, -1 if the second date is greater, and zero is 
     */
    public int compareTo(Date anotherDate)
    {
        //First check if the years are the same or not and return based upon years
        if(this.getYear() > anotherDate.getYear())
        {
            return 1;
        }
        else if(this.getYear() < anotherDate.getYear())
        {
            return -1;
        }
        //If the years are equal, then check for month
        else
        {
            if(this.getMonth() > anotherDate.getMonth())
            {
                return 1;
            }
            else if(this.getMonth() < anotherDate.getMonth())
            {
                return -1;
            }
            //If months are equal then check for days
            else
            {
                if(this.getDay() > anotherDate.getDay())
                {
                    return 1;
                }
                else if(this.getDay() < anotherDate.getDay())
                {
                    return -1;
                }
                //If days are equal then return zero
                else
                {
                    return 0;
                }
            }
        }
    }

    /**
     * Method getMonth - a getter that returns the private field month
     *
     * @return The return value is the month field
     */
    public int getMonth()
    {
        return month;
    }

    /**
     * Method getDay - a getter that returns the private field day
     *
     * @return The return value is the day field
     */
    public int getDay()
    {
        return day;
    }

    /**
     * Method getYear - a getter that returns the private field year
     *
     * @return The return value is the year field
     */
    public int getYear()
    {
        return year;
    }

    /**
     * Method getDayOfYear - a getter that returns the private field dayOfYear
     *
     * @return The return value is the dayOfYear field
     */
    public int getDayOfYear()
    {
        return dayOfYear;
    }

    /**
     * Method getLeapYearStatus - a getter that returns the private field leapYearStatus
     *
     * @return The return value is the leapYearStatus field
     */
    public boolean getLeapYearStatus()
    {
        return leapYearStatus;
    }

    /**
     * Method getDayOfWeek - a getter that returns the private field dayOfWeek
     *
     * @return The return value is the dayOfWeek field
     */
    public String getDayOfWeek()
    {
        return dayOfWeek;
    }
    
    
    /**
     * Method getWeekOfYear - a getter that returns the private field weekOfYear
     *
     * @return The return value is the weekOfYear field
     */
    public int getWeekOfYear()
    {
        return weekOfYear;
    }
    
    /**
     * Method setMonth - a setter that sets month
     *
     * @param month field
     */
    public void setMonth(int month)
    {
        this.month = month;
        dayOfYear = dayOfYear();
    }
    
    /**
     * Method setDay - a setter that sets day
     *
     * @param day field
     */
    public void setDay(int day)
    {
        this.day = day;
        dayOfYear = dayOfYear();
    }

    /**
     * Method setYear - a setter that sets year
     *
     * @param year field
     */
    public void setYear(int year)
    {
        this.year = year;
        this.updateLeapYearStatus();
        dayOfYear = dayOfYear();
    }

    /**
     * Method setLeapYearStatus - a setter that sets leapYearStatus
     *
     * @param leapYearStatus field
     */
    public void setLeapYearStatus(boolean leapYearStatus)
    {
        this.leapYearStatus = leapYearStatus;
    }

    /**
     * Method setDayOfWeek - a setter that sets dayOfWeek, only runs with constructor otherwise shouldn't be called
     *
     */
    public void setDayOfWeek()
    {
        //Use Sunday, Jan, 1st, 2012 as base date
        int daysBetween = this.daysBetween(1, 1, 2012);
        //Set day of week based on mods if year is before 2012 if daysBetween is less than seven
        if(year < 2012)
        {
            if(daysBetween <=7)
            {
                if(daysBetween % 7 == 0)
                {
                    dayOfWeek = "Tuesday";
                }
                else if(daysBetween % 7 == 1)
                {
                    dayOfWeek = "Monday";
                }
                else if(daysBetween % 7 == 2)
                {
                    dayOfWeek = "Sunday";
                }
                else if(daysBetween % 7 == 3)
                {
                    dayOfWeek = "Saturday";
                }
                else if(daysBetween % 7 == 4)
                {
                    dayOfWeek = "Friday";
                }
                else if(daysBetween % 7 == 5)
                {
                    dayOfWeek = "Thursday";
                }
                else
                {
                    dayOfWeek = "Wednesday";
                }
            }
            //If greater than seven
            else
            {
                if(daysBetween % 7 == 0)
                {
                    dayOfWeek = "Sunday";
                }
                else if(daysBetween % 7 == 1)
                {
                    dayOfWeek = "Saturday";
                }
                else if(daysBetween % 7 == 2)
                {
                    dayOfWeek = "Friday";
                }
                else if(daysBetween % 7 == 3)
                {
                    dayOfWeek = "Thursday";
                }
                else if(daysBetween % 7 == 4)
                {
                    dayOfWeek = "Wednesday";
                }
                else if(daysBetween % 7 == 5)
                {
                    dayOfWeek = "Tuesday";
                }
                else
                {
                    dayOfWeek = "Monday";
                }
            }
        }
        //Otherwise if date on or after 2012, use these mod to string conversions
        else
        {
            if(daysBetween % 7 == 0)
            {
                dayOfWeek = "Sunday";
            }
            else if(daysBetween %7 == 1)
            {
                dayOfWeek = "Monday";
            }
            else if(daysBetween %7 == 2)
            {
                dayOfWeek = "Tuesday";
            }
            else if(daysBetween %7 == 3)
            {
                dayOfWeek = "Wednesday";
            }
            else if(daysBetween %7 == 4)
            {
                dayOfWeek = "Thursday";
            }
            else if(daysBetween %7 == 5)
            {
                dayOfWeek = "Friday";
            }
            else
            {
                dayOfWeek = "Saturday";
            }
        }
    }

    /**
     * Method setWeekOfYear - day of year divided by seven is week of year
     *
     *
     */
    public void setWeekOfYear()
    {
        weekOfYear = dayOfYear/7;
    }
    
    /**
     * Method updateLeapYearStatus - updates leap year based on parameter year
     *
     * @param year - A parameter that references year
     * @return The return value true or false based on if it is a leap year
     */
    public boolean updateLeapYearStatus(int year)
    {
        if(year%100 == 0)
        {
            if(year%400 == 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            if(year%4 == 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    /**
     * Method updateLeapYearStatus - overloaded method that does the same thing to the leapYearStatus field
     *
     */
    public void updateLeapYearStatus()
    {
        if(year%100 == 0)
        {
            if(year%400 == 0)
            {
                leapYearStatus = true;
            }
            else
            {
                leapYearStatus = false;
            }
        }
        else
        {
            if(year%4 == 0)
            {
                leapYearStatus = true;
            }
            else
            {
                leapYearStatus = false;
            }
        }
    }

    /**
     * Method dayOfYear - converts the date to day of year based on date
     *
     * @return The return value an integer that holds day out of 365 or 366
     */
    public int dayOfYear()
    {
        //Add number of days based upon month, to existing day, and return value
        if(month == 1)
        {
            return day;
        }
        else if(month == 2)
        {
            return day + 31;
        }
        else if(month == 3)
        {
            if(!leapYearStatus)
            {
                return day + 59;
            }
            else
            {
                return day + 60;
            }
        }
        else if(month == 4)
        {
            if(!leapYearStatus)
            {
                return day + 90;
            }
            else
            {
                return day + 91;
            }
        }
        else if(month == 5)
        {
            if(!leapYearStatus)
            {
                return day + 120;
            }
            else
            {
                return day + 121;
            }
        }
        else if(month == 6)
        {
            if(!leapYearStatus)
            {
                return day + 151;
            }
            else
            {
                return day + 152;
            }
        }
        else if(month == 7)
        {
            if(!leapYearStatus)
            {
                return day + 181;
            }
            else
            {
                return day + 182;
            }
        }
        else if(month == 8)
        {
            if(!leapYearStatus)
            {
                return day + 212;
            }
            else
            {
                return day + 213;
            }
        }
        else if(month == 9)
        {
            if(!leapYearStatus)
            {
                return day + 243;
            }
            else
            {
                return day + 244;
            }
        }
        else if(month == 10)
        {
            if(!leapYearStatus)
            {
                return day + 273;
            }
            else
            {
                return day + 274;
            }
        }
        else if(month == 11)
        {
            if(!leapYearStatus)
            {
                return day + 304;
            }
            else
            {
                return day + 305;
            }
        }
        else
        {
            if(!leapYearStatus)
            {
                return day + 334;
            }
            else
            {
                return day + 335;
            }
        }
    }

    /**
     * Method dayOfYear - does same thing as other overloaded method
     *
     * @param month
     * @param day
     * @param year
     * @return The return value is integer day of year out of 365 or 366
     */
    public int dayOfYear(int month, int day, int year)
    {
        //Add number of days based upon month, to existing day, and return value
        if(month == 1)
        {
            return day;
        }
        else if(month == 2)
        {
            return day + 31;
        }
        else if(month == 3)
        {
            if(!this.updateLeapYearStatus(year))
            {
                return day + 59;
            }
            else
            {
                return day + 60;
            }
        }
        else if(month == 4)
        {
            if(!this.updateLeapYearStatus(year))
            {
                return day + 90;
            }
            else
            {
                return day + 91;
            }
        }
        else if(month == 5)
        {
            if(!this.updateLeapYearStatus(year))
            {
                return day + 120;
            }
            else
            {
                return day + 121;
            }
        }
        else if(month == 6)
        {
            if(!this.updateLeapYearStatus(year))
            {
                return day + 151;
            }
            else
            {
                return day + 152;
            }
        }
        else if(month == 7)
        {
            if(!this.updateLeapYearStatus(year))
            {
                return day + 181;
            }
            else
            {
                return day + 182;
            }
        }
        else if(month == 8)
        {
            if(!this.updateLeapYearStatus(year))
            {
                return day + 212;
            }
            else
            {
                return day + 213;
            }
        }
        else if(month == 9)
        {
            if(!this.updateLeapYearStatus(year))
            {
                return day + 243;
            }
            else
            {
                return day + 244;
            }
        }
        else if(month == 10)
        {
            if(!this.updateLeapYearStatus(year))
            {
                return day + 273;
            }
            else
            {
                return day + 274;
            }
        }
        else if(month == 11)
        {
            if(!this.updateLeapYearStatus(year))
            {
                return day + 304;
            }
            else
            {
                return day + 305;
            }
        }
        else
        {
            if(!this.updateLeapYearStatus(year))
            {
                return day + 334;
            }
            else
            {
                return day + 335;
            }
        }
    }

    /**
     * Method dayOfYearToDate
     *
     * @param dayOfYear A parameter
     * @param year A parameter
     * @param leapYearStatus A parameter
     * @return The return value
     */
    public Date dayOfYearToDate(int dayOfYear, int year, boolean leapYearStatus)
    {
        int newMonth = 0, newDay = 0;
        if(!leapYearStatus)
        {
            if(dayOfYear < 32)
            {
                newMonth = 1;
                newDay = dayOfYear;
            }
            else if(dayOfYear < 60)
            {
                newMonth = 2;
                newDay = dayOfYear - 31;
            }
            else if(dayOfYear < 91)
            {
                newMonth = 3;
                newDay = dayOfYear - 59;
            }
            else if(dayOfYear < 121)
            {
                newMonth = 4;
                newDay = dayOfYear - 90;
            }
            else if(dayOfYear < 152)
            {
                newMonth = 5;
                newDay = dayOfYear - 120;
            }
            else if(dayOfYear < 182)
            {
                newMonth = 6;
                newDay = dayOfYear - 151;
            }
            else if(dayOfYear < 213)
            {
                newMonth = 7;
                newDay = dayOfYear - 181;
            }
            else if(dayOfYear < 244)
            {
                newMonth = 8;
                newDay = dayOfYear - 212;
            }
            else if(dayOfYear < 274)
            {
                newMonth = 9;
                newDay = dayOfYear - 243;
            }
            else if(dayOfYear < 305)
            {
                newMonth = 10;
                newDay = dayOfYear - 273;
            }
            else if(dayOfYear < 335)
            {
                newMonth = 11;
                newDay = dayOfYear - 304;
            }
            else if(dayOfYear < 366)
            {
                newMonth = 12;
                newDay = dayOfYear - 334;
            }
        }
        else
        {
            if(dayOfYear < 32)
            {
                newMonth = 1;
                newDay = dayOfYear;
            }
            else if(dayOfYear < 61)
            {
                newMonth = 2;
                newDay = dayOfYear - 31;
            }
            else if(dayOfYear < 92)
            {
                newMonth = 3;
                newDay = dayOfYear - 60;
            }
            else if(dayOfYear < 122)
            {
                newMonth = 4;
                newDay = dayOfYear - 91;
            }
            else if(dayOfYear < 153)
            {
                newMonth = 5;
                newDay = dayOfYear - 121;
            }
            else if(dayOfYear < 183)
            {
                newMonth = 6;
                newDay = dayOfYear - 152;
            }
            else if(dayOfYear < 214)
            {
                newMonth = 7;
                newDay = dayOfYear - 182;
            }
            else if(dayOfYear < 245)
            {
                newMonth = 8;
                newDay = dayOfYear - 213;
            }
            else if(dayOfYear < 275)
            {
                newMonth = 9;
                newDay = dayOfYear - 244;
            }
            else if(dayOfYear < 306)
            {
                newMonth = 10;
                newDay = dayOfYear - 274;
            }
            else if(dayOfYear < 336)
            {
                newMonth = 11;
                newDay = dayOfYear - 305;
            }
            else if(dayOfYear < 367)
            {
                newMonth = 12;
                newDay = dayOfYear - 335;
            }
        }
        if(newDay == 0)
        {
            newDay++;
        }
        Date newDate = new Date(newMonth, newDay, year);
        return newDate;
    }

    /**
     * Method numMonthToWordMonth
     *
     * @param month A parameter
     * @return The return value
     */
    public String numMonthToWordMonth(int month)
    {
        if(month == 1)
        {
            return "January";
        }
        else if(month == 2)
        {
            return "February";
        }
        else if(month == 3)
        {
            return "March";
        }
        else if(month == 4)
        {
            return "April";
        }
        else if(month == 5)
        {
            return "May";
        }
        else if(month == 6)
        {
            return "June";
        }
        else if(month == 7)
        {
            return "July";
        }
        else if(month == 8)
        {
            return "August";
        }
        else if(month == 9)
        {
            return "September";
        }
        else if(month == 10)
        {
            return "October";
        }
        else if(month == 11)
        {
            return "November";
        }
        else if(month == 12)
        {
            return "December";
        }
        else
        {
            return "Invalid Month";
        }
    }

    /**
     * Method wordMonthToNumMonth
     *
     * @param month A parameter
     * @return The return value
     */
    public int wordMonthToNumMonth(String month)
    {
        if(month == "January")
        {
            return 1;
        }
        else if(month == "February")
        {
            return 2;
        }
        else if(month == "March")
        {
            return 3;
        }
        else if(month == "April")
        {
            return 4;
        }
        else if(month == "May")
        {
            return 5;
        }
        else if(month == "June")
        {
            return 6;
        }
        else if(month == "July")
        {
            return 7;
        }
        else if(month == "August")
        {
            return 8;
        }
        else if(month == "September")
        {
            return 9;
        }
        else if(month == "October")
        {
            return 10;
        }
        else if(month == "November")
        {
            return 11;
        }
        else if(month == "December")
        {
            return 12;
        }
        else
        {
            return 0;
        }
    }
}
