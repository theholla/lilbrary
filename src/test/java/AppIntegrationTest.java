import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.assertj.core.api.Assertions.assertThat;


public class AppIntegrationTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();


  @Override
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @ClassRule
  public static DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Library Catalog");
  }

  @Test
  public void bookFormIsDisplayed() {
    goTo("http://localhost:4567/");
    click("a", withText("Add or view a book"));
    assertThat(pageSource()).contains("Add a new book");
  }

  @Test
  public void authorFormIsDisplayed() {
    goTo("http://localhost:4567/");
    click("a", withText("Add or view an author"));
    assertThat(pageSource()).contains("Add a new author");
  }

  @Test
  public void bookTitleIsDisplayedInListWhenCreated() {
    goTo("http://localhost:4567/books");
    fill("#title").with("50 Shades of Green");
    submit(".btn");
    assertThat(pageSource()).contains("50 Shades of Green");
  }

  @Test
  public void authorNameIsDisplayedInListWhenCreated() {
    goTo("http://localhost:4567/authors");
    fill("#name").with("JK Rowling");
    submit(".btn");
    assertThat(pageSource()).contains("JK Rowling");
  }

  @Test
  public void bookHasItsOwnPage() {
    Book testBook = new Book("Love In The Time of Cholera");
    testBook.save();
    goTo("http://localhost:4567/books");
    click("a", withText("Love In The Time of Cholera"));
    assertThat(pageSource()).contains("Love In The Time of Cholera");
  }

  @Test
  public void authorHasItsOwnPage() {
    Author testAuthor = new Author("E.L. Jim");
    testAuthor.save();
    goTo("http://localhost:4567/authors");
    click("a", withText("E.L. Jim"));
    assertThat(pageSource()).contains("E.L. Jim");
  }

  // @Test
  // public void authorIsAddedToBook() {
  //   Author testAuthor = new Author("E.L. Jim");
  //   testAuthor.save();
  //   Book testBook = new Book("50 Shades of Green");
  //   testBook.save();
  //   goTo("http://localhost:4567/books/");
  //   click("a", withText("50 Shades of Green"));
  //   submit(".btn");
  //   assertThat(pageSource()).contains("E.L. Jim");
  // }




  }
