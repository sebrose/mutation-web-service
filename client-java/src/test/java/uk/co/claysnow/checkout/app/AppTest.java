package uk.co.claysnow.checkout.app;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.claysnow.checkout.domain.Team;

@RunWith(MockitoJUnitRunner.class)
public class AppTest {
  
  @Mock
  private CheckoutClient client;
  
  private App testee;

  private ByteArrayOutputStream bos;
  
  @Before
  public void setUp() {
    bos = new ByteArrayOutputStream();
    testee = new App(client, new PrintStream(bos));
  }
  

  @Test
  public void shouldRegisterTeamWithServer() {
    when(client.registerTeam(anyString())).thenReturn(new Team("foo"));
    testee.runCommand("register", "foo");
    verify(client).registerTeam("foo");
  }
  
  @Test
  public void shouldPrintTeamNameReturnedByServerToConsole() {
    String name = "thename";
    when(client.registerTeam(anyString())).thenReturn(new Team(name));
    testee.runCommand("register", "foo");
    assertTrue(bos.toString().contains(name));
  }
  
  @Test
  public void shouldPrintRequirementsToConsole() {
    String expectedText = "some text that seb wrote";
    when(client.requestRequirements(any(Team.class))).thenReturn(expectedText);
    testee.runCommand("requirements", "foo");
    assertTrue(bos.toString().contains(expectedText));
  }

}
