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
                <title>Update - Nguyễn Đình Vượng</title>
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
                                    <li class="breadcrumb-item active"><a href="/admin/user?page=${currentPage}"
                                            style="text-decoration: none">User</a></li>
                                    <li class="breadcrumb-item active">Update</li>
                                </ol>
                                <!-- Nội dung -->
                                <div>
                                    <div class="mt-5">
                                        <div class="row">
                                            <div class="col-md-6 col-12 mx-auto">
                                                <h3>Update a user</h3>
                                                <hr />
                                                <form:form method="post" action="/admin/user/update"
                                                    modelAttribute="updateUser">
                                                    <!-- form:<...> : bao voi java rang hay quan sat du lieu cua thang nay
                                              path : dinh danh cac the input 
                                              newUser laf 1 bien object lua lai cac bien  : email , password , fullName , address , phone 
                                          -->

                                                    <div class="mb-3" style="display: none;">
                                                        <label class="form-label">Id</label>
                                                        <form:input type="text" class="form-control" path="id" />
                                                    </div>
                                                    <div class="mb-3">
                                                        <label class="form-label">Email address</label>
                                                        <form:input type="email" class="form-control" path="email"
                                                            disabled="true" />
                                                    </div>
                                                    <div class="mb-3">
                                                        <c:set var="errorFullName">
                                                            <form:errors path="fullName" cssClass="invalid-feedback" />
                                                        </c:set>
                                                        <label class="form-label">Full name</label>
                                                        <form:input type="text"
                                                            class="form-control ${not empty errorFullName?'is-invalid':' '}"
                                                            path="fullName" />
                                                        ${errorFullName}
                                                    </div>
                                                    <div class="mb-3">
                                                        <label class="form-label">Address</label>
                                                        <form:input type="text" class="form-control" path="address" />
                                                    </div>
                                                    <div class="mb-3">
                                                        <label class="form-label">Phone</label>
                                                        <form:input type="text" class="form-control" path="phone" />
                                                    </div>
                                                    <div class="mb-3 col-12 col-md-6">
                                                        <label class="form-label"> Role</label>
                                                        <form:select class="form-select" path="role.name">
                                                            <!-- path="role.name" <-> ánh xạ với thuộc tính name trong đối tượng role của user tương ứng -->
                                                            <form:option value="ADMIN">ADMIN</form:option>
                                                            <form:option value="USER">USER</form:option>
                                                        </form:select>
                                                    </div>
                                                    <button type="submit" class="btn btn-warning"
                                                        style="width : 100%">Update</button>
                                                </form:form>

                                            </div>
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