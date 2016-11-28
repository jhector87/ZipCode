package ZipBarCode;

/**
 * Created by jonathanhector on 23.11.16.
 */
public class ZipCodeConverter {
    /**
     * Checks to make sure the zip code entered is a valid format.
     * @param zip a 5 numerical digit String, must be >= 0 when in integer form
     * @return true or false depending on validity of zip code entered
     */
    public static boolean isValidZipCode(String zip)
    {
        String myZip = zip;

        if(myZip.length() != 5 && myZip.length() != 9 && myZip.length() != 10) // makes sure zip is correct length
            return false;

        if(myZip.length() == 10)
        {
            if(myZip.substring(5, 6).compareTo("-") != 0)
                return false;
            else
                myZip = formatZip(myZip);
        }

        try // cannot contain letters or '+' or such, must be an integer
        {
            Integer.parseInt(myZip);
        }
        catch(NumberFormatException e)
        {
            return false;
        }

        if(Integer.parseInt(myZip) < 0) // zip cannot be negative
            return false;

        if(myZip.substring(0,1).equals("+")) 	// does the compiler see '+' as meaning this number is positive?
            return false;					// could not think of another way to check for this

        return true;
    }

    /**
     * Checks to make to make sure the barcode entered is a valid format
     * @param zip a 32 character length String barcode representing a zip code
     * @return true or false depending on validity of barcode
     */
    public static boolean isValidBarcode(String zip)
    {
        int zipLength = zip.length();

        if(zipLength != 32 && zipLength != 52) // makes sure it is of length 32 or 52
            return false;

        if(!(zip.substring(0, 1).compareTo("|") == 0 && zip.substring(zip.length() - 1).compareTo("|") == 0))
            return false;

        int checkDigit = decodeHelper(zip) % 10; // getting check digit after barcode has been decoded
        int zipCode = decodeHelper(zip) / 10; // takes everything but check digit
        //check for invalid check dig here?
        if(isValidCheckDigit(checkDigit, zipCode)) // makes sure the check digit is valid
            return true;
        else
            return false;
    }

    /**
     * Takes the zip code and if length is 10, takes out the '-' at index 5 if it is there
     * @param zip
     * @return
     */
    public static String formatZip(String zip)
    {
        String myZip = zip;
        if(myZip.length() == 10)
        {
            if(myZip.indexOf('-') == 5)
                myZip = zip.substring(0, 5) + zip.substring(6);
        }
        return myZip;
    }


    /**
     * Checks if check digit is a correct number for rest of zip code
     * @param checkDigit , number from 0 - 9
     * @param zipCode zipcode without check digit, must be a positive integer of 5 digits in length
     * @return true or false depending on validty of check digit
     */
    private static boolean isValidCheckDigit(int checkDigit, int zipCode)
    {
        int zipTotal = 0;
        int modZipCode = zipCode;
        int zipDigit;

        for(int i = 0; i < Integer.toString(zipCode).length(); i++)
        {
            zipDigit = modZipCode % 10;
            zipTotal += zipDigit; // finds sum of the ints in zipCode
            modZipCode /= 10;
        }

        if((zipTotal + checkDigit) % 10 == 0 && zipTotal >= 0) // makes sure that zipTotal + checkDigit is a multiple of ten
            return true;
        else
            return false;

    }

    /**
     * Decodes the barcode given, converts to a zip code w/ check digit
     * @param zip zip a 32 character length String barcode representing a zip code
     * @return zipCodeWithCheckDigit barcode converted to zip code w/ check digit, must be >= 0
     * & 6 integers in length(because of check digit)
     */
    private static int decodeHelper(String zip)
    {
        String modBarcode = zip.substring(1, zip.length()-1); // peels off enclosing bars
        String fiveBars = "";
        int zipCodeDigit = 0;
        double zipCodeWithCheckDigit = 0;
        int setsOfFiveDigits = modBarcode.length()/5;

        for(double i = 0; i < setsOfFiveDigits; i++)
        {
            fiveBars = modBarcode.substring(0, 5);
            zipCodeDigit = decodeFiveBars(fiveBars); // decodes individual sets of 5 bars
            zipCodeWithCheckDigit = (zipCodeWithCheckDigit * 10) + zipCodeDigit; // creating zip code w/ check digit
            modBarcode = modBarcode.substring(5);
        }
        return (int)zipCodeWithCheckDigit;
    }

