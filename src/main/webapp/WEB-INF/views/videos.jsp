<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%> 

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>Media Catalog</title>
  <!-- Bootstrap core CSS -->
  <style><%@include file="/WEB-INF/views/css/shop-homepage.css"%></style>
  <!-- <link href="WEB-INF/resources/css/bootstrap/bootstrap.min.css" rel="stylesheet"> -->
  <!-- Custom styles for this template -->
    <style><%@include file="/WEB-INF/views/css/bootstrap.min.css"%></style>
  <!-- <link href="resources/css/shop-homepage.css" rel="stylesheet"> -->
  <script type="text/javascript">
 function showIt() {
  var x = document.getElementById("myDIV");
  if (x.style.display == "none") {
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}</script>
</head>

<body>
  <!-- Navigation -->
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
      <a class="navbar-brand" href="http://localhost:8080/media-catalog/api/home.jsp">Media Catalog</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            <a class="nav-link" href="http://localhost:8080/media-catalog/api/users/logout.htm">Logout</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <!-- Page Content -->
  <div class="container">
    <div class="row">
      <div class="col-lg-3">
        <h1 class="my-4">Videos</h1>
        <div class="list-group">
          <a href="http://localhost:8080/media-catalog/api/media/videos.jsp" class="list-group-item">Videos</a>
          <a href="http://localhost:8080/media-catalog/api/media/audios.jsp" class="list-group-item">Audios</a>
          <a href="http://localhost:8080/media-catalog/api/media/images.jsp" class="list-group-item">Images</a>
          <a href="http://localhost:8080/media-catalog/api/files" class="list-group-item">Upload Files</a>          
        </div>
      </div>
      <!-- /.col-lg-3 -->
      <div class="col-lg-9">
        <div id="carouselExampleIndicators" class="carousel slide my-4" data-ride="carousel">
          <ol class="carousel-indicators">
            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
          </ol>
          
	 <div class="carousel-inner" role="listbox">
            <div class="carousel-item active">
              	<img class="d-block img-fluid" src="<c:url value="http://localhost:8080/media-catalog/api/uploads/sunflower.jpg"/>" alt="First slide" width="900" height="300" >
			</div>
            <div class="carousel-item">
              <img class="d-block img-fluid" src="<c:url value="http://localhost:8080/media-catalog/api/uploads/pinklotus.jpg"/>" alt="Second slide" width="900" height="300">
            </div>
            <div class="carousel-item">
              <img class="d-block img-fluid" src="<c:url value="http://localhost:8080/media-catalog/api/uploads/rose.jpg"/>" alt="Third slide" width="900" height="300">
            </div>
          </div>
          <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
          </a>
          <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
          </a>
        </div>
	

			
		
			<div class="row">
	          <table class="table">
	          <div class="col-lg-4 col-md-6 mb-4">
	          <c:forEach var="media" items="${mediaList}">
	            <tr>
	            <td>
	            <div class="card h-100">
	              <%-- <a href="#"><embed overflow= "hidden" left = "0" right = "0" height = "auto" width = "auto" controls autoplay = "0" autostart = "0" class="card-img-top" src="<c:url value= "http://localhost:8080/media-catalog/api/uploads/${media.fileName}"/>" alt=""   ></a> --%>
	              <div class="embed-responsive embed-responsive-16by9">
  						<iframe class="embed-responsive-item" src="<c:url value= "http://localhost:8080/media-catalog/api/uploads/${media.fileName}"/>"></iframe>
				  </div>
	              <div class="card-body">
	                <h4 class="card-title">
	                  <a href="<c:url value= "http://localhost:8080/media-catalog/api/uploads/${media.fileName}"/>"> ${media.title} </a>
	                </h4>
	                <h5>${media.description}</h5>
	                <p class="card-text"></p>
	              </div>
	              <div class="card-footer" align= "right">
	                <div align= "left">
							<button onclick="showIt()" >Update</button>
							
							<div id="myDIV" style="display:none">
								<form method="post" action="<c:url value= "http://localhost:8080/media-catalog/api/updateMedia/${media.fileName}"/>" enctype="multipart/form-data">
									<div class="form-label-group">
					                  <input minlength = "4" type="text" name="input_title" id="inputTitle" class="form-control" required autofocus><div><label for="inputEmail">Title</label></div>
					                </div>
					                <div class="form-label-group">
					                  <input minlength = "4" type="text" name="input_description" id="inputDescription" class="form-control" required autofocus><div><label for="inputEmail">Description</label></div>
					                </div>
								  <button  class="btn btn-lg btn-secondary btn-block btn-login text-uppercase font-weight-bold mb-2" type="submit">Make Changes</button>
								</form>
							</div>
	                
	                </div>
	                
	                <a href="<c:url value= "http://localhost:8080/media-catalog/api/delete/${media.fileName}"/>">Remove</a>
	              </div>
	            </div>
	            </td>
	            </tr>
	            </c:forEach>
	          </div>
	          </table>
	         </div>
			</div>
		
		</div>
	   </div>
		
	<footer class="py-5 bg-dark">
    <div class="container">
      <p class="m-0 text-center text-white">Copyright © Media Catalog 2019</p>
    </div>
    <!-- /.container -->
  </footer>
  <!-- Bootstrap core JavaScript -->
  <div class="py-5">
    <div class="container">
      <div class="row">
        <div class="col-md-12"></div>
      </div>
    </div>
  </div>
  <div class="py-5">
    <div class="container">
      <div class="row">
        <div class="col-md-12"></div>
      </div>
    </div>
  </div>			
			
	<script><%@include file="/WEB-INF/views/js/jquery.min.js"%></script>
  	<script><%@include file="/WEB-INF/views/js/bootstrap.bundle.min.js"%></script>
	

</body>
</html>