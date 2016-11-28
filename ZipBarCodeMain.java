package ZipBarCode;

import java.util.Scanner;

/**
 * Created by jonathanhector on 23.11.16.
 */
public class ZipBarCodeMain {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Please enter a 5 digit zip code or a 32 character barcode.");
        String zipInput = in.nextLine();

        //checks to make sure the zip code or barcode entered is valid
        while(!(ZipCodeConverter.isValidZipCode(zipInput) || ZipCodeConverter.isValidBarcode(zipInput)))
        {
            System.out.println("\nYou did not enter a valid zip code or barcode, please re-enter.\n");
            zipInput = in.nextLine();
        }

        if(ZipCodeConverter.isValidZipCode(zipInput))//if encoding zip, cast string to int and pass through
        {
            System.out.println(zipInput + " as a barcode is " + ZipCodeConverter.encodeZipCode(zipInput) + ".\n");
        }
        else
            System.out.println(zipInput + " as a zip code is " + ZipCodeConverter.decodeBarcode(zipInput));
    }


}
