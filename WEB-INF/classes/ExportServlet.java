import java.util.*;
import java.io.IOException;  
import java.io.PrintWriter;  
import javax.servlet.ServletException;  
import javax.servlet.http.Cookie;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;
import java.sql.*;  
import java.io.*;

public class ExportServlet extends HttpServlet 
{  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException 
    {
        response.setContentType("text/html");  
        System.out.println("In ExportServlet");
        String cType=null;
        Cookie[] cookies= request.getCookies();
        for (Cookie ck: cookies) {
            if("cType".equals(ck.getName()))
                cType=ck.getValue();
        }
        //cType recieved from Cookies
        String exports[]= new String[10];
        String export_check="";

        for(int j=0;j<10;j++){exports[j]=null;}

        int i=-1;
        do
        {
            i++;
            if(i>=10)break;
            exports[i]=request.getParameter("export-"+Integer.toString(i+1));
            if(exports[i]==null)
            {
            	export_check+="0"+"|";
            }
            else export_check+="1"+"|";
            System.out.println("export_check status: " +export_check);
        }while(true);

        System.out.println("Final export check stats: "+export_check);

        Cookie cookey = new Cookie("export_check",export_check);
            cookey.setMaxAge(60*2);       //2 mins
            response.addCookie(cookey); 
        response.sendRedirect(request.getContextPath()+"/"+cType+".html");
      }
}