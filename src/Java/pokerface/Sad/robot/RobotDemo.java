 package pokerface.Sad.robot;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import com.sun.jna.Library;
import com.sun.jna.Native;

import sun.misc.BASE64Decoder;
public class RobotDemo {

	
private Robot robot = null;

	public RobotDemo() {
	   try {
	    robot = new Robot();
	   } catch (AWTException e) {
	    e.printStackTrace();
	   }
	}
	public interface CLibrary extends Library {
		CLibrary INSTANCE = (CLibrary)Native.loadLibrary("TopSetQQ",CLibrary.class);
		
		void TopSetQQ();
	}
	public void keyBoardInput() throws IOException  {
		System.out.println("login...");
		Properties pro = new Properties();
		pro.load(new FileInputStream("pwd.properties"));
		String password = pro.getProperty("password");
		byte  result[]= (new BASE64Decoder()).decodeBuffer(password);
		password=new String(result);
		char pwd[] = password.toCharArray();
		for(int i=0;i<pwd.length;i++)
		{
			if(Character.isLetter(pwd[i]))
			{		
				if(Character.isLowerCase(pwd[i]))
					this.keyPressAndRelease((int)pwd[i]-32);
				else
					this.keyPressWithShift(pwd[i]);
			}
			else
				this.keyPressAndRelease((int)pwd[i]);
		}
		this.keyPressAndRelease(KeyEvent.VK_ENTER);
	}
	public void keyPressAndRelease(int keycode){
		robot.keyPress(keycode);
		robot.keyRelease(keycode);
	}
	public void keyPressWithShift(int keycode){
		robot.keyPress(KeyEvent.VK_SHIFT);
		this.keyPressAndRelease(keycode);
		robot.keyRelease(KeyEvent.VK_SHIFT);
	}
	public static void waitForQQStart() throws IOException,
	InterruptedException, FileNotFoundException {

		BufferedReader br = null;
		Runtime.getRuntime().exec("cmd /k tasklist | find \"QQApp\">out.txt");
		Thread.currentThread().sleep(500);
		br = new BufferedReader(new InputStreamReader(new FileInputStream("out.txt")));
		if(br.readLine()==null) 
		{
			Runtime.getRuntime().exec("cmd /c start QQ");
			while(br.readLine()==null)
			{
				System.out.println("Wait For QQ Strat...");
				Thread.currentThread().sleep(500);
				Runtime.getRuntime().exec("cmd /k tasklist | find \"QQApp\">out.txt");
			}
		}
		System.out.println("QQ Strat Successfully...");
		Thread.currentThread().sleep(1000);
		br.close();
		CLibrary.INSTANCE.TopSetQQ(); //JNA调用DLL置顶QQ
	}

	/**
	   * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws Exception 
	   */
	public static void main(String[] args) throws IOException, InterruptedException {

		waitForQQStart();
		RobotDemo demo=new RobotDemo();
		demo.keyBoardInput();
		new File("out.txt").delete();
		
		
	}

}