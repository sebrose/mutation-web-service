package uk.co.claysnow.checkout.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.claysnow.checkout.app.CheckoutClient;
import uk.co.claysnow.checkout.domain.Basket;
import uk.co.claysnow.checkout.domain.Batch;
import uk.co.claysnow.checkout.domain.Team;
import uk.co.claysnow.checkout.service.BadResponseException;
import uk.co.claysnow.checkout.service.CheckoutService;
import uk.co.claysnow.checkout.service.JsonHandler;
import uk.co.claysnow.checkout.service.Response;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutClientTest {
  
  private CheckoutClient testee;
  
  @Mock
  private CheckoutService service;

  @Mock
  private JsonHandler jsonHandler;
  
  @Before
  public void setUp() {
    testee = new CheckoutClient(service, jsonHandler);
  }
  
  @Test
  public void shouldReturnTeamWhenServerResponds201() {
    Team expected = new Team("mighthavenamechanged");
    when(service.registerTeam(anyString())).thenReturn(responseWithCode(201));
    when(jsonHandler.fromJson(anyString(), eq(Team.class))).thenReturn(expected);
    Team actual = testee.registerTeam("foo");
    assertSame(expected, actual);
  }
  
  @Test(expected=BadResponseException.class)
  public void shouldThrowExceptionWhenServerDoesNotRespondWith201ForTeamRegistration() {
    when(service.registerTeam(anyString())).thenReturn(responseWithCode(400));
    testee.registerTeam("foo");
  }
  
  @Test
  public void shouldReturnRequirementsWhenServerRespones200() {
    String expected = "some json";
    when(service.requestRequirements(anyString())).thenReturn(new Response(200, expected));
    String actual = testee.requestRequirements(new Team("foo"));
    assertEquals(expected, actual);
  }
  
  @Test
  public void shouldReturnNextBatchWhenNoServerResponds200() {
    Batch expected = new Batch(Collections.<Basket>emptyList());
    when(service.requestBatch(anyString())).thenReturn(responseWithCode(200));
    when(jsonHandler.fromJson(anyString(), eq(Batch.class))).thenReturn(expected);
    Batch actual = testee.requestBatch(new Team("foo"));
    assertSame(expected, actual);
  }
  
  @Test(expected=BadResponseException.class)
  public void shouldThrowExceptionWhenServerDoesNotRespondWith200ForBatchRequest() {
    when(service.requestRequirements(anyString())).thenReturn(responseWithCode(400));
    testee.requestRequirements(new Team("foo"));
  }
  
  private Response responseWithCode(int code) {
    return new Response(code, dummyJson()); 
  }
  
  private String dummyJson() {
    return "never parsed";
  }


}
