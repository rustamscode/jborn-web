package rustamscode;

import rustamscode.dao.UserDao;
import rustamscode.dao.impl.UserDaoImpl;
import rustamscode.service.AccountService;
import rustamscode.service.AuthService;
import rustamscode.service.CredentialReader;
import rustamscode.service.MenuService;

import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
  private final static Logger logger = Logger.getLogger(Main.class.getName());

  public static void main(String[] args) {
    printBanner();
    try (Scanner scan = new Scanner(System.in)) {
      CredentialReader credentialReader = new CredentialReader();
      UserDao userDao = new UserDaoImpl();
      AuthService authService = new AuthService(credentialReader, userDao);
      AccountService accountService = new AccountService();
      MenuService menuService = new MenuService(scan, authService, accountService);

      menuService.start();
    } catch (Exception e) {
      logger.severe("Application error: " + e.getMessage());
    }
  }

  private static void printBanner() {
    System.out.println("""
        ░██╗░░░░░░░██╗███████╗██╗░░░░░░█████╗░░█████╗░███╗░░░███╗███████╗
        ░██║░░██╗░░██║██╔════╝██║░░░░░██╔══██╗██╔══██╗████╗░████║██╔════╝
        ░╚██╗████╗██╔╝█████╗░░██║░░░░░██║░░╚═╝██║░░██║██╔████╔██║█████╗░░
        ░░████╔═████║░██╔══╝░░██║░░░░░██║░░██╗██║░░██║██║╚██╔╝██║██╔══╝░░
        ░░╚██╔╝░╚██╔╝░███████╗███████╗╚█████╔╝╚█████╔╝██║░╚═╝░██║███████╗
        ░░░╚═╝░░░╚═╝░░╚══════╝╚══════╝░╚════╝░░╚════╝░╚═╝░░░░░╚═╝╚══════╝
        """);
  }
}