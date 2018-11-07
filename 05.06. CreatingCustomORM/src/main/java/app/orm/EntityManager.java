package app.orm;

import app.annotations.Column;
import app.annotations.Entity;
import app.annotations.PrimaryKey;
import app.contracts.DbContext;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class EntityManager<T> implements DbContext<T> {
    private static final String WHERE_CLAUSE = "WHERE %s";
    private static final String SELECT_ALL_FROM = "SELECT * FROM %s";
    private static final String INSERT_INTO = "INSERT INTO %s(%s) VALUES (%s)";
    private static final String UPDATE = "UPDATE %s SET %s WHERE %s";
    private static final String CREATE_TABLE_TEMPLATE = "CREATE TABLE %s (%s)";
    private static final String ALTER_TABLE_TEMPLATE = "ALTER TABLE %s %s";
    private static final String DELETE_TEMPLATE = "DELETE FROM %s WHERE %s"; //adding the WHERE because there might be a delete on all records, and that's bad

    private Connection connectionToDb;
    private PreparedStatement sqlStatement;
    private Class<T> table; //Class<T> table = User.class in this app

    public EntityManager(Connection connectionToDb, Class<T> table) throws SQLException, InstantiationException, IllegalAccessException {
        this.connectionToDb = connectionToDb;
        this.table = table;

        if (this.tableExists()) {
            this.updateTable();
        } else {
            this.createTable();
        }
    }

    @Override
    public boolean persist(T entity) throws IllegalAccessException {
        Field primaryKeyField = this.getPrimaryKeyField(entity.getClass());
        primaryKeyField.setAccessible(true);
        Object value = primaryKeyField.get(entity);
        primaryKeyField.setAccessible(false);

        if (value == null || (long)value <= 0) {
            return this.doInsert(entity, primaryKeyField);
        }

        return this.doUpdate(entity, primaryKeyField);
    }

    @Override
    public Iterable<T> find() throws SQLException, InstantiationException, IllegalAccessException {
        return this.find("");
    }

    @Override
    public Iterable<T> find(String whereClause) throws SQLException, InstantiationException, IllegalAccessException {
        String query = String.format(SELECT_ALL_FROM + " " + WHERE_CLAUSE,
                this.getTableName(), whereClause);

        if (whereClause.equals(""))
            query = query.replace("WHERE", "");

        Collection<T> collection = new ArrayList<>();
        this.sqlStatement = this.connectionToDb.prepareStatement(query);

        ResultSet resultSet = this.sqlStatement.executeQuery();
        Collection<T> result = new ArrayList<>();

        while (resultSet.next()) {
            T currentEntity = this.mapToEntityFromDb(resultSet);
            result.add(currentEntity);
        }

        return result;
    }

    @Override
    public T findFirst() throws SQLException, InstantiationException, IllegalAccessException {
        String query = String.format(SELECT_ALL_FROM, this.getTableName());
        this.sqlStatement = this.connectionToDb.prepareStatement(query);

        ResultSet resultSet = this.sqlStatement.executeQuery();
        if (!resultSet.next())
            return null;

        T entity = this.mapToEntityFromDb(resultSet);

        return entity;
    }

    @Override
    public T findFirst(String whereClause) throws SQLException, InstantiationException, IllegalAccessException {
        String query = String.format(SELECT_ALL_FROM + " " + WHERE_CLAUSE,
                this.getTableName(), whereClause);

        this.sqlStatement = this.connectionToDb.prepareStatement(query);

        ResultSet resultSet = this.sqlStatement.executeQuery();

        if (!resultSet.next())
            return null;

        return mapToEntityFromDb(resultSet);
    }

    @Override
    public T findById(long id) throws IllegalAccessException, SQLException, InstantiationException {
        return this.findFirst("id = " + id);
    }

    /*
    Can add delete entity and delete with a provided where clause, but it's not needed
     */
    @Override
    public boolean deleteById(long id) throws SQLException, InstantiationException, IllegalAccessException {
        if (findById(id) == null)
            return false;

        String query = String.format(DELETE_TEMPLATE, this.getTableName(), "id = " + id);
        this.sqlStatement = this.connectionToDb.prepareStatement(query);
        this.sqlStatement.executeUpdate();
        return true;
    }

    private boolean tableExists() throws SQLException {
        String query = String
                .format(
                        "SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = '%s'",
                        this.getTableName()
                );

        PreparedStatement preparedStatement = this.connectionToDb.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }

    private void createTable() throws SQLException {
        Field primaryKeyField = getPrimaryKeyField(this.table);

        String primaryKeyNameInDb = primaryKeyField.getAnnotation(PrimaryKey.class).nameInDb();
        String primaryKeyType = this.getSqlType(primaryKeyField);
        //if the key was something different than INT or LONG then AUTO_INCREMENT wouldn't work
        String primaryKeyAdditionalAttributes = "PRIMARY KEY AUTO_INCREMENT";

        String primaryKeyInfo = String.format("%s %s %s",
                primaryKeyNameInDb, primaryKeyType, primaryKeyAdditionalAttributes);

        Map<String, String> columnsNamesTypes = this.getColumnsNamesDataTypes();
        List<String> columnsNamesTypesCombined =
                columnsNamesTypes.entrySet().stream()
                .map(kvp -> kvp.getKey() + " " + kvp.getValue())
                .collect(Collectors.toList());

        String columnsInfo = String.join(", ", columnsNamesTypesCombined);
        String info = primaryKeyInfo + ", " + columnsInfo;

        String query = String.format(CREATE_TABLE_TEMPLATE, this.getTableName(), info);

        this.sqlStatement = this.connectionToDb.prepareStatement(query);

        this.sqlStatement.execute();
    }

    private void updateTable() throws IllegalAccessException, SQLException, InstantiationException {
        Map<String, String> columnsNamesTypes = this.getColumnsNamesDataTypes();

        List<String> dbColumnNames = this.getDatabaseTableColumnsNames();

        this.addNewColumnsToDb(columnsNamesTypes, dbColumnNames);
        this.removeColumns(columnsNamesTypes, dbColumnNames);
    }

    /*
    Checks which columns are in the table in the database and if they are NOT in the java
    version of the table, then they are removed.
     */
    private void removeColumns(Map<String, String> columnsNamesTypes, List<String> dbColumnNames) throws SQLException {
        List<String> columnNamesWhichAreNotInDb = dbColumnNames.stream()
                .filter(c -> !columnsNamesTypes.containsKey(c) && !c.toLowerCase().contains("id"))
                .collect(Collectors.toList());

        if (!columnNamesWhichAreNotInDb.isEmpty()) {
            for (int i = 0; i < columnNamesWhichAreNotInDb.size(); i++) {
                columnNamesWhichAreNotInDb.set(i, "DROP COLUMN " + columnNamesWhichAreNotInDb.get(i));
            }

            String dropCommands = String.join(", ", columnNamesWhichAreNotInDb);
            String query = String.format(ALTER_TABLE_TEMPLATE, this.getTableName(), dropCommands);

            this.sqlStatement = this.connectionToDb.prepareStatement(query);
            this.sqlStatement.execute();
        }
    }

    /*
    Checks which column names are in the java version of the table and if they are not
    in the database, it ads them.
     */
    private void addNewColumnsToDb(Map<String, String> columnsNamesTypes, List<String> dbColumnNames) throws SQLException {
        List<String> newColumnNamesTypes =
                columnsNamesTypes.entrySet().stream()
                        .filter(kvp -> !dbColumnNames.contains(kvp.getKey())) //remove the column names which already exists
                        .map(kvp -> kvp.getKey() + " " + kvp.getValue())
                        .collect(Collectors.toList());

        for (int i = 0; i < newColumnNamesTypes.size(); i++) {
            newColumnNamesTypes.set(i, "ADD COLUMN " + newColumnNamesTypes.get(i));
        }

        String addColumns = String.join(", ", newColumnNamesTypes);
        if (!addColumns.equals("")) {
            String query = String.format(ALTER_TABLE_TEMPLATE, this.getTableName(), addColumns);

            this.sqlStatement = this.connectionToDb.prepareStatement(query);
            this.sqlStatement.execute();
        }
    }

    private boolean doInsert(T entity, Field primaryKeyField) throws IllegalAccessException {
        try {
            Map<String, String> namesValues = this.getColumnNamesValues(entity);

            String columns = String.join(", ", namesValues.keySet());
            String values = String.join(", ", namesValues.values());

            String query = String.format(INSERT_INTO, this.getTableName(), columns, values);
            this.sqlStatement = this.connectionToDb.prepareStatement(query);
            this.sqlStatement.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean doUpdate(T entity, Field primaryKeyField) throws IllegalAccessException {
        try {
            primaryKeyField.setAccessible(true);
            long primaryKeyValue = (long) primaryKeyField.get(entity);
            primaryKeyField.setAccessible(false);

            Map<String, String> namesValues = this.getColumnNamesValues(entity);

            List<String> nameEqualsValue = new ArrayList<>();
            for (Map.Entry<String, String> nameValue : namesValues.entrySet()) {
                nameEqualsValue.add(String.format("%s = %s", nameValue.getKey(), nameValue.getValue()));
            }

            String setClause = String.join(", ", nameEqualsValue);

            String whereClause = "id = " + primaryKeyValue;

            //UPDATE users SET user_name = 'wat', age = 3000 WHERE id = 7; //should look something like this
            String updateQuery = String.format(UPDATE, this.getTableName(), setClause, whereClause);
            this.sqlStatement = this.connectionToDb.prepareStatement(updateQuery);
            this.sqlStatement.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getTableName() {
        if (!this.table.isAnnotationPresent(Entity.class)) {
            throw new UnsupportedOperationException("'Entity' annotation is missing from the class!");
        }

        Entity entity = (Entity) this.table.getAnnotation(Entity.class);
        return entity.nameInDb();
    }

    private Field getPrimaryKeyField(Class entityClass) {
        String tableName = this.getTableName();

        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(PrimaryKey.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("There is NO id field in this class: " + tableName));
    }

    private Field[] getColumnFields() {
        Field[] result = Arrays.stream(this.table.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Column.class))
                .toArray(n -> new Field[n]);

        return result;
    }

    private T mapToEntityFromDb(ResultSet resultSet) throws IllegalAccessException, InstantiationException, SQLException {
        Field[] columnFields = this.getColumnFields();

        T entity = this.table.newInstance();

        Field primaryKeyField = this.getPrimaryKeyField(entity.getClass());

        PrimaryKey primaryKeyAnnotation = primaryKeyField.getAnnotation(PrimaryKey.class);
        String primaryKeyColumnNameInDb = primaryKeyAnnotation.nameInDb();
        long primaryKeyValue = resultSet.getLong(primaryKeyColumnNameInDb);

        primaryKeyField.setAccessible(true);
        primaryKeyField.set(entity, primaryKeyValue);

        for (int i = 0; i < columnFields.length; i++) {
            String columnNameInDb = columnFields[i].getAnnotation(Column.class).nameInDb();
            String columnType = columnFields[i].getType().getSimpleName();

            columnFields[i].setAccessible(true);

            switch (columnType.toLowerCase()) {
                case "string":
                    columnFields[i].set(entity, resultSet.getString(columnNameInDb));
                    break;

                case "int": case "integer":
                    columnFields[i].set(entity, resultSet.getInt(columnNameInDb));
                    break;

                case "long":
                    columnFields[i].set(entity, resultSet.getLong(columnNameInDb));
                    break;
            }
            columnFields[i].setAccessible(false);
        }

        return entity;
    }

    private Map<String, String> getColumnNamesValues(T entity) throws IllegalAccessException {
        //HashMap would work too, but just for the sake of debugging, LinkedHashMap will be used
        Map<String, String> result = new LinkedHashMap<>();
        Field[] columnFields = this.getColumnFields();

        for (int i = 0; i < columnFields.length; i++) {
            columnFields[i].setAccessible(true);

            String columnNameInDb = columnFields[i].getAnnotation(Column.class).nameInDb();
            String columnValue = "" + columnFields[i].get(entity);
            String columnType = columnFields[i].getType().getSimpleName();

            columnFields[i].setAccessible(false);

            if (columnType.equals("String")) {
                columnValue = "'" + columnValue + "'";
            }

            result.put(columnNameInDb, columnValue);
        }

        return result;
    }

    private Map<String, String> getColumnsNamesDataTypes() {
        Map<String, String> nameDataType = new LinkedHashMap<>();
        Field[] columns = this.getColumnFields();

        for (int i = 0; i < columns.length; i++) {
            String columnNameInDb = columns[i].getAnnotation(Column.class).nameInDb();
            String columnDataTypeInDb = this.getSqlType(columns[i]);

            nameDataType.put(columnNameInDb, columnDataTypeInDb);
        }

        return nameDataType;
    }

    private String getSqlType(Field field) { //does not cover all data types, but will do for now
        String javaDataType = field.getType().getSimpleName();
        String correspondingSqlDataType = "";

        switch (javaDataType.toLowerCase()) {
            case "string":
                correspondingSqlDataType = "VARCHAR(50)";
                break;

            case "int": case "integer": case "long":
                correspondingSqlDataType =  "INT(11)";
                break;
        }

        return correspondingSqlDataType;
    }

    private List<String> getDatabaseTableColumnsNames() throws SQLException {
        String query = String
                .format(
                        "SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = '%s'",
                        this.getTableName()
                );

        PreparedStatement preparedStatement = this.connectionToDb.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        List<String> columnsNames = new ArrayList<>();

        while (resultSet.next()) {
            columnsNames.add(resultSet.getString(1));
        }

        return columnsNames;
    }

}

/*
What if some columns types have changed? Table in db needs to be modified! But at this point
I don't know how to get the column type from a table in the db.
 */