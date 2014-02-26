package uk.co.claysnow.checkout.app;

import java.io.PrintStream;

import uk.co.claysnow.checkout.domain.Team;
import uk.co.claysnow.checkout.service.CheckoutService;
import uk.co.claysnow.checkout.service.JerseyRestCall;
import uk.co.claysnow.checkout.service.JsonHandler;

import com.sun.jersey.api.client.Client;

public class App {

  private final CheckoutClient client;
  private final PrintStream    out;

  App(CheckoutClient client, PrintStream out) {
    this.client = client;
    this.out = out;
  }

  public static void main(String[] args) {
    if (checkArgs(args)) {
      Client webClient = Client.create();
      JerseyRestCall rest = new JerseyRestCall(webClient, args[1],
          Integer.valueOf(args[2]));
      CheckoutService service = new CheckoutService(rest);

      CheckoutClient client = new CheckoutClient(service, new JsonHandler());
      App app = new App(client, System.out);
      app.runCommand(args[0], args[3]);
    } else {
      showUsage();
    }
  }

  void runCommand(String command, String team) {
    if ("register".equalsIgnoreCase(command)) {
      Team registeredTeam = client.registerTeam(team);
      out.println("Team registered as " + registeredTeam.getName());
    } else if ("requirements".equalsIgnoreCase(command)) {
      out.println("Server says :- "
          + client.requestRequirements(new Team(team)));
    }

  }

  private static void showUsage() {
    System.out.println("Usage: CheckoutConsole <op> <url> <port> <team>\n"
        + "  <op>  : 'register', 'requirements' or 'batch'\n"
        + "  <url> : The URL of the server e.g. '172.16.66.1'\n"
        + "  <port> : Port of server e.g 9988\n"
        + "  <team>: The team's name\n");

  }

  private static boolean checkArgs(String[] args) {
    return (args.length == 4 && isNumeric(args[2]));
  }

  private static boolean isNumeric(String s) {
    try {
      Integer.parseInt(s);
      return true;
    } catch (NumberFormatException ex) {
      return false;
    }
  }

}
