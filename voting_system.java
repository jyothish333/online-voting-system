import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


class User_Admin extends Frame
{
	Button b1,b2;
	public User_Admin()
	{
		Label l=new Label("VOTING PORTAL",Label.CENTER);
		Font f=new Font("Arial", Font.PLAIN,30);
		l.setFont(f);
		l.setBounds(90,100,500,100);
		b1=new Button("USER");
		b2=new Button("ADMIN");
		b1.setBounds(290,200,70,40);
		b2.setBounds(290,270,70,40);
		add(l);
		add(b1);
		add(b2);
		User_AdminListener ual=new User_AdminListener(this);
		b1.addActionListener(ual);
		b2.addActionListener(ual);
		this.setSize(700,500);
		this.setTitle("Welcome Form");
		this.setLayout(null);
		this.setVisible(true);
		this.addWindowListener(new close());
	}
}
class User_AdminListener implements ActionListener
{
	User_Admin x;
	User_AdminListener(User_Admin x)
	{
		this.x=x;
	}
	public void actionPerformed(ActionEvent e)
	{
		String s=e.getActionCommand();
		if(s.equals("USER"))
		{
			x.setVisible(false);
			new User_Frame();
		}
		if(s.equals("ADMIN"))
		{
			x.setVisible(false);
			new Admin_Frame();
		}
	}
}

class User_Frame extends Frame
{
	TextField name, pwd;
   	Button b1,b2;
	User_Frame()
	{
		Label l=new Label("USER LOGIN FORM",Label.CENTER);
		User_FrameListener ufl=new User_FrameListener(this);
		l.setBounds(250,80,200,40);
		add(l);
		Label l1 = new Label("Name: ");
        	Label l2 = new Label("Password: ");
        	l1.setBounds(150,165,70,30);
        	l2.setBounds(150,215,70,30);
		name = new TextField(10);
		pwd = new TextField(10);
		pwd.setEchoChar('*'); 
        	name.setBounds(220,165,250,30);
        	pwd.setBounds(220,215,250,30);
		b1 = new Button("Submit");
		b1.setBounds(350,300,70,40);
		b2=new Button("Go Back");
		b2.setBounds(220,300,70,40);
		b1.addActionListener(ufl);
		b2.addActionListener(ufl);
		add(l1);
		add(l2);
 		add(name);	
		add(pwd);
		add(b1);
		add(b2);
		this.setSize(700,500);
		this.setTitle("User Login");
		this.setLayout(null);
		this.setVisible(true);
		this.addWindowListener(new close());
	}
}

class User_FrameListener implements ActionListener
{
	User_Frame x;
	int k;
	User_FrameListener(User_Frame x)
	{
		this.x=x;
	}
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			Graphics g = x.getGraphics();
			String un1=x.name.getText();
			String up1=x.pwd.getText();
			String s=e.getActionCommand();
			if(s.equals("Go Back"))
			{
				x.setVisible(false);
				new User_Admin();
			}
			if(s.equals("Submit"))
			{
				try
				{	
					if(un1.equals("") || up1.equals(""))
					{
						throw new Exception();
					}
					else
					{
						try
						{
							Class.forName("com.mysql.cj.jdbc.Driver");
							String url="jdbc:mysql://localhost:3306/voting_system";
							String user = "root"; 
           						String pwd = "harsha";
           						Connection con = DriverManager.getConnection(url, user, pwd);
							Statement st=con.createStatement();
							String query="SELECT * FROM user_data WHERE u_name=? AND u_pass=?";
							PreparedStatement p=con.prepareStatement(query);
							p.setString(1,un1);
							p.setString(2,up1);
							ResultSet rs=p.executeQuery();
							if(rs.next())
							{
								
								g.drawString("Login successful",275,390);
								String sqlQuery = "SELECT * FROM user_data WHERE u_name = ? AND (status = 1 OR status = 0)";
								PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
								preparedStatement.setString(1, un1);
								ResultSet resultSet = preparedStatement.executeQuery();
								int stat ;
								while (resultSet.next()) 
								{
									String matchedUserName = resultSet.getString("u_name");
									stat = resultSet.getInt("status");
									k=stat;
								}
								if(k==1)
								{
									g.drawString("you have already voted ",265,410);
									g.drawString("Re directing user login page ",255,430);
									Thread.sleep(3000);
									x.setVisible(false);
									new User_Frame();
								}
								else
								{
									Thread.sleep(3000);
									x.setVisible(false);
									User_Frame_1 obj = new User_Frame_1(un1);
								}

								resultSet.close();
								preparedStatement.close();
							
								
							}
							else
							{
								g.drawString("Login failed",275,390);
								g.drawString("Re directing user login page ",255,430);
								Thread.sleep(3000);
								x.setVisible(false);
								new User_Frame();
							}
							st.close();
							rs.close();
							con.close();
						}
						catch(Exception e3)
						{
							System.out.println(e3);
						}
					}
				}
				catch(NullPointerException e2)
				{
					g.drawString("User Name or Password must not be empty",275,390);
				}
			}
		}
		catch(Exception e1)
		{
			System.out.println(e1);
		}
		
	}
}

