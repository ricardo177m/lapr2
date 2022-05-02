package app.ui.console;

import java.util.ArrayList;
import java.util.List;
import app.ui.console.utils.Utils;

/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 */

public class AdminUI implements Runnable {
  public AdminUI() {}

  public void run() {
    List<MenuItem> options = new ArrayList<MenuItem>();
    options.add(new MenuItem("List Employees By Role", new ListEmployeesByRoleUI()));
    options.add(new MenuItem("Register a new Vaccination Center", new VaccinationCenterUI()));
    options.add(new MenuItem("Option B ", new ShowTextUI("You have chosen Option B.")));
    options.add(new MenuItem("Option C ", new ShowTextUI("You have chosen Option C.")));

    int option = 0;
    do {
      option = Utils.showAndSelectIndex(options, "\n\nAdmin Menu:");

      if ((option >= 0) && (option < options.size())) {
        options.get(option).run();
      }
    } while (option != -1);
  }
}
