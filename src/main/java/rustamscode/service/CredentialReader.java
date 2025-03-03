package rustamscode.service;

import rustamscode.model.UserCredentials;

import java.util.Scanner;

public class CredentialReader {

  public UserCredentials readCredentials() {
    Scanner scan = new Scanner(System.in);

    System.out.print("Email: ");
    String email = scan.nextLine();
    System.out.print("Password: ");
    String password = scan.nextLine();

    UserCredentials userCredentials = new UserCredentials();
    userCredentials.setEmail(email);
    userCredentials.setPassword(password);

    return userCredentials;
  }
}
