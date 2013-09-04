package com.github.bjarneh.web;


// local
import com.github.bjarneh.data.BlogPost;

// stdlib
import java.util.Date;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import static java.lang.String.format;

// servlet api
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// mongo
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.DBCollection;
import com.mongodb.MongoException;
import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;



/**
 * Superclass to all Mongo-servlets, supermongo!
 *
 * @version 1.0 
 * @author  bjarneh@ifi.uio.no
 */

public class MongoServlet extends HttpServlet {


    // Defaults if no web.xml names were given
    final String DEFAULT_DB   = "blogdb";
    final String DEFAULT_COLL = "blogentries";

    protected MongoClient client;
    protected DBCollection coll;

    protected BasicDBObject dateSort = new BasicDBObject("date", -1);


    @Override
    public void init(ServletConfig config) throws ServletException {

        String dbname, collection;

        dbname     = config.getInitParameter("dbname");
        collection = config.getInitParameter("collection");

        //FIXME these seem to be null quite often :-)

        if( dbname == null ){
            dbname = DEFAULT_DB;
        }

        if( collection == null ){
            collection = DEFAULT_COLL;
        }

        try{

            client = new MongoClient();
            DB db  = client.getDB( dbname );

            if( ! db.collectionExists(collection) ){
                coll = db.createCollection(collection, null);
            }else{
                coll = db.getCollection(collection);
            }

        }catch(UnknownHostException e){
            throw new ServletException("No mongodb running?", e);
        }

    }


    @Override
    public void destroy() {
        client.close();
    }


    protected BlogPost fromArticleID(String article){

        if( article != null && ! article.equals("new") ){

            DBObject dbo = fromId(article);

            if( dbo != null ){
                return BlogPost.fromJSON( dbo );
            }
        }

        return null;
    }


    protected DBObject fromId(String id) {
        return coll.findOne(new ObjectId(id));
    }


    // needed in AjaxServlet and BlogServlet
    protected String getHtmlList(DBCursor cursor){

        StringBuilder sb = new StringBuilder();
        String fmtstr    = "<li><a href='blog?a=%s'>%s</li>";

        DBObject doc;

        while( cursor.hasNext() ){

            doc = cursor.next();
            sb.append(format(fmtstr,doc.get("_id"), doc.get("title")));

        }

        return sb.toString();

    }

    protected String getOlderList(){

        DBCursor cursor = coll.find().sort(dateSort).limit(10);
        return getHtmlList( cursor );

    }

}
