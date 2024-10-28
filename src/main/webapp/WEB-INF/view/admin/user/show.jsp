
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Detail user ${id}</title>
                <!-- Latest compiled and minified CSS -->
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
                rel="stylesheet">
                <!-- Latest compiled JavaScript -->
                <script
                src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js">
                </script>

                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

            </head>

            <body>
                <!-- hello from tableUser.jsp -->
                 <div class="container mt-5">
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
            </body>

            </html>