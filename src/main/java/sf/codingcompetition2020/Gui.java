package sf.codingcompetition2020;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.util.ArrayList;

public class Gui extends JPanel {
  public Gui() {
    setLayout(new BorderLayout());
    JLabel title = new JLabel("Claims Open for N Months");
    title.setHorizontalAlignment(JLabel.CENTER);
    add(title, BorderLayout.NORTH);
    CodingCompCsvUtil util = new CodingCompCsvUtil();
    List<Double> claimCounts = new ArrayList<Double>();
    String claimsPath = "src/main/resources/DataFiles/claims.csv";
    for (int i = 1; i <= 12; i++) {
      claimCounts.add((double) util.countClaimsOpenForMonths(claimsPath, i));
    }
    GraphPanel graphPanel = new GraphPanel(claimCounts);
    graphPanel.setPreferredSize(new Dimension(800, 600));
    add(graphPanel, BorderLayout.CENTER);
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("Claims Open for N Months");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(new Gui());
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
