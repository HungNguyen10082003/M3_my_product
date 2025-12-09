package service;

import entity.Product;
import repo.IProductRepo;
import repo.ProductRepo;

import java.util.List;

public class ProductService implements IProductService{
    private IProductRepo productRepo = new ProductRepo();

    @Override
    public List<Product> getAll() {
        return productRepo.getAll();
    }

    @Override
    public boolean add(Product product) {
        // Kiểm tra xem sản phẩm đã tồn tại hay chưa
        for (Product p : productRepo.getAll()){
            if (p.getName().equalsIgnoreCase(product.getName()) &&
                    p.getDescription().equalsIgnoreCase(product.getDescription())){
                return false; // Sản phẩm đã tồn tại
            }
        }
        return productRepo.add(product); // Thêm sản phẩm mới
    }

    @Override
    public boolean delete(int id) {
        return productRepo.delete(id);
    }

    @Override
    public boolean edit(Product product) {

        for (Product p : productRepo.getAll()){
            if (p.getId() == product.getId()){
                return productRepo.edit(product);
            }
        }
        return false;
    }

    @Override
    public Product showDetail(int id) {
        return productRepo.showDetail(id);
    }

    @Override
    public List<Product> getByCategoryId(int categoryId) {
        return productRepo.getByCategoryId(categoryId);
    }
}