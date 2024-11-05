package pwdsystem;

import java.util.Scanner;

public class Rhu {
    public void pRHU() {
        Scanner sc = new Scanner(System.in);
        Rhu rh = new Rhu();
        boolean exit = true;
                
        do {
            System.out.println("\n----- WELCOME TO THE SYSTEM -----");
            System.out.println("1. Add Healthcare Provider");
            System.out.println("2. Add Appointment");
            System.out.println("3. View Record and Appointment");
            System.out.println("4. Update Appointment");
            System.out.println("5. Delete Healthcare Provider and Appointment");
            System.out.println("6. Exit");
            System.out.println("----------------------------------");
            System.out.print("Enter Action: ");
            int action = sc.nextInt();
            
            switch (action) {
                case 1:
                    rh.addDoctor();
                    break;
                    
                case 2:
                    Pwd pd = new Pwd();
                    System.out.println("\nList of Healthcare Provider");
                    rh.viewDr();
                    System.out.println("\nList of PWD Patient");
                    pd.viewPWD();
                    rh.addApp();
                    break;
                    
                case 3:
                    System.out.println("\nList of Healthcare Provider");
                    rh.viewDr();
                    System.out.println("\nList of Appointment");
                    rh.viewApp();
                    break;
                    
                case 4:
                    System.out.println("\nList of Appointment");
                    rh.viewApp();
                    rh.updateApp();
                    System.out.println("\nList of Appointment");
                    rh.viewApp();
                    break;
                
                case 5:
                    System.out.println("\nList of Healthcare Provider");
                    rh.viewDr();
                    System.out.print("Delete Healthcare Provider? (yes/no): ");
                    String dhp = sc.next();
                    while(dhp.equalsIgnoreCase("yes")){
                        rh.deleteDr();
                        rh.viewDr();
                        break;
                    }
                    
                    System.out.println("\nList of Appointment");
                    rh.viewApp();
                    System.out.print("Delete Appointment? (yes/no): ");
                    String app = sc.next();
                    while(app.equalsIgnoreCase("yes")){
                        rh.deleteApp();
                        rh.viewApp();
                        break;
                    }
                    break;
                    
                case 6:
                    System.out.print("Do you want to exit? (yes/no): ");
                        String resp = sc.next();
                        if(resp.equalsIgnoreCase("yes")){
                        exit = false;
                        }
                    break;

                    default:
                        System.out.println("Action Error, There's no such number");
            }

        } while (exit);
        System.out.println("Thank You!");
    }

    public void addPatient(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        Pwd pd = new Pwd();
        Rhu rhu = new Rhu();
        
        System.out.println("List of PWD");
        pd.viewPWD();
        System.out.print("Enter PWD ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter PhilHealth ID (xx-xxxxxxxxxx-xx: ");
        String phid = sc.nextLine();
        System.out.print("Enter Barangay: ");
        String brgy = sc.nextLine();
        System.out.print("Enter Municipal/City: ");
        String mc = sc.nextLine();
        System.out.print("Enter Province: ");
        String pro = sc.nextLine();
        
        System.out.print("Assign Doctor? (yes/no): ");
        String res = sc.nextLine();
        while(res.equalsIgnoreCase("yes")){
            rhu.viewDr();
            System.out.print("Enter Doctor ID: ");
        }
            int drid = sc.nextInt();
        
        String sql = "INSERT INTO patient (p_pid, p_brgy, p_mc, p_pro, p_ass2Dr) VALUES (?, ?, ?, ?, ?)";
        conf.addRecord(sql, phid, brgy, mc, pro, drid);
    }
    
    public void addDoctor() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        Pwd pd = new Pwd();
        Rhu rhu = new Rhu();

        System.out.print("Enter Doctor Full Name: ");
        String dfname = sc.nextLine();

        System.out.print("Enter Licence Number: ");
        int ln = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Doctor Specialization: ");
        String ds = sc.nextLine();
        System.out.print("Enter Contact Number: ");
        int cn = sc.nextInt();
        sc.nextLine();
        System.out.print("Assign Patient? (yes/no): ");
        String res = sc.nextLine();

        int pid = -1; 

        while (res.equalsIgnoreCase("yes")) {
            pd.viewPWD();
            System.out.print("Enter Patient ID: ");
            pid = sc.nextInt();
            sc.nextLine();

            System.out.print("Assign another patient? (yes/no): ");
            res = sc.nextLine();
        }

        System.out.print("View Full details of PWD? (yes/no): ");
        String res1 = sc.nextLine();

        while (res1.equalsIgnoreCase("yes")) {
            rhu.viewFullp();
            System.out.print("View Full details of PWD again? (yes/no): ");
            res1 = sc.nextLine(); 
        }

