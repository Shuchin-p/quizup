package demoQuiz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.SwingWorker;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.swing.JOptionPane;

/* 
 * ##|10101010 |##
 * ##|VORTEX   |##
 * ##|QUIZSWING|##
 * ##|		   |##
 * ## `.	  .'##
 * 		`....' 
 */
class Question extends Thread
{
	private String line1=null;
	private String q,a,b,c,d,left = "";
	private BufferedReader br1,br2;
	JFrame jf1,jf2;
	private JLabel qlabel = new JLabel();
	private JLabel alabel = new JLabel();
	private JLabel blabel = new JLabel();
	private JLabel clabel = new JLabel();
	private JLabel dlabel = new JLabel();
	private JLabel leftTime = new JLabel();
	private JFileChooser choose;
	private JCheckBox bkchk = new JCheckBox("BackUp this QUIZ set");
	private JButton strtb = new JButton("Let's Start");
	private JButton fileb = new JButton("Select File");
	private static String fpath,timeuser,bpath;
	
	Question() throws IOException
	{	
		jf1 = new JFrame("Welcome");
		strtb.setBounds(70, 50, 150, 50);
		fileb.setBounds(70, 150, 150, 50);
		bkchk.setBounds(70, 200, 150, 50);
		jf1.setSize(300, 300);
		jf1.setVisible(true);
		jf1.setLayout(null);
		jf1.add(strtb);
		strtb.setEnabled(false);
		jf1.add(fileb);
		jf1.add(bkchk);
		jf1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf2 = new JFrame("LIVE");
		JFrame.setDefaultLookAndFeelDecorated(true);
		fileb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				choose = new JFileChooser();
				choose.setCurrentDirectory(new java.io.File("."));
				choose.setDialogTitle("Select Game File");
				choose.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int i = choose.showOpenDialog(null);
				if(i==JFileChooser.APPROVE_OPTION)
				{			
					File f = choose.getSelectedFile();
					fpath = f.getPath();
					if(fpath.endsWith(".txt"))
					{
						
						if(bkchk.isSelected()==true)
						{	
							Checkbkup ob = new Checkbkup();
							System.out.println("" + ob.result());
							if(ob.result())
							{
								try
								{
									br1 = new BufferedReader(new FileReader(fpath));
									br2 = new BufferedReader(new FileReader(ob.temp));
									PrintWriter pw = new PrintWriter(ob.temp);
									String br1line,br2line;
									//String divider = "\n";
									pw.print(" ");
									pw.flush();
									while((br2line=br2.readLine()) != null)
									{
										pw.println(br2line);
										
									}			
									while((br1line=br1.readLine()) != null)
									{
										pw.println(br1line);
										
									}
									pw.flush();
									br1.close();
									br2.close();
									pw.close();
								}
								catch(FileNotFoundException fe)
								{
									JOptionPane.showMessageDialog(null, "Backup File not found at default directory. Choose default directory again", "File not found", JOptionPane.ERROR_MESSAGE);
									choose = new JFileChooser();
									choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
									choose.setCurrentDirectory(new java.io.File("."));
									if(choose.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
									{
										File bak = choose.getSelectedFile();
										bpath = bak.getPath();
										System.out.println("B0	"+bpath);
										
										try {
												bak = new File(bpath+"/backupquestion.txt");
												if(bak.createNewFile())
												{
													String temp = bpath;
													bpath=bak.getPath();
													System.out.println("T	"+temp);
													System.out.println("F	"+ fpath);
													System.out.println("b	"+ bpath);
													br1 = new BufferedReader(new FileReader(fpath));
													br2 = new BufferedReader(new FileReader(bpath));
													@SuppressWarnings("resource")
													PrintWriter pw = new PrintWriter(bpath);
													System.out.println("F	"+ fpath);
													System.out.println("b	"+ bpath);
													String br1line,br2line;
													//String divider = "\n";
													while((br2line=br2.readLine()) != null)
													{
														System.out.println("L2	"+ br2line);
														pw.println(br2line);
													}
													//pw.println(divider);
													while((br1line=br1.readLine()) != null)
													{
														System.out.println("L1	"+ br1line);
														pw.println(br1line);
													}
													pw.flush();
													br2.close();
													pw.close();
													System.out.println("Created");
													new Checkbkup(bpath);
													System.out.println("Created");
												}
											}
											
										 catch (IOException io) {
											 System.out.println("OB.FILE BAK");
										}
										
									}
									
								}
								catch(IOException io)
								{
									System.out.println("OB.result IO");
								}
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Select the default directory to backup", "Backup Question", JOptionPane.INFORMATION_MESSAGE);
								choose = new JFileChooser();
								choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
								choose.setCurrentDirectory(new java.io.File("."));
								if(choose.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
								{
									File bak = choose.getSelectedFile();
									bpath = bak.getPath();
									System.out.println("B0	"+bpath);
									
									try {
											bak = new File(bpath+"/backupquestion.txt");
											if(bak.createNewFile())
											{
												String temp = bpath;
												bpath=bak.getPath();
												System.out.println("T	"+temp);
												System.out.println("F	"+ fpath);
												System.out.println("b	"+ bpath);
												br1 = new BufferedReader(new FileReader(fpath));
												br2 = new BufferedReader(new FileReader(bpath));
												@SuppressWarnings("resource")
												PrintWriter pw = new PrintWriter(bpath);
												System.out.println("F	"+ fpath);
												System.out.println("b	"+ bpath);
												String br1line,br2line;
												String divider = "\n";
												while((br2line=br2.readLine()) != null)
												{
													System.out.println("L2	"+ br2line);
													pw.println(br2line);
												}
												pw.println(divider);
												while((br1line=br1.readLine()) != null)
												{
													System.out.println("L1	"+ br1line);
													pw.println(br1line);
												}
												pw.flush();
												br2.close();
												pw.close();
												System.out.println("Created");
												new Checkbkup(bpath);
												System.out.println("Created");
											}
											
										}
										
									 catch (IOException io) {
										 System.out.println("IO FILE BAK");
									}
									
								}	
							}	
																					
						}
						else
						{
							JOptionPane.showMessageDialog(null, "File Loaded Successfully.");
						}
						
						timeuser = JOptionPane.showInputDialog(null, "Enter the round time in seconds");
					
						if(timeuser !=null && (timeuser.isEmpty()==false))
						{
							strtb.setEnabled(true);
						}
						else
						{
							timeuser = JOptionPane.showInputDialog(null, "Enter the round time in seconds");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Select .TXT file only", "Wrong FILE TYPE", JOptionPane.ERROR_MESSAGE);
					
					}
					
				}
			}
		});
		strtb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jf1.dispose();

					if(fpath!=null && (fpath.isEmpty()==true))
					{
						JOptionPane.showMessageDialog(null, "Select the question .TXT file only","Select your file",JOptionPane.INFORMATION_MESSAGE);
						
					}
					else
					{
						try
						{
							br1 = new BufferedReader(new FileReader(fpath));
							
						}
						catch(FileNotFoundException fe)
						{
							JOptionPane.showMessageDialog(null, "FILE NOT FOUND", "FILE PATH ERROR", JOptionPane.ERROR_MESSAGE);
						}
						
					}
					jf2.setVisible(true);						
			}
		});
		
		jf2.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				jf2.add(qlabel);
				jf2.add(alabel);
				jf2.add(blabel);
				jf2.add(clabel);
				jf2.add(dlabel);
				jf2.add(leftTime);
				jf2.setSize(450, 500);
				//Bounds
				qlabel.setBounds(10, 100, 500, 50);
				alabel.setBounds(30, 140, 250, 50);
				blabel.setBounds(30, 160, 250, 50);
				clabel.setBounds(30, 180, 250, 50);
				dlabel.setBounds(30, 200, 250, 50);
				leftTime.setBounds(30, 240, 100, 50);
				jf2.setLayout(null);
				jf2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				
				SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>(){
					
					@SuppressWarnings("static-access")
					@Override
					protected Boolean doInBackground() throws Exception {
						try 
						{
							while((line1=br1.readLine())!= null)
							{
								int gtime = Integer.parseInt(timeuser) ;
								String tmp[] = line1.split("\t");
								q= tmp[0];
								a= tmp[1];
								b= tmp[2];
								c= tmp[3];
								d= tmp[4];
								while(gtime!=0)
								{
									left = Integer.toString(gtime);
									publish(left);
									Thread.sleep(1000);
									gtime--;
								}
								publish(q,a,b,c,d);
							}br1.close();
						}
						catch(InterruptedException ie)
						{
							System.out.println("SW: Game interrupted");
						}
						catch(IOException io)
						{
							System.out.println("SW: IOException");
						}
						
						return true;
					}
					@Override
					protected void done() {
						qlabel.setText(" ");
						qlabel.setText("#######################	GAME ENDED	#######################");
						alabel.setText(" ");
						blabel.setText(" ");
						clabel.setText(" ");
						dlabel.setText(" ");
						leftTime.setText("00:00");
					}
					@Override
					protected void process(List<String> chunks) {
						qlabel.setText(" ");
						qlabel.setText(q);
						alabel.setText(" ");
						alabel.setText(a);
						blabel.setText(" ");
						blabel.setText(b);
						clabel.setText(" ");
						clabel.setText(c);
						dlabel.setText(" ");
						dlabel.setText(d);
						leftTime.setText(" ");
						leftTime.setText("TIME LEFT	  " + left);
					}
				};
				worker.execute();
				
			}
		});
		
	}
}

