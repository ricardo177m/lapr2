package app.ui.gui;

import java.util.logging.Level;
import java.util.logging.Logger;
import app.controller.App;
import app.controller.FindCoordinatorVaccinationCenterController;
import app.domain.model.Company;
import app.exception.NotAuthorizedException;
import app.session.EmployeeSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CoordinatorUI extends EmployeeRoleUI {
  private EmployeeSession employeeSession;
  private FindCoordinatorVaccinationCenterController ctrl;

  @FXML
  private Label lblCenter;

  @Override
  public void init(ApplicationUI mainApp) throws NotAuthorizedException {
    super.setMainApp(mainApp);
    App app = App.getInstance();
    Company company = app.getCompany();
    this.employeeSession = new EmployeeSession();
    this.ctrl = new FindCoordinatorVaccinationCenterController(company, employeeSession);

    this.ctrl.findCoordinatorCenter();

    if (!this.employeeSession.hasCenter()) throw new NotAuthorizedException("You are not assigned to any vaccination center.");

    this.lblCenter.setText(this.ctrl.getVaccinationCenterName());
  }

  @FXML
  void handleAnalyseCenterNavigation(ActionEvent event) {
    try {
      AnalyseCenterPerformanceUI analyseCenterUI = (AnalyseCenterPerformanceUI) this.mainApp.replaceSceneContent("/fxml/AnalyseCenter.fxml");
      analyseCenterUI.init(this);
    } catch (Exception e) {
      Logger.getLogger(CoordinatorUI.class.getName()).log(Level.SEVERE, null, e);
    }
  }

  public EmployeeSession getEmployeeSession() {
    return this.employeeSession;
  }

  public String getVaccinationCenterName() {
    return this.ctrl.getVaccinationCenterName();
  }

  @FXML
  public void toImportLegacyDataScene1() {
    try {
      ImportLegacyData1UI importUI = (ImportLegacyData1UI) this.mainApp.replaceSceneContent("/fxml/ImportLegacyData_1.fxml");
      importUI.init(this);

      // importUI.getNameTxtField.requestFocus() to make sure the text field you need focused is, in fact, focused
    } catch (Exception ex) {
      Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public String getUIRoleName() {
    return "COORDINATOR";
  }
}
