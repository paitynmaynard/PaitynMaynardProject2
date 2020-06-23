package dto;
/**
 *  Project 2:<br>
 * <br>
 *
 *  <br> <br>
 *  Created: <br>
 *     <br>
 *     With assistance from: <br>
 *  Modifications:
 *      Paityn Maynard added comment block and comment lines<br>
 *      Paityn Maynard also reorganize getters and setter<br>
 * <br>
 *  @author
 *  @version
 */

public class MaintenanceTicketTransfer {//Start of MaintenanceTicketTransfer Class

//Instance Variables
    private int mainId, attractionId, employeeId;
    private String status, description;
    //private String startDate;
    private String endDate;
    private boolean isActive;

//Getters

    public boolean getIsActive() {
        return isActive;
    }

    public int getMainId() {
        return mainId;
    }

    public int getAttractionId() {
        return attractionId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    /*public String getStartDate() {return startDate;}*/

    public String getEndDate() {
        return endDate;
    }

//Setters
    public void setActive(boolean active) {
        isActive = active;
    }

    public void setMainId(int mainId) {
        this.mainId = mainId;
    }

    public void setAttractionId(int attractionId) {
        this.attractionId = attractionId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*public void setStartDate(String startDate) {this.startDate = startDate;}*/

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}//End of MaintenanceTicketTransfer Class


