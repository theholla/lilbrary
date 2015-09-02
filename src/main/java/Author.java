import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Author {
  private int id;
  private String name;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Author(String name) {
    this.name = name;
  }

  public static List<Author> all() {
    String sql = "SELECT id, name FROM Authors";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Author.class);
    }
  }

  @Override
  public boolean equals(Object otherAuthor){
    if (!(otherAuthor instanceof Author)) {
      return false;
    } else {
      Author newAuthor = (Author) otherAuthor;
      return this.getName().equals(newAuthor.getName());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO Authors(name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .executeUpdate()
      .getKey();
    }
  }

  public static Author find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM Authors WHERE id=:id";
      Author Author = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Author.class);
      return Author;
    }
  }

  public void addBook(Book book) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO authors_books (book_id, author_id) VALUES (:book_id, :author_id)";
      con.createQuery(sql)
      .addParameter("book_id", book.getId())
      .addParameter("author_id", this.getId())
      .executeUpdate();
    }
  }

  public ArrayList<Book> getBooks() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT book_id FROM authors_books WHERE author_id = :author_id";
      List<Integer> bookIds = con.createQuery(sql)
      .addParameter("author_id", this.getId())
      .executeAndFetch(Integer.class);

      ArrayList<Book> books = new ArrayList<Book>();

      for (Integer bookId : bookIds) {
        String bookQuery = "Select * From books WHERE id = :bookId";
        Book book = con.createQuery(bookQuery)
        .addParameter("bookId", bookId)
        .executeAndFetchFirst(Book.class);
        books.add(book);
      }
      return books;
    }
  }

  public void update(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE authors SET name = :name WHERE id = :id";
      con.createQuery(sql)
      .addParameter("name", name)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM authors WHERE id = :id;";
      con.createQuery(deleteQuery)
      .addParameter("id", id)
      .executeUpdate();

      String joinDeleteQuery = "DELETE FROM authors_books WHERE author_id = :authorId";
      con.createQuery(joinDeleteQuery)
      .addParameter("authorId", this.getId())
      .executeUpdate();
    }
  }
}
