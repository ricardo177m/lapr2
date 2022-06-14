package app.ui.console;

import java.text.ParseException;
import java.util.List;
import app.controller.App;
import app.controller.RegisterEmployeeController;
import app.domain.model.MyUserRole;
import app.domain.shared.FieldToValidate;
import app.ui.console.utils.Utils;
import pt.isep.lei.esoft.auth.domain.model.UserRole;

/**
 * Register Employee View
 * 
 * @author Tomás Russo <1211288@isep.ipp.pt>
 */
public class RegisterEmployeeUI extends RegisterUI<RegisterEmployeeController> {
  private String roleId = "";

  /**
   * Constructor for RegisterEmployeeUI.
   */
  public RegisterEmployeeUI() {
    super(new RegisterEmployeeController(App.getInstance().getCompany()));
  }

  /**
   * Displays in the screen all the available employee roles
   * 
   * @param employeeRoles the list of employee roles
   */
  private void displayEmployeeRoles(List<MyUserRole> employeeRoles) {
    Utils.showList(employeeRoles, "\nEmployee Roles");
  }

  /**
   * Selects an employee role
   * 
   * @param employeeRoles the list of employee roles
   */
  private MyUserRole selectEmployeeRole(List<MyUserRole> employeeRoles) {
    int roleIndex = Utils.selectsIndex(employeeRoles);

    if (roleIndex == -1) return null;

    return employeeRoles.get(roleIndex);
  }

  /**
   * Asks user to input employee data
   */
  @Override
  public void insertData() throws IllegalArgumentException, ParseException {
    List<MyUserRole> employeeRoles = ctrl.getEmployeeRoles();
    displayEmployeeRoles(employeeRoles);

    MyUserRole role = selectEmployeeRole(employeeRoles);

    if (role == null) return;

    this.roleId = role.getId();

    String name = Utils.readLineFromConsole("Name: ");
    String address = Utils.readLineFromConsole("Address: ");
    String phoneNumber = Utils.readLineFromConsoleWithValidation("Phone Number (+351xxxxxxxxx): ", FieldToValidate.PHONE_NUMBER);
    String email = Utils.readLineFromConsoleWithValidation("Email (example@example.com): ", FieldToValidate.EMAIL);
    String citizenCard = Utils.readLineFromConsoleWithValidation("Citizen Card Number (xxxxxxxxxLLx): ", FieldToValidate.CITIZEN_CARD);

    super.ctrl.create(name, address, phoneNumber, email, citizenCard, this.roleId);
  }
}
