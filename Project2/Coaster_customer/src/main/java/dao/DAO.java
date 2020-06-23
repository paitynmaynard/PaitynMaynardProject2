package dao;

import java.util.ArrayList;

/**
 *  Project 2:<br>
 * <br>
 *  The DAO interface serves as an interface for a generic repository structure and behavior base.
 *  DAO subclasses can be tailored to use their own choice of data/object type to obtain information from
 *    an appropriate database.
 *  Generic T can be replaced by any primary data/obejct type required/expected for a system.
 *  Generic ID can be replaced by the appropriate data type used for a primary key.
 *
 *  <br> <br>
 *  Created: <br>
 *     11 May 2020, Barthelemy Martinon<br>
 *     With assistance from: August Duet<br>
 *  Modifications: <br>
 *     11 May 2020, Barthelemy Martinon,    Created class.
 * <br>
 *  @author Barthelemy Martinon   With assistance from: August Duet
 *  @version 11 May 2020
 */
public interface DAO<T, ID> {
    T findById(ID id);
    ArrayList<T> findAll();
    ID save(T obj);
    void update(T newObj, ID id);
    void delete(T obj);
}

