import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class BookTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

//Test whether the array is empty or nor
  @Test
  public void all_emptyAtFirst() {
    assertEquals(Book.all().size(), 0);
  }

// Test for override objects
  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Book firstBook = new Book("Huckleberry Finn");
    Book secondBook = new Book("Huckleberry Finn");
    assertTrue(firstBook.equals(secondBook));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Book myBook = new Book("Huckleberry Finn");
    myBook.save();
    Book savedBook = Book.all().get(0);
    assertTrue(savedBook.equals(myBook));
  }

  @Test
  public void save_assignsIdToObject() {
    Book myBook = new Book("Huckleberry Finn");
    myBook.save();
    Book savedBook = Book.all().get(0);
    assertEquals(myBook.getId(), savedBook.getId());
  }

  @Test
  public void find_findsBookInDatabase_true() {
    Book myBook = new Book("Huckleberry Finn");
    myBook.save();
    Book savedBook = Book.find(myBook.getId());
    assertTrue(myBook.equals(savedBook));
  }

  @Test
  public void addAuthor_addsAuthorToBook() {
    Author myAuthor = new Author("Mark Twain");
    myAuthor.save();

    Book myBook = new Book("Huckleberry Finn");
    myBook.save();

    myBook.addAuthor(myAuthor);
    Author savedAuthor = myBook.getAuthors().get(0);
    assertTrue(myAuthor.equals(savedAuthor));
  }

  @Test
  public void getAuthors_returnsAllAuthors_ArrayList() {
    Author myAuthor = new Author("Mark Twain");
    myAuthor.save();

    Book myBook = new Book("Huckleberry Finn");
    myBook.save();

    myBook.addAuthor(myAuthor);
    List savedAuthors = myBook.getAuthors();
    assertEquals(savedAuthors.size(), 1);
  }

  @Test
  public void delete_deletesAllBooksAndListsAssociations() {
    Author myAuthor = new Author("Mark Twain");
    myAuthor.save();

    Book myBook = new Book("Huckleberry Finn");
    myBook.save();

    myBook.addAuthor(myAuthor);
    myBook.delete();
    assertEquals(myAuthor.getBooks().size(), 0);
  }
 }
