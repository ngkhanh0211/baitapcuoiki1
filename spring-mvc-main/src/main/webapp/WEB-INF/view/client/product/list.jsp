<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Sản Phẩm - Laptopshop</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
            href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
            rel="stylesheet">

    <!-- Icon Font Stylesheet -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
          rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
    <link href="/client/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">


    <!-- Customized Bootstrap Stylesheet -->
    <link href="/client/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="/client/css/style.css" rel="stylesheet">
    <link href="/client/css/customHomeClient.css" rel="stylesheet">
</head>

<body>

<!-- Spinner Start -->
<div id="spinner"
     class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
    <div class="spinner-grow text-primary" role="status"></div>
</div>
<!-- Spinner End -->

<jsp:include page="../layout/header.jsp"/>

<!-- Modal Search Start -->
<div class="modal fade" id="searchModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-fullscreen">
        <div class="modal-content rounded-0">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Tìm kiếm sản phẩm</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body d-flex align-items-center">
                <form action="/search" method="get" class="input-group w-75 mx-auto d-flex">
                    <input type="search" name="keyword" class="form-control p-3" placeholder="Nhập từ khóa tìm kiếm..."
                           aria-describedby="search-icon-1" value="${keyword}">
                    <button type="submit" id="search-icon-1" class="input-group-text p-3"><i class="fa fa-search"></i></button>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Modal Search End -->

