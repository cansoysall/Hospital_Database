public class Tester {
    public static void main(String[] args) {
        HospitalDatabase hospitalDB = new HospitalDatabase();

        // Adding patients
        hospitalDB.addPatient("John Doe", "Dr. Smith", 15, 3, 2023);
        hospitalDB.addPatient("Jane Smith", "Dr. Johnson", 25, 5, 2022);
        hospitalDB.addPatient("Alice Jones", "Dr. Brown", 10, 7, 2024);

        // Showing all patients
        System.out.println("All Patients:");
        hospitalDB.showAllPatients();
        System.out.println();

        // Adding members to patients
        hospitalDB.addMember("John Doe", "Nurse A", "Nurse");
        hospitalDB.addMember("John Doe", "Dr. Rogers", "Surgeon");
        hospitalDB.addMember("Jane Smith", "Dr. Wilson", "Anesthesiologist");

        // Showing patients
        System.out.println("John Doe's Details:");
        hospitalDB.showPatient("John Doe");
        System.out.println();

        // Removing a member from a patient
        hospitalDB.removeMember("John Doe", "Nurse A");
        System.out.println("After removing Nurse A from John Doe's team:");
        hospitalDB.showPatient("John Doe");
        System.out.println();

        // Showing patients for a doctor
        System.out.println("Patients of Dr. Smith:");
        hospitalDB.showDoctorPatients("Dr. Smith");
        System.out.println();

        // Showing patients for a specific year
        System.out.println("Patients from 2023:");
        hospitalDB.showPatients(2023);
    }
}
