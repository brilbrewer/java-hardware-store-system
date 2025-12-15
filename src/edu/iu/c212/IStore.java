package edu.iu.c212;

import edu.iu.c212.models.Item;
import edu.iu.c212.models.Staff;

import java.io.IOException;
import java.util.List;

/**
 * this is the interface for the program where it declares methods that have to be overridden in the store class
 */
public interface IStore {
    List<Item> getItemsFromFile() throws IOException;

    List<Staff> getStaffFromFile() throws IOException;

    void saveItemsFromFile();
    void saveStaffFromFile();
    void takeAction() throws IOException;
}
