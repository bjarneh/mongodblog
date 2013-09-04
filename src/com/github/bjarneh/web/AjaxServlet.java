package com.github.bjarneh.web;

// stdlib
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

// servlet api
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// mongo
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.DBCollection;
import com.mongodb.MongoException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import org.bson.types.ObjectId;

/**
 * Return blog posts matching search criteria.
 *
 * @version 1.0 
 * @author  bjarneh@ifi.uio.no
 */

public class AjaxServlet extends MongoServlet {


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

        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/plain;charset=UTF-8");

        String q = req.getParameter("q");
        if( q != null ){
            DBCursor cursor = getMatchCursor( q );
            if( cursor.count() > 0 ){
                writer.print(getHtmlList( cursor ));
            } else {
                writer.print(getOlderList());
            }
        }

    }

    private DBCursor getMatchCursor(String r){
        BasicDBObject obj = new BasicDBObject();
        obj.append("title", Pattern.compile(r));
        return coll.find(obj);
    }

}
