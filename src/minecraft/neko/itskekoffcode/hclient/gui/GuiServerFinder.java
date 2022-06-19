package neko.itskekoffcode.hclient.gui;

import com.mojang.authlib.GameProfile;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.CPacketLoginStart;

public class GuiServerFinder extends JFrame {
    public static Socket socket;
    public static boolean tf = false;
    public static boolean fast = false;
    public static NetworkManager networkManager;
    public JButton jButton1;
    public JLabel jLabel1;
    public JLabel jLabel2;
    public JLabel jLabel3;
    public JLabel jLabel4;
    public static JLabel jLabel5;
    public JLabel jLabel6;
    public JLabel jLabel7;
    public static JTextField jTextField1;
    public static JTextField jTextField2;
    public static JTextField jTextField3;
    public static JTextField jTextField4;
    public static JTextField jTextField5;
    public static JTextField jTextField6;
    public static TextArea textArea1;
    public static JTextField textField;
    public static JTextField textField_1;
    public static List<String> listSocketPort = new ArrayList();
    public static boolean on;
    public int c = 0;
    public static List Servers2 = new ArrayList();

    public GuiServerFinder() {
        this.initComponents();
    }

    private void initComponents() {
        jTextField1 = new JTextField();
        this.jLabel1 = new JLabel();
        jTextField2 = new JTextField();
        this.jLabel2 = new JLabel();
        this.jButton1 = new JButton();
        jTextField3 = new JTextField();
        this.jLabel4 = new JLabel();
        jLabel5 = new JLabel();
        this.jLabel7 = new JLabel();
        jTextField6 = new JTextField();
        jTextField4 = new JTextField();
        jTextField5 = new JTextField();
        this.jLabel3 = new JLabel();
        this.jLabel6 = new JLabel();
        textArea1 = new TextArea();
        this.setTitle("Server Finder v2.0 | Результаты в файле .minecraft/ServerIs2.txt");
        this.jLabel1.setText("IP");
        this.jLabel2.setText("Port");
        this.jButton1.setText("Start");
        jTextField3.setToolTipText("");
        jTextField3.setName("");
        this.jLabel4.setText("Nick");
        this.jLabel7.setText("-");
        this.jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GuiServerFinder.this.jButton1ActionPerformed(evt);
            }
        });
        this.jLabel3.setText("-");
        this.jLabel6.setText(".");
        textField = new JTextField();
        textField_1 = new JTextField();
        JLabel label = new JLabel();
        label.setText(".");
        JLabel label_1 = new JLabel();
        label_1.setText("-");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(textArea1, Alignment.TRAILING, -1, 387, 32767).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jTextField3, -2, 104, -2)).addGroup(layout.createSequentialGroup().addGap(52).addComponent(this.jLabel4))).addGap(4).addGroup(layout.createParallelGroup(Alignment.TRAILING).addGroup(layout.createSequentialGroup().addComponent(jTextField2, -2, 52, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jLabel7).addPreferredGap(ComponentPlacement.RELATED).addComponent(jTextField6, -2, 52, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.TRAILING).addComponent(this.jLabel2).addGroup(layout.createSequentialGroup().addComponent(textField, -2, 30, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent(label_1, -2, 4, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent(textField_1, -2, 30, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(label, -2, 4, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent(jTextField4, -2, 30, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jLabel3).addPreferredGap(ComponentPlacement.RELATED).addComponent(jTextField5, -2, 30, -2).addPreferredGap(ComponentPlacement.RELATED))).addGap(48)))).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel5)).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jTextField1, -2, 96, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jLabel6)).addGroup(layout.createSequentialGroup().addGap(55).addComponent(this.jLabel1)).addGroup(layout.createSequentialGroup().addGap(27).addComponent(this.jButton1, -2, 288, -2))).addGap(0, 65, 32767))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1, -2, 14, -2).addGap(1).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jTextField1, -2, -1, -2).addComponent(this.jLabel6).addComponent(textField, -2, -1, -2).addComponent(label_1).addComponent(textField_1, -2, -1, -2).addComponent(label).addComponent(jTextField4, -2, -1, -2).addComponent(this.jLabel3).addComponent(jTextField5, -2, -1, -2)).addGap(32).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.jLabel2)).addPreferredGap(ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jTextField3, -2, -1, -2).addComponent(jTextField2, -2, -1, -2).addComponent(this.jLabel7).addComponent(jTextField6, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addComponent(jLabel5).addPreferredGap(ComponentPlacement.RELATED).addComponent(textArea1, -1, -1, 32767).addPreferredGap(ComponentPlacement.RELATED).addComponent(this.jButton1, -2, 34, -2).addContainerGap()));
        this.getContentPane().setLayout(layout);
        this.pack();
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        try {
            on = true;

            try {
                for(int tt = Integer.parseInt(textField.getText()); tt <= Integer.parseInt(textField_1.getText()); ++tt) {
                    final int t = tt;

                    for(int aa = Integer.parseInt(jTextField4.getText()); aa <= Integer.parseInt(jTextField5.getText()); ++aa) {
                        final int a = aa;

                        for(int ii = Integer.parseInt(jTextField2.getText()); ii <= Integer.parseInt(jTextField6.getText()); ++ii) {
                            System.out.println(jTextField1.getText() + "." + t + "." + a);

                            try {
                                ++this.c;
                                if (this.c > 1000) {
                                    System.out.println(jTextField1.getText() + "." + t + "." + a + ":" + ii);
                                    if (portIsOpen(jTextField1.getText() + "." + t + "." + a, ii, 150)) {
                                        if (!textArea1.getText().contains("(1.12.2) Сервер найден: " + jTextField1.getText() + "." + t + "." + a + ":" + ii)) {
                                            textArea1.append("(1.12.2) Сервер найден: " + jTextField1.getText() + "." + t + "." + a + ":" + ii + "\n");
                                            Servers2.add("(1.12.2) " + jTextField1.getText() + "." + t + "." + a + ":" + ii);
                                            ServerIs2();
                                        }

                                        listSocketPort.add(jTextField1.getText() + "." + t + "." + a + ":" + ii);
                                    }

                                    if (this.c > 1004) {
                                        this.c = 0;
                                    }
                                }

                                int finalIi = ii;
                                (new Thread() {
                                    public void run() {
                                        try {
                                            System.out.println(GuiServerFinder.jTextField1.getText() + "." + t + "." + a + ":" + finalIi);
                                            if (GuiServerFinder.portIsOpen(GuiServerFinder.jTextField1.getText() + "." + t + "." + a, finalIi, 150)) {
                                                if (!GuiServerFinder.textArea1.getText().contains("(1.12.2) Сервер найден: " + GuiServerFinder.jTextField1.getText() + "." + t + "." + a + ":" + finalIi)) {
                                                    GuiServerFinder.textArea1.append("(1.12.2) Сервер найден: " + GuiServerFinder.jTextField1.getText() + "." + t + "." + a + ":" + finalIi + "\n");
                                                    GuiServerFinder.Servers2.add("(1.12.2) " + GuiServerFinder.jTextField1.getText() + "." + t + "." + a + ":" + finalIi);
                                                    GuiServerFinder.ServerIs2();
                                                }

                                                GuiServerFinder.listSocketPort.add(GuiServerFinder.jTextField1.getText() + "." + t + "." + a + ":" + finalIi);
                                            }
                                        } catch (Exception var2) {
                                            var2.printStackTrace();
                                        }

                                    }
                                }).start();
                            } catch (Exception var9) {
                                var9.printStackTrace();
                            }

                            Thread.sleep(3L);
                        }
                    }
                }
            } catch (Exception var10) {
                var10.printStackTrace();
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException var8) {
            var8.printStackTrace();
        }

        Iterator var13 = listSocketPort.iterator();

        while(var13.hasNext()) {
            final String listSocketPort2 = (String)var13.next();
            System.out.println("#2: " + listSocketPort2);
            (new Thread() {
                public void run() {
                    String[] listSocketPort22 = listSocketPort2.split(":");
                    InetAddress var1 = null;

                    try {
                        var1 = InetAddress.getByName(listSocketPort22[0]);
                    } catch (UnknownHostException var5) {
                        var5.printStackTrace();
                    }

                    (GuiServerFinder.networkManager = NetworkManager.createNetworkManagerAndConnect(var1, Integer.parseInt(listSocketPort22[1]), Minecraft.getMinecraft().gameSettings.isUsingNativeTransport())).setNetHandler(new NetHandlerLoginClient(GuiServerFinder.networkManager, Minecraft.getMinecraft(), new GuiIngameMenu()));
                    GuiServerFinder.networkManager.sendPacket(new C00Handshake(listSocketPort22[0], Integer.parseInt(listSocketPort22[1]), EnumConnectionState.LOGIN));
                    GuiServerFinder.networkManager.sendPacket(new CPacketLoginStart(new GameProfile(null, GuiServerFinder.jTextField3.getText())));
                    GuiServerFinder.textArea1.append("(1.12.2) Сервер найден #2 / Проверяется: " + listSocketPort2 + "\n");
                    GuiServerFinder.Servers2.add("(1.12.2) #2: " + listSocketPort2);
                    GuiServerFinder.ServerIs2();

                    try {
                        Thread.sleep(50L);
                    } catch (InterruptedException var4) {
                        var4.printStackTrace();
                    }

                }
            }).start();
        }

        listSocketPort.clear();
    }

    public static void ServerIs2() {
        try {
            File file = new File(Minecraft.getMinecraft().mcDataDir, "Jessica/Servers.txt");
            BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(file));

            for(int i = 0; i < Servers2.size(); ++i) {
                bufferedwriter.write(Servers2.get(i) + "\r\n");
            }

            bufferedwriter.close();
        } catch (Exception var3) {
            System.err.print(var3);
        }

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

    public static void main(String[] args) {
        try {
            LookAndFeelInfo[] installedLookAndFeels;
            int length = (installedLookAndFeels = UIManager.getInstalledLookAndFeels()).length;

            for(int i = 0; i < length; ++i) {
                LookAndFeelInfo info = installedLookAndFeels[i];
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException var5) {
            Logger.getLogger(GuiServerFinder.class.getName()).log(Level.SEVERE, null, var5);
        } catch (InstantiationException var6) {
            Logger.getLogger(GuiServerFinder.class.getName()).log(Level.SEVERE, null, var6);
        } catch (IllegalAccessException var7) {
            Logger.getLogger(GuiServerFinder.class.getName()).log(Level.SEVERE, null, var7);
        } catch (UnsupportedLookAndFeelException var8) {
            Logger.getLogger(GuiServerFinder.class.getName()).log(Level.SEVERE, null, var8);
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                (new GuiServerFinder()).setVisible(true);
            }
        });
    }
}
