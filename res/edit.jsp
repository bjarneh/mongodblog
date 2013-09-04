<!DOCTYPE html>
<html>

    <head>
        <title> MongoDBlog </title>
        <link rel="stylesheet" type="text/css" href="css/main.css" /> 
    </head>

    <body>

        <h1> <a id='main' href='blog'> MongoDBlog  </a> </h1>
        <hr />

        <form action='edit' method='POST'>

            <input type='text'
                   name='btitle'
                   size='53'
                   value='${blog.title}' /> Title <br />

            <input type='hidden' name='bid' value='${blog.id}' />

            <textarea name='bbody' cols='80' rows='25'>${blog.body}</textarea>

            <br />
            <input type='submit' name='submit' value='Save' />
            <p> ${blog.date} </p>

        </form>

    </body>

    <script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="js/main.js" type="text/javascript"></script>

</html>
