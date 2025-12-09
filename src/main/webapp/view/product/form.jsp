<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Quản lý sản phẩm</title>
    <c:import url="../layout/library.jsp"/>
</head>
<body>
<div class="container mt-5">
    <div class="shadow p-3 mb-5 bg-white rounded border-blue">
        <h1 class="text-center fw-bold mb-4">Quản lý sản phẩm</h1>
        <div class="d-flex justify-content-between align-items-center mb-4">
            <a class="btn btn-sm btn-success" href="/products?action=add">+ Thêm sản phẩm mới</a>
        </div>

        <!-- MESSAGE -->
        <c:if test="${not empty param.mess}">
            <div class="alert alert-info alert-dismissible fade show" role="alert">
                ${param.mess}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <!-- FILTER BY CATEGORY -->
        <div class="card mb-4">
            <div class="card-body">
                <form action="/products?action=filter" method="get" class="row g-3">
                    <div class="col-md-8">
                        <label class="form-label">Lọc theo danh mục:</label>
                        <select name="categoryId" class="form-select">
                            <option value="">-- Tất cả danh mục --</option>
                            <c:forEach items="${categoryList}" var="cat">
                                <option value="${cat.id}" ${cat.id == selectedCategory ? 'selected' : ''}>${cat.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-4 d-flex align-items-end">
                        <button type="submit" class="btn btn-primary w-100">Lọc</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- PRODUCT TABLE -->
        <div class="table-responsive">
            <table class="table table-bordered align-middle">
                <thead class="table-light">
                <tr>
                    <th>Mã</th>
                    <th>Danh mục</th>
                    <th>Tên sản phẩm</th>
                    <th>Giá</th>
                    <th>Hành động</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="product" items="${productList}" varStatus="status">
                    <tr>
                        <td>${product.id}</td>
                        <td>
                            <c:forEach items="${categoryList}" var="cat">
                                <c:if test="${cat.id == product.categoryId}">${cat.name}</c:if>
                            </c:forEach>
                        </td>
                        <td>${product.name}</td>
                        <td>${product.price} đ</td>
                        <td>
                            <a href="/products?action=detail&id=${product.id}" class="btn btn-info btn-sm">Xem</a>
                            <a href="/products?action=edit&id=${product.id}" class="btn btn-warning btn-sm">Sửa</a>
                            <button onclick="getInfoToDelete('${product.id}','${product.name}')" type="button" class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#deleteModal">
                                Xóa
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <c:if test="${productList.isEmpty()}">
                    <tr>
                        <td colspan="5" class="text-center text-danger">
                            Không có sản phẩm nào
                        </td>
                    </tr>
                </c:if>
            </table>
        </div>
    </div>
</div>

<!-- DELETE MODAL -->
<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="/products?action=delete" method="post">
                <div class="modal-header bg-danger text-white">
                    <h1 class="modal-title fs-5" id="deleteModalLabel">Xóa sản phẩm</h1>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <input hidden="hidden" id="deleteId" name="deleteId">
                    <p>Sản phẩm: <strong id="deleteName"></strong></p>
                    <p class="text-danger">Bạn có chắc chắn muốn xóa sản phẩm này?</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-danger">Xóa</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    function getInfoToDelete(id, name) {
        document.getElementById("deleteId").value = id;
        document.getElementById("deleteName").innerHTML = name;
    }
</script>
</body>
</html>