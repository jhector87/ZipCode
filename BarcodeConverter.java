package ZipBarCode;

import java.util.Scanner;

/**
 * Created by jonathanhector on 23.11.16.
 */
public class BarcodeConverter extends ZipBarCodeMain{

    public int num2;    // 10000 digit
    public int num3;    // 1000 digit
    public int num4;    // 100 digit
    public int num5;    // 10 digit
    public int num6;    // 1 digit
    public int checkDig; // check digit
    public static int num;
    public static String temp;
    public static int menu;
    public static int zip;
    public static String bar0;
    public static String bar1;
    public static String bar2;
    public static String bar3;
    public static String bar4;
    public static String bar5;
    public static String bar6;
    public static String bar7;
    public static String bar8;
    public static String bar9;
    public static String str;
    public static int numb;
    public String bar;

    private static String encodeDigit(int digit)
    {
        int myDigit = digit;
        String digitAsBars = "";

        switch(myDigit)
        {
            case 0: digitAsBars = "||:::";
                break;
            case 1: digitAsBars = ":::||";
                break;
            case 2: digitAsBars = "::|:|";
                break;
            case 3: digitAsBars = "::||:";
                break;
            case 4: digitAsBars = ":|::|";
                break;
            case 5: digitAsBars = ":|:|:";
                break;
            case 6: digitAsBars = ":||::";
                break;
            case 7: digitAsBars = "|:::|";
                break;
            case 8: digitAsBars = "|::|:";
                break;
            case 9: digitAsBars = "|:|::";
                break;
        }

        return digitAsBars;
    }
    public BarcodeConverter(int num) {
        switch (num){
            case 0:
                bar0 = "||:::";
                break;
            case 1:
                bar1 = ":::||";
                break;
            case 2:
                bar2 = "::|:|";
                break;
            case 3:
                bar3 = "::||:";
                break;
            case 4:
                bar4 = ":|::|";
                break;
            case 5:
                bar5 = ":|:|:";
                break;
            case 6:
                bar6 = ":||::";
                break;
            case 7:
                bar7 = "|:::|";
                break;
            case 8:
                bar8 = "|::|:";
            case 9:
                bar9 = "|:|::";
                default:
                    System.out.println("Sorry number number not found");
        }
    }

    public void getBar(){
        System.out.println("Enter the ZIP code: ");
        Scanner input = new Scanner(System.in);
        if((num < 01001) || (num > 99950)) {
            System.out.println("**** ERROR ****");
            System.out.println("The ZIP code must be between 01001 and 99950");
            System.out.println("Please,enter the correct zip : ");

            while(input.hasNext()){
                BarcodeConverter numBar = new BarcodeConverter(num);
                System.out.println(numBar.bar);
            }
        } else{
            System.out.println("There is a mistake somewhere!");

        }
    }

    public static void getNumber(String temp){
        System.out.println("");
        System.out.println(temp);
        if(temp.equals(bar0)){
            numb = 0;
        }
        if(temp.equals(bar1)){
            numb = 1;
        }
        if(temp.equals(bar2)){
            numb = 2;
        }
        if(temp.equals(bar3)){
            numb = 3;
        }
        if(temp.equals(bar4)){
            numb = 4;
        }
        if(temp.equals(bar5)){
            numb = 5;
        }
        if(temp.equals(bar6)){
            numb = 6;
        }
        if(temp.equals(bar7)){
            numb = 7;
        }
        if(temp.equals(bar8)){
            numb = 8;
        }
        if(temp.equals(bar9)){
            numb = 9;
        }
        System.out.print(numb);
    }


    public void getDigit(int num) {
        num2 = num / 10000;
        num3 = ((num / 1000) - num2 * 10);
        num4 = (num / 100 - (num2 * 100 + num3 * 10));
        num5 = (num / 10 - (num2 * 1000 + num3 * 100 + num4 * 10));
        num6 = (num - (num2 * 10000 + num3 * 1000 + num4 * 100 + num5 * 10));

        checkDig = 100 - (num2 + num3 + num4 + num5 + num6);
        System.out.println(checkDig);

        while (checkDig > 10) {
            checkDig -= 10;
        }
        System.out.println("**** BARCODE ****");
        System.out.print("|");
        getBar();
        getBar();
        getBar();
        getBar();
        getBar();
        getBar();
        System.out.print("|");

    }
}
