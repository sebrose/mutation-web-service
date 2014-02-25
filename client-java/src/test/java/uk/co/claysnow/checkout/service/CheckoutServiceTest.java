package uk.co.claysnow.checkout.service;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

// pointless md5sum test of a pure integration concern
@RunWith(MockitoJUnitRunner.class)
public class CheckoutServiceTest {
  
  private static String SOME_JSON = "{foo:100}";
  
  @Mock
  private RestCall rest;
  
  private CheckoutService testee;
 
  
  
  @Before
  public void setUp() {
    testee = new  CheckoutService(rest);
  }

  @Test
  public void shouldMakePutCallWhenRegisteringTeam() {
    testee.registerTeam(SOME_JSON);
    verify(rest).put("/Checkout/Team", SOME_JSON);
  }
  
  @Test
  public void shouldMakeGetCallWhenRequestingRequirements() {
    testee.requestRequirements("aTeam");
    verify(rest).get("/Checkout/Requirements/aTeam");
  }

  @Test
  public void shouldMakeGetCallWhenRequestingScore() {
    testee.requestScore("aTeam");
    verify(rest).get("/Checkout/Score/aTeam");
  }
  
  @Test
  public void shouldMakeGetCallWhenRequestingBatch() {
    testee.requestBatch("aTeam");
    verify(rest).get("/Checkout/Batch/aTeam");
  }
  
  @Test
  public void shouldMakeGetCallWhenRequestingPriceList() {
    testee.requestPriceList("aTeam");
    verify(rest).get("/Checkout/PriceList/aTeam");
  }
  
  @Test
  public void shouldMakePutCallWhenSubmittingTotals() {
    testee.submitTotals("aTeam", SOME_JSON);
    verify(rest).put("/Checkout/Batch/aTeam", SOME_JSON);
  }
  
}
