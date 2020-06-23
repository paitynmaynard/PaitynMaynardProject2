package data;

import java.util.List;
/**
 *  Project 2:<br>
 * <br>
 *  GenericDAO
 *
 *  <br> <br>
 *  Created: <br>
 *     May 11, 2020 Paityn Maynard<br>
 *     With assistance from: <br>
 *  Modifications: <br>
 *
 * <br>
 *  @author
 *  @version 11 May 2020
 */
public interface GenericDAO<O,ID> {//Start of GenericDAO interface
    /**
     * Finds and returns all objects
     * @return a list of all objects, or <code>null</code> if an error occurred while retrieving
     *  those objects from the database.
     */
    List<O> findAll();

    /**
     * Adds a new object to the database.
     * @param newObj The object to be added
     * @return <code>true</code> if the object was successfully added, <code>false</code> otherwise.
     */
    boolean add(O newObj);

    /**
     * Finds and returns the object with the specified ID.
     * @param id the ID of the object to find
     * @return the object with that ID, or <code>null</code> if such an object could not be found.
     */
    O findByID(ID id);

    /**
     * Updates the object with the specified ID.
     * @param id the ID of the object to be updated
     * @param newObj the new object that will replace the existing object in the database
     * @return <code>true</code> if an update occurred successfully, <code>false</code> otherwise.
     */
    boolean update(ID id, O newObj);

    /**
     * Removes the object with the specified ID.
     * This method returns <code>true</code> even if that object does not exist in the database
     * (and hence there is nothing to remove).
     * @param id the ID of the object to remove
     * @return <code>true</code> if the deletion was successful, <code>false</code> otherwise.
     */
    boolean remove(ID id);

}//End of GenericDAO interface