    /**
     * Converts a postal bar code into zip code form
     * @param zip zip a 32 character length String barcode representing a zip code
     * @return zipCodeString barcode as a 5 digit integer, must be >= 0
     */
    public static String decodeBarcode(String zip)
    {
        String myZip = zip;
        int zipCode = decodeHelper(myZip) / 10;
        String zipCodeString = Integer.toString(zipCode);
        int zipCodeLength = (zip.length() - 7)/5; //finds out what length the zipcode should be// after peeling off frame bars, check digit and decoding
        int end = zipCodeLength-zipCodeString.length();

        //pads ZipCodeString with appropriate amount of zeroes
        for(int i = 0; i < end; i++) // doesn't start at 0 because ZipCodeString will at least have 1 char in it
            zipCodeString = "0" + zipCodeString;

        if(zip.length() == 52)
            zipCodeString = zipCodeString.substring(0, 5) + "-" + zipCodeString.substring(5);
        return zipCodeString;
    }

    /**
     * Decodes sets of 5 bars from a full postal bar code
     * @param bars a 5 char length String containing either '|'s or ':'s
     * @return zipDigit an int of what the set of 5 bars represent, must be 0 through 9, -1 if invalid
     */
    private static int decodeFiveBars(String bars)
    {
        String myBars = bars;
        int zipDigit = -100000; // if bars does not represent 0 - 9, set to -100 to set off an error message

        switch(myBars)
        {
            case "||:::": zipDigit = 0; break;
            case ":::||": zipDigit = 1; break;
            case "::|:|": zipDigit = 2; break;
            case "::||:": zipDigit = 3; break;
            case ":|::|": zipDigit = 4; break;
            case ":|:|:": zipDigit = 5; break;
            case ":||::": zipDigit = 6; break;
            case "|:::|": zipDigit = 7; break;
            case "|::|:": zipDigit = 8; break;
            case "|:|::": zipDigit = 9; break;
        }

        return zipDigit;
    }


    /**!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * Creates a postal bar code from a zip code
     * @param zip a 5 digit long int, must be >= 0
     * @return formatted barcode representing zip code and check digit
     */
    public static String encodeZipCode(String zip)
    {
        zip = formatZip(zip); // taking out '-' if it's there
        int modZipCode = Integer.parseInt(zip); // named modZipCode because I will be modifying it very shortly in the for loop
        int zipDigit = 0;
        int total = 0;
        String barcode = "";

        for(int i = 0; i < zip.length(); i++)
        {
            zipDigit = modZipCode % 10; // "extracting" the zip's digits
            total += zipDigit;
            modZipCode /= 10;

            barcode = encodeDigit(zipDigit) + barcode; // converting to bars
        }

        barcode += encodeDigit(findCheckDigit(total)); // finds check digit and encodes



        return "|" + barcode + "|";
    }

    /**
     * Finds a check digit for a given zip code
     * @param total sum of all ints in the zip code, must be >= 0 & <= 45
     * @return checkDigit the digit that when summed with total, that result is a multiple of 10
     */
    private static int findCheckDigit(int total)
    {
        int checkDigit;

        for(checkDigit = 0; !((total + checkDigit) % 10 == 0); checkDigit++); // finding check digit

        return checkDigit;
    }

    /**
     * Creates a set of bars from a single digit from zip code
     * @param digit a single int from the zip code, must be from 0 - 9
     * @return digitAsBars the set of 5 bars representing a zip code digit
     */
    private static String encodeDigit(int digit)
    {
        int myDigit = digit;
        String digitAsBars = "";

        switch(myDigit)
        {
            case 0: digitAsBars = "||:::";
            break;
            case 1: digitAsBars = ":::||"; break;
            case 2: digitAsBars = "::|:|"; break;
            case 3: digitAsBars = "::||:"; break;
            case 4: digitAsBars = ":|::|"; break;
            case 5: digitAsBars = ":|:|:"; break;
            case 6: digitAsBars = ":||::"; break;
            case 7: digitAsBars = "|:::|"; break;
            case 8: digitAsBars = "|::|:"; break;
            case 9: digitAsBars = "|:|::"; break;
        }

        return digitAsBars;
    }
}
