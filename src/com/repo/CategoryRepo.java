package repo;

import entity.Category;
import util.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepo implements ICategoryRepo {
    private final String SELECT_ALL = "select * from categories";
    private final String SELECT_ID = "select * from categories where id = ?";

    @Override
    public List<Category> getAll() {
        List<Category> categoryList = new ArrayList<>();
        try(Connection connection = ConnectDB.getConnectDB()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                categoryList.add(new Category(id, name));
            }
        } catch (SQLException e) {
            System.out.println("Fail Select All Categories !!");
        }
        return categoryList;
    }

    @Override
    public Category getById(int id) {
        try(Connection connection = ConnectDB.getConnectDB()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                String name = resultSet.getString("name");
                return new Category(id, name);
            }
        } catch (SQLException e) {
            System.out.println("Fail Select Category By Id !!");
        }
        return null;
    }
}