package pwdsystem;

import java.util.Scanner;

public class Pwd { 
    public void addPWD(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        System.out.print("Enter First Name: ");
        String fname = sc.nextLine();
        System.out.print("Enter Last Name: ");
        String lname = sc.nextLine();
        System.out.print("Enter Date of Birth (mm/dd/yy): ");
        String db = sc.nextLine();
        System.out.print("Enter Address: ");
        String address = sc.nextLine();
        System.out.print("Enter PWD Status: ");
        String status = sc.nextLine();
        System.out.print("Enter Emergency Contact Number: ");
        int cn = sc.nextInt();

        String sql = "INSERT INTO pwd (s_fname, s_lname, s_db, s_address, s_status, s_ecn) VALUES (?, ?, ?, ?, ?, ?)";

        conf.addRecord(sql, fname, lname, db, address, status, cn);
    }
    
    public void pwdDisability(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        System.out.print("Enter Disability Type: ");
        String dt = sc.nextLine();
        System.out.print("Enter Severity Level: ");
        String sl = sc.nextLine();
        System.out.print("Enter Description: ");
        String des = sc.nextLine();
        
        String sql = "INSERT INTO disability (pd_distype, pd_sevl, pd_desscrip) VALUES (?, ?, ?)";
        
        conf.addRecord(sql, dt, sl, des);
    }
    
    public void medCon(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        System.out.print("Enter Condition Name: ");
        String cname = sc.nextLine();
        System.out.print("Enter Description: ");
        String medDes = sc.nextLine();
        System.out.print("Enter Onset Date (mm/dd/yy): ");
        String od = sc.nextLine();
        
        String sql = "INSERT INTO medCondition (mc_name, mc_des, mc_oDate) VALUES (?, ?, ?)";
        
        conf.addRecord(sql, cname, medDes, od);
    }
    
    public void viewPWD() {   
        String qry = "SELECT * FROM pwd ";
        String[] hdrs = {"PWD ID", "First Name", "Last Name", "Birth Date", "Address", "EMCY Contact Number", "PWD Status"};
        String[] clms = {"s_id", "s_fname", "s_lname", "s_db", "s_address", "s_ecn", "s_status"};

        config conf = new config();
        conf.viewRecords(qry, hdrs, clms);
    }
    
    public void viewDis(){
        String qry = "SELECT * FROM disability";
        String[] hdrs = {"Disability Type", "Severity Level", "Description"};
        String[] clms = {"pd_distype", "pd_sevl", "pd_desscrip"};
        
        config conf = new config();
        conf.viewRecords(qry, hdrs, clms);
    }
    
    public void viewMencon(){
        String qry = "SELECT * FROM medCondition";
        String[] hdrs = {"Condition Name", "Description", "Onset DAte"};
        String[] clms = {"mc_name", "mc_des", "mc_oDate"};
        
        config conf = new config();
        conf.viewRecords(qry, hdrs, clms);
    }
    
    public void updatePWD(){
        Scanner sc= new Scanner(System.in);
        config conf = new config();
        
        System.out.print("Enter the ID to Update: ");
        int id = sc.nextInt();
        System.out.print("Enter new Address: ");
        String address = sc.next();
        System.out.print("Enter new Emergency Contact Number: ");
        String cn = sc.next();
        System.out.print("Enter new Status: ");
        String status = sc.next();
        
        String qry = "UPDATE pwd SET s_address = ?, s_ecn = ?, s_status = ? WHERE s_id = ?";
        
        conf.updateRecord(qry, address, cn,  status, id);
        
    }
    
    public void deletePWD(){
        
        Scanner sc= new Scanner(System.in);
        System.out.print("Enter PWD ID to Delete: ");
        int id = sc.nextInt();
        
        String qry = "DELETE FROM pwd WHERE s_id = ?";
        
        config conf = new config();
        conf.deleteRecord(qry, id);
    
    }
    
    public void ePWD(){
    Scanner sc = new Scanner(System.in);
    Pwd pd = new Pwd();
        boolean exit = true;
        
        do{
            System.out.println("\n---------- WELCOME TO THE SYSTEM ----------");
            System.out.println("| 1. Add PWD Information                 |");
            System.out.println("| 2. View List of PWD Information        |");
            System.out.println("| 3. Update PWD Information              |");
            System.out.println("| 4. Delete PWD Information              |");
            System.out.println("| 5. Exit                                |");
            System.out.println("------------------------------------------");
            System.out.print("Enter Action: ");
            int action = sc.nextInt();
            
            switch(action){
                case 1:
                    pd.addPWD();
                    System.out.println("\n--- DISABILITY ---");
                    pd.pwdDisability();
                    System.out.println("\n---- MEDICAL CONDITION ----");
                    pd.medCon();
                break;
                
                case 2:
                    System.out.println("\n---- PWD INFORMATION ----");
                    pd.viewPWD();
                    System.out.println("\nDISABILITY");
                    pd.viewDis();
                    System.out.println("\nMEDICAL CONDITION");
                    pd.viewMencon();
                break;
                
                case 3:
                    pd.viewPWD();
                    pd.updatePWD();
                    pd.viewPWD();
                break;
                
                case 4:
                    pd.viewPWD();
                    pd.deletePWD();
                    pd.viewPWD();
                break;
                
                case 5:
                    System.out.print("Do you want to exit? (yes/no): ");
                        String resp = sc.next();
                        if(resp.equalsIgnoreCase("yes")){
                        exit = false;
                        }
                    break;

                    default:
                        System.out.println("Action Error, There's no such number");      
            }
        }while(exit);
}
}