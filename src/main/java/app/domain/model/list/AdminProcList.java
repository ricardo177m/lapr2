package app.domain.model.list;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import app.domain.model.AdminProcess;

/**
 * @author Carlos Lopes <1211277@isep.ipp.pt>
 */
public class AdminProcList implements Serializable {

  private List<AdminProcess> adminProcList;

  public AdminProcList() {
    adminProcList = new ArrayList<>();
  }

  public void addAdminProc(AdminProcess adminProc) {
    adminProcList.add(adminProc);
  }

  public List<AdminProcess> getList() {
    return adminProcList;
  }

  public int size() {
    return adminProcList.size();
  }

}
