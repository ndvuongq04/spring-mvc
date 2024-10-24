
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
                
                <form
                    style = "width : 400px"
                    method="post"
                    action="/admin/user/create"
                >
                    <h2>
                        Create a user
                    </h2>
                    <hr>
                    <div class="mb-3">
                      <label for="email" class="form-label">Email address</label>
                      <input type="email" class="form-control" id="email" aria-describedby="emailHelp">
                      <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>
                    </div>
                    <div class="mb-3">
                      <label for="password" class="form-label">Password</label>
                      <input type="password" class="form-control" id="password">
                    </div>
                    <div class="mb-3">
                        <label for="fullName" class="form-label">Full name</label>
                        <input type="text" class="form-control" id="fullName">
                    </div>
                    <div class="mb-3">
                        <label for="address" class="form-label">Address</label>
                        <input type="text" class="form-control" id="address">
                    </div>
                    <div class="mb-3">
                        <label for="phone" class="form-label">Phone</label>
                        <input type="text" class="form-control" id="phone">
                    </div>
                    <button type="submit" class="btn btn-primary" style = "width : 100%">Create</button>
                  </form>
            </div>

            
        </body>

        </html>