<!DOCTYPE html>
<html>

    <head>
        <title> MongoDBlog </title>
        <link rel="stylesheet" type="text/css" href="css/main.css" /> 
    </head>

    <body>

        <div id='searchbox'>
            <input id="searchbox" type="text" />
        </div>
        <div class='reset'></div>

        <h1> <a id='main' href='blog'> MongoDBlog  </a> </h1>

        <div id='searchresult'> </div>
        <div class='reset'></div>

        <hr />

        <div id='blogpost'>
            <div id='controls'>
                <a href='edit?a=${blog.id}'>edit</a> &nbsp;
                <a href='edit?a=new'>create</a>
            </div>
            <h2> ${blog.title} </h2>
             <p> ${blog.bodyHtmlNL}  </p>
        </div>

        <p id='tinydate'>${blog.date}</p>

        <div id='previous'>
            <ul class='squarebullets'>
                   ${older}
            </ul>
        </div>

    </body>

    <script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="js/main.js" type="text/javascript"></script>

</html>
