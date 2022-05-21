package app.controller;

import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import app.domain.model.Company;
import app.domain.model.Employee;
import app.domain.model.store.EmployeeRoleStore;
import app.domain.model.store.EmployeeStore;

/**
 * @author André Barros <1211299@isep.ipp.pt>
 */
public class RegisterVaccinationCenterTest {
  Company company = new Company("designation");
  RegisterVaccinationCenterController controller = new RegisterVaccinationCenterController(company);
  String centerName = "Vaccination Center";
  Employee coordinator;

  /**
   * Set up for the tests
   */
  @Before
  public void setUp() {
    EmployeeRoleStore roleStore = company.getEmployeeRoleStore();
    roleStore.addEmployeeRole("COORDINATOR", "COORDINATOR");

    EmployeeStore employeeStore = company.getEmployeeStore();

    coordinator = employeeStore.createEmployee("Joana", "+351916478865", "email@email.com",
        "address", "000000000ZZ4", "COORDINATOR");
    employeeStore.saveEmployee(coordinator);
  }

  /**
   * To string method for tests purpose
   */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Vaccination Center data:\n");
    sb.append(String.format("Name: %s\n", "name"));
    sb.append(String.format("Address: %s\n", "address"));
    sb.append(String.format("Email: %s\n", "example@gmail.com"));
    sb.append(String.format("Phone number: %s\n", "+351913456789"));
    sb.append(String.format("Fax number: %s\n", "+351913456788"));
    sb.append(String.format("Web address: %s\n", "https://www.teste.com"));
    sb.append(String.format("Opening hours: %s\n", "11:00"));
    sb.append(String.format("Closing hours: %s\n", "23:00"));
    sb.append(String.format("Slot duration: %s\n", "5"));
    sb.append(String.format("Maximum vaccines per slot: %s\n", "5"));
    sb.append(String.format("Coordinator: %s\n", "Joana"));

