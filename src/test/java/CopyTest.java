import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class CopyTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Copy.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Copy firstCopy = new Copy(true, "2015-01-15");
    Copy secondCopy = new Copy(true, "2015-01-15");
    assertTrue(firstCopy.equals(secondCopy));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Copy myCopy = new Copy(true, "2015-01-15");
    myCopy.save();
    Copy savedCopy = Copy.all().get(0);
    assertTrue(savedCopy.equals(myCopy));
  }

  @Test
  public void save_assignsIdToObject() {
    Copy myCopy = new Copy(true, "2015-01-15");
    myCopy.save();
    Copy savedCopy = Copy.all().get(0);
    assertEquals(myCopy.getId(), savedCopy.getId());
  }

  @Test
  public void find_findsCopyInDatabase_true() {
    Copy myCopy = new Copy(true, "2015-01-15");
    myCopy.save();
    Copy savedCopy = Copy.find(myCopy.getId());
    assertTrue(myCopy.equals(savedCopy));
  }

  @Test
  public void addPatron_addsPatronToCopy() {
    Patron myPatron = new Patron("Mark Twain", "555-555-5555");
    myPatron.save();

    Copy myCopy = new Copy(true, "2015-01-15");
    myCopy.save();

    myCopy.addPatron(myPatron);
    Patron savedPatron = myCopy.getPatrons().get(0);
    assertTrue(myPatron.equals(savedPatron));
  }

  @Test
  public void getPatrons_returnsAllPatrons_ArrayList() {
    Patron myPatron = new Patron("Mark Twain", "555-555-5555");
    myPatron.save();

    Copy myCopy = new Copy(true, "2015-01-15");
    myCopy.save();

    myCopy.addPatron(myPatron);
    List savedPatrons = myCopy.getPatrons();
    assertEquals(savedPatrons.size(), 1);
  }
}
