import java.util.*;
import java.io.IOException;  
import java.io.PrintWriter;  
import javax.servlet.ServletException;  
import javax.servlet.http.Cookie;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;
// Imports need to be checked
import java.sql.*;  
import java.io.*;
/*Some Documentation:
        
            ERRORMESSAGEs in Cookies Types:
             1) insertfailed:    
             
*/

public class InsertServlet extends HttpServlet 
{  
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException 
    {
       
        response.setContentType("text/html");  
        System.out.println("In InsertServlet");
        String cType=request.getParameter("cType");  
        int preferences[]= new int[10];
        String cName[]= new String[10];
        for(int j=0;j<10;j++){preferences[j]=-1;cName[j]=null;}
        int i=-1;
        do
        {
            i++;
            if(i>=10)break;
            cName[i]=request.getParameter("con-field-"+Integer.toString(i+1));
/*Exception here*/
            if(cName[i]==null)break;
            System.out.println("cName["+i+"] : "+cName[i]);
            String ps= request.getParameter("rating-"+(i+1));
            if(ps==null)break;
            preferences[i]=Integer.parseInt(ps);
            System.out.println("preferences["+i+"] : "+preferences[i]);
       
        }while(true);
        // i= 1 more than no. of ratings
        int pref_count=i;
        System.out.println("pref_count: "+ i);
        String errorMsg = null;
        Cookie[] cookies= request.getCookies();
        String name=null;


        for (Cookie ck: cookies) {
            if("username".equals(ck.getName()))
                name=ck.getValue();
        }
        if(name==null) {
            errorMsg="No User Logged In!";  //changed to use this directly as the error message
            Cookie cookey = new Cookie("insertfailed", errorMsg);
            cookey.setMaxAge(60*2); 
            response.addCookie(cookey);
            response.sendRedirect(request.getContextPath()+"/index.html");
        }
        if(name=="kinkax") {
            //For our poor kinkax. 
                System.out.println("Exiting 'Kinkax' ;-)");
            response.sendRedirect(request.getContextPath()+"/results.html");
           //return;  
        }

          // JDBC driver name and database URL
           String JDBC_DRIVER="oracle.jdbc.driver.OracleDriver";  
            String DB_URL="jdbc:oracle:thin:@localhost:1521:XE"; 
               //  Database credentials
            String USER = "system";
            String PASS = "2710";
            PreparedStatement ps = null;
            ResultSet rs = null;
            PreparedStatement ps_check = null;
            ResultSet rs_check = null; 
            Statement ins_pref = null;
            Statement ins_pref_final = null;
            PreparedStatement ps_ins = null;
            ResultSet rs_ins = null;
            PreparedStatement ps_get = null;
            ResultSet rs_get = null;
        try{
                // Register JDBC driver
                Class.forName(JDBC_DRIVER);
                //Open the Connection
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                
                if (conn != null) 
                        {
                            System.out.println("Connected");
                           
                        }
                else
                {
                    System.out.println("Couldn't connect to Database");
                }
                i=0;

                while(i<pref_count)
                {
                    errorMsg=null;
                    System.out.println(cName[i]);
                    ps = conn.prepareStatement("select count(*) as count_val from "+cType+" where title ='"+cName[i]+"'");
                    rs = ps.executeQuery();
                    System.out.println("After initial Ambiguity check Query Execution"); 
                    int count_val=0;
                    String cType_id=null;  
                        while(rs.next())
                        {
                             count_val=rs.getInt("count_val");
                             break;
                        }

                            System.out.println("count_val: "+count_val);
                           if(count_val==0) { 
                             errorMsg="Your choice of "+cType+"("+Integer.toString(i+1)+") : "+cName[i]+" is unique Sir! It doesn't exist in our database.";  
                            Cookie cookey = new Cookie("insertfailed", errorMsg);
                            cookey.setMaxAge(60); 
                            response.addCookie(cookey);
                            response.sendRedirect(request.getContextPath()+"/"+cType+".html");
                                                
                            }

                       else if(count_val>1)
                         { 
                            errorMsg="The "+cType+"("+Integer.toString(i+1)+") : "+cName[i]+" name used is ambiguous!";  
                            Cookie cookey = new Cookie("insertfailed", errorMsg);
                            cookey.setMaxAge(60); 
                            response.addCookie(cookey);
                            response.sendRedirect(request.getContextPath()+"/"+cType+".html");
                            
                        }
                        else 
                        {
                        
                             ps_get= conn.prepareStatement("select distinct("+cType+"_id) as dis_id from "+cType+" where title= '"+cName[i]+"'");
                             rs_get = ps_get.executeQuery();    
                             System.out.println("After cType_id Query Execution"); 
                                    while(rs_get.next())
                                {
                                     cType_id= rs_get.getString("dis_id"); 
                                     break;
                                }

                                System.out.println("cType_id: "+cType_id); 

                              ps_check= conn.prepareStatement("select count(*) as count_check from user_"+cType+" where "+cType+"_id = '"+cType_id+"' and user_id='"+name+"'");
                              rs_check = ps_check.executeQuery();    
                              int val_check=0;
                              while(rs_check.next()){val_check=rs_check.getInt("count_check");break;}
                             
                              System.out.println("count_check: "+val_check);
                              System.out.println("After count_check Query Execution"); 
                                 if(val_check!=0)
                                 {
                                   /*errorMsg="You have already rated "+cType+"("+Integer.toString(i+1)+")"+" before!";  //changed to use this directly as the error message
                                    Cookie cookey = new Cookie("insertfailed", errorMsg);
                                    cookey.setMaxAge(60); 
                                    response.addCookie(cookey);
                                    response.sendRedirect(request.getContextPath()+"/"+cType+".html");*/

                                    ins_pref= conn.createStatement();
                                    String sql= "delete from user_"+cType+" where user_id='"+name+"' and "+cType+"_id ='"+cType_id+"'";
                                    System.out.println("Just before Delete Statement");
                                    ins_pref.executeUpdate(sql);
                                    /*try{
                                        conn.commit();
                                    }catch(Exception e){e.printStackTrace();}*/
                                        
                                }
                                

                            ins_pref_final=conn.createStatement();
                            System.out.println("Before Final Insert Query..");
                            String sql= "insert into user_"+cType+" values('"+name+"','"+cType_id+"',"+preferences[i]+")";
                            ins_pref_final.executeUpdate(sql);
                            System.out.println("Insertion Done!!"); 

                          }

                     i++;
                 }  //While loop ends here
                                
                // If control reaches here, then NO errors in queries. Hence, **Redirecting....
                
                    Cookie cookey = new Cookie("cType", cType);
                    response.addCookie(cookey);
                    response.sendRedirect(request.getContextPath()+"/redirect.html");
                    

            }
            catch(Exception e) {
                e.printStackTrace();
            }

        finally 
        {
            try
             {
                if(rs!=null) rs.close();
                if(ps!=null) ps.close();
                if(rs_check!=null) rs_check.close();
                if(ps_check!=null) ps_check.close();
                if(rs_get!=null) rs_get.close();
                if(ps_get!=null) ps_get.close();
                if(rs_ins!=null) rs_ins.close();
                if(ps_ins!=null) ps_ins.close();
              }catch(Exception e){e.printStackTrace();}
        } 
    }
}
