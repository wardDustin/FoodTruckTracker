package FoodTruckPackage;

public class MainStudentsCalls{
  public static void main(String[] args) throws Exception {
          AccessStudentsCalls db = new AccessStudentsCalls();
          db.connectToDB();
          db.readStudents();
          db.readCalls();
          System.out.println("\n");
          db.callsLongerThan(4);
          db.close();
  }
}
