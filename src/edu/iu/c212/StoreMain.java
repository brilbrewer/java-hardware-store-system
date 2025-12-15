package edu.iu.c212;

import java.io.IOException;

public class StoreMain {

    /**
     * this is how we run the program
     * @param args String[]
     * @throws IOException file exception
     */
   public static void main(String[] args) throws IOException {

       Store store = new Store();

       store.takeAction();

       System.out.println("Press enter to continue...");
       try {
           System.in.read();
       }
       catch (Exception e) {
           e.printStackTrace();
       }
    }
}
