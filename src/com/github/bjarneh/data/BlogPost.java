package com.github.bjarneh.data;

// stdlib
import java.util.Date;

// mongo
import com.mongodb.DBObject;


/**
 * Represents a blog document inside MongoDB.
 *
 * @version 1.0 
 * @author  bjarneh@ifi.uio.no
 */
public class BlogPost{

    // 'new' makes it unsavable
    private String id    = "new";
    private String title = "";
    private String body  = "";
    private Date date    = null;


    public BlogPost(){;}

    public BlogPost(String t, String b, Date d){
        title = t;
        body  = b;
        date  = d;
    }

    public BlogPost setId(String i){
        id = i;
        return this;
    }
    public BlogPost setTitle(String t){
        title = t;
        return this;
    }
    public BlogPost setBody(String b){
        body = b;
        return this;
    }
    public BlogPost setDate(Date d){
        date = d;
        return this;
    }

    public String getId(){ return id; }
    public String getTitle(){ return title; }
    public String getBody(){ return body; }
    public Date getDate(){ return date; }

    public String getBodyHtmlNL(){
        return body.replace("\n", "<br />");
    }


    public static BlogPost fromJSON(DBObject o){
        BlogPost bp = new BlogPost();
        bp.setTitle( (String) o.get("title") );
        bp.setBody( (String) o.get("body") );
        bp.setDate( (Date) o.get("date") );
        bp.setId( o.get("_id").toString() );
        return bp;
    }



    // ------------------------------------
    // just some template stuff under here
    // ------------------------------------
    public static BlogPost errorBlog() {
        BlogPost bp = new BlogPost();
        bp.setTitle(errTitle);
        bp.setBody(errBody);
        bp.setDate(new Date());
        bp.setId("new");
        return bp;
    }

    public static BlogPost infoBlog() {
        BlogPost bp = new BlogPost();
        bp.setTitle(infoTitle);
        bp.setBody(infoBody);
        bp.setDate(new Date());
        bp.setId("new");
        return bp;
    }


    static final String errTitle = "Error";
    static final String errBody  = ""+
        "Something sinister has happened, you should "+
        "contact your system administrator, they love email!";


    static final String infoTitle = "Empty";
    static final String infoBody  = ""+
        "No blog posts have been written yet, press create "+
        "or edit to write a new blog post...";

}
