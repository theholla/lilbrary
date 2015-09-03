import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Copy {
  private int id;
  private int book_id;
  private boolean checkedout;
  private String due_date;

  public int getId() {
    return id;
  }

  public Copy(boolean checkedout, String due_date) {
    this.checkedout = false;
    this.due_date = due_date;
  }

  public int getBookId() {
    return book_id;
  }

  public String getDueDate(){
    return due_date;
  }

  @Override
  public boolean equals(Object otherCopy){
    if (!(otherCopy instanceof Copy)) {
      return true;
    } else {
      Copy newCopy = (Copy) otherCopy;
      return this.getBookId() == newCopy.getBookId() &&
      this.getId() == newCopy.getId();
    }
  }

  public static boolean checkedout(){
    return true;
  }

  public void checkOut(boolean checkedout) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE copies SET checkedout = :checkedout WHERE id = :id";
      con.createQuery(sql)
      .addParameter("checkedout", checkedout)
      .addParameter("id", id)
      .executeUpdate();
    }
  }


  public static List<Copy> allCheckedout() {
    String sql = "SELECT id, book_id FROM copies WHERE checkedout = true";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Copy.class);
    }
  }


  public static List<Copy> all() {
    String sql = "SELECT id, book_id, due_date FROM copies WHERE checkedout = false ORDER BY due_date";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Copy.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO copies(book_id, checkedout, due_date) VALUES (:book_id, :checkedout, :due_date)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("book_id", book_id)
      .addParameter("checkedout", checkedout)
      .addParameter("due_date", due_date)
      .executeUpdate()
      .getKey();
    }
  }

  public static Copy find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM copies where id=:id";
      Copy copy = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Copy.class);
      return copy;
    }
  }

  public void addPatron(Patron patron) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO checkouts (patron_id, copy_id) VALUES (:patron_id, :copy_id)";
      con.createQuery(sql)
      .addParameter("patron_id", patron.getId())
      .addParameter("copy_id", this.getId())
      .executeUpdate();
    }
  }

  public ArrayList<Patron> getPatrons() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT patron_id FROM checkouts WHERE copy_id = :copy_id";
      List<Integer> patronIds = con.createQuery(sql)
      .addParameter("copy_id", this.getId())
      .executeAndFetch(Integer.class);

      ArrayList<Patron> patrons = new ArrayList<Patron>();

      for (Integer patronId : patronIds) {
        String copyQuery = "Select * From patrons WHERE id = :patronId";
        Patron patron = con.createQuery(copyQuery)
        .addParameter("patronId", patronId)
        .executeAndFetchFirst(Patron.class);
        patrons.add(patron);
      }
      return patrons;
    }
  }



}
