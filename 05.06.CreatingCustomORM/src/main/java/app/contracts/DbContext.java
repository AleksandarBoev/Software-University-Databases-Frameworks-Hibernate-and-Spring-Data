package app.contracts;

import java.sql.SQLException;

public interface DbContext<T> { //to understand it better, just replace "T" with "User"
    boolean persist(T entity) throws IllegalAccessException; //will return if the update/insert operation is successful.

    Iterable<T> find() throws SQLException, InstantiationException, IllegalAccessException; //get all entities from a table / get all users from a user table

    Iterable<T> find(String whereClause) throws SQLException, InstantiationException, IllegalAccessException; //get all entities, which match the given condition

    T findFirst() throws SQLException, InstantiationException, IllegalAccessException; //returns the first entity from the table

    T findFirst(String whereClause) throws SQLException, InstantiationException, IllegalAccessException; //returns an entity, which matches the where clause

    T findById(long id) throws IllegalAccessException, SQLException, InstantiationException;

    boolean deleteById(long id) throws SQLException, InstantiationException, IllegalAccessException;
}
