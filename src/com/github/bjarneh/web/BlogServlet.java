package com.github.bjarneh.web;

// local
import com.github.bjarneh.data.BlogPost;

// stdlib
import java.util.Date;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;

// servlet api
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// mongo
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.BasicDBObject;


/**
 * Show blog posts based on ID or latest.
 *
 * @version 1.0 
 * @author  bjarneh@ifi.uio.no
 */

public class BlogServlet extends MongoServlet {


    private final BlogPost info = BlogPost.infoBlog();


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException
    {
        doGet(req, resp);
    }


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException
    {

        resp.setContentType("text/plain;charset=UTF-8");
        req.setAttribute("blog", getArticle( req ));
        req.setAttribute("older", getOlderList());
        req.getRequestDispatcher("blog.jsp").forward(req, resp);

    }



    private BlogPost getArticle(HttpServletRequest req) {

        BlogPost art = fromArticleID( req.getParameter("a") );

        if( art != null ) { return art; }

        art = latest();

        if( art != null ) { return art; }

        return info;
    }


    private BlogPost latest(){

        DBCursor cursor = coll.find().sort(dateSort).limit(1);

        if( cursor.hasNext() ){
            return BlogPost.fromJSON( cursor.next() );
        }

        return null;
    }

}
