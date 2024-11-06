<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="utf-8" />
            <meta http-equiv="X-UA-Compatible" content="IE=edge" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
            <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
            <meta name="author" content="Hỏi Dân IT" />
            <title>View - Nguyễn Đình Vượng</title>
            <link href="/css/styles.css" rel="stylesheet" />
            <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        </head>

        <body class="sb-nav-fixed">
            <jsp:include page="../layout/header.jsp"/>
            <div id="layoutSidenav">
                <jsp:include page="../layout/sidebar.jsp"/>  
                <div id="layoutSidenav_content">
                    <main>
                        <div class="container-fluid px-4">
                            <h1 class="mt-4">Manage Users</h1>
                            <ol class="breadcrumb mb-4">
                                <li class="breadcrumb-item active"><a href="/admin" style="text-decoration: none">Dashboard</a></li>
                                <li class="breadcrumb-item active"><a href="/admin/user" style="text-decoration: none">User</a></li>
                                <li class="breadcrumb-item active">View</li>
                            </ol>
                            <!-- Nội dung -->
                            <div>
                                <div class="mt-5">
                                    <div class="row">
                                        <div class="col-12 mx-auto">
                                            <div class="d-flex justify-content-between">
                                                <h3>Table users id = ${id}</h3>
                                            </div>
                
                                            <hr/>
                                            <div class="card" style="width: 60%;">
                                                <div class="card-header">
                                                Detail information
                                                </div>
                                                <ul class="list-group list-group-flush">
                                                <li class="list-group-item">Id : ${user.id}</li>
                                                <li class="list-group-item">Email : ${user.email}</li>
                                                <li class="list-group-item">Full name : ${user.fullName}</li>
                                                <li class="list-group-item">Address : ${user.address}</li>
                                                <li class="list-group-item">phone : ${user.phone}</li>
                                                </ul>
                                            </div>
                                            <a href = "/admin/user" class="btn  mt-3 btn-primary">Back</a>
                
                                        </div>
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


