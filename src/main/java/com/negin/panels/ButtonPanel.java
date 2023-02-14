package com.negin.panels;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ButtonPanel extends JPanel implements ActionListener {

    public static final String ENABLE_AUTO_REFRESH = "Enable Auto Refresh";
    public static final String REFRESH = "Refresh";
    public static final String EXIT = "Exit";
    public static final String DISABLE_AUTO_REFRESH = "Disable Auto Refresh";
    private final ContentPanel contentPanel;
    private final JButton autoRefreshBtn;
    private int countDownTime = 5;
    private final transient ActionListener countDown = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            countDownTime--;
            autoRefreshBtn.setText(DISABLE_AUTO_REFRESH + " - " + countDownTime);

            if (countDownTime == 0) {
                countDownTime = 5;
                autoRefreshBtn.setText(DISABLE_AUTO_REFRESH + " - " + countDownTime);
            }
        }
    };
    private final Timer timer = new Timer(1000, countDown);

    public ButtonPanel(ContentPanel contentPanel) {
        this.contentPanel = contentPanel;

        this.setLayout(new GridLayout(1,3));
        // Buttons
        JButton refreshManuallyBtn = new JButton(REFRESH);
        refreshManuallyBtn.setPreferredSize(new Dimension(200,100));
        autoRefreshBtn = new JButton(ENABLE_AUTO_REFRESH);
        autoRefreshBtn.setPreferredSize(new Dimension(200,100));
        JButton exitButton = new JButton(EXIT);
        exitButton.setPreferredSize(new Dimension(200,100));

        // Button Listeners
        refreshManuallyBtn.addActionListener(this);
        autoRefreshBtn.addActionListener(this);
        exitButton.addActionListener(this);

        this.add(refreshManuallyBtn);
        this.add(autoRefreshBtn);
        this.add(exitButton);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {

            case REFRESH: {
                this.contentPanel.refresh();
                break;
            }

            case ENABLE_AUTO_REFRESH: {
                this.contentPanel.enableAutoRefresh(true);
                autoRefreshBtn.setText(DISABLE_AUTO_REFRESH + " - 5");
                timer.start();
                break;
            }

            case DISABLE_AUTO_REFRESH + " - 0":
            case DISABLE_AUTO_REFRESH + " - 1":
            case DISABLE_AUTO_REFRESH + " - 2":
            case DISABLE_AUTO_REFRESH + " - 3":
            case DISABLE_AUTO_REFRESH + " - 4":
            case DISABLE_AUTO_REFRESH + " - 5":
            case DISABLE_AUTO_REFRESH: {
                this.contentPanel.enableAutoRefresh(false);
                timer.stop();
                autoRefreshBtn.setText(ENABLE_AUTO_REFRESH);
                break;
            }

            case EXIT: {
                Runtime.getRuntime().exit(0);
                break;
            }

            default: {
                break;
                // No default event!
            }
        }
    }
}
