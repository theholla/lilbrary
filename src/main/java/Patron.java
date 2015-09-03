import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Patron {
  private int id;
  private String name;
  private String phone;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPhone() {
    return phone;
  }

  public Patron(String name, String phone) {
    this.name = name;
    this.phone = phone;
  }

  @Override
  public boolean equals(Object otherPatron){
    if (!(otherPatron instanceof Patron)) {
      return false;
    } else {
      Patron newPatron = (Patron) otherPatron;
      return this.getName().equals(newPatron.getName()) &&
             this.getPhone().equals(newPatron.getPhone()) &&
             this.getId() == newPatron.getId();
    }
  }

  public void delete(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM patrons WHERE id = :id";
      con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void update(String name, String phone) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE patrons SET name = :name, phone = :phone WHERE id = :id";
      con.createQuery(sql)
      .addParameter("name", name)
      .addParameter("phone", phone)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public static List<Patron> all() {
    String sql = "SELECT * FROM patrons ORDER BY name ASC";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Patron.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO patrons (name, phone) VALUES (:name, :phone)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", name)
      .addParameter("phone", phone)
      .executeUpdate()
      .getKey();
    }
  }

  public static Patron find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM patrons where id=:id";
      Patron patron = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Patron.class);
      return patron;
    }
  }

  // public void addAuthor(Author author) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "INSERT INTO authors_patrons (author_id, patron_id) VALUES (:author_id, :patron_id)";
  //     con.createQuery(sql)
  //     .addParameter("author_id", author.getId())
  //     .addParameter("patron_id", this.getId())
  //     .executeUpdate();
  //   }
  // }

  // public ArrayList<Copy> getCopies() {
  //   try(Connection con = DB.sql2o.open()){
  //     String sql = "SELECT copy_id FROM checkouts WHERE patron_id = :patron_id";
  //     List<Integer> copy_ids = con.createQuery(sql)
  //     .addParameter("patron_id", this.getId())
  //     .executeAndFetch(Integer.class);
  //
  //     ArrayList<Copy> copies = new ArrayList<Copy>();
  //
  //     for (Integer copy_id : copy_ids) {
  //       String patronQuery = "Select * From copies WHERE id = :copy_id";
  //       Copy copy = con.createQuery(patronQuery)
  //       .addParameter("copy_id", copy_id)
  //       .executeAndFetchFirst(Copy.class);
  //       copys.add(copy);
  //     }
  //     return copies;
  //   }
  // }

  // public void delete() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String deleteQuery = "DELETE FROM patrons WHERE id = :id;";
  //     con.createQuery(deleteQuery)
  //     .addParameter("id", id)
  //     .executeUpdate();
  //
  //     String joinDeleteQuery = "DELETE FROM checkouts WHERE patron_id = :patron_id";
  //     con.createQuery(joinDeleteQuery)
  //     .addParameter("patron_id", this.getId())
  //     .executeUpdate();
  //   }
  // }

}
