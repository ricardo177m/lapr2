package app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;
import app.domain.model.Address;
import app.domain.model.AdminProcess;
import app.domain.model.Appointment;
import app.domain.model.Company;
import app.domain.model.DoseInfo;
import app.domain.model.Employee;
import app.domain.model.SNSUser;
import app.domain.model.VaccinationCenter;
import app.domain.model.Vaccine;
import app.domain.model.VaccineType;
import app.domain.model.list.AppointmentScheduleList;
import app.domain.model.store.EmployeeStore;
import app.domain.model.store.SNSUserStore;
import app.domain.model.store.VaccinationCenterStore;
import app.domain.model.store.VaccineStore;
import app.domain.model.store.VaccineTechnologyStore;
import app.domain.model.store.VaccineTypeStore;
import app.domain.shared.Constants;
import app.domain.shared.Gender;
import app.dto.VaccineTypeDTO;
import app.exception.AppointmentNotFoundException;
import app.mapper.VaccineTypeMapper;
import app.service.AppointmentValidator;

public class RegisterSNSUserArrivalControllerTest {
  Company company;
  RegisterSNSUserArrivalController ctrl;
  EmployeeStore empStore;
  SNSUserStore snsUserStore;
  VaccinationCenterStore vcStore;
  VaccineTechnologyStore vtechStore;
  VaccineTypeStore vtStore;
  VaccineType vacType;
  VaccinationCenter center;
  AppointmentScheduleList aptSchList;
  VaccineStore vaccineStore;

  @Before
  public void setUp() {
    this.company = new Company("designation", "12345");
    this.vcStore = company.getVaccinationCenterStore();
    this.snsUserStore = company.getSNSUserStore();
    this.empStore = company.getEmployeeStore();
    this.vtStore = company.getVaccineTypeStore();
    this.vtechStore = company.getVaccineTechnologyStore();
    this.vaccineStore = company.getVaccineStore();

    Calendar date = Calendar.getInstance();
    date.add(Calendar.YEAR, -18);

    SNSUser snsUser = snsUserStore.createSNSUser("00000000", "123456789", "name", date.getTime(), Gender.MALE, "+351212345678", "s@user.com",
        new Address("street", 1, "11-11", "city"));
    this.snsUserStore.saveSNSUser(snsUser);

    Employee e2 =
        empStore.createEmployee("Name2", "+351916919269", "c@user.com", new Address("street", 1, "11-1", "city"), "15542404", Constants.ROLE_COORDINATOR);
    this.empStore.saveEmployee(e2);

    this.vtechStore.addVaccineTechnology("M_RNA_TECHNOLOGY");

    this.vacType = vtStore.addVaccineType("00000", "COVID-19", "M_RNA_TECHNOLOGY");
    this.vtStore.saveVaccineType(vacType);

    this.center = vcStore.createCommunityMassCenter("Centro Vacinação de Teste", new Address("street", 1, "11-11", "city"), "test@gmail.com", "+351212345678",
        "+351212345679", "http://www.test.com", "20:00", "21:00", 7, 5, e2, vacType);
    this.vcStore.saveVaccinationCenter(this.center);

    Vaccine vaccine = new Vaccine("designation", "12345", "brand", vacType);
    AdminProcess adminProcess = new AdminProcess(10, 19, 1);
    adminProcess.addDoseInfo(new DoseInfo(200, 10));
    vaccine.addAdminProc(adminProcess);
    vaccineStore.saveVaccine(vaccine);

    this.aptSchList = this.center.getAppointmentList();

    ctrl = new RegisterSNSUserArrivalController(company, this.center);
  }

  @Test
  public void ensureArrivalIsCreatedSuccessfully() {
    ctrl.create();
  }

  @Test
  public void ensureArrivalIsSavedSuccessfully() {
    ctrl.create();
    ctrl.save();
  }

  @Test
  public void ensureStringifyWorksAsExpected() throws AppointmentNotFoundException {
    this.createAppointment();
    ctrl.findSNSUser("123456789");
    ctrl.findSNSUserAppointment();
    String stringified = ctrl.stringifyData();
    System.out.println(stringified);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String expected =
        String.format("Appointment\n\tSNS User: name (123456789)\n\tDate: %s 20:30\n\tCenter: Centro Vacinação de Teste\n\tVaccine type: COVID-19",
            sdf.format(Calendar.getInstance().getTime()));
    assertEquals(stringified, expected);
  }

  @Test(expected = IllegalArgumentException.class)
  public void ensureUnknownSNSUserThrowsException() {
    ctrl.findSNSUser("987654321");
  }

  public void createAppointment() throws IllegalArgumentException {
    SNSUser snsUser = snsUserStore.findSNSUserByNumber("123456789");
    if (snsUser == null) throw new IllegalArgumentException("SNS User Number not found.");
    Appointment appointment;

    Calendar date = Calendar.getInstance();
    date.set(Calendar.HOUR_OF_DAY, 20);
    date.set(Calendar.MINUTE, 30);
    VaccineTypeDTO vtdto = VaccineTypeMapper.toDto(this.vacType);

    appointment = this.aptSchList.createAppointment(snsUser, date, vtdto, true);

    AppointmentValidator appointmentValidator = new AppointmentValidator(this.vaccineStore);
    appointmentValidator.validateAppointment(appointment);
    this.aptSchList.saveAppointment(appointment);
  }

  @Test
  public void ensureGetResourceNameReturnsCorrectly() {
    assertEquals(ctrl.getResourceName(), "SNS User Arrival");
  }

  @Test(expected = AppointmentNotFoundException.class)
  public void ensureSNSUserDoesntArriveTwice() throws AppointmentNotFoundException {
    this.createAppointment();
    ctrl.findSNSUser("123456789");
    ctrl.findSNSUserAppointment();
    ctrl.create();
    ctrl.save();
    ctrl.findSNSUserAppointment();
  }

  // @Test
  // public void ensureGetRegisteredObjectReturnsCorrectly() throws AppointmentNotFoundException {
  // SNSUser snsUser = snsUserStore.findSNSUserByNumber("123456789");
  // Calendar date = Calendar.getInstance(); // this will never work, calendar instances are different
  // date.set(Calendar.HOUR_OF_DAY, 20);
  // date.set(Calendar.MINUTE, 30);

  // Appointment appointment = new Appointment(snsUser, date, this.center, this.vacType, true);
  // Arrival arrival = new Arrival(appointment);

  // ArrivalDTO dto = ArrivalMapper.toDto(arrival);

  // this.createAppointment();
  // ctrl.findSNSUser("123456789");
  // ctrl.findSNSUserAppointment();
  // ctrl.create();

  // assertEquals(ctrl.getRegisteredObject(), dto);
  // }

}
