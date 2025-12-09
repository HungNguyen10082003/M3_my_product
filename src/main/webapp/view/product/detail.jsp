<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Chi tiết sản phẩm</title>
  <c:import url="../layout/library.jsp"/>
</head>
<body class="bg-light">

<div class="container mt-5">
  <div class="row justify-content-center">
    <div class="col-md-6">

      <div class="card shadow-lg border-0 rounded-3">
        <div class="card-header bg-primary text-white text-center py-3 rounded-top">
          <h4 class="mb-0">Chi tiết sản phẩm</h4>
        </div>

        <div class="card-body p-4">
          <!-- Nếu product null -->
          <c:if test="${product == null}">
            <div class="alert alert-danger text-center">
              Sản phẩm không tồn tại!
            </div>
          </c:if>
          <!-- Nếu product tồn tại -->
          <c:if test="${product != null}">
            <table class="table table-bordered">
              <thead class="table-light">
              <tr>
                <th>Mã</th>
                <th>Danh mục</th>
                <th>Tên</th>
                <th>Mô tả</th>
                <th>Giá</th>
              </tr>
              </thead>
              <tr>
                <td>${product.id}</td>
                <td>${category.name}</td>
                <td>${product.name}</td>
                <td>${product.description}</td>
                <td>${product.price} đ</td>
              </tr>
            </table>
            <div class="text-center mt-4 d-flex gap-2 justify-content-center">
              <a href="/products" class="btn btn-secondary btn-sm px-4">Quay lại</a>
              <a href="/products?action=edit&id=${product.id}" class="btn btn-warning btn-sm px-4">Sửa</a>
            </div>
          </c:if>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>