<!-- Single Product Start -->
<div class="container-fluid py-5 mt-5">
    <div class="container py-5">
        <div class="row g-4 mb-5">
            <div>
                <nav aria-label="breadcrumb">
                    <ol class="breadcrumb mt-4">
                        <li class="breadcrumb-item"><a href="/">Home</a></li>
                        <li class="breadcrumb-item active" aria-current="page">Sản phẩm
                        </li>
                    </ol>
                </nav>
            </div>
            
            <c:if test="${not empty keyword}">
                <div class="col-12 mb-3">
                    <h4>Kết quả tìm kiếm cho: "${keyword}"</h4>
                </div>
            </c:if>

            <div class="row g-4 fruite">
                <div class="col-12 col-md-4">
                    <div class="row g-4">
                        <div class="col-12" id="factoryFilter">
                            <div class="mb-2"><b>Hãng sản xuất</b></div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="factory-1"
                                       value="APPLE">
                                <label class="form-check-label" for="factory-1">Apple</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="factory-2"
                                       value="ASUS">
                                <label class="form-check-label" for="factory-2">Asus</label>
                            </div>

                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="factory-3"
                                       value="LENOVO">
                                <label class="form-check-label" for="factory-3">Lenovo</label>
                            </div>

                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="factory-4"
                                       value="DELL">
                                <label class="form-check-label" for="factory-4">Dell</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="factory-5"
                                       value="LG">
                                <label class="form-check-label" for="factory-5">LG</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="factory-6"
                                       value="ACER">
                                <label class="form-check-label" for="factory-6">Acer</label>
                            </div>

                        </div>
                        <div class="col-12" id="targetFilter">
                            <div class="mb-2"><b>Mục đích sử dụng</b></div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="target-1"
                                       value="GAMING">
                                <label class="form-check-label" for="target-1">Gaming</label>
                            </div>

                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="target-2"
                                       value="SINHVIEN-VANPHONG">
                                <label class="form-check-label" for="target-2">Sinh viên - văn
                                    phòng</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="target-3"
                                       value="THIET-KE-DO-HOA">
                                <label class="form-check-label" for="target-3">Thiết kế đồ
                                    họa</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="target-4"
                                       value="MONG-NHE">
                                <label class="form-check-label" for="target-4">Mỏng nhẹ</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="target-5"
                                       value="DOANH-NHAN">
                                <label class="form-check-label" for="target-5">Doanh nhân</label>
                            </div>


                        </div>
                        <div class="col-12" id="priceFilter">
                            <div class="mb-2"><b>Mức giá</b></div>

                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="price-2"
                                       value="duoi-10-trieu">
                                <label class="form-check-label" for="price-2">Dưới 10 triệu</label>
                            </div>

                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="price-3"
                                       value="10-15-trieu">
                                <label class="form-check-label" for="price-3">Từ 10 - 15
                                    triệu</label>
                            </div>

                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="price-4"
                                       value="15-20-trieu">
                                <label class="form-check-label" for="price-4">Từ 15 - 20
                                    triệu</label>
                            </div>

                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="price-5"
                                       value="tren-20-trieu">
                                <label class="form-check-label" for="price-5">Trên 20 triệu</label>
                            </div>
                        </div>
                        <div class="col-12">
                            <div class="mb-2"><b>Sắp xếp</b></div>

                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" id="sort-1"
                                       value="gia-tang-dan" name="radio-sort">
                                <label class="form-check-label" for="sort-1">Giá tăng dần</label>
                            </div>

                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" id="sort-2"
                                       value="gia-giam-dan" name="radio-sort">
                                <label class="form-check-label" for="sort-2">Giá giảm dần</label>
                            </div>

                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" id="sort-3" checked
                                       value="gia-nothing" name="radio-sort">
                                <label class="form-check-label" for="sort-3">Không sắp xếp</label>
                            </div>

                        </div>
                        <div class="col-12">
                            <button
                                    class="btn border-secondary rounded-pill px-4 py-3 text-primary text-uppercase mb-4"
                                    id="btnFilter">
                                Lọc Sản Phẩm
                            </button>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-md-8 text-center">
                    <div class="row g-4">
                        <c:forEach var="product" items="${products}">
                            <div class="col-md-6 col-lg-4">
                                <div class="rounded position-relative fruite-item">
                                    <div class="fruite-img">
                                        <img src="/images/product_thumbnail/${product.image}"
                                             class="img-fluid w-100 rounded-top"
                                             alt="Product thumbnail"
                                             style="height: 261px; object-fit: contain;">
                                    </div>
                                    <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                         style="top: 10px; left: 10px;">Laptop
                                    </div>
                                    <div class="p-4 border border-secondary border-top-0 rounded-bottom">
                                        <h4 class="item-title">
                                            <a href="/product/${product.id}">
                                                    ${product.name}
                                            </a>
                                        </h4>
                                        <p class="fixed-height-paragraph"
                                           title="${product.shortDesc}">${product.shortDesc}</p>
                                        <div class="d-flex justify-content-center flex-lg-wrap">
                                            <p class="text-dark fs-5 fw-bold mb-3"
                                               style="text-align: center; width: 100%">
                                                <fmt:formatNumber type="number" value="${product.price}"/> đ
                                            </p>
                                            <form action="/add-product-to-cart/${product.id}" method="post">
                                                <input type="hidden" name="${_csrf.parameterName}"
                                                       value="${_csrf.token}"/>
                                                <button class="mx-auto btn border border-secondary rounded-pill px-3 text-primary">
                                                    <i class="fa fa-shopping-bag me-2 text-primary"></i>
                                                    Add to cart
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>

                        <div class="pagination d-flex justify-content-center mt-5">
                            <li class="page-item">
                                <a class="${1 eq currentPage ? 'disabled page-link' : 'page-link'}"
                                   href="/product?page=${currentPage - 1}${queryString}" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                            <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                                <li class="page-item">
                                    <a class="${(loop.index) eq currentPage ? 'active page-link' : 'page-link'}"
                                       href="/product?page=${loop.index}${queryString}">
                                            ${loop.index}
                                    </a>
                                </li>
                            </c:forEach>
                            <li class="page-item">
                                <a class="${totalPages eq currentPage ? 'disabled page-link' : 'page-link'}"
                                   href="/product?page=${currentPage + 1}${queryString}" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Single Product End -->

<jsp:include page="../layout/footer.jsp"/>


<!-- Back to Top -->
<a href="#" class="btn btn-primary border-3 border-primary rounded-circle back-to-top"><i
        class="fa fa-arrow-up"></i></a>


<!-- JavaScript Libraries -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="/client/lib/easing/easing.min.js"></script>
<script src="/client/lib/waypoints/waypoints.min.js"></script>
<script src="/client/lib/lightbox/js/lightbox.min.js"></script>
<script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>

<!-- Template Javascript -->
<script src="/client/js/main.js"></script>
</body>

</html>