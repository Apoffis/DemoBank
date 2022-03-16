<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="css/fontawesome-free-6.0.0-web/css/all.css">
    <link rel="stylesheet" href="css/default.css">
    <title>Login</title>
</head>

<body class="d-flex align-items-center justify-content-center">

<!-- Card : Login Form Card -->
<div class="card login-form-card col-4 bg-transparent border-0">
    <!-- Card Body -->
    <div class="card-body">
        <!-- Form Header -->
        <h1 class="form-header card-title mb-3">
            <i class="fa fa-user-circle"></i>Login
        </h1>
        <!-- End Of Form Header -->

        <!-- Display Message -->
        <c:if test="${requestScope.success != null}">
            <div class="alert alert-success text-center border border-success">
                <b>${requestScope.success}</b>
            </div>
        </c:if>
        <!-- End Of Display Message -->

        <!-- Display Message -->
        <c:if test="${requestScope.error != null}">
            <div class="alert alert-danger text-center border border-danger">
                <b>${requestScope.error}</b>
            </div>
        </c:if>
        <!-- End Of Display Message -->

        <!-- End Of Display Message -->

        <!-- Display Message -->
        <c:if test="${logged_out != null}">
            <div class="alert alert-info text-center border border-info">
                <b>${logged_out}</b>
            </div>
        </c:if>
        <!-- End Of Display Message -->

        <!-- Login Form -->
        <form action="/login" method="POST" class="login-form">

            <!-- Form Group -->
            <div class="form-group">
                <input type="email" name="email" class="form-control form-control-lg" placeholder="Endter Email" />
            </div>
            <!-- End Of Form Group -->

            <!-- Form Group -->
            <div class="form-group">
                <input type="password" name="password" class="form-control form-control-lg"
                       placeholder="Endter Password" />
            </div>
            <!-- End Of Form Group -->

            <!-- Form Group -->
            <div class="form-group">
                <input type="hidden" name="_token" value="${token}" />
            </div>
            <!-- End Of Form Group -->

            <!-- Form Group -->
            <div class="form-group">
                <button class="btn btn-lg">Login</button>
            </div>
            <!-- End Of Form Group -->
        </form>
        <!-- End Of Login Form -->

        <!-- Card Text -->
        <p class="card-text text-white my-2">
            Dont have on account? <span class="ms2 text-warning">
                    <a href="/register" class="btn btn-sm text-white text-warning">Sig Up</a>
                </span>
        </p>
        <!-- End Of Card Text -->

        <!-- Back Button To Loding Page -->
        <small class="text-warning">
            <i class="fa fa-arrow-alt-circle-left"></i><a href="/" class="btn btn-sm text-warning">Back</a>
        </small>
        <!-- End Of Back Button To Loding Page -->

    </div>
    <!-- End Of Card Body -->
</div>
<!-- End Of Card : Login Form Card -->

</body>

</html>