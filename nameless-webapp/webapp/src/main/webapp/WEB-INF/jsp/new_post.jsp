<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="header.jsp" %>
<c:url value="/nuevoPost" var="postPath"/>

<div class="container-fluid container-navbar-margin">
    <div class="row interact">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <form:form modelAttribute="postForm" action="${postPath}" method="post" enctype="multipart/form-data">
                <form:input type="text" class="form-control" id="title" path="title" placeholder="Titulo"></form:input>
                <form:input type="file" path="file" accept="url/png, url/jpeg, url/gif" id="file" class="form-control input-sm"/>
                <input type="submit" class="btn btn-primary btn-lg" value="Listo"/>
            </form:form>
        </div>
        <div class="col-md-3"></div>
    </div>
</div>

<%@ include file="footer.jsp" %>