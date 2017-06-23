import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Сергій on 12.06.2017.
 */
public class JDBC {

    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/test";
    private static final String user = "root";
    private static final String password = "";

    // JDBC variables for opening and managing connection
    private Connection con;

    private  Statement stmt;


    private  ResultSet rs;


    public JDBC() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);
             // getting Statement object to execute query
            stmt = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Teacher> getTeachers() {
        return  this.getTeachers("");
    }

    public Teacher getTeacher(String name) {
        return  this.getTeachers("where name='"+name+"'").get(0);
    }



        private List<Teacher> getTeachers(String parametrsQuery) {
        String query = "select * from teacher "+parametrsQuery;
        List<Teacher> teacherList=new ArrayList<Teacher>();

        try {
               // executing SELECT query
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                Teacher teacher=new Teacher();
                teacher.setId(rs.getInt(1));
                teacher.setName( rs.getString(2));
                teacher.setCountOfLerner(rs.getInt(3));
                teacherList.add(teacher);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return teacherList;
    }


    public List<Lerner> getLerners() {
        return  this.getLerners("");
    }

    public Lerner getLerner(String name) {
        return  this.getLerners("where name='"+name+"'").get(0);
    }



    public List<Lerner> getLerners(String parametrsQuery) {
        String query = "select * from lerner "+parametrsQuery;
        List<Lerner> teacherList=new ArrayList<Lerner>();

        try {
                // executing SELECT query
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                Lerner lerner=new Lerner();
                lerner.setId(rs.getInt(1));
                lerner.setName( rs.getString(2));
                lerner.setTeacher(rs.getString(3));
                teacherList.add(lerner);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return teacherList;
    }

    public void insertDate(String query){
       try {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertTeacher(Teacher teacher){
        String query = "INSERT INTO test.teacher (name, countOfLerner) \n" +
                     " VALUES ( '"+teacher.getName() +"', "+0+");";
        insertDate(query);
    }

    public void insertLerner(Lerner lerner){
        String query = "INSERT INTO test.lerner (name, teacher) \n" +
                " VALUES ( '"+lerner.getName() +"', '"+lerner.getTeacher() +"');";
        insertDate(query);

    }

        public void updateTeacher(String name, int countOfLector){
            try
            {
                // create our java preparedstatement using a sql update query
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE Teacher SET countOfLerner = ? WHERE name = ?");

                // set the preparedstatement parameters
                ps.setInt(1,countOfLector);
                ps.setString(2,name);
                ps.executeUpdate();
                ps.close();
            }
            catch (SQLException se)
            {}
        }


    public void findAll(String teacher) {
    }


   public boolean isPresentTeacher(String table,String name){

       boolean isUserExists = false;
            try (PreparedStatement ps = con.prepareStatement("select 1 from `"+table+"` where `name` = ?"))
            {
        ps.setString(1, name);

        try (ResultSet rs = ps.executeQuery()){
            if (rs.next())
                return true;
        }
    } catch (SQLException e) {
                e.printStackTrace();
            }

   return false;
   }
}