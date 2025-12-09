package service;

import entity.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAll();
    boolean add(Product product);
    boolean delete(int id);
    boolean edit(Product product);
    Product showDetail(int id);
    List<Product> getByCategoryId(int categoryId);
}