
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Document</title>
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
                <div class="d-flex justify-content-center align-items-center vh-100">
                    
                    <form:form
                        style = "width : 400px"
                        method="post"
                        action="/admin/user/create1"
                        modelAttribute="newUser"
                    >
                    <!-- form:<...> : bao voi java rang hay quan sat du lieu cua thang nay
                        path : dinh danh cac the input 
                        newUser laf 1 bien object lua lai cac bien  : email , password , fullName , address , phone 
                    -->
                        <h2>
                            Create a user
                        </h2>
                        <hr>
                        <div class="mb-3">
                            <label for="email" class="form-label">Email address</label>
                            <form:input type="email" class="form-control" path="email"/>
                        <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Password</label>
                            <form:input type="password" class="form-control" path="password"/>
                        </div>
                        <div class="mb-3">
                            <label for="fullName" class="form-label">Full name</label>
                            <form:input type="text" class="form-control" path="fullName"/>
                        </div>
                        <div class="mb-3">
                            <label for="address" class="form-label">Address</label>
                            <form:input type="text" class="form-control" path="address"/>
                        </div>
                        <div class="mb-3">
                            <label for="phone" class="form-label">Phone</label>
                            <form:input type="text" class="form-control" path="phone"/>
                        </div>
                        <button type="submit" class="btn btn-primary" style = "width : 100%">Create</button>
                    </form:form>
                </div>

                
            </body>

            </html>