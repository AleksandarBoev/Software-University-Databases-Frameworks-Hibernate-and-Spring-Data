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

public class EntityManager<T> implements DbContext<T> {
    private static final String WHERE_CLAUSE = "WHERE %s";
    private static final String EQUALITY_MESSAGE = "%s = %s";
    private static final String SELECT_ALL_FROM = "SELECT * FROM %s";
    private static final String INSERT_INTO = "INSERT INTO %s(%s) VALUES (%s)";
    private static final String UPDATE = "UPDATE %s SET %s WHERE %s";

    private Connection connectionToDb;
    private PreparedStatement sqlStatement;
    private Class<T> table;

    public EntityManager(Connection connectionToDb, Class<T> table) {
        this.connectionToDb = connectionToDb;
        this.table = table;
    }

    @Override
    public boolean persist(T entity) throws IllegalAccessException {
        Field primaryKeyField = this.getPrimaryKeyField(entity.getClass());
        primaryKeyField.setAccessible(true);
        Object value = primaryKeyField.get(entity);

        if (value == null || (long)value <= 0) {
            return this.doInsert(entity, primaryKeyField);
        }

        return this.doUpdate(entity, primaryKeyField);
    }

    @Override
    public Iterable<T> find() throws SQLException, InstantiationException, IllegalAccessException { //Class<T> table = User.class
        Collection<T> collection = new ArrayList<>();
        String query = String.format(SELECT_ALL_FROM, this.getTableName());
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
    public Iterable<T> find(String whereClause) throws SQLException, InstantiationException, IllegalAccessException {
        String query = String.format(SELECT_ALL_FROM + " " + WHERE_CLAUSE,
                this.getTableName(), whereClause);

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


    //using just Class and not Class<T>, because entity.getClass works for Class, but not for Class<T>
    private Field getPrimaryKeyField(Class entityClass) {
        String tableName = this.getTableName();

        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(PrimaryKey.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("There is NO id field in this class: " + tableName));
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
            columnFields[i].setAccessible(true);

            String columnNameInDb = columnFields[i].getAnnotation(Column.class).nameInDb();
            String columnType = columnFields[i].getType().getSimpleName();

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
        }

        return entity;
    }

    private Field[] getColumnFields() {
        Field[] result = Arrays.stream(this.table.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Column.class))
                .toArray(n -> new Field[n]);

        return result;
    }

    private Map<String, String> getColumnNamesValues(T entity) throws IllegalAccessException {
        //HashMap would work too, but just for the sake of debugging, LinkedHashMap will be used
        Map<String, String> result = new LinkedHashMap<>();
        Field[] columnFields = this.getColumnFields(); //TODO problem! method does not work!

        for (int i = 0; i < columnFields.length; i++) {
            columnFields[i].setAccessible(true);

            String columnNameInDb = columnFields[i].getAnnotation(Column.class).nameInDb();
            String columnValue = "" + columnFields[i].get(entity);
            String columnType = columnFields[i].getType().getSimpleName();

            if (columnType.equals("String")) {
                columnValue = "'" + columnValue + "'";
            }

            result.put(columnNameInDb, columnValue);
        }

        return result;
    }
}
