import java.awt.EventQueue;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main{
	
  /**
	 * Launch the application. Load data from the properties file.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Properties prop = new Properties();
					FileInputStream input = new FileInputStream("config.properties");
					prop.load(input);
					
					int screenWidth = Integer.parseInt(prop.getProperty("screen_width"));
					int screenHeight = Integer.parseInt(prop.getProperty("screen_height"));
					
					int imagePlaceWidth = Integer.parseInt(prop.getProperty("imagePlaceWidth"));
					int imagePlaceHeight = Integer.parseInt(prop.getProperty("imagePlaceHeight"));					
					
					input.close();					
					
					Window f = new Window(screenWidth, screenHeight, imagePlaceWidth, imagePlaceHeight);
					f.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


}
