<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sửa sản phẩm</title>
    <c:import url="../layout/library.jsp"/>
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow-sm border-0">
                <div class="card-body p-4">
                    <h3 class="text-center mb-4">Sửa sản phẩm</h3>

                    <form action="/products?action=update" method="post">
                        <input type="hidden" name="id" value="${product.id}">

                        <!-- CATEGORY -->
                        <div class="mb-3">
                            <label class="form-label">Danh mục</label>
                            <select name="categoryId" class="form-select ${not empty categoryErr ? 'is-invalid' : ''}">
                                <option value="">-- Chọn danh mục --</option>
                                <c:forEach items="${categoryList}" var="cat">
                                    <option value="${cat.id}" ${cat.id == product.categoryId ? 'selected' : ''}>${cat.name}</option>
                                </c:forEach>
                            </select>
                            <c:if test="${not empty categoryErr}">
                                <div class="invalid-feedback d-block">${categoryErr}</div>
                            </c:if>
                        </div>

                        <!-- NAME -->
                        <div class="mb-3">
                            <label class="form-label">Tên sản phẩm</label>
                            <input type="text" name="name" class="form-control ${not empty nameErr ? 'is-invalid' : ''}" value="${product.name}">
                            <c:if test="${not empty nameErr}">
                                <div class="invalid-feedback d-block">${nameErr}</div>
                            </c:if>
                        </div>

                        <!-- DESCRIPTION -->
                        <div class="mb-3">
                            <label class="form-label">Mô tả</label>
                            <input type="text" name="description" class="form-control ${not empty desErr ? 'is-invalid' : ''}" value="${product.description}">
                            <c:if test="${not empty desErr}">
                                <div class="invalid-feedback d-block">${desErr}</div>
                            </c:if>
                        </div>

                        <!-- PRICE -->
                        <div class="mb-3">
                            <label class="form-label">Giá</label>
                            <input type="text" name="price" class="form-control ${not empty priceErr ? 'is-invalid' : ''}" value="${product.price}">
                            <c:if test="${not empty priceErr}">
                                <div class="invalid-feedback d-block">${priceErr}</div>
                            </c:if>
                        </div>

                        <button class="btn btn-warning w-100">Cập nhật</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>