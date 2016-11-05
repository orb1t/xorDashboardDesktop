package orTestStandDesktop.app;

import orTestStandDesktop.ui.MainForm;

import java.awt.*;

/**
 * Created by orb1t_ua on 20.09.16.
 */
public class Main {

  public static void main(String[] args) {

    EventQueue.invokeLater(new Runnable() {

      @Override
      public void run() {
        MainForm mainForm = new MainForm();
//        mainForm.setVisible(true);
      }
    });
  }

}
