<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>LapStore - Admin</title>
    <link href="/css/styles.css" rel="stylesheet"/>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
        $(document).ready(() => {
            const avatarFile = $("#thumb");
            avatarFile.change(function (e) {
                const imgUrl = URL.createObjectURL(e.target.files[0]);
                $("#thumbPreview").attr("src", imgUrl);
                $("#thumbPreview").css({"display": "block"});
            });
        });
    </script>
</head>
<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp"/>
<div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp"/>
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h1 class="mt-4">Quản lý sản phẩm</h1>
                <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                    <li class="breadcrumb-item"><a href="/admin/product">Sản phẩm</a></li>
                    <li class="breadcrumb-item active">Cập nhật</li>
                </ol>
                <div class="my-5">
                    <div class="row">
                        <div class="col-md-6 col-12 mx-auto">
                            <h3>Cập nhật sản phẩm</h3>
                            <hr/>
                            <form:form method="post" action="/admin/product/update" modelAttribute="product"
                                       class="row g-3" enctype="multipart/form-data">
                                <div class="mb-3" style="display: none">
                                    <label for="id" class="form-label">ID:</label>
                                    <form:input type="text" class="form-control" path="id"/>
                                </div>
                                <div class="col-md-6">
                                    <c:set var="invalidName">
                                        <form:errors path="name" cssClass="invalid-feedback"/>
                                    </c:set>
                                    <label for="name" class="form-label">Tên:</label>
                                    <form:input type="text"
                                                class="form-control ${not empty invalidName ? 'is-invalid' : ''}"
                                                path="name"/>
                                        ${invalidName}
                                </div>
                                <div class="col-md-6">
                                    <c:set var="invalidPrice">
                                        <form:errors path="price" cssClass="invalid-feedback"/>
                                    </c:set>
                                    <label for="price" class="form-label">Giá:</label>
                                    <form:input type="number"
                                                class="form-control ${not empty invalidPrice ? 'is-invalid' : ''}"
                                                path="price"/>
                                        ${invalidPrice}
                                </div>
                                <div>
                                    <c:set var="invalidDetailDesc">
                                        <form:errors path="detailDesc" cssClass="invalid-feedback"/>
                                    </c:set>
                                    <label for="detailDesc" class="form-label">Mô tả chi tiết:</label>
                                    <form:textarea type="text"
                                                   class="form-control ${not empty invalidDetailDesc ? 'is-invalid' : ''}"
                                                   path="detailDesc"/>
                                        ${invalidDetailDesc}
                                </div>
                                <div class="col-md-6">
                                    <c:set var="invalidShortDesc">
                                        <form:errors path="shortDesc" cssClass="invalid-feedback"/>
                                    </c:set>
                                    <label for="shortDesc" class="form-label">Mô tả ngắn:</label>
                                    <form:input type="text"
                                                class="form-control ${not empty invalidShortDesc ? 'is-invalid' : ''}"
                                                path="shortDesc"/>
                                        ${invalidShortDesc}
                                </div>
                                <div class="col-md-6">
                                    <c:set var="invalidQuantity">
                                        <form:errors path="quantity" cssClass="invalid-feedback"/>
                                    </c:set>
                                    <label for="quantity" class="form-label">Số lượng:</label>
                                    <form:input type="number"
                                                class="form-control ${not empty invalidQuantity ? 'is-invalid' : ''}"
                                                path="quantity"/>
                                        ${invalidQuantity}
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Hãng máy:</label>
                                    <form:select class="form-select" path="factory">
                                        <form:option value="ACER">Acer</form:option>
                                        <form:option value="APPLE">Apple (Macbook)</form:option>
                                        <form:option value="ASUS">Asus</form:option>
                                        <form:option value="DELL">Dell</form:option>
                                        <form:option value="HP">HP</form:option>
                                        <form:option value="LENOVO">Lenovo</form:option>
                                        <form:option value="LG">LG</form:option>
                                    </form:select>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Mục đích:</label>
                                    <form:select class="form-select" path="target">
                                        <form:option value="GAMING">Gaming</form:option>
                                        <form:option value="SINHVIEN-VANPHONG">Sinh viên - Văn phòng</form:option>
                                        <form:option value="THIET-KE-DO-HOA">Thiết kế đồ họa</form:option>
                                        <form:option value="MONG-NHE">Mỏng nhẹ</form:option>
                                        <form:option value="DOANH-NHAN">Doanh nhân</form:option>
                                    </form:select>
                                </div>
                                <div class="col-md-6">
                                    <label for="thumb" class="form-label">Ảnh sản phẩm:</label>
                                    <input class="form-control" type="file" id="thumb" name="productImg"
                                           accept=".png, .jpg, .jpeg"/>
                                </div>
                                <div class="col-12 mb-3">
                                    <c:if test="${not empty product.image}">
                                        <img style="max-height: 250px;" alt="Product thumbnail" id="thumbPreview" src="/images/product_thumbnail/${product.image}"/>
                                    </c:if>
                                    <img style="max-height: 250px; display: none;" alt="Product thumbnail"
                                         id="thumbPreview"/>
                                </div>
                                <div class="d-flex justify-content-between">
                                    <a href="/admin/product" class="btn btn-success">Quay lại</a>
                                    <button type="submit" class="btn btn-primary">Lưu</button>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <jsp:include page="../layout/footer.jsp"/>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
<script src="/js/scripts.js"></script>
</body>
</html>
