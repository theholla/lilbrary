import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/library_catalog_test", null, null);
   }

   protected void after() {
     try(Connection con = DB.sql2o.open()) {
       String deleteStudentsQuery = "DELETE FROM books *;";
       String deleteCoursesQuery = "DELETE FROM authors *;";
       String deleteCoursesStudentsQuery = "DELETE FROM authors_books *";
       con.createQuery(deleteStudentsQuery).executeUpdate();
       con.createQuery(deleteCoursesQuery).executeUpdate();
       con.createQuery(deleteCoursesStudentsQuery).executeUpdate();
     }
   }
}
