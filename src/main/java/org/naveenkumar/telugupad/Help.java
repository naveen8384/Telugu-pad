package org.naveenkumar.telugupad;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Help extends JFrame {

	private static final long serialVersionUID = 3019079644495270817L;
	JLabel lbl;

	public Help() {
		setType(Type.UTILITY);
		setSize(320, 528);
		lbl = new JLabel();
		try {
			InputStream inputStream = getClass().getResourceAsStream("/resources/img/help.jpg");
			lbl.setIcon(new ImageIcon(ImageIO.read(inputStream)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		getContentPane().add(lbl);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(rootPane);
	}
}
