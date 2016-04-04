<%@ page import="java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
  <title>Movies</title>
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="css\materialize.css" type="text/css" rel="stylesheet" media="screen,projection"/>
  <link href="css\style.css" type="text/css" rel="stylesheet" media="screen,projection"/>
</head>
<body>
  <nav class="blue darken-1" role="navigation">
    <div class="nav-wrapper container"><a id="logo-container" href="index.html" class="brand-logo"><i class="material-icons">thumb_up</i></a>
      <ul class="right">
        <li><a id="profile-name" class="waves-effect" onclick='logout();'>If you are seeing this, I must've screwed something up.</a></li>
      </ul>
  </div>
  </nav>
  <!--div class="parallax-container">
    <div class="parallax"><img src="rsc/bg.jpg"/-->
    <div class="row">
      <div class="col s8">
        <div class="row">
          <div class="section" id="index-banner">
            <div class="container">
              <br><h3 class="header center red-text">Movies</h3>
              <div class="row center">
                <h5 class="light center-align">Enter the names of movies that you like</h5>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col s8 offset-s2">
            <form action="execute" id="content-form" method="post">
              <div class="row">
                <div class="col s2 offset-s5">
                  <input type="submit" class="btn waves-effect waves-blue right tooltipped" data-position="bottom" data-delay="700" data-tooltip="Submit when the fields are filled" value="Submit"/>
                </div>
              </div>
              <input type="text" class="grey lighten-5" name="con-field-1" id="con-field-1" size=255 required/>
              <input type="text" class="grey lighten-5" name="con-field-2" id="con-field-2" size=255 required/>
              <input type="text" class="grey lighten-5" name="con-field-3" id="con-field-3" size=255 required/>
            </form>
            <a class="btn red waves-effect tooltipped" data-position="bottom" data-delay="1000" data-tooltip="Add new field" id="add-field" onclick="addField();"><i class="material-icons circle white-text">add</i></a>
          </div>
        </div>
      </div>
      <div class="col s4">
        <br><br><br><br><br><br><br>
        <form action="search" method="get">
          <p>If you are not sure what the movie title is search here</p>
          <input type="text" class="grey lighten-5" name="search-field" id="search-field" size=255 required/>
          <label for=""
          <div class="row center">
            <input type="submit" class="btn waves-effect waves-red right blue" value="Search"/>
          </div> 
        </form>
      </div>
    <!--/div>
    </div-->
  </div>

  <footer class="page-footer red darken-1">
    <div class="container">
      <div class="row">
        <div class="col l6 s12 offset-l8">
          <h5 class="white-text">Recommendation system</h5>
          <p class="grey-text text-lighten-4">Helps you find things that you'll love.</p>
        </div>
      </div>
    </div>
    <div class="footer-copyright">
      <div class="container">
      Made by <a class="red-text text-lighten-3" href="#!">Jawahar and Shikhar</a>
      </div>
    </div>
  </footer>



  <script src="js/jquery-2.2.2.min.js"></script>
  <script src="js/materialize.js"></script>
  <script src="js/init_content.js"></script>
  <script src="js/logout.js"></script>
  </body>
</html>
