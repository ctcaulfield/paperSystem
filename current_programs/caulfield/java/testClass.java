kimport java.sql.*;
import java.util.*;

public class testClass{

   public static void main(String [] args){
      
      //attributes for Equipment constructor
      int equipID = 6543;
      String equipmentName = "Bus 288";
      String equipmentDescription = "Coach";
      int equipmentCapacity = 35;
      
      //creating instance of Equipment
      Equipment equipmentTable = new Equipment(equipID,equipmentName,equipmentDescription,equipmentCapacity);
      equipmentTable.post();
      equipmentTable.fetch();
      int newEquipID = 568;
      equipmentTable.swap(newEquipID);
      equipmentTable.fetch();
      
      Equipment equipmentTable2 = new Equipment(newEquipID);
      equipmentTable2.fetch(); 
   }
}