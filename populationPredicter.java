import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
public class populationPredicter{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Number Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 800);

        // Create the table model with one column
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Years");
        model.addColumn("Population");
        model.addColumn("# increased");

        // Fill the column with numbers from 1 to 75
        for (int i = 1; i <= 70; i++) {
            model.addRow(new Object[]{i});
        }
        double currentPop = 7951000000.00;
        double growthRate = 0.91;
        for (int j = 0; j < 70; j++) {
            currentPop *= (1 + growthRate / 100); // Update population for the next year
            model.setValueAt(formatPopulation(currentPop), j, 1); // Set the population value in the second column
        }

        
        for (int k = 1; k < 70; k++) {
            String prevPopulationStr = (String) model.getValueAt(k - 1, 1);
            String currPopulationStr = (String) model.getValueAt(k, 1);

            // Parse the formatted strings to double values
            double prevPopulation = parseFormattedPopulation(prevPopulationStr);
            double currPopulation = parseFormattedPopulation(currPopulationStr);

            // Calculate the population difference
            double populationDiff = currPopulation - prevPopulation;
            model.setValueAt(formatPopulation(populationDiff), k, 2); // Set the difference value in the third column
        }

        // Create the table with the model
        JTable table = new JTable(model);
        
        // Put the table in a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to the frame
        frame.add(scrollPane, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);

        Object populationYear1 = model.getValueAt(1, 1);
        System.out.println("Population at Year 1: " + populationYear1);
    }
    private static String formatPopulation(double population) {
        BigDecimal pop = new BigDecimal(population);
        pop = pop.setScale(-8, RoundingMode.HALF_UP); // Round to the nearest hundred million

        if (pop.compareTo(new BigDecimal("1000000000000")) >= 0) {
            return pop.divide(new BigDecimal("1000000000000")).setScale(1, RoundingMode.HALF_UP) + " trillion";
        } else if (pop.compareTo(new BigDecimal("1000000000")) >= 0) {
            return pop.divide(new BigDecimal("1000000000")).setScale(1, RoundingMode.HALF_UP) + " billion";
        } else {
            return pop.toString();
        }
    }
    private static double parseFormattedPopulation(String formattedPopulation) {
        // Remove non-numeric characters and parse to double
        String cleanedString = formattedPopulation.replaceAll("[^0-9.]", "");
        return Double.parseDouble(cleanedString);
    }
}

