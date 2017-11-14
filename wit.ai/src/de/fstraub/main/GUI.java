package de.fstraub.main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
	private final String ACCESS_TOKEN = "[REPLACE WITH TOKEN]",
                         TITLE        = "Wit.ai Client";


	private JPanel contentPane;
	private JButton btn_cancel_recording;
    private JButton btn_record;
	private JTextField tf_textInput;
    private JTextArea  ta_textOutput;

	private Wit wit;
	private JavaSoundRecorder rec;
	private boolean isRecording;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	    // Set System Look and Feel
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
	 * Create the GUI frame.
	 */
	public GUI() {
		wit = new Wit(ACCESS_TOKEN);
		rec = new JavaSoundRecorder();

		try {
			Image img = ImageIO.read(getClass().getResourceAsStream("/resources/wit.png"));
			setIconImage(img);
		} catch (IOException e) {
			e.printStackTrace();
		}

		setTitle(TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(2, 2));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

        JPanel panel_1 = new JPanel();
        panel.add(panel_1, BorderLayout.EAST);
        panel_1.setLayout(new BorderLayout(0, 0));

		tf_textInput = new JTextField();
		panel.add(tf_textInput, BorderLayout.CENTER);
		tf_textInput.setColumns(10);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        ta_textOutput = new JTextArea();
        ta_textOutput.setEditable(false);
        scrollPane.setViewportView(ta_textOutput);

		btn_cancel_recording = new JButton("");
		btn_cancel_recording.setEnabled(false);
		btn_cancel_recording.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rec.cancel();
				isRecording = false;
				btn_cancel_recording.setEnabled(false);
				btn_record.setIcon(new ImageIcon(GUI.class.getResource("/resources/rec.png")));
			}
		});
		btn_cancel_recording.setIcon(new ImageIcon(GUI.class.getResource("/resources/cancel.png")));
		panel_1.add(btn_cancel_recording, BorderLayout.EAST);


        btn_record = new JButton("");
		btn_record.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!isRecording){
					btn_cancel_recording.setEnabled(true);
					btn_record.setIcon(new ImageIcon(GUI.class.getResource("/resources/stop.png")));
					rec.start();
				}else{
					btn_cancel_recording.setEnabled(false);
					btn_record.setIcon(new ImageIcon(GUI.class.getResource("/resources/rec.png")));
					rec.finish();
					sendSpeechRequest(rec.wavFile);
				}
				isRecording = !isRecording;
			}
		});

		btn_record.setIcon(new ImageIcon(GUI.class.getResource("/resources/rec.png")));
		panel_1.add(btn_record, BorderLayout.CENTER);

		JButton btnNewButton_2 = new JButton("Send");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tf_textInput.getText() != null && tf_textInput.getText().length() > 0){
                    sendTextRequest(tf_textInput.getText());
				}
			}
		});
		panel_1.add(btnNewButton_2, BorderLayout.WEST);
	}

    /**
     * sends a speech request to the wit.ai server and handles the response
     * @param wavFile the speech request (recorded with the microphone) that should be interpreted
     */
    private void sendSpeechRequest(File wavFile) {
        // TODO: send a speech request to wit.ai

        WitResponse r = wit.sendSpeechRequest(wavFile);

        // TODO: handle received request data
    }

    /**
     * sends a speech request to the wit.ai server and handles the response
     * @param text the text that should be interpreted
     */
    private void sendTextRequest(String text){
        // TODO: send a speech request to wit.ai

        WitResponse r = wit.sendTextRequest(text);

        // TODO: handle received request data
    }


}
