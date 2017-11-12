package de.fstraub.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class GUI extends JFrame {

	private static final long serialVersionUID = 2484809953602059008L;
	private JPanel contentPane;
	private JTextField textField;

	private Wit wit;
	private JavaSoundRecorder rec;
	private boolean isRecording;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
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
	public GUI() {

		wit = new Wit("V7TD6H2JWIUVM5GNYEBPEF3PQNAEPDRQ");
		rec = new JavaSoundRecorder();

		setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/img/wit.png")));
		setTitle("Wit.ai Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(2, 2));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		textField = new JTextField();
		panel.add(textField, BorderLayout.CENTER);
		textField.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new BorderLayout(0, 0));

		JButton btnNewButton_1 = new JButton("");
		JButton btnNewButton = new JButton("");
		btnNewButton.setEnabled(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rec.cancel();
				isRecording = false;
				btnNewButton.setEnabled(false);
				btnNewButton_1.setIcon(new ImageIcon(GUI.class.getResource("/img/rec.png")));
			}
		});
		btnNewButton.setIcon(new ImageIcon(GUI.class.getResource("/img/cancel.png")));
		panel_1.add(btnNewButton, BorderLayout.EAST);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!isRecording){
					btnNewButton.setEnabled(true);
					btnNewButton_1.setIcon(new ImageIcon(GUI.class.getResource("/img/stop.png")));
					rec.start();
				}else{
					btnNewButton.setEnabled(false);
					btnNewButton_1.setIcon(new ImageIcon(GUI.class.getResource("/img/rec.png")));
					rec.finish();
					textArea.setText(wit.sendSpeechRequest(rec.wavFile));
				}
				isRecording = !isRecording;
			}
		});
		btnNewButton_1.setIcon(new ImageIcon(GUI.class.getResource("/img/rec.png")));
		panel_1.add(btnNewButton_1, BorderLayout.CENTER);

		JButton btnNewButton_2 = new JButton("Send");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textField.getText() != null && textField.getText().length() > 0)
					textArea.setText(wit.sendTextRequest(textField.getText()));
			}
		});
		panel_1.add(btnNewButton_2, BorderLayout.WEST);



	}

}
