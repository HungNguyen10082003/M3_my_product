package repo;

import entity.Category;

import java.util.List;

public interface ICategoryRepo {
    List<Category> getAll();
    Category getById(int id);
}