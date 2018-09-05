package a;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;
import javax.sound.sampled.*;
import javax.swing.*;
//����
class Tile{
	int x;
	int y;
	
	public Tile(int x0,int y0){
		x = x0;
		y = y0;
	}
}

class Barrier{
	int length;
	Tile[] barrier;
	public Barrier(int len){
		length = len;
		barrier = new Tile[length];
		for(int i = 0;i < length;i++)
		{
			barrier[i] = new Tile(0,0);//ÿ��ǽ��ÿ��ש�������Ĭ��Ϊ(0,0)
		}
	}
}
//���״̬��
public class SnakeDemo extends JComponent{
	private JLabel label  = new JLabel("��ǰ���ȣ�");
	private JLabel label2 = new JLabel("����ʱ�䣺");
	private JLabel label3 = new JLabel("��ǰ�÷֣�");
	private JLabel label4 = new JLabel("ʣ��ʱ�䣺");
	private JLabel label5 = new JLabel("ʣ���ӵ���");
	private JLabel Length = new JLabel("1");
	private JLabel Score = new JLabel("0");
	private JLabel Time = new JLabel("");
	private JLabel Time2 = new JLabel("5");
	private JLabel Weapon = new JLabel("0");
	private Font f = new Font("΢���ź�",Font.PLAIN,15);
	private JPanel p = new JPanel();
	
	public final int MAX_SIZE = 400;
	private Tile temp = new Tile(0,0);
	private Tile temp2 = new Tile(0,0);
	private Tile head = new Tile(0,0);
	private Tile[] body = new Tile[MAX_SIZE];
	
	private boolean first_launch = false;
	private boolean iseaten = false;
	private boolean isrun = true;
	private int randomx,randomy;
	public int body_length = 0;//���峤�ȳ�ʼ��Ϊ0
	private Thread run;
	private Thread run2;
	private Thread run3;
	
	private int hour =0;
	private int min =0;
	private int sec =0 ;
	
	private boolean pause = false;
	
	public static long normal_speed = 300;
//	public static long normal_speed = 600000; //debug speed
	private long millis = normal_speed;//ÿ��normal_speed����ˢ��һ��
	private boolean speed = true;
	private Calendar Cld;
	private int MI,MI2,MI3;
	private int SS,SS2,SS3;
	
	private final int foodkind = 6;
	private int score = 0;
	private int foodtag;
	private int[] point_list = new int[6];
	public ImageIcon snakehead;
	public ImageIcon snakebody;
	private ImageIcon[] food = new ImageIcon[foodkind];
	public JLabel head_label;
	public JLabel[] body_label = new JLabel[MAX_SIZE];
	private JLabel food_label;//ÿ�β���һ��ʳ��
		
	private int countsecond = 50;//ÿ��ʳ�����5�����ʧ���߻�λ��
	private boolean ifcount = false;
	
	public static boolean If_remove = false;//�Ƿ��Ƴ�������
	
	private boolean hit_flag = false;
	
	private Barrier[] obstacle = new Barrier[5];//ÿ�β���5��ǽ
	public JLabel[] obstacle_label = new JLabel[40];//ÿ��ǽ����󳤶�Ϊ8,5��ǽ����󳤶�Ϊ40
	private ImageIcon brickIcon;
	private int brick_amount = 0;
	private int brick_history_amount = 0;
	private boolean hit_barrier = false; 
	private String direction = "R";//��ʼ����Ϊ��
	private String current_direction = "R";//��ǰ����
	private int bullet = 0;//��¼�ӵ�����
	private ImageIcon fireIcon;
	private JLabel fire_label;
	private Tile fire_position = new Tile(0,0);
	private Tile target;
	private boolean startfire = false;
	private String running;
	private int targetx,targety;
	private int target_ptr;
	
	public SnakeDemo(){
		
		String lookAndFeel =UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		//���岼��
		add(label);
	    label.setBounds(900, 10, 80, 20);
	    label.setFont(f);
	    add(Length);
	    Length.setBounds(900, 35, 80, 20);
	    Length.setFont(f);
	    add(label2);
	    label2.setBounds(900, 70, 80, 20);
	    label2.setFont(f);
	    add(Time);
	    Time.setBounds(900, 95, 80, 20);
	    Time.setFont(f);    
	    add(label3);
	    label3.setBounds(900, 130, 80, 20);
	    label3.setFont(f);
	    add(Score);
	    Score.setBounds(900, 155, 80, 20);
	    Score.setFont(f);
	    add(p);
	    p.setBounds(898, 200, 93, 1);
	    p.setBorder(BorderFactory.createLineBorder(Color.white));
	    add(label4);
	    label4.setBounds(900, 210, 80, 20);
	    label4.setFont(f);
	    add(Time2);
	    Time2.setBounds(900, 235, 80, 20);
	    Time2.setFont(f);
	    add(label5);
	    label5.setBounds(900, 270, 80, 20);
	    label5.setFont(f);
	    add(Weapon);
	    Weapon.setBounds(900, 295, 80, 20);
	    Weapon.setFont(f); 
	    
	    //������ɫ��Ϊ�˱��ڷֱ棬��Ϊ��ɫ
	    label.setForeground(Color.white);
	    label2.setForeground(Color.white);
	    label3.setForeground(Color.white);
	    label4.setForeground(Color.white);
	    label5.setForeground(Color.white);
	    Length.setForeground(Color.white);
		Score.setForeground(Color.white);
		Time.setForeground(Color.white);
		Time2.setForeground(Color.white);    
		Weapon.setForeground(Color.white); 
		
		for(int i = 0;i < 5;i++)
		{
			obstacle[i] = new Barrier(8);
		}
		
	    //��ʼ��ͷ������
	    ProduceRandom();
	    head = new Tile(randomx,randomy);
	    snakehead = new ImageIcon("head//head.png");
	    snakehead.setImage(snakehead.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));//����ͼƬ������
        head_label = new JLabel(snakehead); 
        add(head_label);
        head_label.setBounds(head.x, head.y, 20, 20);
        head_label.setOpaque(false);
        