class User_Frame_1 extends Frame 
{
    	String un;
    	private Label l;
    	CheckboxGroup cbg;
    	Checkbox cb1, cb2, cb3, cb4;
    	Button submitButton;

    	public User_Frame_1(String un)
	{
        	this.un = un;
        	User_Frame_1Listener uf1l = new User_Frame_1Listener(this, un);
        	l = new Label("CAST YOUR VOTE",Label.CENTER);
		Font f=new Font("Arial", Font.PLAIN,30);
		l.setFont(f);
        	l.setBounds(90,100,500,100);
        	cbg = new CheckboxGroup();
        	cb1 = new Checkbox("Candidate-1", cbg, false);
        	cb2 = new Checkbox("Candidate-2", cbg, false);
        	cb3 = new Checkbox("Candidate-3", cbg, false);
        	cb4 = new Checkbox("Candidate-4", cbg, false);
        	cb1.setBounds(275, 200, 100, 30);
        	cb2.setBounds(275, 230, 100, 30);
        	cb3.setBounds(275, 260, 100, 30);
        	cb4.setBounds(275, 290, 100, 30);
		add(l);
        	add(cb1);
        	add(cb2);
        	add(cb3);
        	add(cb4);
        	cb1.addItemListener(uf1l);
        	cb2.addItemListener(uf1l);
        	cb3.addItemListener(uf1l);
        	cb4.addItemListener(uf1l);

        	submitButton = new Button("Submit");
        	submitButton.setBounds(270, 350, 75, 30);
        	submitButton.addActionListener(uf1l);
        	add(submitButton);

        	this.setSize(700, 500);
        	this.setTitle("User Voting");
        	this.setLayout(null);
        	this.setVisible(true);
        	this.addWindowListener(new close());
    }
}



class User_Frame_1Listener implements ItemListener, ActionListener 
{
	User_Frame_1 x;
    	String un;
	String ch = "NO",can="";

    	User_Frame_1Listener(User_Frame_1 x, String un)
	{
        	this.x = x;
        	this.un = un;
    	}

