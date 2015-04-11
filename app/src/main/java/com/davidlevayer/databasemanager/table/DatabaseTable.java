package com.davidlevayer.databasemanager.table;

/**
 * Created by David Levayer on 11/04/15.
 */
public interface DatabaseTable<U> {

    /**
     * Return a SQLite query, formatted as a string, which allows to create an empty table
     * @return SQLite create query
     */
    public String getConstructionQuery();

    /**
     * Return the name of the table. It must be unique for each table of your database.
     * @return the table's name
     */
    public String getTableName();

    /**
     * Return a short description of this table. It may contains information about the table purpose
     * and eventually a list of the table's columns.
     * @return the table's description
     */
    public String getTableDescription();

    /**
     * Add an entry to the table
     * @param item the entry to add to the table
     */
    public void add(U item);

}
