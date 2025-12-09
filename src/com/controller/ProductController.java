package controller;

import entity.Category;
import entity.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CategoryService;
import service.IProductService;
import service.ProductService;
import util.Validate;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "productController",value = "/products")
public class ProductController extends HttpServlet {
    private IProductService productService = new ProductService();
    private CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action==null){
            action = "";
        }
        switch (action){
            case "add":
                showFormAdd(req,resp);
                break;
            case "detail":
                showDetail(req,resp);
                break;
            case "edit":
                showFormEdit(req,resp);
                break;
            case "filter":
                filterByCategory(req,resp);
                break;
            default:
                showList(req,resp);
                break;
        }
    }

    private void filterByCategory(HttpServletRequest req, HttpServletResponse resp) {
        String categoryIdStr = req.getParameter("categoryId");
        if (categoryIdStr == null || categoryIdStr.isEmpty()) {
            showList(req, resp);
            return;
        }
        try {
            int categoryId = Integer.parseInt(categoryIdStr);
            List<Product> productList = productService.getByCategoryId(categoryId);
            List<Category> categoryList = categoryService.getAll();
            req.setAttribute("productList", productList);
            req.setAttribute("categoryList", categoryList);
            req.setAttribute("selectedCategory", categoryId);
            req.getRequestDispatcher("/view/product/form.jsp").forward(req,resp);
        } catch (NumberFormatException e) {
            showList(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showFormEdit(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        Product product = productService.showDetail(id);
        List<Category> categoryList = categoryService.getAll();
        req.setAttribute("product", product);
        req.setAttribute("categoryList", categoryList);
        try {
            req.getRequestDispatcher("/view/product/edit.jsp").forward(req,resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showDetail(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        Product product = productService.showDetail(id);
        Category category = categoryService.getById(product.getCategoryId());
        req.setAttribute("product",product);
        req.setAttribute("category", category);
        try {
            req.getRequestDispatcher("/view/product/detail.jsp").forward(req,resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) {
        List<Product> productList = productService.getAll();
        List<Category> categoryList = categoryService.getAll();
        req.setAttribute("productList",productList);
        req.setAttribute("categoryList", categoryList);
        try {
            req.getRequestDispatcher("/view/product/form.jsp").forward(req,resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showFormAdd(HttpServletRequest req, HttpServletResponse resp) {
        List<Category> categoryList = categoryService.getAll();
        req.setAttribute("categoryList", categoryList);
        try {
            req.getRequestDispatcher("/view/product/add.jsp").forward(req,resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action==null){
            action = "";
        }
        switch (action){
            case "add":
                save(req,resp);
                break;
            case "delete":
                deleteById(req,resp);
                break;
            case "update":
                update(req,resp);
                break;
            default:
        }
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        String categoryIdStr = req.getParameter("categoryId");
        int categoryId = 0;
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String price = req.getParameter("price");
        double setPrice = 0;
        String categoryErr = "";
        String nameErr = "";
        String desErr = "";
        String priceErr = "";

        if (categoryIdStr==null || categoryIdStr.trim().isEmpty()){
            categoryErr = "Vui lòng chọn danh mục";
        }else {
            try {
                categoryId = Integer.parseInt(categoryIdStr);
                if (categoryId<=0) categoryErr = "Danh mục không hợp lệ";
            } catch (NumberFormatException e) {
                categoryErr = "Danh mục phải là số";
            }
        }

        if (name==null || name.trim().isEmpty()){
            nameErr = "Tên sản phẩm không được để trống!";
        } else if (!Validate.checkName(name)) {
            nameErr = "Tên sản phẩm không hợp lệ, vui lòng nhập lại";
        }

        if (description==null || description.trim().isEmpty()){
            desErr = "Mô tả không được để trống!";
        } else if (!Validate.checkDes(description)) {
            desErr = "Mô tả không hợp lệ, vui lòng nhập lại";
        }

        if (price == null || price.trim().isEmpty()){
            priceErr = "Giá không được để trống";
        }else {
            try {
                setPrice = Double.parseDouble(price);
                if (setPrice<0) priceErr = "Giá không được âm";
            }catch (NumberFormatException e){
                priceErr = "Giá phải là số";
            }
        }

        if (!categoryErr.isEmpty() || !nameErr.isEmpty() || !desErr.isEmpty() || !priceErr.isEmpty()) {
            if (!categoryErr.isEmpty()) req.setAttribute("categoryErr",categoryErr);
            if(!desErr.isEmpty()) req.setAttribute("desErr",desErr);
            if (!nameErr.isEmpty()) req.setAttribute("nameErr",nameErr);
            if (!priceErr.isEmpty()) req.setAttribute("priceErr",priceErr);
            List<Category> categoryList = categoryService.getAll();
            req.setAttribute("categoryList", categoryList);
            try {
                req.getRequestDispatcher("/view/product/edit.jsp").forward(req,resp);
            } catch (ServletException | IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        Product product = new Product(id, categoryId, name, description, setPrice);
        boolean isSuccess = productService.edit(product);
        String mess = isSuccess ? "Cập nhật thành công" : "Cập nhật thất bại";
        try {
            resp.sendRedirect("/products?mess="+mess);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteById(HttpServletRequest req, HttpServletResponse resp) {
        int deleteId = Integer.parseInt(req.getParameter("deleteId"));
        boolean isSuccess = productService.delete(deleteId);
        String mess = isSuccess ? "Xóa thành công" : "Xóa thất bại";
        try {
            resp.sendRedirect("/products?mess="+mess);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void save(HttpServletRequest req, HttpServletResponse resp) {
        String categoryIdStr = req.getParameter("categoryIdStr");
        int categoryId = 0;
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String price = req.getParameter("price");
        double setPrice = 0;
        String categoryErr = "";
        String nameErr = "";
        String desErr = "";
        String priceErr = "";

        if (categoryIdStr==null || categoryIdStr.trim().isEmpty()){
            categoryErr = "Vui lòng chọn danh mục";
        }else {
            try {
                categoryId = Integer.parseInt(categoryIdStr);
                if (categoryId<=0) categoryErr = "Danh mục không hợp lệ";
            } catch (NumberFormatException e) {
                categoryErr = "Danh mục phải là số";
            }
        }

        if (name==null || name.trim().isEmpty()){
            nameErr = "Tên sản phẩm không được để trống!";
        } else if (!Validate.checkName(name)) {
            nameErr = "Tên sản phẩm không hợp lệ, vui lòng nhập lại";
        }

        if (description==null || description.trim().isEmpty()){
            desErr = "Mô tả không được để trống!";
        } else if (!Validate.checkDes(description)) {
            desErr = "Mô tả không hợp lệ, vui lòng nhập lại";
        }

        if (price == null || price.trim().isEmpty()){
            priceErr = "Giá không được để trống";
        }else {
            try {
                setPrice = Double.parseDouble(price);
                if (setPrice<0) priceErr = "Giá không được âm";
            }catch (NumberFormatException e){
                priceErr = "Giá phải là số";
            }
        }

        if (!categoryErr.isEmpty() || !nameErr.isEmpty() || !desErr.isEmpty() || !priceErr.isEmpty()) {
            if (!categoryErr.isEmpty()) req.setAttribute("categoryErr",categoryErr);
            if(!desErr.isEmpty()) req.setAttribute("desErr",desErr);
            if (!nameErr.isEmpty()) req.setAttribute("nameErr",nameErr);
            if (!priceErr.isEmpty()) req.setAttribute("priceErr",priceErr);
            List<Category> categoryList = categoryService.getAll();
            req.setAttribute("categoryList", categoryList);
            try {
                req.getRequestDispatcher("/view/product/add.jsp").forward(req,resp);
            } catch (ServletException | IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        Product product = new Product(categoryId,name,description,setPrice);
        boolean isSuccess = productService.add(product);
        String mess = isSuccess ? "Thêm sản phẩm thành công" : "Thêm sản phẩm thất bại";
        try {
            resp.sendRedirect("/products?mess="+mess);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
