import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class PatronTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Patron.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Patron firstPatron = new Patron("Mark Twain", "555-555-5555");
    Patron secondPatron = new Patron("Mark Twain", "555-555-5555");
    assertTrue(firstPatron.equals(secondPatron));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Patron myPatron = new Patron("Mark Twain", "555-555-5555");
    myPatron.save();
    assertTrue(Patron.all().get(0).equals(myPatron));
  }

  @Test
  public void find_findPatronInDatabase_true() {
    Patron myPatron = new Patron("Mark Twain", "555-555-5555");
    myPatron.save();
    Patron savedPatron = Patron.find(myPatron.getId());
    assertTrue(myPatron.equals(savedPatron));
  }
//
//   @Test
//   public void addBook_addsBookToPatron() {
//     Patron myPatron = new Patron("Mark Twain");
//     myPatron.save();
//
//     Book myBook = new Book("Huckleberry Finn");
//     myBook.save();
//
//     myPatron.addBook(myBook);
//     Book savedBook = myPatron.getBooks().get(0);
//     assertTrue(myBook.equals(savedBook));
//   }
//
//   @Test
//   public void getBooks_returnsAllBooks_ArrayList() {
//     Patron myPatron = new Patron("Mark Twain");
//     myPatron.save();
//
//     Book myBook = new Book("Huckleberry Finn");
//     myBook.save();
//
//     myPatron.addBook(myBook);
//     List savedBooks = myPatron.getBooks();
//     assertEquals(savedBooks.size(), 1);
//   }
//
//   @Test
//   public void delete_deletesAllBooksAndListsAssoications() {
//     Patron myPatron = new Patron("Mark Twain");
//     myPatron.save();
//
//     Book myBook = new Book("Huckleberry Finn");
//     myBook.save();
//
//     myPatron.addBook(myBook);
//     myPatron.delete();
//     assertEquals(myBook.getPatrons().size(), 0);
//   }
}
