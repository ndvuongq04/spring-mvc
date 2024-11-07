<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
                <meta name="author" content="Hỏi Dân IT" />
                <title>Create - Nguyễn Đình Vượng</title>
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
                                <h1 class="mt-4">Manage Users</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item active"><a href="/admin"
                                            style="text-decoration: none">Dashboard</a></li>
                                    <li class="breadcrumb-item active"><a href="/admin/user"
                                            style="text-decoration: none">User</a></li>
                                    <li class="breadcrumb-item active">Create</li>
                                </ol>
                                <!-- Nội dung -->
                                <div class="mt-5">
                                    <div class="row">
                                        <div class="col-md-6 col-12 mx-auto">
                                            <h3>Create a user</h3>
                                            <hr />

                                            <form:form method="post" action="/admin/user/create"
                                                modelAttribute="newUser" class="row">
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label for="email" class="form-label">Email address</label>
                                                    <form:input type="email" class="form-control" path="email" />
                                                </div>
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label for="password" class="form-label">Password</label>
                                                    <form:input type="password" class="form-control" path="password" />
                                                </div>
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label for="fullName" class="form-label">Full name</label>
                                                    <form:input type="text" class="form-control" path="fullName" />
                                                </div>
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label for="phone" class="form-label">Phone</label>
                                                    <form:input type="text" class="form-control" path="phone" />
                                                </div>

                                                <div class="mb-3">
                                                    <label for="address" class="form-label">Address</label>
                                                    <form:input type="text" class="form-control" path="address" />
                                                </div>
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label class="form-label"> Role</label>
                                                    <select class="form-select">
                                                        <option selected value="ADMIN">ADMIN</option>
                                                        <option value="USER">USER</option>
                                                    </select>
                                                </div>

                                                <div class="mb-3 col-12 col-md-6">
                                                    <label for="formFile" class="form-label">Avatar </label>
                                                    <input class="form-control" type="file" id="formFile"
                                                        accept=".png, .jpg, .jpeg" />
                                                </div>
                                                <div class="col-12 mb-3">
                                                    <img style="max-height: 250px; display: none;" alt="avatar preview"
                                                        id="avatarPreview" />
                                                </div>


                                                <button type="submit" class="btn btn-primary"
                                                    style="width : 100%">Create</button>
                                            </form:form>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </main>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="/js/scripts.js"></script>
            </body>

            </html>