import java.util.HashMap;
import java.util.List;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;


public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";
    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Patron> patrons = Patron.all();
      model.put("patrons", patrons);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    /* Index --> List of Books */
    get("/books", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Book> books = Book.all();
      model.put("books", books);
      model.put("allAuthors", Author.all());
      model.put("template", "templates/books.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    /* Index --> List of Authors */
    get("/authors", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Author> authors = Author.all();
      model.put("authors", authors);
      model.put("template", "/templates/authors.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    /* Index --> POST a new patron*/
    post("/patron/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String phone = request.queryParams("phone");
      Patron newPatron = new Patron(name, phone);
      newPatron.save();
      response.redirect("/");
      return null;
    });

    /* Index --> Patron page */
    get("/patrons/:id", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Patron patron = Patron.find(id);
      model.put("patron", patron);
      model.put("allPatrons", Patron.all());
      List<Book> books = Book.all();
      model.put("books", books);
      model.put("allAuthors", Author.all());
      model.put("template", "templates/patron.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    /* List of books -> POST a new book*/
    post("/books", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("title");
      Book newBook = new Book(name);
      newBook.save();
      response.redirect("/books");
      return null;
    });

    /* List of authors -> POST a new author*/
    post("/authors", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Author newAuthor = new Author(name);
      newAuthor.save();
      response.redirect("/authors");
      return null;
    });

    /* List of books --> view an individual book */
    get("/books/:id", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Book book = Book.find(id);
      model.put("book", book);
      model.put("allAuthors", Author.all());
      model.put("template", "templates/book.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    /* List of authors --> view an individual author */
    get("/authors/:id", (request,response) ->{
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Author author = Author.find(id);
      model.put("author", author);
      model.put("allBooks", Book.all());
      model.put("template", "templates/author.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    /* Individual book --> POST an author */
    post("/add_authors", (request, response) -> {
      int bookId = Integer.parseInt(request.queryParams("book_id"));
      int authorId = Integer.parseInt(request.queryParams("author_id"));
      Author author = Author.find(authorId);
      Book book = Book.find(bookId);
      book.addAuthor(author);
      response.redirect("/books/" + bookId);
      return null;
    });

    /* Individual author --> POST an book */
    post("/add_books", (request, response) -> {
      int bookId = Integer.parseInt(request.queryParams("book_id"));
      int authorId = Integer.parseInt(request.queryParams("author_id"));
      Author author = Author.find(authorId);
      Book book = Book.find(bookId);
      author.addBook(book);
      response.redirect("/authors/" + authorId);
      return null;
    });

    get("/books/:id/edit", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Book editBook = Book.find(id);
      model.put("editBook", editBook);
      List<Book> books = Book.all();
      model.put("books", books);
      model.put("allAuthors", Author.all());
      model.put("template", "templates/books.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/books/:id/edit", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Book editBook = Book.find(id);
      String title = request.queryParams("title");
      editBook.update(title);
      List<Book> books = Book.all();
      model.put("books", books);
      model.put("allAuthors", Author.all());
      model.put("template", "templates/books.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/authors/:id/edit", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Author editAuthor = Author.find(id);
      model.put("editAuthor", editAuthor);
      List<Author> authors = Author.all();
      model.put("authors", authors);
      model.put("allBooks", Book.all());
      model.put("template", "templates/authors.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/authors/:id/edit", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Author editAuthor = Author.find(id);
      String name = request.queryParams("name");
      editAuthor.update(name);
      List<Author> authors = Author.all();
      model.put("authors", authors);
      model.put("allBooks", Book.all());
      model.put("template", "templates/authors.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/books/:id/delete", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Book deleteBook = Book.find(id);
      deleteBook.delete(id);
      List<Book> books = Book.all();
      model.put("books", books);
      model.put("allAuthors", Author.all());
      model.put("template", "templates/books.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    get("/copies/:id/checkedout", (request,response) -> {
     HashMap<String, Object> model = new HashMap<String, Object>();
     int id = Integer.parseInt(request.params("id"));
     Copy copy = Copy.find(id);
     Copy patronCopy = copy.addPatron();
     patronCopy.checkOut(patronCopy.isCheckedOut());

     ArrayList<Copy> patronCopies = Copy.getCopies();
     model.put("patronCopies", patronCopies);

     List<Copy> allLibraryCheckouts = Copy.allCheckedoutCopies();
     model.put("allCheckedout", allLibraryCheckouts);
     model.put("allPatrons", Patron.all());
     model.put("template", "templates/patron.vtl");
     return new ModelAndView(model, layout);
   }, new VelocityTemplateEngine());


  }
}
