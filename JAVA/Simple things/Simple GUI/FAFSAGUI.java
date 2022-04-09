import javax.swing.*;
public class FAFSAGUI {
    public static void main(String[] args) {
        boolean check = true;
        while (check) {
            JOptionPane.showMessageDialog(null, "Welcome to FAFSA!", "Welcome", JOptionPane.PLAIN_MESSAGE);
            boolean ab = true;
            boolean bc = true;
            boolean cd = true;
            boolean de = true;
            boolean ef = true;
            boolean isGraduate=true;
            String age = "0";
            String hours = "0";
            String income = "0.0";
            String pincome = "0.0";
            Object standing=null;

            int a = JOptionPane.showOptionDialog(null, "Have you been accepted into a degree or certificate program?", "Program Acceptance", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (a == JOptionPane.YES_OPTION) {
                ab = true;
            } else
                ab = false;
            int b = JOptionPane.showOptionDialog(null, "Are you registered for the selective service?", "Selective Service", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (b == JOptionPane.YES_OPTION) {
                bc = true;
            } else
                bc = false;
            int c = JOptionPane.showOptionDialog(null, "Do you have a social security number?", "Social Security Number", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (c == JOptionPane.YES_OPTION) {
                cd = true;
            } else
                cd = false;
            int d = JOptionPane.showOptionDialog(null, "Do you have valid residency status?", "Residency Status", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (d == JOptionPane.YES_OPTION) {
                de = true;
            } else
                de = false;
            for (; ; ) {
                age = JOptionPane.showInputDialog(null, "How old are you?", "Age", JOptionPane.QUESTION_MESSAGE);
                if (Integer.parseInt(age) < 0) {
                    JOptionPane.showMessageDialog(null, "Age cannot be a negative number.", "Error: Age", JOptionPane.ERROR_MESSAGE);
                    continue;
                } else
                    break;
            }
            for (; ; ) {
                hours = JOptionPane.showInputDialog(null, "How many credit hours do you plan on taking?", "Credit Hours", JOptionPane.QUESTION_MESSAGE);
                if (Integer.parseInt(hours) < 1 || Integer.parseInt(hours) > 24) {
                    JOptionPane.showMessageDialog(null, "Credit hours must be between 1 and 24, inclusive.", "Error: Credit Hours", JOptionPane.ERROR_MESSAGE);
                    continue;
                } else
                    break;
            }
            for (; ; ) {
                income = JOptionPane.showInputDialog(null, "What is your total yearly income?", "Student Income", JOptionPane.QUESTION_MESSAGE);
                if (Integer.parseInt(income) < 0) {
                    JOptionPane.showMessageDialog(null, "Income cannot be a negative number.", "Error: Student Income", JOptionPane.ERROR_MESSAGE);
                    continue;
                } else
                    break;
            }
            for (; ; ) {
                pincome = JOptionPane.showInputDialog(null, "What is your parent's total yearly income?", "Parent Income", JOptionPane.QUESTION_MESSAGE);
                if (Integer.parseInt(pincome) < 0) {
                    JOptionPane.showMessageDialog(null, "Income cannot be a negative number.", "Error: Student Income", JOptionPane.ERROR_MESSAGE);
                    continue;
                } else
                    break;
            }
            int depen = JOptionPane.showOptionDialog(null, "Are you a dependent?", "Dependency", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            String[] possi = {"Freshman", "Sophomore", "Junior", "Senior", "Graduate"};

            if (depen == JOptionPane.YES_OPTION) {
                ef = true;
                standing = JOptionPane.showInputDialog(null, "What is your current class standing?", "Class Standing", JOptionPane.PLAIN_MESSAGE, null, possi, possi[0]);
                if(standing=="Graduate"){
                    standing="GRADUATE";
                }
                else
                    standing="UNDERGRADUATE";
            } else
                ef = false;


            FAFSA fa = new FAFSA(ab, bc, cd, de, ef, Integer.parseInt(age), Integer.parseInt(hours), Double.parseDouble(income), Double.parseDouble(pincome), standing.toString());
            //FAFSA Result
            JOptionPane.showMessageDialog(null, "Loans: $" + fa.calcStaffordLoan() + "\n" +
                    "Grants: $" + fa.calcFederalGrant() + "\n" +
                    "WorkStudy: $" + fa.calcWorkStudy() + "\n" +
                    "----------\n" + "Total: $" + fa.calcFederalAidAmount(), "FAFSA Results", JOptionPane.INFORMATION_MESSAGE);
            int done = JOptionPane.showOptionDialog(null, "Would like to complete another Application?", "Continue", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (done == JOptionPane.YES_OPTION) {
                check = true;
            } else
                check = false;
        }
    }
}