    	public void itemStateChanged(ItemEvent ie) 
	{
		
        	try
		{
            		Graphics g = x.getGraphics();
			Label l1 = new Label("Once u submit u cannot change it  ");
			l1.setBounds(50, 50, 400, 30);
			can=x.cbg.getSelectedCheckbox().getLabel();
            		String choosen = x.cbg.getSelectedCheckbox().getLabel();
			x.add(l1);
            		if (choosen.equals("Candidate-1"))
                		ch = "A";
            		else if (choosen.equals("Candidate-2"))
                		ch = "B";
            		else if (choosen.equals("Candidate-3"))
                		ch = "C";
            		else if (choosen.equals("Candidate-4"))
                		ch = "D";
           
        	} 
		catch (Exception e) 
		{
            		System.out.println(e);
        	}
    	}
    	public void actionPerformed(ActionEvent ae) 
	{
		String s=ae.getActionCommand();
        	try 
		{
			
			if(s.equals("Submit"))
			{
				Graphics g = x.getGraphics();
				Label l = new Label("Selected candidate: " +can);
				l.setBounds(50, 425, 200, 30);
				x.add(l);
				Class.forName("com.mysql.cj.jdbc.Driver");
				String url = "jdbc:mysql://localhost:3306/voting_system";
				String user = "root";
				String pwd = "harsha";
				Connection con = DriverManager.getConnection(url, user, pwd);
				String query = "UPDATE user_data SET status = ?, opted = ? WHERE u_name = ?";
				try (PreparedStatement statement = con.prepareStatement(query)) 
				{
					statement.setInt(1, 1);
					statement.setString(2, ch);
					statement.setString(3, un);
					statement.executeUpdate();
				} 
				catch (SQLException e1) 
				{
					System.out.println(e1);
				}

				g.drawString("You sucessfully voted",260,410);
				g.drawString("Re directing to user login page ",260,450);
				Thread.sleep(3000);
				x.setVisible(false);
				new User_Frame();
			} 
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
    
	}
}


class Admin_Frame extends Frame
{
	TextField name, pwd;
   	Button b1,b2;
	Admin_Frame()
	{
		Label l=new Label("ADMIN LOGIN FORM",Label.CENTER);
		Font f=new Font("Lucida Calligraphy",Font.PLAIN, 20);
		Admin_FrameListener afl=new Admin_FrameListener(this);
		l.setFont(f);
		l.setBounds(250,80,200,40);
		add(l);
		Label l1 = new Label("Name: ");
        	Label l2 = new Label("Password: ");
        	l1.setBounds(150,165,70,30);
        	l2.setBounds(150,215,70,30);
		name = new TextField(10);
		pwd = new TextField(10);
		pwd.setEchoChar('*'); 
        	name.setBounds(220,165,250,30);
        	pwd.setBounds(220,215,250,30);
		b1 = new Button("Submit");
		b1.setBounds(350,300,70,40);
		b2=new Button("Go Back");
		b2.setBounds(220,300,70,40);
		b1.addActionListener(afl);
		b2.addActionListener(afl);
		add(l1);
		add(l2);
 		add(name);	
		add(pwd);
		add(b1);
		add(b2);
		this.setSize(700,500);
		this.setTitle("Admin Login");
		this.setLayout(null);
		this.setVisible(true);
		this.addWindowListener(new close());
	}
}

class Admin_FrameListener implements ActionListener
{
	Admin_Frame x;
	boolean flag=false;
	Connection con=null;
	
	Admin_FrameListener(Admin_Frame x)
	{
		this.x=x;
	}
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			String admin_n=x.name.getText();
			String admin_p=x.pwd.getText();
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/voting_system";
			String user = "root"; 
           		String pwd = "harsha";
           		con = DriverManager.getConnection(url, user, pwd);
			Graphics g=x.getGraphics();
			String s=e.getActionCommand();
			if(s.equals("Go Back"))
			{
				x.setVisible(false);
				new User_Admin();
			}
			if(s.equals("Submit"))
			{
				String query="select admin_pass from admin_data_table where admin_name=?";
				PreparedStatement pst=con.prepareStatement(query);
				pst.setString(1,admin_n);
				ResultSet rs=pst.executeQuery();
				if(rs.next())
				{
					String admin_pass=rs.getString(1);
					if(admin_p.equals(admin_pass))
					{
						g.drawString("Login Succesfull",275,390);
						Thread.sleep(2000);
						x.setVisible(false);
						new Admin_Frame_1();
					}
					else
					{
						g.drawString("Wrong Password",275,390);
						Thread.sleep(2000);
						x.setVisible(false);
						new Admin_Frame();
					}
				}
				else
				{
					g.drawString("Admin Name is Invalid",265,390);
					Thread.sleep(2000);
					x.setVisible(false);
					new Admin_Frame();
				}
			}
		}
		catch(Exception e1)
		{
			System.out.println(e1);
		}
	}
}


