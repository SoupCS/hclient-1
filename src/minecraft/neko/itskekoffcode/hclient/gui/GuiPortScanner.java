package neko.itskekoffcode.hclient.gui;

import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager.LookAndFeelInfo;

public class GuiPortScanner extends JFrame {
    private JButton a;
    private JLabel b;
    private JLabel c;
    private JLabel d;
    private JLabel e;
    private JTextField f;
    private JTextField g;
    private JTextField h;
    private TextArea i;
    private TextField j;

    public GuiPortScanner() {
        this.a();
    }

    private void a() {
        this.f = new JTextField();
        this.g = new JTextField();
        this.b = new JLabel();
        this.h = new JTextField();
        this.c = new JLabel();
        this.d = new JLabel();
        this.e = new JLabel();
        this.a = new JButton();
        this.i = new TextArea();
        this.j = new TextField();
        this.b.setText(":");
        this.c.setText("-");
        this.d.setText("IP");
        this.e.setText("Port");
        this.a.setText("Start");
        this.a.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GuiPortScanner.this.a(evt);
            }
        });
        this.j.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GuiPortScanner.this.b(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.f, -2, 140, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.b).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.g, -2, 65, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.c).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.h, -2, 65, -2)).addGroup(layout.createSequentialGroup().addGap(73, 73, 73).addComponent(this.d).addGap(140, 140, 140).addComponent(this.e))).addGap(0, 86, 32767)).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(this.i, -1, -1, 32767).addGroup(layout.createSequentialGroup().addComponent(this.a, -2, 122, -2).addGap(99, 99, 99).addComponent(this.j, -1, -1, 32767))))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(17, 17, 17).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.d).addComponent(this.e)).addPreferredGap(ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.TRAILING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.f, -2, -1, -2).addComponent(this.g, -2, -1, -2).addComponent(this.b).addComponent(this.h, -2, -1, -2).addComponent(this.c)).addGap(18, 18, 18).addComponent(this.a)).addComponent(this.j, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.i, -1, 182, 32767).addContainerGap()));
        this.pack();
    }

    private void a(ActionEvent actionEvent) {
        this.i.setText("");

        for(int i = Integer.parseInt(this.g.getText()); i <= Integer.parseInt(this.h.getText()); ++i) {
            if (this.a(this.f.getText(), i, 150)) {
                this.i.append("Открытый порт: " + i + "\n");
            }

            this.j.setText("Порт: " + i);
        }

    }

    public boolean a(String s, int n, int n2) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(s, n), n2);
            socket.close();
            return true;
        } catch (Exception var5) {
            return false;
        }
    }

    private void b(ActionEvent actionEvent) {
    }

    public static void main(String[] array) {
        try {
            LookAndFeelInfo[] installedLookAndFeels;
            int length = (installedLookAndFeels = UIManager.getInstalledLookAndFeels()).length;

            for(int i = 0; i < length; ++i) {
                LookAndFeelInfo lookAndFeelInfo = installedLookAndFeels[i];
                if ("Nimbus".equals(lookAndFeelInfo.getName())) {
                    UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException var5) {
            Logger.getLogger(GuiPortScanner.class.getName()).log(Level.SEVERE, null, var5);
        } catch (InstantiationException var6) {
            Logger.getLogger(GuiPortScanner.class.getName()).log(Level.SEVERE, null, var6);
        } catch (IllegalAccessException var7) {
            Logger.getLogger(GuiPortScanner.class.getName()).log(Level.SEVERE, null, var7);
        } catch (UnsupportedLookAndFeelException var8) {
            Logger.getLogger(GuiPortScanner.class.getName()).log(Level.SEVERE, null, var8);
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                (new GuiPortScanner()).setVisible(true);
            }
        });
    }
}