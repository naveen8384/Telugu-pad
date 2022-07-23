package org.naveenkumar.telugupad;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class TeluguPadGUI extends JFrame {
	private static final long serialVersionUID = -85132249468039924L;
	private JTextArea input, output;
	private Font gautami,notoSerif,mandali,mallanna;
	private JPanel statusPanel,leftPanel,rightPanel;
	private Invocable invocable;
	private JLabel lblStatus;
	private JScrollPane leftScroll,rightScroll;
	private JMenuBar menuBar;
	private JMenu fileMenu,viewMenu,helpMenu,fontMenu,fontSizeMenu;
	private JMenuItem newMenuItem,saveMenuItem,exitMenuItem,printMenuItem,helpMenuItem,aboutMenuItem;
	private ImageIcon iconNew,iconSave,iconExit,iconPrint,iconHelp,iconAbout,iconFont,iconSize;
	
	public TeluguPadGUI() {
		setTitle(Config.NAME+" "+Config.VERSION);
        setSize(800, 500);
        
		try {
			ImageIcon ii;
			ii = new ImageIcon(ImageIO.read(getFileFromResourceAsStream("resources/icons/logo.png")));
			setIconImage(ii.getImage());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
        setLocationRelativeTo(null);
        setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		createMenuBar();
		initFont();
		initEngine();

		
		lblStatus = new JLabel();
		lblStatus.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel = new JPanel();
		statusPanel.setLayout(new BorderLayout(0, 0));
		statusPanel.add(lblStatus);
		lblStatus.setText("Ready..");
		
        leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout(0, 0));
        
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout(0, 0));
        
        input = new JTextArea();
        
        output = new JTextArea();
        input.setText("");
        output.setText("");
        output.setFont(mallanna);
        output.setToolTipText("Type here to convert it into telugu");
        
        DefaultContextMenu.addDefaultContextMenu(input);
        DefaultContextMenu.addDefaultContextMenu(output);
        
        leftScroll = new JScrollPane (input,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        rightScroll = new JScrollPane (output,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        //leftScroll.getVerticalScrollBar().setModel(rightScroll.getVerticalScrollBar().getModel());
        rightScroll.getVerticalScrollBar().setModel(leftScroll.getVerticalScrollBar().getModel());
        
        leftPanel.add(leftScroll);
        rightPanel.add(rightScroll);
  
        JSplitPane splitPlane = new JSplitPane(SwingConstants.VERTICAL, leftPanel, rightPanel);
        splitPlane.setResizeWeight(0.5);
        splitPlane.setOrientation(SwingConstants.VERTICAL);
        getContentPane().setLayout(new BorderLayout(0, 0));
        getContentPane().add(splitPlane);
        getContentPane().add(statusPanel,BorderLayout.SOUTH);
        setVisible(true);
        
        input.getDocument().addDocumentListener((SimpleDocumentListener) e -> {
        	lblStatus.setText("Translating..");
            output.setText(translateText(input.getText()));
            lblStatus.setText("Ready..");
        });
	}
	
	private void initFont() {
		try {
		    gautami = Font.createFont(Font.TRUETYPE_FONT, getFileFromResourceAsStream("resources/font/Gautami.ttf")).deriveFont(20f);
		    notoSerif = Font.createFont(Font.TRUETYPE_FONT, getFileFromResourceAsStream("resources/font/NotoSerifTelugu-Medium.ttf")).deriveFont(20f);
		    mandali = Font.createFont(Font.TRUETYPE_FONT, getFileFromResourceAsStream("resources/font/Mandali.ttf")).deriveFont(20f);
		    mallanna = Font.createFont(Font.TRUETYPE_FONT, getFileFromResourceAsStream("resources/font/Mallanna.ttf")).deriveFont(20f);
		    
//		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//		    ge.registerFont(gautami);
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
	}
	
	 private void createMenuBar() {
		 
	        try {
				iconNew = new ImageIcon(ImageIO.read(getFileFromResourceAsStream("resources/icons/new-document.png")));
		        iconSave = new ImageIcon(ImageIO.read(getFileFromResourceAsStream("resources/icons/save.png")));
		        iconPrint = new ImageIcon(ImageIO.read(getFileFromResourceAsStream("resources/icons/print.png")));
		        iconExit = new ImageIcon(ImageIO.read(getFileFromResourceAsStream("resources/icons/exit.png")));
		        iconHelp = new ImageIcon(ImageIO.read(getFileFromResourceAsStream("resources/icons/help.png")));
		        iconAbout = new ImageIcon(ImageIO.read(getFileFromResourceAsStream("resources/icons/about.png")));
		        iconFont = new ImageIcon(ImageIO.read(getFileFromResourceAsStream("resources/icons/font.png")));
		        iconSize = new ImageIcon(ImageIO.read(getFileFromResourceAsStream("resources/icons/size.png")));
			} catch (IOException e2) {
				e2.printStackTrace();
			}


	        menuBar = new JMenuBar();

	        fileMenu = new JMenu("File");
	        viewMenu = new JMenu("View");
	        fontMenu = new JMenu("Font");
	        fontMenu.setIcon(iconFont);
	        fontSizeMenu = new JMenu("Font Size");
	        fontSizeMenu.setIcon(iconSize);
	        helpMenu = new JMenu("Help");
	        

	        
	        
	        newMenuItem = new JMenuItem("New",iconNew);
	        saveMenuItem = new JMenuItem("Save as txt",iconSave);
	        printMenuItem = new JMenuItem("Print",iconPrint);
	        exitMenuItem = new JMenuItem("Exit",iconExit);
	        helpMenuItem = new JMenuItem("Help",iconHelp);
	        aboutMenuItem = new JMenuItem("About",iconAbout);
	        
	        JMenuItem gautamiFontMenuItem = new JMenuItem("Gautami");
	        JMenuItem mallannaFontMenuItem = new JMenuItem("Mallanna");
	        JMenuItem mandaliFontMenuItem = new JMenuItem("Mandali");
	        JMenuItem notoserifFontMenuItem = new JMenuItem("NotoSerif");
	        
	        fontMenu.add(gautamiFontMenuItem);
	        fontMenu.add(mallannaFontMenuItem);
	        fontMenu.add(mandaliFontMenuItem);
	        fontMenu.add(notoserifFontMenuItem);

	        
	        JMenuItem lowFontMenuItem = new JMenuItem("Low");
	        JMenuItem mediumFontMenuItem = new JMenuItem("Medium");
	        JMenuItem highFontMenuItem = new JMenuItem("High");
	        
	        fontSizeMenu.add(lowFontMenuItem);
	        fontSizeMenu.add(mediumFontMenuItem);
	        fontSizeMenu.add(highFontMenuItem);
	        
	        gautamiFontMenuItem.addActionListener((event) -> output.setFont(gautami));
	        mallannaFontMenuItem.addActionListener((event) -> output.setFont(mallanna));
	        mandaliFontMenuItem.addActionListener((event) -> output.setFont(mandali));
	        notoserifFontMenuItem.addActionListener((event) -> output.setFont(notoSerif));
	        
	        
	        exitMenuItem.addActionListener((event) -> System.exit(0));
	        aboutMenuItem.addActionListener((event) -> new About());

	        
	        printMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						output.print();
					} catch (PrinterException e1) {
						e1.printStackTrace();
					}
				}
			});
	        
	        newMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					input.setText("");
					output.setText("");
					
				}
			});
	        
	        saveMenuItem.addActionListener(new ActionListener() {
	        	@Override
				public void actionPerformed(ActionEvent e) {
					 final JFileChooser SaveAs = new JFileChooser();
				      SaveAs.setApproveButtonText("Save");
				      int actionDialog = SaveAs.showSaveDialog(output);
				      if (actionDialog != JFileChooser.APPROVE_OPTION) {
				         return;
				      }

				      File fileName = new File(SaveAs.getSelectedFile() + ".txt");
				      BufferedWriter outFile = null;
				      try {
				         outFile = new BufferedWriter(new FileWriter(fileName));
				         output.write(outFile);
				      } catch (IOException ex) {
				         ex.printStackTrace();
				      } finally {
				         if (outFile != null) {
				            try {
				               outFile.close();
				            } catch (IOException ex) {
				              ex.printStackTrace();
				            }
				         }
				      }
				}
			});
	        
	        helpMenuItem.addActionListener(new ActionListener() {
	        	@Override
				public void actionPerformed(ActionEvent e) {
					new Help().setVisible(true);
				}
			});
	        
	        fileMenu.add(newMenuItem);
	        fileMenu.add(saveMenuItem);
	        fileMenu.add(printMenuItem);
	        fileMenu.addSeparator();
	        fileMenu.add(exitMenuItem);
	        
	        viewMenu.add(fontMenu);
	        
	        helpMenu.add(helpMenuItem);
	        helpMenu.add(aboutMenuItem);

	        menuBar.add(fileMenu);
	        menuBar.add(viewMenu);
	        menuBar.add(helpMenu);

	        setJMenuBar(menuBar);
	    }
	
	private void initEngine() {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
	    try {
	    	engine.eval(new InputStreamReader(getFileFromResourceAsStream("resources/js/Shared.js")));
			engine.eval(new InputStreamReader(getFileFromResourceAsStream("resources/js/Padma.js")));
			engine.eval(new InputStreamReader(getFileFromResourceAsStream("resources/js/PageScripts.js")));
			engine.eval(new InputStreamReader(getFileFromResourceAsStream("resources/js/parser.js")));
			engine.eval(new InputStreamReader(getFileFromResourceAsStream("resources/js/RTS.js")));
			engine.eval(new InputStreamReader(getFileFromResourceAsStream("resources/js/RTSParser.js")));
			engine.eval(new InputStreamReader(getFileFromResourceAsStream("resources/js/Telugu.js")));
			engine.eval(new InputStreamReader(getFileFromResourceAsStream("resources/js/Unicode.js")));
			engine.eval(new InputStreamReader(getFileFromResourceAsStream("resources/js/Transformer.js")));
			engine.eval(new InputStreamReader(getFileFromResourceAsStream("resources/js/RTSTransformer.js")));
			engine.eval(new InputStreamReader(getFileFromResourceAsStream("resources/js/Syllable.js")));
			invocable = (Invocable) engine;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String translateText(String txt) {
		String result = "";
		try {
			result =  (String) invocable.invokeFunction("vReturnTransform", txt);
		} catch (NoSuchMethodException | ScriptException e) {
			e.printStackTrace();
		}
		return result;
	}
	
    private InputStream getFileFromResourceAsStream(String fileName) {
        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

}
