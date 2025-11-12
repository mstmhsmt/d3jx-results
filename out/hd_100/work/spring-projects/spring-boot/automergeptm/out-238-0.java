package org.springframework.boot.actuate.endpoint;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link EndpointId}.
 *
 * @author Phillip Webb
 */
public class EndpointIdTests {
  @Test public void ofWhenNullThrowsException() {
    assertThatIllegalArgumentException().isThrownBy(() -> EndpointId.of(null)).withMessage("Value must not be empty");
  }

  @Test public void ofWhenEmptyThrowsException() {
    assertThatIllegalArgumentException().isThrownBy(() -> EndpointId.of("")).withMessage("Value must not be empty");
  }


  @Test public void ofWhenContainsInvalidCharThrowsException() {
    assertThatIllegalArgumentException().isThrownBy(() -> EndpointId.of("foo!bar")).withMessage("Value must only contain valid chars");
  }


  @Test public void ofWhenHasBadCharThrowsException() {
    assertThatIllegalArgumentException().isThrownBy(() -> EndpointId.of("foo!bar")).withMessage("Value must only contain valid chars");
  }

  @Test public void ofWhenStartsWithNumberThrowsException() {
    assertThatIllegalArgumentException().isThrownBy(() -> EndpointId.of("1foo")).withMessage("Value must not start with a number");
  }

  @Test public void ofWhenStartsWithUppercaseLetterThrowsException() {
    assertThatIllegalArgumentException().isThrownBy(() -> EndpointId.of("Foo")).withMessage("Value must not start with an uppercase letter");
  }

  @Test public void ofWhenContainsDotIsValid() {
    EndpointId endpointId = EndpointId.of("foo.bar");
    assertThat(endpointId.toString()).isEqualTo("foo.bar");
  }

  @Test public void ofWhenContainsDashIsValid() {
    EndpointId endpointId = EndpointId.of("foo-bar");
    assertThat(endpointId.toString()).isEqualTo("foo-bar");
  }

  @Test public void equalsAndHashCode() {
    EndpointId one = EndpointId.of("foobar1");
    EndpointId two = EndpointId.of("fooBar1");
    EndpointId three = EndpointId.of("foo-bar1");
    EndpointId four = EndpointId.of("foo.bar1");
    EndpointId five = EndpointId.of("barfoo1");
    EndpointId six = EndpointId.of("foobar2");
    assertThat(one.hashCode()).isEqualTo(two.hashCode());
    assertThat(one).isEqualTo(one).isEqualTo(two).isEqualTo(two).isEqualTo(three).isEqualTo(four).isNotEqualTo(five).isNotEqualTo(six);
  }

  @Test public void toLowerCaseStringReturnsLowercase() {
    assertThat(EndpointId.of("fooBar").toLowerCaseString()).isEqualTo("foobar");
  }

  @Test public void toStringReturnsString() {
    assertThat(EndpointId.of("fooBar").toString()).isEqualTo("fooBar");
  }

  @Test public void fromPropertyValueStripsDashes() {
    EndpointId fromPropertyValue = EndpointId.fromPropertyValue("foo-bar");
    assertThat(fromPropertyValue).isEqualTo(EndpointId.of("fooBar"));
  }

  @Test public void ofWhenContainsSlashThrowsException() {
    this.thrown.expect(IllegalArgumentException.class);
    this.thrown.expectMessage("Value must only contain valid chars");
    EndpointId.of("foo/bar");
  }
}
