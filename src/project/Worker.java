package project;


public class Worker {
    private int workerID;
    private String workerName;
    private int workerPhoneNum;
    private int age;
    private int insuranceNum;
    private String employmentDate;


    public Worker(int workerID, String workerName, int age, int workerPhoneNum, int insuranceNum,
                  String employmentDate) {
        super();
        this.workerID = workerID;
        this.workerName = workerName;
        this.workerPhoneNum = workerPhoneNum;
        this.age = age;
        this.insuranceNum = insuranceNum;
        this.employmentDate = employmentDate;
    }


    public Worker(int workerID) {
        super();
        this.workerID = workerID;
        // TODO Auto-generated constructor stub
    }


    public int getWorkerID() {
        return workerID;
    }


    public void setWorkerID(int workerID) {
        this.workerID = workerID;
    }

    public String getWorkerName() {
        return workerName;
    }


    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }


    public int getWorkerPhoneNum() {
        return workerPhoneNum;
    }


    public void setWorkerPhoneNum(int workerPhoneNum) {
        this.workerPhoneNum = workerPhoneNum;
    }


    public int getAge() {
        return age;
    }


    public void setAge(int age) {
        this.age = age;
    }


    public int getInsuranceNum() {
        return insuranceNum;
    }


    public void setInsuranceNum(int insuranceNum) {
        this.insuranceNum = insuranceNum;
    }


    public String getEmploymentDate() {
        return employmentDate;
    }


    public void setEmploymentDate(String employmentDate) {
        this.employmentDate = employmentDate;
    }


    @Override
    public String toString() {
        return "Worker [workerID=" + workerID + ", workerName=" + workerName + ", workerPhoneNum=" + workerPhoneNum
                + ", age=" + age + ", insuranceNum=" + insuranceNum + ", employmentDate=" + employmentDate + "]";
    }

}

