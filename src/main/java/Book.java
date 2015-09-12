import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Book {
  private int id;
  private String title;

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public Book(String title) {
    this.title = title;
  }

  @Override
  public boolean equals(Object otherBook){
    if (!(otherBook instanceof Book)) {
      return false;
    } else {
      Book newBook = (Book) otherBook;
      return this.getTitle().equals(newBook.getTitle()) &&
      this.getId() == newBook.getId();
    }
  }

  public void delete(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM books WHERE id = :id";
      con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void update(String title) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE books SET title = :title WHERE id = :id";
      con.createQuery(sql)
      .addParameter("title", title)
      .addParameter("id", id)
      .executeUpdate();
    }
  }


  public static List<Book> all() {
    String sql = "SELECT * FROM books ORDER BY title ASC";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Book.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books (title) VALUES (:title)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("title", title)
      .executeUpdate()
      .getKey();
    }
  }

  public static Book find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM books where id=:id";
      Book book = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Book.class);
      return book;
    }
  }

  public void addAuthor(Author author) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO authors_books (author_id, book_id) VALUES (:author_id, :book_id)";
      con.createQuery(sql)
      .addParameter("author_id", author.getId())
      .addParameter("book_id", this.getId())
      .executeUpdate();
    }
  }

  public ArrayList<Author> getAuthors() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT DISTINCT author_id FROM authors_books WHERE book_id = :book_id";
      List<Integer> authorIds = con.createQuery(sql)
      .addParameter("book_id", this.getId())
      .executeAndFetch(Integer.class);

      ArrayList<Author> authors = new ArrayList<Author>();

      for (Integer authorId : authorIds) {
        String bookQuery = "Select * From authors WHERE id = :authorId";
        Author author = con.createQuery(bookQuery)
        .addParameter("authorId", authorId)
        .executeAndFetchFirst(Author.class);
        authors.add(author);
      }
      return authors;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM books WHERE id = :id;";
      con.createQuery(deleteQuery)
      .addParameter("id", id)
      .executeUpdate();

      String joinDeleteQuery = "DELETE FROM authors_books WHERE book_id = :bookId";
      con.createQuery(joinDeleteQuery)
      .addParameter("bookId", this.getId())
      .executeUpdate();
    }
  }


}
