package form_ChucNang;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.testng.TestNG;

import utils.QuanLyDiem;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Panel;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.CardLayout;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class jChuc_Nang extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JTextArea logArea;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					jChuc_Nang frame = new jChuc_Nang();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public jChuc_Nang() {
		setTitle("Automation Testing");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 660, 445);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Automation Testing");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
		getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.WEST);
		panel.setLayout(new GridLayout(7, 1, 5, 5));
		
		JButton btnNewButton = new JButton("CRUD TestCases");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logArea.append("\n▶ Đang chạy chức năng TestCase...\n");
				QuanLyDiem.reset(); 
		        TestNG testng = new TestNG();
		        testng.setTestClasses(new Class[] { tests.test_TestCase.class }); 

		        testng.run();
			}
		});
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("CRUD TestPlan");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logArea.append("\n▶ Đang chạy chức năng TestPlan...\n");
				QuanLyDiem.reset(); 
				TestNG testng = new TestNG();
				testng.setUseDefaultListeners(false);
		        testng.setTestClasses(new Class[] { tests.test_TestPlan.class });
		        testng.run();
			}
		});
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("CRUD SRS");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logArea.append("\n▶ Đang chạy chức năng TestPlan...\n");
				QuanLyDiem.reset(); 
				TestNG testng = new TestNG();
				testng.setUseDefaultListeners(false);
		        testng.setTestClasses(new Class[] { tests.test_SRS.class });
		        testng.run();
			}
		});
		panel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Test Giao Diện");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logArea.append("\n▶ Đang chạy chức năng TestPlan...\n");
				QuanLyDiem.reset(); 
				TestNG testng = new TestNG();
				testng.setUseDefaultListeners(false);
		        testng.setTestClasses(new Class[] { tests.Test_GiaoDien.class });
		        testng.run();
			}
		});
		panel.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Test Hình Ảnh và Hiển thị");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logArea.append("\n▶ Đang chạy chức năng TestPlan...\n");
				QuanLyDiem.reset(); 
				TestNG testng = new TestNG();
				testng.setUseDefaultListeners(false);
		        testng.setTestClasses(new Class[] { tests.UITest_TestCasePage.class });
		        testng.run();
			}
		});
		panel.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("Exit");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jChuc_Nang.this.dispose();
			}
		});
		panel.add(btnNewButton_5);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		splitPane.setLeftComponent(panel_1);
		
		JPanel panelLog = new JPanel();
		splitPane.setRightComponent(panelLog);
		panelLog.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panelLog.add(scrollPane, BorderLayout.CENTER);

		logArea = new JTextArea();
		logArea.setEditable(false);
		logArea.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
		logArea.setLineWrap(true);
		logArea.setWrapStyleWord(true);

		// ✅ Gắn textArea vào scrollpane
		scrollPane.setViewportView(logArea);
		redirectConsoleToLog();
	}
	private void redirectConsoleToLog() {

	    PrintStream printStream;
	    try {
	        printStream = new PrintStream(new OutputStream() {
	            private ByteArrayOutputStream buffer = new ByteArrayOutputStream();

	            @Override
	            public void write(int b) {
	                if (b == '\n') {
	                    try {
	                        String text = buffer.toString("UTF-8");
	                        logArea.append(text + "\n");
	                        logArea.setCaretPosition(logArea.getDocument().getLength());
	                        buffer.reset();
	                    } catch (Exception ignored) {}
	                } else {
	                    buffer.write(b);
	                }
	            }
	        }, true, "UTF-8"); // ✅ Dùng String thay vì Charset
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }

	    System.setOut(printStream);
	    System.setErr(printStream);
	}
}
