<%@ page contentType = "text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                            <li class="breadcrumb-item active">Đơn hàng</li>
                        </ol>
                        <div class="my-5">
                            <div class="row">
                                <div class="col-12 mx-auto">
                                    <div class="d-flex justify-content-between">
                                        <h3>Danh sách đơn hàng</h3>
                                    </div>
                                    <hr />
                                    <table class="table table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Tên người nhận</th>
                                            <th scope="col">Địa chỉ</th>
                                            <th scope="col">Số điện thoại</th>
                                            <th scope="col">Tổng tiền</th>
                                            <th scope="col">Trạng thái</th>
                                            <th scope="col">Action</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="order" items="${orders}">
                                            <tr>
                                                <th scope="row">${order.id}</th>
                                                <td>${order.receiverName}</td>
                                                <td>${order.receiverAddress}</td>
                                                <td>${order.receiverPhone}</td>
                                                <td><fmt:formatNumber type="number" value="${order.totalPrice}"/> đ</td>
                                                <td>${order.status}</td>
                                                <td>
                                                    <a href="/admin/order/${order.id}" class="btn btn-success">View</a>
                                                    <a href="/admin/order/update/${order.id}" class="btn btn-warning mx-2">Update</a>
                                                    <a href="/admin/order/delete/${order.id}" class="btn btn-danger">Delete</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                    <nav aria-label="Page navigation example">
                                        <ul class="pagination justify-content-center">
                                            <li class="page-item">
                                                <a class="${1 eq currentPage ? 'disabled page-link' : 'page-link'}"
                                                   href="/admin/order?page=${currentPage - 1}"
                                                   aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </li>
                                            <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                                                <li class="page-item">
                                                    <a class="${(loop.index) eq currentPage ? 'active page-link' : 'page-link'}"
                                                       href="/admin/order?page=${loop.index}">${loop.index}
                                                    </a>
                                                </li>
                                            </c:forEach>
                                            <li class="page-item">
                                                <a class="${totalPages eq currentPage ? 'disabled page-link' : 'page-link'}"
                                                   href="/admin/order?page=${currentPage + 1}" aria-label="Next">
                                                    <span aria-hidden="true">&raquo;</span>
                                                </a>
                                            </li>
                                        </ul>
                                    </nav>
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

