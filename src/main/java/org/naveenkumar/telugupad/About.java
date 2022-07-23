package org.naveenkumar.telugupad;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class About extends JFrame {

	private static final long serialVersionUID = -3557978807363907747L;

	About(){
		setType(Type.POPUP);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		String html = "<center><h1>"+Config.NAME+"</h1>"
				+"Version "+Config.VERSION +" | Build "+Config.BUILD +"</center>"
				+"<p>Email : hello@naveenkumar.org</p>"
				+"<p>Website : "+getLink(Config.WEBSITE)+"</p>"
				+"<hr>"
				+"<p>This application is based on original code from "+getLink("https://lekhini.org")+" website developed by "+ getLink("https://veeven.com/")+".</p>"
				+"<p><br/></p>"
				+"<p>Icons from "+getLink("https://www.flaticon.com")+"</p>"
				+ "";
		
		JEditorPane editorPaneAbout = new JEditorPane();
		editorPaneAbout.setContentType("text/html");
		editorPaneAbout.setText(html);
		editorPaneAbout.setEditable(false);
		editorPaneAbout.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent hle) {
                if (HyperlinkEvent.EventType.ACTIVATED.equals(hle.getEventType())) {
                    System.out.println(hle.getURL());
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        desktop.browse(hle.getURL().toURI());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
		
		getContentPane().add(editorPaneAbout, BorderLayout.CENTER);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				
			}
		});
		setTitle("About");
		setResizable(false);
		setSize(450,300);
		setLocationRelativeTo(null);
		getContentPane().add(btnClose, BorderLayout.SOUTH);
		setVisible(true);
		
	}
	
	private String getLink(String url) {
		return "<a href=\""+url+"\">"+url+"</a>";
	}

}
