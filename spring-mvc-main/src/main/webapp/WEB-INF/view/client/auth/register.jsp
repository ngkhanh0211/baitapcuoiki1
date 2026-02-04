<%@page contentType="text/html" pageEncoding="UTF-8" %>
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
    <title>Register - LapStore</title>
    <link href="/css/styles.css" rel="stylesheet"/>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>
<body class="bg-primary">
<div id="layoutAuthentication">
    <div id="layoutAuthentication_content">
        <main>
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-7">
                        <div class="card shadow-lg border-0 rounded-lg mt-5">
                            <div class="card-header"><h3 class="text-center font-weight-light my-4">Đăng ký tài
                                khoản</h3></div>
                            <div class="card-body">
                                <form:form action="/register" method="post" modelAttribute="registerUser">
                                    <div class="row mb-3">
                                        <div class="col-md-6">
                                            <div class="form-floating mb-3 mb-md-0">
                                                <c:set var="invalidFirstName">
                                                    <form:errors path="firstName" cssClass="invalid-feedback"/>
                                                </c:set>
                                                <form:input
                                                        class="form-control ${not empty invalidFirstName ? 'is-invalid' : ''}"
                                                        id="inputFirstName" type="text"
                                                        placeholder="Enter your first name" path="firstName"/>
                                                    ${invalidFirstName}
                                                <label for="inputFirstName">First name</label>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-floating">
                                                <c:set var="invalidLastName">
                                                    <form:errors path="lastName" cssClass="invalid-feedback"/>
                                                </c:set>
                                                <form:input
                                                        class="form-control ${not empty invalidLastName ? 'is-invalid' : ''}"
                                                        id="inputLastName" type="text"
                                                        placeholder="Enter your last name" path="lastName"/>
                                                    ${invalidLastName}
                                                <label for="inputLastName">Last name</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-floating mb-3">
                                        <c:set var="invalidEmail">
                                            <form:errors path="email" cssClass="invalid-feedback"/>
                                        </c:set>
                                        <form:input
                                                class="form-control ${not empty invalidEmail ? 'is-invalid' : ''}"
                                                id="inputEmail" type="email"
                                                placeholder="name@example.com" path="email"/>
                                            ${invalidEmail}
                                        <label for="inputEmail">Email address</label>
                                    </div>
                                    <div class="row mb-3">
                                        <div class="col-md-6">
                                            <div class="form-floating mb-3 mb-md-0">
                                                <form:input class="form-control" id="inputPassword" type="password"
                                                            placeholder="Create a password" path="password"/>
                                                <label for="inputPassword">Password</label>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-floating mb-3 mb-md-0">
                                                <c:set var="invalidConfirmPassword">
                                                    <form:errors path="confirmPassword" cssClass="invalid-feedback"/>
                                                </c:set>
                                                <form:input
                                                        class="form-control ${not empty invalidConfirmPassword ? 'is-invalid' : ''}"
                                                        id="inputPasswordConfirm"
                                                        type="password"
                                                        placeholder="Confirm password" path="confirmPassword"/>
                                                    ${invalidConfirmPassword}
                                                <label for="inputPasswordConfirm">Confirm Password</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="mt-4 mb-0">
                                        <div class="d-grid">
                                            <button class="btn btn-primary btn-block">Tạo tài khoản</button>
                                        </div>
                                    </div>
                                </form:form>
                            </div>
                            <div class="card-footer text-center py-3">
                                <div class="small"><a href="/login">Bạn đã có tài khoản? Đăng nhập ngay</a></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
    <div id="layoutAuthentication_footer">
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
