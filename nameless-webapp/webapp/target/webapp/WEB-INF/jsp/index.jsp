<%@ include file="header.jsp" %>

<div class="container-fluid container-navbar-margin">
    <div class="row">
        <div class="col-md-1"></div>
        <!--Posts-->
        <div class="col-md-7">
            <!--Post-->
            <div class="row">
                <div class="col-md-12">
                    <div class="row">
                        <h2><strong>Gano Scioli</strong></h2>
                        <img src="<c:url value="/img/example.jpg"/>"/>
                    </div>
                    <div class="row interact">
                        <button type="button" class="btn btn-default" title="Me gusta"><span class="glyphicon glyphicon-thumbs-up"/></button>
                        <button type="button" class="btn btn-default" title="No me gusta"><span class="glyphicon glyphicon-thumbs-down"/></button>
                        <button type="button" class="btn btn-default" title="Reportar"><span class="glyphicon glyphicon-flag"/></button>
                    </div>
                </div>
            </div>

            <hr/>

            <!--Post-->
            <div class="row">
                <div class="col-md-12">
                    <div class="row">
                        <h2><strong>Gano Scioli</strong></h2>
                        <img src="<c:url value="/img/example.jpg"/>"/>
                    </div>
                    <div class="row interact">
                        <button type="button" class="btn btn-default" title="Me gusta"><span class="glyphicon glyphicon-thumbs-up"/></button>
                        <button type="button" class="btn btn-default" title="No me gusta"><span class="glyphicon glyphicon-thumbs-down"/></button>
                        <button type="button" class="btn btn-default" title="Reportar"><span class="glyphicon glyphicon-flag"/></button>
                    </div>
                </div>
            </div>


        </div>

        <!--Side-Column-->
        <div class="col-md-4">
            <!--TODO: PUT CONTENT HERE-->
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>