class Admin_Frame_1 extends Frame
{
	Button b1,b2,b3,b4;
	Admin_Frame_1()
	{
		b1=new Button("Register a Person");
		b2=new Button("Check Poll Status");
		b3=new Button("Declare Results");
		b4=new Button("Log Out");
		b1.setBounds(225,75,110,40);
		b2.setBounds(140,155,105,40);
		b3.setBounds(310,155,100,40);
		b4.setBounds(50,300,70,40);
		Admin_Frame_1Listener af1l=new Admin_Frame_1Listener(this);
		b1.addActionListener(af1l);
		b2.addActionListener(af1l);
		b3.addActionListener(af1l);
		b4.addActionListener(af1l);
		add(b1);
		add(b2);
		add(b3);
		add(b4);
		this.setSize(575,400);
		this.setTitle("Admin Frame 1");
		this.setLayout(null);
		this.setVisible(true);
		this.addWindowListener(new close());
	}
}
class Admin_Frame_1Listener implements ActionListener
{
	Admin_Frame_1 x;
	Admin_Frame_1Listener(Admin_Frame_1 x)
	{
		this.x=x;
	}
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			Graphics g=x.getGraphics();
			String s=e.getActionCommand();
			if(s.equals("Register a Person"))
			{
				x.setVisible(false);
				new Register();
			}
			if(s.equals("Check Poll Status"))
			{
				try
			    	{	
					Class.forName("com.mysql.cj.jdbc.Driver");
					String url="jdbc:mysql://localhost:3306/voting_system";
					String user = "root"; 
           				String pwd = "harsha";
           				Connection con = DriverManager.getConnection(url, user, pwd);
					String query="SELECT * FROM user_data";
					Statement stmt=con.createStatement();
					ResultSet rs=stmt.executeQuery(query);
					int cou=0, row_cnt=0;
					while(rs.next())
					{
						String row=rs.getString("status");
						for(int i=0;i<row.length();i++)
						{
							if(row.charAt(i)=='1')
								cou++;
							row_cnt++;
						}
					}
					int re = row_cnt - cou;
					g.drawString("Numbers of people voted :  "+cou,275,300);
					g.drawString("Numbers of people not voted :  "+re,275,340);
					Thread.sleep(2000);
					x.setVisible(false);
					new Admin_Frame_1();
					rs.close();
					stmt.close();
					con.close();
                		}
                		catch (SQLException e1) 
                		{
                    			System.out.println(e1);
               			}
			}
			if(s.equals("Declare Results"))
			{
				try
			    	{	
					Class.forName("com.mysql.cj.jdbc.Driver");
					String url="jdbc:mysql://localhost:3306/voting_system";
					String user = "root"; 
           				String pwd = "harsha";
           				Connection con = DriverManager.getConnection(url, user, pwd);
					String query="SELECT * FROM user_data";
					Statement stmt=con.createStatement();
					ResultSet rs=stmt.executeQuery(query);
					int a1=0,a2=0,a3=0,a4=0,h=0;
					char h1='N';
					while(rs.next())
					{
						String row=rs.getString("opted");
						for(int i=0;i<row.length();i++)
						{
							if(row.charAt(i)=='A')
							{
								a1++;
								if(h<a1)
								{h=a1;
								h1='A';}
							}	
							else if(row.charAt(i)=='B')
							{	
								a2++;
								if(h<a2){
								h=a2;
								h1='B';}
							}
							else if(row.charAt(i)=='C')
							{
								a3++;
								if(h<a3){
								h=a3;
								h1='C';}
							}
							else if(row.charAt(i)=='D')
							{
								a4++;
								if(h<a4){
								h=a4;
								h1='D';}
							}
						}
					}
					g = x.getGraphics();
					g.drawString("Winner :  "+h1,275,300);
					g.drawString("Numbers of people voted to him :  "+h,275,340);
					Thread.sleep(3000);
					x.setVisible(false);
					new Admin_Frame_1();
					rs.close();
					stmt.close();
					con.close();
                		}
                		catch (SQLException e1) 
                		{
                    			System.out.println(e1);
               			}
			}
			if(s.equals("Log Out"))
			{
				g.drawString("Logging Out",250,320);
				Thread.sleep(2000);
				x.setVisible(false);
				new Admin_Frame();
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
}

class Register extends Frame
{
	TextField t1, t2;
	Button b1,b2,b3;
	Register()
	{
		RegisterListener rl=new RegisterListener(this);
		Label l1=new Label("FILL THE FOLLOWING DETAILS");
		Label l2=new Label("Enter Name:");
		Label l3=new Label("Enter Age:");
		l1.setBounds(240,50,200,40);
		l2.setBounds(100,150,100,40);
		l3.setBounds(100,200,100,40);
		add(l1);
		add(l2);
		add(l3);
		b1=new Button("Go Back");
		b2=new Button("Refresh");
		b3=new Button("Submit");
		b1.setBounds(125,325,70,40);
		b2.setBounds(255,325,70,40);
		b3.setBounds(380,325,70,40);
		t1 = new TextField(20);
		t2 = new TextField(20);
        	t1.setBounds(220,150,200,30);
        	t2.setBounds(220,200,200,30);
		add(t1);
		add(t2);
		b1.addActionListener(rl);
		b2.addActionListener(rl);
		b3.addActionListener(rl);
		add(b1);
		add(b2);
		add(b3);
		this.setSize(700,500);
		this.setTitle("Registration Form");
		this.setLayout(null);
		this.setVisible(true);
		this.addWindowListener(new close());
	}
}

class RegisterListener implements ActionListener
{
	Register x;
	int count=0;
	RegisterListener(Register x)
	{
		this.x=x;
		
	}
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			Graphics g=x.getGraphics();
			String s=e.getActionCommand();
			if(s.equals("Go Back"))
			{
				x.setVisible(false);
				new Admin_Frame_1();
			}
			if(s.equals("Refresh"))
			{
				x.setVisible(false);
				new Register();
			}
			if(s.equals("Submit"))
			{
				try
				{
					String name=x.t1.getText();
					String a1=x.t2.getText();
					if(name.equals("") || a1.equals(""))
					{
						throw new Exception();
					}
					else if(name.length()<3)
					{
						g.drawString("Name length should be greater than or equal to 3",200,400);
						Thread.sleep(3000);
						x.setVisible(false);
						new Register();
					}
					int age=Integer.parseInt(a1);
					if(age<18)
					{
						g.drawString("In eligible to Register",275,390);
						Thread.sleep(3000);
						x.setVisible(false);
						new Register();
					}
					else
					{
						count+=1;
						String un=name.substring(0,3)+"_"+count;
						x.setVisible(false);
						new Register_1(un,name,age);
					}
				}
				catch(Exception ne)
				{
					g.drawString("Name or Age should not be null",200,400);
					Thread.sleep(3000);
					x.setVisible(false);
					new Register();
				}
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
}

class Register_1 extends Frame
{
	TextField t1, t2;
	Button b1,b2,b3;
	Register_1(String un,String name,int age)
	{	
		try
		{
			Label l1=new Label("Your user Name is: "+un);
			Label l2=new Label("This is System Generated, you cannot Modify it.");
			Label l3=new Label("Set your Password");
			Label l4=new Label("Enter new Password:");
			Label l5=new Label("Confirm Password:");
			l1.setBounds(50,50,150,40);
			l2.setBounds(50,100,350,40);
			l3.setBounds(50,150,350,40);
			l4.setBounds(110,200,150,40);
			l5.setBounds(110,250,150,40);
			add(l1);
			add(l2);
			add(l3);
			add(l4);
			add(l5);
			b1=new Button("Go Back");
			b2=new Button("Refresh");
			b3=new Button("Submit");
			b1.setBounds(125,325,70,40);
			b2.setBounds(255,325,70,40);
			b3.setBounds(380,325,70,40);
			add(b1);
			add(b2);
			add(b3);
			t1 = new TextField(20);
			t1.setEchoChar('*');
			t2 = new TextField(20);
        		t1.setBounds(270,200,200,30);
        		t2.setBounds(270,250,200,30);
			add(t1);
			add(t2);
			Register_1Listener r1l=new Register_1Listener(this, un, name, age);
			b1.addActionListener(r1l);
			b2.addActionListener(r1l);
			b3.addActionListener(r1l);
			this.setSize(700,500);
			this.setTitle("Registration 1");
			this.setLayout(null);
			this.setVisible(true);
			this.addWindowListener(new close());
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		
	}
}

class Register_1Listener implements ActionListener
{
	Register_1 x;
	String un;
	String name;
	int age;
	Register_1Listener(Register_1 x, String un, String name, int age)
	{
		this.x=x;
		this.un = un;
		this.name=name;
		this.age=age;
	}
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			Graphics g = x.getGraphics();
			String s=e.getActionCommand();
			if(s.equals("Go Back"))
			{
				x.setVisible(false);
				new Register();
			}
			if(s.equals("Refresh"))
			{
				x.setVisible(false);
				new Register_1(un,name,age);
			}
			if(s.equals("Submit"))
			{
				String new_p = x.t1.getText();
				String temp = x.t2.getText();
				try
				{
					if(new_p.equals("") || temp.equals(""))
					{
						throw new Exception();
					}
					if(new_p.equals(temp))
					{

					       	try 
						{
            						String url = "jdbc:mysql://localhost:3306/voting_system";
            						String username = "root";
            						String password = "harsha";
            						Connection con = DriverManager.getConnection(url, username, password);
            						String insertQuery = "INSERT INTO user_data VALUES (?, ?, ?, ?, ?, ?)";
            						PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            						preparedStatement.setString(1, un); 
            						preparedStatement.setString(2, new_p);
            						preparedStatement.setString(3, name); 
	    						preparedStatement.setInt(4, age); 
            						preparedStatement.setInt(5, 0);
	    						preparedStatement.setString(6, "NL");
							preparedStatement.executeUpdate();
            						preparedStatement.close();
            						con.close();
        					} 
						catch (SQLException e2) 
						{
            						System.out.println(e2);
        					}
						g.drawString("Successfully Registered", 200,400 );
						g.drawString("Name : "+name, 550,415 );
						g.drawString("user name : "+un, 550,430 );
						g.drawString("age : "+age, 550,445 );
						g.drawString("password : "+temp, 550,460);
						Thread.sleep(3000);
						g.drawString("Redirecting to Home Screen",150 ,425 );
						Thread.sleep(3000);
						x.setVisible(false);
						new Admin_Frame_1();
					}
					else
					{
						g.drawString("Error! Passwords are not matching", 50,400 );
						Thread.sleep(3000);
						x.setVisible(false);
						new Register_1(un,name,age);
					}
				}
				catch(Exception e1)
				{
					g.drawString("Passwords should not be empty", 50,400 );
					Thread.sleep(3000);
					x.setVisible(false);
					new Register_1(un,name,age);
				}
				
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
}

class close extends WindowAdapter
{
	public void windowClosing(WindowEvent e)
	{
		System.exit(0);
	}
}


class voting_system
{
	public static void main(String args[]) throws Exception
	{
		new User_Admin();
	}
}