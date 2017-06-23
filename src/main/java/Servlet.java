import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Сергій on 13.06.2017.
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
     private  JDBC jdbc;
    public Servlet() {
            jdbc=new JDBC();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
   //     resp.getWriter().write("{ \"result\": \"result\"}");
     }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
          Gson gson = new Gson();
          if(req.getParameter("object").equals("teacher")){
              switch (req.getParameter("method")){
                  case "add":
                      Teacher teacher=requestToTeacher(req);
                      if(!teacher.getName().isEmpty()&&!jdbc.isPresentTeacher("teacher",teacher.getName()))
                      {
                          jdbc.insertTeacher(teacher);
                          resp.getWriter().write(gson.toJson(jdbc.getTeacher(teacher.getName())));
                      }
                      else resp.getWriter().write("{ \"alert\": \"teacher is present\"}");
                    break;
                case "delete":
                    break;
                case "showAll":
                      List<Teacher> t=jdbc.getTeachers();
                    System.out.print(gson.toJson(t));
                    resp.getWriter().write(gson.toJson(t));
                    break;
            }}
        if(req.getParameter("object").equals("lerner")) {
            Lerner lerner=requestToLerner(req);
            switch (req.getParameter("method")) {
                case "add":
                    if(!lerner.getName().isEmpty()&&!jdbc.isPresentTeacher("lerner",lerner.getName()))
                    {
                    jdbc.insertLerner(lerner);
                    jdbc.updateTeacher(lerner.getTeacher(),
                    jdbc.getTeacher(lerner.getTeacher()).getCountOfLerner()+1);
                    resp.getWriter().write(gson.toJson(jdbc.getLerner(lerner.name)));
                    }
                    else resp.getWriter().write("{ \"alert\": \"lerner is present\"}");

                    break;
                case "delete":
                    break;
                case "showAll":
                    List<Lerner> t=jdbc.getLerners();
                    System.out.print(gson.toJson(t));
                    resp.getWriter().write(gson.toJson(t));
                    break;
            }
      }




    }

    private Teacher requestToTeacher(HttpServletRequest req){
        Teacher teacher=new Teacher();
         teacher.setName(req.getParameter("data"));
            teacher.setCountOfLerner(0);
        return teacher;
    }

    private Lerner requestToLerner(HttpServletRequest req){
        Lerner lerner=new Lerner();
        lerner.setName(req.getParameter("name"));
        lerner.setTeacher(req.getParameter("teacher"));
        return lerner;
    }
}