        //��ʼ���������нڵ�
        snakebody = new ImageIcon("body//body.png");
		snakebody.setImage(snakebody.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));//����ͼƬ������
		for(int i = 0; i < MAX_SIZE;i++)
		{
			body[i] = new Tile(0,0);
			body_label[i] = new JLabel(snakebody); 
			body_label[i].setOpaque(false);
		}
		
		//��ʼ��ʳ��
		food[0] = new ImageIcon("food//hotdog.png");
	    food[0].setImage(food[0].getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));//����ͼƬ������
	    food[1] = new ImageIcon("food//hamburger.png");
	    food[1].setImage(food[1].getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));//����ͼƬ������
	    food[2] = new ImageIcon("food//drink.png");
	    food[2].setImage(food[2].getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));//����ͼƬ������
	    food[3] = new ImageIcon("food//green_apple.png");
	    food[3].setImage(food[3].getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));//����ͼƬ������
	    food[4] = new ImageIcon("food//red_apple.png");
	    food[4].setImage(food[4].getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));//����ͼƬ������
	    food[5] = new ImageIcon("food//weapon.png");
	    food[5].setImage(food[5].getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));//����ͼƬ������
	    
	    fireIcon = new ImageIcon("fire.png");//����ӵ�ʱ�����Ļ���ͼ��
	    fireIcon.setImage(fireIcon.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));//����ͼƬ������
	    fire_label = new JLabel(fireIcon);
	    
	    //��ʼ����ʳ���Ӧ�ĵ÷�
	    point_list[0] = 20;
	    point_list[1] = 40;
	    point_list[2] = 30;
	    point_list[3] = 10;
	    point_list[4] = 30;
	    point_list[5] = 0;
	    
	    
	    //��ʼ������ש��
	    brickIcon = new ImageIcon("brick.png");
	    brickIcon.setImage(brickIcon.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));//����ͼƬ������
		for(int i = 0; i < 40;i++)
		{
			obstacle_label[i] = new JLabel(brickIcon); 
			obstacle_label[i].setOpaque(false);
		}
    
	    ProduceFood();
        food_label.setOpaque(false);
		
		//��Ӽ���������������
		this.addKeyListener(new KeyAdapter() {
	    	public void keyPressed(KeyEvent e) {
	    		super.keyPressed(e);
	    		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
	    		{
	    			if(isrun && current_direction != "L")
	    			{
	    				direction = "R";
	    			}
	    		}
	    		if(e.getKeyCode() == KeyEvent.VK_LEFT)
	    		{
	    			if(isrun && current_direction != "R")
	    			{
	    				direction = "L";
	    			}
	    		}
	    		if(e.getKeyCode() == KeyEvent.VK_UP)
	    		{
	    			if(isrun && current_direction != "D")
	    			{
	    				direction = "U";
	    			}
	    		}
	    		if(e.getKeyCode() == KeyEvent.VK_DOWN)
	    		{
	    			if(isrun && current_direction != "U")
	    			{
	    				direction = "D";
	    			}
	    		}
	    		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)//�������б�����ʼֵ
	    		{
	    			Reset(); 
	    		}
	    		
	    		if(e.getKeyCode() == KeyEvent.VK_SPACE)
	    		{
	    			if(!pause)//��ͣ
	    			{
	    				pause = true;
	    				isrun = false;
	    				ifcount = false;
	    			}
	    			else//��ʼ
	    			{
	    				pause = false;
	    				isrun = true;
	    				ifcount = true;
	    			}
	    		}
	    		
	    		if(e.isShiftDown())
	    		{
	    			if(!startfire && bullet > 0)
	    			{
	    				System.out.println("Fire a bullet");
		     			target = Search(head,current_direction);
		     			if(current_direction == "L")
	     				{
	     					fire_position.x = head.x - 22;
	     					fire_position.y = head.y;
	     				}
	     				if(current_direction == "R")
	     				{
	     					fire_position.x = head.x + 22;
	     					fire_position.y = head.y;
	     				}
	     				if(current_direction == "U")
	     				{
	     					fire_position.x = head.x;
	     					fire_position.y = head.y - 22;
	     				}
	     				if(current_direction == "D")
	     				{
	     					fire_position.x = head.x;
	     					fire_position.y = head.y + 22;
	     				}
	     				
	     				if(target.x != -1 && target.y != -1)
	     				{
	     					targetx = obstacle[target.x].barrier[target.y].x;
	     					targety = obstacle[target.x].barrier[target.y].y;
	     				}
	     				target_ptr = 0;
						for(int i = 0;i < target.x;i++)
						{
							target_ptr += obstacle[i].length;
						}
						target_ptr += target.y;
						
		     			running = current_direction;
		     			add(fire_label);
		     			fire_label.setBounds(fire_position.x, fire_position.y, 20, 20);
		     			fire_label.setVisible(true);
		     			startfire = true;
		     			bullet--;
		     			Weapon.setText("" + bullet);
	    			}
	    		}
	    	}
		});
		//��Ϸ��ʼ���ʱ
		this.addKeyListener(new KeyAdapter() {
	    	public void keyPressed(KeyEvent e) {
	    		int key = e.getKeyCode();
	    		if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN)
	    		{
	    			if(speed)
	    			{
	    				Cld = Calendar.getInstance();
		    			int YY = Cld.get(Calendar.YEAR) ;
		    	        int MM = Cld.get(Calendar.MONTH)+1;
		    	        int DD = Cld.get(Calendar.DATE);
		    	        int HH = Cld.get(Calendar.HOUR_OF_DAY);
		    	        int mm = Cld.get(Calendar.MINUTE);
		    	        SS = Cld.get(Calendar.SECOND);
		    	        MI = Cld.get(Calendar.MILLISECOND); 
		    	        System.out.println("Pressed time  " + YY + "/" + MM + "/" + DD + "-" + HH
		    	        		+ ":" + mm + ":" + SS + ":" + MI);
		    	        
		    	        speed = false;
	    			}
	    			
	    			Cld = Calendar.getInstance();
	    			SS3 = Cld.get(Calendar.SECOND);
	    	        MI3 = Cld.get(Calendar.MILLISECOND); 
	    	        int x = SS3 * 1000 + MI3 - ( SS * 1000 + MI);
	    	        if(x > 400)//��һ����ť��ʱ������400����ʶ��Ϊ����
	    	        {
	    	        	millis = 50;//����ʱÿ��50����ˢ��һ��
	    	        	System.out.println("Long Pressed");
	    	        }
	    		}
	    	}
		});
		
		this.addKeyListener(new KeyAdapter() {
	    	public void keyReleased(KeyEvent e) {
	    		int key = e.getKeyCode();
	    		if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN)
	    		{
	    			Cld = Calendar.getInstance();
	    			int YY2 = Cld.get(Calendar.YEAR) ;
	    	        int MM2 = Cld.get(Calendar.MONTH)+1;
	    	        int DD2 = Cld.get(Calendar.DATE);
	    	        int HH2 = Cld.get(Calendar.HOUR_OF_DAY);
	    	        int mm2 = Cld.get(Calendar.MINUTE);
	    	        SS2 = Cld.get(Calendar.SECOND);
	    	        MI2 = Cld.get(Calendar.MILLISECOND); 
	    	        System.out.println("Released time " + YY2 + "/" + MM2 + "/" + DD2 + "-" + HH2 
	    	        		+ ":" + mm2 + ":" + SS2 + ":" + MI2 + "\n" );

	    	        speed = true;
	    	        millis = normal_speed;
	    		}
	    	}
		});
		
		new Timer();//��ʼ��ʱ
		new Countdown();//��ʼ����ʱ
		Thread3();
		setFocusable(true);
	}
	
	public void paintComponent(Graphics g1){
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D) g1;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_NORMALIZE);
		
		//ˢ��ͷ������
		if(hit_flag)
		{
			head.x = temp.x;
			head.y = temp.y;
		}
		head_label.setBounds(head.x, head.y, 20, 20);
		
		if(!first_launch)
		{
			//��ʼ��ʳ��λ��
			ProduceFood();
			ProduceRandom();
			add(food_label);
			food_label.setBounds(randomx, randomy, 19, 19);
			ifcount = true;
			
			ProduceBarrier();
			//��ʼ��ǽ��λ��
			for(int i = 0; i < brick_amount;i++)//��ʼ�����ש��
			{
				add(obstacle_label[i]);
			}
			
			int ptr = 0;
			for(int i = 0; i < 5;i++)//ÿ��5��ǽ
			{
				for(int j = 0;j < obstacle[i].length;j++)
				{
					obstacle_label[ptr++].setBounds(obstacle[i].barrier[j].x,obstacle[i].barrier[j].y , 20, 20);
//					System.out.println(obstacle[i].barrier[j].x + "   " + obstacle[i].barrier[j].y);
				}
//				System.out.println();
			}
		}
		else
		{
			//ÿ��ˢ������
			for(int i = 0;i < body_length;i++)
			{
				body_label[i].setBounds(body[i].x, body[i].y, 20, 20);
			}
			
			if(EatFood())//���������²���ʳ��
			{
				remove(food_label);//ȥ�����Ե���ʳ��
				ProduceFood();
				ProduceRandom();
				add(food_label);
				food_label.setBounds(randomx, randomy, 19, 19);
				iseaten = false;
				
				ifcount = false;
				Time2.setText("5");
				countsecond = 5;
				ifcount = true;
			}
			else
			{
				food_label.setBounds(randomx, randomy, 19, 19);
			}
		}
		first_launch = true;
		
		//ǽ
		g.setPaint(new GradientPaint(115,135,Color.CYAN,230,135,Color.MAGENTA,true));
		g.setStroke( new BasicStroke(4,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
		g.drawRect(2, 7, 887, 469);//+400
		
		if(!If_remove)
		{
			//������
			for(int i = 1;i < 40;i++)
			{
//				g.setStroke( new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));//ʵ��
				g.setStroke( new BasicStroke(1f, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_ROUND, 3.5f, new float[] { 15, 10, },
                        0f));//����
				g.setColor(Color.black);
				g.drawLine(5+i*22,9,5+i*22,472);
				if(i <= 20)
				{
					g.drawLine(4,10+i*22,887,10+i*22);//+400
				}
			}
		}
	}
	
	public void ProduceFood(){
		Random rand = new Random();
		double x;
		x = rand.nextDouble();
		
		if(x >= 0 && x <0.1)
		{
			food_label = new JLabel(food[1]);
			foodtag = 1;
		}
		else if(x >= 0.1 && x <0.25)
		{
			food_label = new JLabel(food[2]);
			foodtag = 2;
		}
		else if(x >= 0.25 && x <0.5)
		{
			food_label = new JLabel(food[0]);
			foodtag = 0;
		}
		else if(x >= 0.5 && x <0.8)
		{
			food_label = new JLabel(food[3]);
			foodtag = 3;
		}
		else if(x >= 0.8 && x <0.95)
		{
			food_label = new JLabel(food[4]);
			foodtag = 4;
		}
		else if(x >= 0.95 && x <1)
		{
			food_label = new JLabel(food[5]);
			foodtag = 5;
		}
		
	} 
	
	public void ProduceRandom(){
		boolean flag = true;
		Random rand = new Random();
		randomx = (rand.nextInt(39) + 1) * 22 + 7;
		randomy = (rand.nextInt(20) + 1) *22 + 12;
		while(flag)
		{
			if(body_length == 0)
			{
				for(int i = 0;i < 5;i++)
				{
					for(int j = 0;j < obstacle[i].length; j++)
					{
						//��֤����ڵ㣬ͷ����ʳ�ﶼ���ܺ�ש���غϣ����ұ�֤����ռ��ܹ����������ǽ
						if( (randomx == obstacle[i].barrier[j].x && randomy == obstacle[i].barrier[j].y) ||
							(head.x == randomx  && head.y == randomy ) )
						{
							randomx = (rand.nextInt(39) + 1) * 22 + 7;
							randomy = (rand.nextInt(20) + 1) *22 + 12;
							flag = true;
							break;
						}
						else
						{
							if(i == 4 && j == obstacle[i].length - 1)
							{
								flag = false;
							}
						}
					}
				}
			}
			else
			{
				for(int i = 0;i < 5;i++)
				{
					for(int k = 0;k < body_length; k++)
					{
						for(int j = 0;j < obstacle[i].length; j++)
						{
							//��֤����ڵ㣬ͷ����ʳ�ﶼ���ܺ�ש���غϣ����ұ�֤����ռ��ܹ����������ǽ
							if( (body[k].x == randomx && body[k].y == randomy) || 
								(randomx == obstacle[i].barrier[j].x && randomy == obstacle[i].barrier[j].y) ||
								(head.x == randomx && head.y == randomx) )
							{
								randomx = (rand.nextInt(39) + 1) * 22 + 7;
								randomy = (rand.nextInt(20) + 1) *22 + 12;
								flag = true;
								break;
							}
							else
							{
								if(i == 4 && k == body_length - 1 && j == obstacle[i].length - 1)//������϶��ж��꣬ȷ�ϴ˶�ǽ��λ�ú���
								{
									flag = false;
								}
							}
						}
					}
				}
			}
			System.out.println("����һ���������ɹ�");
		}
	}
	
	public void ProduceBarrier(){
		
		brick_amount = 0;
		Random rand = new Random();
		int length;
		int tag;//tag = 0��ʾǽ�ķ���Ϊ����1��ʾǽ�ķ���Ϊ����
		int barrierx,barriery;
		boolean flag = true;
		for(int i = 0; i < 5;i++)//ÿ�β���5��ǽ
		{
			length = rand.nextInt(4) + 5;//ǽ�ĳ��ȴ�5��8���
			brick_amount += length;
			tag = rand.nextInt(2);//0��1
			barrierx = (rand.nextInt(39) + 1) * 22 + 7;//ÿ��ǽ��ʼש��ĺ�����
			barriery = (rand.nextInt(20) + 1) *22 + 12;//ÿ��ǽ��ʼש���������
			
			obstacle[i] = new Barrier(length);
			
			for(int j = 0;j < length;j++)
			{
				if(tag == 0)
				{
					obstacle[i].barrier[j].x = barrierx + j * 22;
					obstacle[i].barrier[j].y = barriery;
				}
				else if(tag == 1)
				{
					obstacle[i].barrier[j].x = barrierx;
					obstacle[i].barrier[j].y = barriery + j * 22;
				}
//				System.out.println(obstacle[i].barrier[j].x + "   " + obstacle[i].barrier[j].y);
			}
//			System.out.println();
			
			flag = true;
			
			while(flag)
			{
				if(body_length == 0)
				{
					for(int j = 0;j < length; j++)
					{
						//��֤����ڵ㣬ͷ����ʳ�ﶼ���ܺ�ש���غϣ����ұ�֤����ռ��ܹ����������ǽ
						if( (randomx == obstacle[i].barrier[j].x && randomy == obstacle[i].barrier[j].y) ||
							(head.x == obstacle[i].barrier[j].x && head.y == obstacle[i].barrier[j].y) ||
							(tag == 0 && obstacle[i].barrier[0].x > 39 * 22 + 7 - (length - 1) * 22) || 
							(tag == 1 && obstacle[i].barrier[0].y > 20 * 22 + 12 - (length - 1) * 22) )
						{
							barrierx = (rand.nextInt(39) + 1) * 22 + 7;
							barriery = (rand.nextInt(20) + 1) *22 + 12;
							for(int jj = 0;jj < length;jj++)
							{
								if(tag == 0)
								{
									obstacle[i].barrier[jj].x = barrierx + jj * 22;
									obstacle[i].barrier[jj].y = barriery;
								}
								else if(tag == 1)
								{
									obstacle[i].barrier[jj].x = barrierx;
									obstacle[i].barrier[jj].y = barriery + jj * 22;
								}
//								System.out.println(obstacle[i].barrier[jj].x + "   " + obstacle[i].barrier[jj].y);
							}
//							System.out.println();
							
							flag = true;
							break;
						}
						else
						{
							if(j == length - 1)
							{
								flag = false;
							}
						}
					}
				}
				else
				{
					for(int k = 0;k < body_length; k++)
					{
						for(int j = 0;j < length; j++)
						{
							//��֤����ڵ㣬ͷ����ʳ�ﶼ���ܺ�ש���غϣ����ұ�֤����ռ��ܹ����������ǽ
							if( (body[k].x == obstacle[i].barrier[j].x && body[k].y == obstacle[i].barrier[j].y) || 
								(randomx == obstacle[i].barrier[j].x && randomy == obstacle[i].barrier[j].y) ||
								(head.x == obstacle[i].barrier[j].x && head.y == obstacle[i].barrier[j].y) ||
								(tag == 0 && obstacle[i].barrier[0].x > 39 * 22 + 7 - (length - 1) * 22) || 
								(tag == 1 && obstacle[i].barrier[0].y > 20 * 22 + 12 - (length - 1) * 22) )
							{
								barrierx = (rand.nextInt(39) + 1) * 22 + 7;
								barriery = (rand.nextInt(20) + 1) *22 + 12;
								for(int jj = 0;jj < length;jj++)
								{
									if(tag == 0)
									{
										obstacle[i].barrier[jj].x = barrierx + jj * 22;
										obstacle[i].barrier[jj].y = barriery;
									}
									else if(tag == 1)
									{
										obstacle[i].barrier[jj].x = barrierx;
										obstacle[i].barrier[jj].y = barriery + jj * 22;
									}
//									System.out.println(obstacle[i].barrier[jj].x + "   " + obstacle[i].barrier[jj].y);
								}
//								System.out.println();
								
								flag = true;
								break;
							}
							else
							{
								if(k == body_length - 1 && j == length - 1)//������϶��ж��꣬ȷ�ϴ˶�ǽ��λ�ú���
								{
									flag = false;
								}
							}
						}
					}
				}
			}	
		}	
		System.out.println("����ǽ�ɹ�");
	}
	
	@SuppressWarnings("deprecation")
 	public void HitWall(){//�ж��Ƿ�ײǽ
		if(current_direction == "L")
		{
			if(head.x < 7)
			{
				ifcount = false;
				new AePlayWave("over.wav").start();
				isrun = false;
				int result=JOptionPane.showConfirmDialog(null, "Game over! Try again?", "Information", JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_NO_OPTION)
				{
					Reset();
				}
				else
				{
//					run.stop();
					pause = true;
				}		
			}
		}
		if(current_direction == "R")
		{
			if(head.x > 885)
			{
				ifcount = false;
				new AePlayWave("over.wav").start();
				isrun = false;
				int result=JOptionPane.showConfirmDialog(null, "Game over! Try again?", "Information", JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_NO_OPTION)
				{
					Reset();
				}
				else
				{
//					run.stop();
					pause = true;
				}
			}
		}
		if(current_direction == "U")
		{
			if(head.y < 12)
			{
				ifcount = false;
				new AePlayWave("over.wav").start();
				isrun = false;
				int result=JOptionPane.showConfirmDialog(null, "Game over! Try again?", "Information", JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_NO_OPTION)
				{
					Reset();
				}
				else
				{
//					run.stop();
					pause = true;
				}
			}
		}
		if(current_direction == "D")
		{
			if(head.y > 472)
			{
				ifcount = false;
				new AePlayWave("over.wav").start();
				isrun = false;
				int result=JOptionPane.showConfirmDialog(null, "Game over! Try again?", "Information", JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_NO_OPTION)
				{
					Reset();
				}
				else
				{
//					run.stop();
					pause = true;
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void HitSelf(){//�ж��Ƿ�ײ���Լ�����
		for(int i = 0;i < body_length; i++)
		{
			if(body[i].x == head.x && body[i].y == head.y)
			{
				ifcount = false;
				new AePlayWave("over.wav").start();
				isrun = false;
				int result=JOptionPane.showConfirmDialog(null, "Game over! Try again?", "Information", JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_NO_OPTION)
				{
					Reset();
				}
				else
				{
//					run.stop();
					pause = true;
				}
				break;
			}
		}
	}
	
	public void HitBarrier(){//�ж��Ƿ�ײ�ϰ�����
		boolean flag = false;
		for(int i = 0;i < 5;i++)
		{
			for(int j = 0;j < obstacle[i].length;j++)
			{
				if(head.x == obstacle[i].barrier[j].x && head.y == obstacle[i].barrier[j].y)
				{
					hit_barrier = true;
					
					ifcount = false;
					new AePlayWave("over.wav").start();
					isrun = false;
					int result=JOptionPane.showConfirmDialog(null, "Game over! Try again?", "Information", JOptionPane.YES_NO_OPTION);
					if(result==JOptionPane.YES_NO_OPTION)
					{
						Reset();
					}
					else
					{
//						run.stop();
						pause = true;
					}
					flag = true;
					break;
				}
			}
			
			if(flag)
			{
				break;
			}
		}
	}
	
	public boolean  EatFood(){
		if(head.x == randomx && head.y == randomy)
		{
			iseaten = true;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void Thread(){
//		millis = normal_speed;//Ĭ��ÿ��normal_speed����ˢ��һ��
		run = new Thread() {
			public void run() {
				while (true) 
				{
					try {
						Thread.sleep(millis);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
					
					if(!pause)
					{	
						hit_flag = false;
						temp.x = head.x;
						temp.y = head.y;
						
						//ͷ���ƶ�
						if(direction == "L")
						{
							head.x -= 22;
							if(head.x < 7)
							{
								hit_flag = true;
							}
						}
						if(direction == "R")
						{
							head.x += 22;
							if(head.x > 885)
							{
								hit_flag = true;
							}
						}
						if(direction == "U")
						{
							head.y -= 22;
							if(head.y < 12)
							{
								hit_flag = true;
							}
						}
						if(direction == "D")
						{
							head.y += 22;
							if(head.y > 472)
							{
								hit_flag = true;
							}
						}
						current_direction = direction;//ˢ�µ�ǰǰ������
						
						if(hit_barrier)
						{
							hit_flag = true;
						}
						
						if(!hit_flag)
						{
							for(int i = 0;i < body_length;i++)
							{
								temp2.x = body[i].x;
								temp2.y = body[i].y;
								body[i].x = temp.x;
								body[i].y = temp.y;
								temp.x = temp2.x;
								temp.y = temp2.y;
							}
							
							if(EatFood())
							{
								body_length ++;
								body[body_length-1].x = temp.x;
								body[body_length-1].y = temp.y;
								
								add(body_label[body_length-1]);
								
								Length.setText("" + (body_length+1) );//ˢ�³���
								score += point_list[foodtag];
								if(foodtag == 5)//�Ե������ӵ�
								{
									bullet++;
									Weapon.setText("" + bullet);
								}
								
								Score.setText("" + score);//ˢ�µ÷�
								new AePlayWave("eat.wav").start();
							}
						}
						
						repaint();
						
						//ˢ�����ж��Ƿ�ײǽ��ײ�Լ�������
						HitBarrier();
						HitWall();
						HitSelf();
					}
				}
			}
		};
		
		run.start();
	}
	
	public void Thread2(){
		run2 = new Thread() {
			public void run() {
				while (true) 
				{
					try {
						Thread.sleep(20000);//ÿ��20��ˢ��һ��
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
					
					if(isrun && !pause)
					{	
						brick_history_amount = brick_amount;//���ϴε�ש����������brick_history_amount
						for(int i = 0; i < brick_history_amount;i++)//�Ƴ�֮ǰ������ש��
						{
							remove(obstacle_label[i]);
						}
						
						ProduceBarrier();
						for(int i = 0; i < brick_amount;i++)//�������ש��
						{
							add(obstacle_label[i]);
						}
						
						int ptr = 0;
						for(int i = 0; i < 5;i++)//ÿ��5��ǽ
						{
							for(int j = 0;j < obstacle[i].length;j++)
							{
								obstacle_label[ptr].setVisible(true);
								obstacle_label[ptr++].setBounds(obstacle[i].barrier[j].x,obstacle[i].barrier[j].y , 20, 20);
							}
						}
						
						repaint();
						
						//ˢ�����ж��Ƿ�ײǽ��ײ�Լ�������
						HitWall();
						HitSelf();
					}
				}
			}
		};
		
		run2.start();
	}
	
	public void Thread3(){
		run3 = new Thread() {
			public void run() {
				while (true) 
				{
					try {
						Thread.sleep(80);//ÿ��80����ˢ��һ�Σ�ʵ�ֻ�������ƶ�������Ҫ����ͷ�ƶ��Ŀ�
 					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
					
					if(isrun && !pause && startfire)
					{
						
						if(running == "L")
						{
							if(target.x == -1 && target.y == -1)//������ǰ��·��û���ϰ���
							{
								if(fire_position.x > -10)//�Ƶ���Ļ�⼴��
								{
									fire_position.x -= 22;
								}
								else
								{
									startfire = false;
								}
							}
							else
							{
								if(fire_position.x > targetx)
								{
									fire_position.x -= 22;
								}
								else if(fire_position.x == targetx)//����Ŀ��ص�
								{
									new AePlayWave("explode.wav").start();
									obstacle_label[target_ptr].setVisible(false);
									obstacle[target.x].barrier[target.y].x = 1500;
									obstacle[target.x].barrier[target.y].y = 1500;
									fire_label.setVisible(false);
									
									startfire = false;
								}
							}
						}
						if(running == "R")
						{
							if(target.x == -1 && target.y == -1)//������ǰ��·��û���ϰ���
							{
								if(fire_position.x < 1020)//�Ƶ���Ļ�⼴��
								{
									fire_position.x += 22;
								}
								else
								{
									startfire = false;
								}
							}
							else
							{
								if(fire_position.x < targetx)
								{
									fire_position.x += 22;
								}
								else if(fire_position.x == targetx)//����Ŀ��ص�
								{
									new AePlayWave("explode.wav").start();
									obstacle_label[target_ptr].setVisible(false);
									obstacle[target.x].barrier[target.y].x = 1500;
									obstacle[target.x].barrier[target.y].y = 1500;
									fire_label.setVisible(false);
									
									startfire = false;
								}
							}
						}
						if(running == "U")
						{
							if(target.x == -1 && target.y == -1)//������ǰ��·��û���ϰ���
							{
								if(fire_position.y > -30)
								{
									fire_position.y -= 22;
								}
								else
								{
									startfire = false;
								}
							}
							else
							{
								if(fire_position.y > targety)
								{
									fire_position.y -= 22;
								}
								else if(fire_position.y == targety)//����Ŀ��ص�
								{
									new AePlayWave("explode.wav").start();
									obstacle_label[target_ptr].setVisible(false);
									obstacle[target.x].barrier[target.y].x = 1500;
									obstacle[target.x].barrier[target.y].y = 1500;
									fire_label.setVisible(false);
									
									startfire = false;
								}
							}
						}
						if(running == "D")
						{
							if(target.x == -1 && target.y == -1)//������ǰ��·��û���ϰ���
							{
								if(fire_position.y < 550)//�Ƶ���Ļ�⼴��
								{
									fire_position.y += 22;
								}
								else
								{
									startfire = false;
								}
							}
							else
							{
								if(fire_position.y < targety)
								{
									fire_position.y += 22;
								}
								else if(fire_position.y == targety)//����Ŀ��ص�
								{
									new AePlayWave("explode.wav").start();
									obstacle_label[target_ptr].setVisible(false);
									obstacle[target.x].barrier[target.y].x = 1500;
									obstacle[target.x].barrier[target.y].y = 1500;
									fire_label.setVisible(false);
									
									startfire = false;
								}
							}
						}
						
						fire_label.setBounds(fire_position.x, fire_position.y, 20, 20);
						
						repaint();
					}
				}
			}
		};
		
		run3.start();
	}
	
	public void Reset(){
		startfire = false;
		fire_label.setVisible(false);
		hit_barrier = false;
		hit_flag = false;
		remove(food_label);//ȥ�����Ե���ʳ��
		score = 0;
		Score.setText("0");
		//��ʼ��ͷ������
	    ProduceRandom();
	    
	    
	    head = new Tile(randomx,randomy);
	    //��ʼ������ڵ㲿����
	    for(int jj = 0; jj < MAX_SIZE;jj++)
		{
			body[jj].x = 0;
			body[jj].y = 0;
		}
	    
	    for(int i = 0;i < 40;i++)
	    {
	    	body_label[i].setVisible(true);
	    }
	    
	    for(int k = 0;k < body_length;k++)
	    {
	    	remove(body_label[k]);
	    }
	    
	    for(int k = 0;k < brick_amount;k++)
	    {
	    	remove(obstacle_label[k]);
	    }
	    
		hour =0;
		min =0;
		sec =0 ;
		direction = "R";//Ĭ��������
		current_direction = "R";//��ǰ����
		first_launch = false;
		iseaten = false;
		isrun = true;
		pause = false;
		millis = normal_speed;//ÿ��normal_speed����ˢ��һ��
		speed = true;
		body_length = 0;
		Length.setText("1");
		
		countsecond = 5;
		Time2.setText("5");
		ifcount = true;
		bullet = 0;
		Weapon.setText("0");
		
		System.out.println("Start again");
	}
	
	public Tile Search(Tile here,String direction){//Ѱ�ҴӴ˴���������direction�����ϵ���˴������ש��
		Tile res = new Tile(-1,-1);
		int gap = 10000;//����������������С����������֮��ľ������
		for(int i = 0;i < 5;i++)
		{
			for(int j = 0;j < obstacle[i].length;j++)
			{
				if(direction == "L")
				{
					if(obstacle[i].barrier[j].y == here.y && obstacle[i].barrier[j].x < here.x)
					{
						if(gap > (here.x - obstacle[i].barrier[j].x))//ˢ����С���
						{
							gap = here.x - obstacle[i].barrier[j].x;
//							res = obstacle[i].barrier[j];
							res.x = i;
							res.y = j;
						}
					}
				}
				if(direction == "R")
				{
					if(obstacle[i].barrier[j].y == here.y && obstacle[i].barrier[j].x > here.x)
					{
						if(gap > (obstacle[i].barrier[j].x - here.x))//ˢ����С���
						{
							gap = obstacle[i].barrier[j].x - here.x;
//							res = obstacle[i].barrier[j];
							res.x = i;
							res.y = j;
						}
					}
				}
				if(direction == "U")
				{
					if(obstacle[i].barrier[j].x == here.x && obstacle[i].barrier[j].y < here.y)
					{
						if(gap > (here.y - obstacle[i].barrier[j].y))//ˢ����С���
						{
							gap = here.y - obstacle[i].barrier[j].y;
//							res = obstacle[i].barrier[j];
							res.x = i;
							res.y = j;
						}
					}
				}
				if(direction == "D")
				{
					if(obstacle[i].barrier[j].x == here.x && obstacle[i].barrier[j].y > here.y)
					{
						if(gap > (obstacle[i].barrier[j].y - here.y))//ˢ����С���
						{
							gap = obstacle[i].barrier[j].y - here.y;
//							res = obstacle[i].barrier[j];
							res.x = i;
							res.y = j;
						}
					}
				}
			}
		}
		return res;
	}
	
	//����ʱ��
	class Countdown extends Thread{
	    public Countdown(){
	        this.start();
	    }
	    
	    @SuppressWarnings("deprecation")
		public void run() {
	    	while(true){
	    		if(ifcount)
		    	{
		    		if(countsecond >= 1)//2̫������
			    	{
			    		countsecond --;
			    		Time2.setText("" + countsecond);
			    	}
			    	else
			    	{
			    		ifcount = false;
			    		countsecond = 5;
			    		Time2.setText("5");
			    		remove(food_label);
			    		if(foodtag == 1 || foodtag == 5)//��ֵ��ߵ�����ʳ���ڹ涨ʱ����û���Ե�����ʧ
			    		{
			    			ProduceFood();
			    		}
			    		ProduceRandom();
			    		add(food_label);
			    		ifcount = true;
			    	}
		    	}
	    		
	    		try {
	    			Thread.sleep(1000);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	    	}
	    }
	}
	
	//��ʱ����
	class Timer extends Thread{  
		    public Timer(){
		        this.start();
		    }
		    @Override
		    public void run() {
		        while(true){
		            if(isrun){
		                sec +=1 ;
		                if(sec >= 60){
		                    sec = 0;
		                    min +=1 ;
		                }
		                if(min>=60){
		                    min=0;
		                    hour+=1;
		                }
		                showTime();
		            }
		 
		            try {
		                Thread.sleep(1000);
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		             
		        }
		    }

		    private void showTime(){
		        String strTime ="" ;
		        if(hour < 10)
		            strTime = "0"+hour+":";
		        else
		            strTime = ""+hour+":";
		         
		        if(min < 10)
		            strTime = strTime+"0"+min+":";
		        else
		            strTime =strTime+ ""+min+":";
		         
		        if(sec < 10)
		            strTime = strTime+"0"+sec;
		        else
		            strTime = strTime+""+sec;
		         
		        //�ڴ�����������ʾʱ��
		        Time.setText(strTime);
		    }
		}	
}

class AePlayWave extends Thread { 	 
    private String filename;
    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb 

    public AePlayWave(String wavfile) { 
        filename = wavfile;
    } 
    	    
    public void run() { 
        File soundFile = new File(filename); 
        AudioInputStream audioInputStream = null;
        try { 
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (UnsupportedAudioFileException e1) { 
            e1.printStackTrace();
            return;
        } catch (IOException e1) { 
            e1.printStackTrace();
            return;
        } 
 
        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
 
        try { 
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (LineUnavailableException e) { 
            e.printStackTrace();
            return;
        } catch (Exception e) { 
            e.printStackTrace();
            return;
        } 

        auline.start();
        int nBytesRead = 0;
        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
 
        try { 
            while (nBytesRead != -1) { 
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0) 
                    auline.write(abData, 0, nBytesRead);
            } 
        } catch (IOException e) { 
            e.printStackTrace();
            return;
        } finally { 
            auline.drain();
            auline.close();
        } 
    } 
}