        // Ensure that pid has a value before passing it to the SQL query
        if (pid != -1) {
            String sql = "INSERT INTO rhu (dr_name, dr_licensenum, dr_spec, dr_cnum, dr_assp) VALUES (?, ?, ?, ?, ?)";
            conf.addRecord(sql, dfname, ln, ds, cn, pid);
        } else {
            System.out.println("No patient assigned. Doctor record not added.");
        }
     }
    
    public void addApp(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        System.out.print("Enter Dotor ID: ");
        int did = sc.nextInt();
        System.out.print("Enter PWD ID: ");
        int pid = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Date (mm/dd/yy): ");
        String dd = sc.nextLine();
        System.out.print("Enter Time (hh:mm:ss)");
        String tm = sc.nextLine();
        System.out.print("Enter Reason for Visit: ");
        String rv = sc.nextLine();
        System.out.print("Enter Room Number: ");
        int rn = sc.nextInt();
        
        String sql = "INSERT INTO appointment (dr_id, pwd_id, ap_dd, ap_tm, ap_rv, ap_room) VALUES (?, ?, ?, ?, ?, ?)";
        conf.addRecord(sql, did, pid, dd, tm, rv, rn);
    }
    
    public void viewP(){
        String qry = "SELECT patiient.p_id, pwd.s_fname, pwd.s_lname, disability.pd_distype, disability.pd_sevl FROM disability "
                + "INNER JOIN patient ON patient.p_id";
        String [] hdrs = {"Patient ID", "First Name", "Last Name", "Dissability Type", "Severity Level"};
        String [] clms = {"p_id", "s_fname", "s_lname", "pd_distype", "pd_sevl"};
    }
    
    public void viewFullp(){
        String qry = "SELECT pwd.s_fname, pwd.s_lname, pwd.s_db, pwd.address, pwd.s_ecn, pwd.s_status, p_pid, p_brgy, p_mc, "
                + "p_pro, p_ass2Dr FROM pwd INNER JOIN patient ON patient.p_pid";
        String[] hdrs = {"Patient ID", "First Name", "Last Name", "Birth Date", "Address", "EMCY Contact Number", "PWD Status", "PhilHealth ID", "Barangay", "Municipa/City", "Province"};
        String [] clms = {"p_id", "s_fname", "s_lname", "s_db", "s_address", "s_ecn", "s_status", "p_pid", "p_brgy", "p_mc", "p_pro"};
    }
    
    public void viewDr() {
        String qry = "SELECT * FROM rhu";
        String[] hdrs = {"Docotr ID","Doctor Full Name", "License Number", "Specialization", "Contact Number"};
        String[] clms = {"dr_id", "dr_name", "dr_licensenum", "dr_spec", "dr_cnum"};
        
        config conf = new config();
        conf.viewRecords(qry, hdrs, clms);
    }
    
    public void viewApp(){
        String qry = "SELECT appointment.app_id, rhu.dr_id, rhu.dr_name, pwd.s_id, pwd.s_lname, appointment.ap_dd, appointment.ap_tm, appointment.ap_rv, appointment.ap_room FROM appointment "
                + "INNER JOIN rhu ON rhu.dr_id = appointment.dr_id INNER JOIN pwd ON pwd.s_id = appointment.pwd_id";
        String[] hdrs = {"Appointment ID", "Doctor ID","Doctor Name", "PWD ID", "PWD Name", "Date", "Time", "Room Number", "Reason for Visit"};
        String[] clms = {"app_id", "dr_id", "dr_name", "s_id", "s_lname" , "ap_dd", "ap_tm", "ap_room", "ap_rv"};
        
        config conf = new config();
        conf.viewRecords(qry, hdrs, clms);
    }
    
    public void updateApp() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        System.out.print("Enter Appoimentt ID: ");
        int id = sc.nextInt();
        
        while((conf.getSingleValue("SELECT app_id FROM appointment WHERE app_id = ?", id)) == 0){
        System.out.println("Selected Appointment ID doesn't exist!");
        System.out.print("Enter Appointment ID again: ");
        id = sc.nextInt();
}
        sc.nextLine();
        System.out.print("Enter new Date: ");
        String dd = sc.nextLine();
        System.out.print("Enter new Time: ");
        String tm = sc.nextLine();
        System.out.print("Enter new Reason of Visit: ");
        String rv = sc.nextLine();
        System.out.print("Enter new Room Number: ");
        int rn = sc.nextInt();
        
        String sql = "UPDATE appointment SET ap_dd = ?, ap_tm = ?, ap_rv = ?, ap_room = ? WHERE app_id = ?";
        conf.updateRecord(sql, dd, tm, rv, rn, id);
    }
    
    public void deleteDr(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        System.out.print("\nEnter Dr ID to Delete: ");
        int id = sc.nextInt();

        while((conf.getSingleValue("SELECT dr_id FROM rhu WHERE dr_id = ?", id)) == 0){
        System.out.println("Selected Doctor ID doesn't exist!");
        System.out.print("Enter Doctor ID again: ");
        id = sc.nextInt();
}
        String sql = "DELETE FROM rhu WHERE dr_id = ?";
       conf.updateRecord(sql, id);
    }
    
        public void deleteApp(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        System.out.print("\nEnter Appointment ID to Delete: ");
        int id = sc.nextInt();

        while((conf.getSingleValue("SELECT app_id FROM appointment WHERE app_id = ?", id)) == 0){
        System.out.println("Selected Appointment ID doesn't exist!");
        System.out.print("Enter Appointment ID again: ");
        id = sc.nextInt();
}
        String sql = "DELETE FROM appointment WHERE app_id = ?";
        conf.updateRecord(sql, id);
    }
}