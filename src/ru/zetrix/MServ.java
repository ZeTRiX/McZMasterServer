package ru.zetrix;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author ZeTRiX
 */
public class MServ extends JFrame {
    
    public static JTabbedPane Content;
    public static Box MainBox;
    
    public JButton Go = new JButton("Run MasterServer", (new ImageIcon(Util.getRes("go.png"))));
    public JButton Send = new JButton("Send command", (new ImageIcon(Util.getRes("send.png"))));
    public static JLabel ClC = new JLabel("Command:");
    public static JLabel ClT = new JLabel("Recieved text:");
    public static JTextField ClNum = new JTextField(20);
    public static JTextPane Text;
    public DataOutputStream out;
    
    public MServ() {
        setTitle("McZMasterServer (Coded by ZeTRiX)");
        setIconImage(Util.getRes("favicon.png"));
        setBackground(Color.BLACK);
        this.setBounds(200, 200, 480, 220);
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        final ImageIcon icon = new ImageIcon(Util.getRes("bg.png"));
        Content = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT) {
            protected void paintComponent(Graphics g) {
                g.drawImage(icon.getImage(), 0,0, null);
                super.paintComponent(g);
            }
        };
        
        Box box1 = Box.createHorizontalBox();
        box1.add(ClC);
        box1.add(Box.createHorizontalStrut(6));
        box1.add(ClNum);
        Box box2 = Box.createHorizontalBox();
        box2.add(ClT);
        box2.add(Box.createHorizontalStrut(6));
        Text = new JTextPane() {
            private static final long serialVersionUID = 1L;
        };
        Text.setText("Client Message will appear here. Instruction is in the \"About\" tab!");
        Text.setEditable(false);
        box2.add(Text);
        Box box4 = Box.createHorizontalBox();
        box4.add(Box.createHorizontalGlue());
        box4.add(Box.createHorizontalStrut(12));
        Send.setEnabled(false);
        box4.add(Send);        
        box4.add(Go);
        ClC.setPreferredSize(ClT.getPreferredSize());
        MainBox = Box.createVerticalBox();
        MainBox.setBorder(new EmptyBorder(12,12,12,12));
        MainBox.add(box1);
        MainBox.add(Box.createVerticalStrut(12));
        MainBox.add(box2);
        MainBox.add(Box.createVerticalStrut(17));
        MainBox.add(box4);
        Content.add("Starter", MainBox);
        
        this.getContentPane().add(Content);
        
        Go.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MSRun();
                Go.setEnabled(false);
                Send.setEnabled(true);
            }
        });
        Send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SendMessage(ClNum.getText().toString());
            }
        });
    }
    
    public void MSRun() {
        new Thread() {
            @Override
            public void run() {
                int port = 59092; // Cлучайный порт (любое число от 1025 до 65535)
                try {
                    ServerSocket ss = new ServerSocket(port);
                    System.out.println("Waiting for a client...");
                    
                    Socket socket = ss.accept();
                    System.out.println("Connected from: " + socket.getInetAddress().getHostAddress() + "/" + socket.getInetAddress().getHostName());
                    
                    InputStream sin = socket.getInputStream();
                    OutputStream sout = socket.getOutputStream();
                    
                    DataInputStream in = new DataInputStream(sin);
                    out = new DataOutputStream(sout);
                    
                    while(true) {
                        Text.setText(in.readUTF());
                    }
                } catch(Exception x) {
                    Text.setText(x.toString());
                }
            }
        }
                .start();
    }
    
    public void SendMessage(String text) {
        if (text.trim() == null) {
            JOptionPane.showMessageDialog((Component)
                    null,
                    "No command was typed. \n Please, type a command and try again! \n",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                out.writeUTF(text);
                out.flush();
            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        MServ McZMServ = new MServ();
        McZMServ.show();
    }
}
