<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>OrderSuccess - LapStore</title>
    <link href="/css/styles.css" rel="stylesheet"/>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>
<body class="bg-primary">
<div id="layoutError">
    <div id="layoutError_content">
        <main>
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-6">
                        <div class="text-center mt-4">
                            <h1 class="display-1 text-white">Thành công</h1>
                            <p class="lead text-white mb-4">Đơn hàng của bạn đã được đặt thành công!</p>
                            
                            <c:if test="${paymentStatus == 'success'}">
                                <div class="alert alert-success mb-4">
                                    <h4>Thanh toán VNPay</h4>
                                    <p>${paymentMessage}</p>
                                </div>
                            </c:if>
                            
                            <p class="text-white mb-4">Cảm ơn đã tin tưởng mua sắm với chúng tôi.</p>
                            <a href="/" class="btn btn-light btn-lg">
                                <i class="fas fa-arrow-left me-2"></i>
                                Trở về trang chủ
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
    <div id="layoutError_footer">
        <footer class="py-4 bg-light mt-auto">
            <div class="container-fluid px-4">
                <div class="d-flex align-items-center justify-content-between small">
                    <div class="text-muted" id="copyright">Copyright &copy; LapStore <span id="year"></span></div>
                    <div>
                        <a href="#">Privacy Policy</a>
                        &middot;
                        <a href="#">Terms &amp; Conditions</a>
                    </div>
                </div>
            </div>
        </footer>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
<script src="/js/scripts.js"></script>
<script>
    document.getElementById('year').textContent = new Date().getFullYear();
</script>
</body>
</html>