class Checkbkup
{
	String temp,bpath;
	static boolean b;
	Checkbkup(){}
	Checkbkup(String bpath)
	{	
		this.bpath=bpath;
		System.out.println("CB1	" +bpath);
		try
		{
			File bak;
			bak = new File("temp.vortex");
			if(bak.createNewFile())
			{
				//BufferedReader br = new BufferedReader(new FileReader("temp.vortex"));
				@SuppressWarnings("resource")
				PrintWriter pw = new PrintWriter("temp.vortex");
				System.out.println("BC1	" +bpath);
				pw.println(bpath);		
				pw.flush();
				//this.temp(temp);
				//br.close();
			}
			else
			{	
				PrintWriter pw = new PrintWriter("temp.vortex");
				pw.print(" ");
				pw.flush();
				pw.println(bpath);
				pw.flush();
				pw.close();
			}
		}
		catch(IOException io)
		{
			System.out.println("Checkbkup IO");
		}
	}
	boolean result()
	{	
		File chk=new File("temp.vortex");
		if(chk.exists())
		{
			try {
				BufferedReader br = new BufferedReader(new FileReader("temp.vortex"));
				String temp = br.readLine();
				if(temp != null && (temp.isEmpty()==false))
				{
					b = true;
					this.temp(temp);
				}
				else
				{
					b=false;
				}
				br.close();
				
			}
			catch (FileNotFoundException fnfe) {
				System.out.println("BOOL RE Fnfe");
			}
			catch (IOException e) {
				System.out.println("BOOL RE IO");
			}
		}
		else {
			b = false;
		}
		return b;
	}
	String temp(String temp)
	{
		this.temp=temp;
		return temp;
	}
	
}

public class Demo {
	
	public static void main(String[] args) throws IOException 
	{
		Question qt = new Question();
		qt.start();
	}

}
