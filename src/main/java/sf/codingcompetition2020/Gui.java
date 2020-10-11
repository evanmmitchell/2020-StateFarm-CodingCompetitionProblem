package sf.codingcompetition2020;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.util.ArrayList;

public class Gui extends JPanel {
  public Gui(String title, List<Double> points) {
    setLayout(new BorderLayout());
    JLabel titleL = new JLabel(title);
    titleL.setHorizontalAlignment(JLabel.CENTER);
    add(titleL, BorderLayout.NORTH);
    GraphPanel graphPanel = new GraphPanel(points);
    graphPanel.setPreferredSize(new Dimension(800, 600));
    add(graphPanel, BorderLayout.CENTER);
  }

  public static void main(String[] args) {
    CodingCompCsvUtil util = new CodingCompCsvUtil();

    String title1 = "Claims Open for n Months";
    List<Double> claimCounts = new ArrayList<Double>();
    String claimsPath = "src/main/resources/DataFiles/claims.csv";
    for (int i = 1; i <= 12; i++) {
      claimCounts.add((double) util.countClaimsOpenForMonths(claimsPath, i));
    }
    JFrame frame1 = new JFrame(title1);
    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame1.getContentPane().add(new Gui(title1, claimCounts));
    frame1.pack();
    frame1.setLocationRelativeTo(null);
    frame1.setVisible(true);

    String title2 = "Agent Count in Area i";
    String agentsPath = "src/main/resources/DataFiles/agents.csv";
    List<Double> agentCount = new ArrayList<Double>();
    for (int i = 1; i <= 5; i++) {
      String area = "area-" + i;
      agentCount.add((double) util.getAgentCountInArea(agentsPath, area));
    }
    JFrame frame2 = new JFrame(title2);
    frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame2.getContentPane().add(new Gui(title2, agentCount));
    frame2.pack();
    frame2.setLocationRelativeTo(null);
    frame2.setVisible(true);
  }
}
