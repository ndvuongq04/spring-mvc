
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Delete user ${id}</title>
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
                <!-- hello from admin/user/create.jsp -->
                <div class="container mt-5">
                    <div class="row">
                        <div class="col-12 mx-auto">
                            <h2>
                                Delete a user id = ${id}
                            </h2>
                            <hr>
                            <!--  vì cần chuyển id của user về userController -> phải dùng form -->
                            <form:form
                                method="post"
                                action="/admin/user/delete"
                                modelAttribute="userFormData"
                            >
                            <!-- dùng input để gán id cho userFormData -->
                            <div style = "display: none;">
                                <label for="id" class="form-label">Id</label>
                                <form:input type="text" class="form-control" path="id" value = "${id}" />
                            </div>
                            <div class="alert alert-danger">
                                Are you sure to delete this user ?
                            </div>
                            <button type="submit" class="btn btn-danger">Confirm</button>
                        </form:form>
                        </div>
                    </div>
                </div>
            </body>

            </html>