    return sb.toString();
  }


  /**
   * Check that getResourceName method is working properly
   */
  @Test
  public void ensureGetResourceNameIsWorkingCorrectly() {
    assertEquals(centerName, controller.getResourceName());
  }

  /**
   * Check that it is not possible to create a community mass with null values
   * 
   * @throws Exception IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void ensureNullValuesNotAllowed() {
    controller.createCommunityMass(null, null, null, null, null, null, null, null, 0, 0, null);
  }

  /**
   * Check that it is not possible to create a health care with null values
   * 
   * @throws Exception IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void ensureNullValuesNotAllowed2() {
    controller.createHealthCare(null, null, null, null, null, null, null, null, 0, 0, null, null,
        null);
  }

  /**
   * Check that it is not possible to create a community mass with empty values
   * 
   * @throws Exception IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void ensureEmptyValuesNotAllowed() {
    controller.createCommunityMass("", "", "", "", "", "", "", "", 0, 0, coordinator);
  }

  /**
   * Check that it is not possible to create a health care with empty values
   * 
   * @throws Exception IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void ensureEmptyValuesNotAllowed2() {
    controller.createHealthCare("", "", "", "", "", "", "", "", 0, 0, coordinator, "", "");
  }

  /**
   * Check that it is not possible to create a community mass with email invalid
   * 
   * @throws Exception IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void ensureInvalidEmailThrowsException() {
    controller.createCommunityMass("name", "address", "emailaddress", "+351913456789",
        "+351913456788", "https://www.teste.com", "11:00", "12:00", 5, 5, coordinator);
  }

  /**
   * Check that it is not possible to create a health care with email invalid
   * 
   * @throws Exception IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void ensureInvalidEmailThrowsException2() {
    controller.createHealthCare("name", "address", "emailaddress", "+351913456789", "+351913456788",
        "https://www.teste.com", "11:00", "12:00", 5, 5, coordinator, "test", "test");
  }

  /**
   * Check that it is not possible to create a community mass with phone invalid
   * 
   * @throws Exception IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void ensureInvalidPhoneThrowsException() {
    controller.createCommunityMass("name", "address", "example@gmail.com", "913456789",
        "+351913456788", "https://www.teste.com", "11:00", "12:00", 5, 5, coordinator);
  }

  /**
   * Check that it is not possible to create a health care with phone invalid
   * 
   * @throws Exception IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void ensureInvalidPhoneThrowsException2() {
    controller.createHealthCare("name", "address", "example@gmail.com", "913456789",
        "+351913456788", "https://www.teste.com", "11:00", "12:00", 5, 5, coordinator, "test",
        "test");
  }

  /**
   * Check that it is not possible to create a community mass with fax invalid
   * 
   * @throws Exception IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void ensureInvalidFaxThrowsException() {
    controller.createCommunityMass("name", "address", "example@gmail.com", "+351913456789",
        "913456788", "https://www.teste.com", "11:00", "12:00", 5, 5, coordinator);
  }

  /**
   * Check that it is not possible to create a health care with fax invalid
   * 
   * @throws Exception IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void ensureInvalidFaxThrowsException2() {
    controller.createHealthCare("name", "address", "example@gmail.com", "+351913456789",
        "913456788", "https://www.teste.com", "11:00", "12:00", 5, 5, coordinator, "test", "test");
  }

  /**
   * Check that it is not possible to create a community mass with website invalid
   * 
   * @throws Exception IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void ensureInvalidWebsiteThrowsException() {
    controller.createCommunityMass("name", "address", "example@gmail.com", "+351913456789",
        "+351913456788", "abc://www.teste.com", "11:00", "12:00", 5, 5, coordinator);
  }

  /**
   * Check that it is not possible to create a health care with website invalid
   * 
   * @throws Exception IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void ensureInvalidWebsiteThrowsException2() {
    controller.createHealthCare("name", "address", "example@gmail.com", "+351913456789",
        "+351913456788", "abc://www.teste.com", "11:00", "12:00", 5, 5, coordinator, "test",
        "test");
  }

  /**
   * Check that it is not possible to create a community mass with slot duration invalid (x=<0)
   * 
   * @throws Exception IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void ensureInvalidSlotDurationThrowsException() {
    controller.createCommunityMass("name", "address", "example@gmail.com", "+351913456789",
        "+351913456788", "https://www.teste.com", "11:00", "12:00", -5, 5, coordinator);
  }

  /**
   * Check that it is not possible to create a health care with maximum vaccines per slot invalid (x=<0)
   * 
   * @throws Exception IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void ensureInvalidMaxVacSlotThrowsException2() {
    controller.createHealthCare("name", "address", "example@gmail.com", "+351913456789",
        "+351913456788", "https://www.teste.com", "11:00", "12:00", 5, -5, coordinator, "test",
        "test");
  }

  /**
   * Check that it is not possible to create a community mass with invalid coordinator
   * 
   * @throws Exception IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void ensureInvalidCoordinatorThrowsException() {
    controller.createCommunityMass("name", "address", "example@gmail.com", "+351913456789",
        "+351913456788", "https://www.teste.com", "11:00", "12:00", 5, 5, null);
  }

  /**
   * Check that it is not possible to create a health care with invalid coordinator
   * 
   * @throws Exception IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void ensureInvalidCoordinatorThrowsException2() {
    controller.createHealthCare("name", "address", "example@gmail.com", "+351913456789",
        "+351913456788", "https://www.teste.com", "11:00", "12:00", 5, 5, null, "test", "test");
  }

  /**
   * Check that it is possible to create a community mass with all valid values
   */
  @Test
  public void ensureValidValuesCreateNewCenter() {
    controller.createCommunityMass("name", "address", "example@gmail.com", "+351913456789",
        "+351913456788", "https://www.teste.com", "11:00", "12:00", 5, 5, coordinator);
  }

  /**
   * Check that it is possible to create a health care with all valid values
   */
  @Test
  public void ensureValidValuesCreateNewCenter2() {
    controller.createHealthCare("name", "address", "example@gmail.com", "+351913456789",
        "+351913456788", "https://www.teste.com", "11:00", "12:00", 5, 5, coordinator, "test",
        "test");
  }

  /**
   * Check that StringifyData method is working properly
   */
  @Test
  public void ensureStringifyDataWorking() {
    String center = toString();
    controller.createCommunityMass("name", "address", "example@gmail.com", "+351913456789",
        "+351913456788", "https://www.teste.com", "11:00", "23:00", 5, 5, coordinator);
    controller.save();
    assertEquals(controller.stringifyData(), center);
  }

  @Test
  public void ensureCoordinatorListWorking() {
    List<Employee> list = controller.getCoordinators();
    assertEquals(list.size(), 1);
    assertEquals(list.get(0), coordinator);
  }
}
