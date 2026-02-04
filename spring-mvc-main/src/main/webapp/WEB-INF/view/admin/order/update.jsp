<%@ page contentType = "text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>LapStore - Admin</title>
    <link href="/css/styles.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>
<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp" />
<div id="layoutSidenav">
    <jsp:include page="../layout/sidebar.jsp" />
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h1 class="mt-4">Quản lý đơn hàng</h1>
                <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                    <li class="breadcrumb-item"><a href="/admin/order">Đơn hàng</a></li>
                    <li class="breadcrumb-item active">Cập nhật</li>
                </ol>
                <div class="my-5">
                    <div class="row">
                        <div class="col-md-6 col-12 mx-auto">
                            <div class="d-flex justify-content-between">
                                <h3>Update order</h3>
                            </div>
                            <hr />
                            <form:form method="post" action="/admin/order/update" modelAttribute="order"
                                       class="row g-3">
                                <div class="mb-3" style="display: none">
                                    <label for="id" class="form-label">ID:</label>
                                    <form:input type="text" class="form-control" path="id"/>
                                </div>
                                <div class="mb-3" style="display: none">
                                    <label for="id" class="form-label">UserId:</label>
                                    <form:input type="text" class="form-control" path="user.id"/>
                                </div>
                                <div class="mb-3" style="display: none">
                                    <label for="id" class="form-label">Receiver address:</label>
                                    <form:input type="text" class="form-control" path="receiverAddress"/>
                                </div>
                                <div class="mb-3" style="display: none">
                                    <label for="id" class="form-label">Receiver name:</label>
                                    <form:input type="text" class="form-control" path="receiverName"/>
                                </div>
                                <div class="mb-3" style="display: none">
                                    <label for="id" class="form-label">Receiver phone:</label>
                                    <form:input type="text" class="form-control" path="receiverPhone"/>
                                </div>
                                <div class="mb-3" style="display: none">
                                    <label for="id" class="form-label">Total price:</label>
                                    <form:input type="text" class="form-control" path="totalPrice"/>
                                </div>
                                <div class="d-flex gap-5">
                                    <p>ID Đơn hàng: ${order.id}</p>
                                    <p>Tổng tiền: <fmt:formatNumber type="number" value="${order.totalPrice}"/> đ</p>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">User:</label>
                                    <form:input type="text" class="form-control" path="user.fullName" disabled="true"/>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Trạng thái:</label>
                                    <form:select class="form-select" path="status">
                                        <form:option value="Chờ xử lý">Chờ xử lý</form:option>
                                        <form:option value="Đang vận chuyển">Đang vận chuyển</form:option>
                                        <form:option value="Hoàn thành">Hoàn thành</form:option>
                                        <form:option value="Hủy bỏ">Hủy bỏ</form:option>
                                    </form:select>
                                </div>
                                <div class="d-flex justify-content-between">
                                    <a href="/admin/order" class="btn btn-success">Quay lại</a>
                                    <button type="submit" class="btn btn-primary">Lưu</button>
                                </div>
                            </form:form>

                        </div>
                    </div>
                </div>
            </div>
        </main>
        <jsp:include page="../layout/footer.jsp" />
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="/js/scripts.js"></script>
</body>
</html>

