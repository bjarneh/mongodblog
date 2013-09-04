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
import com.mongodb.MongoException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;


/**
 * Edit or create new blog entries.
 *
 * @version 1.0 
 * @author  bjarneh@ifi.uio.no
 */

public class EditServlet extends MongoServlet {


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException
    {
        saveBlog( req );
        doGet(req, resp);
    }


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException
    {

        resp.setContentType("text/plain;charset=UTF-8");
        req.setAttribute("blog", getArticle( req ));
        req.getRequestDispatcher("edit.jsp").forward(req, resp);

    }


    private BlogPost getArticle(HttpServletRequest req) {

        // from POST request (just edited)
        DBObject artJSON = (DBObject) req.getAttribute("aJSON");

        if( artJSON != null ){
            return BlogPost.fromJSON( artJSON );
        }

        BlogPost art = fromArticleID( req.getParameter("a") );

        return (art == null)? new BlogPost() : art;

    }


    private BlogPost fromAidOrBid(HttpServletRequest req){

        String aid  = req.getParameter("a");
        BlogPost bp = fromArticleID( aid );

        if( bp == null ){
            String bid = req.getParameter("bid");
            if( bid != null ){
                bp = fromArticleID( bid );
            }
        }

        return bp;
    }


    private boolean saveBlog(HttpServletRequest req){

        try{
        
            DBObject doc, tmp, query;
            BlogPost bp = fromAidOrBid( req );

            doc = new BasicDBObject("title", req.getParameter("btitle"))
                            .append("body", req.getParameter("bbody"))
                            .append("date", new Date());

            if( bp != null ){

                // excellent way to update an element! :-)
                query = fromId( bp.getId() );
                tmp   = new BasicDBObject().append("$set", doc);
                coll.update( query, tmp );

                // a HACK already
                doc.put("_id", new ObjectId(bp.getId()));

            } else {

                coll.insert(doc);

            }
            

            req.setAttribute("aJSON", doc);

            return true;


        }catch(MongoException e){
            System.out.printf("%s\n", e);
        }

        return false;

    }

}
