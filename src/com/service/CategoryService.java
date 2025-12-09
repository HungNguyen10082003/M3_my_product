package service;

import entity.Category;
import repo.CategoryRepo;
import repo.ICategoryRepo;

import java.util.List;

public class CategoryService {
    private ICategoryRepo categoryRepo = new CategoryRepo();

    public List<Category> getAll() {
        return categoryRepo.getAll();
    }

    public Category getById(int id) {
        return categoryRepo.getById(id);
    }
}