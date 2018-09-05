package a;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class ShadePanel extends JPanel {   
    public ShadePanel() {
        super();
        setLayout(null);
    }
    
    @Override
    protected void paintComponent(Graphics g1) {// ��д����������
        Graphics2D g = (Graphics2D) g1;
        super.paintComponent(g);// ִ�г��෽��
        int width = getWidth();// ��ȡ�����С
        int height = getHeight();
        // �������ģʽ����
        GradientPaint paint = new GradientPaint(0, 0, Color.CYAN, 0, height,Color.MAGENTA);//ʵ����ɫ����
        //GradientPaint paint = new GradientPaint(0, 0, Color.red, 0, height,Color.blue);
        g.setPaint(paint);// ���û�ͼ��������ģʽ
        g.fillRect(0, 0, width, height);// ���ƾ������ؼ�����
    }
}
 
public class About extends JDialog {
    private JPanel contentPane;
    private Font f1 = new Font("΢���ź�",Font.PLAIN,15);
	private Font f2 = new Font("΢���ź�",Font.PLAIN,20);
	private ImageIcon icon;
	private JLabel label;
    public About() {
        setTitle("����");//���ô������
        Image img=Toolkit.getDefaultToolkit().getImage("title.png");//����ͼ��
        setIconImage(img);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModal(true);//����Ϊģ̬����
        setSize(410,165);
        setResizable(false);
        setLocationRelativeTo(null);
        contentPane = new JPanel();// �����������
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        ShadePanel shadePanel = new ShadePanel();// �������䱳�����
        contentPane.add(shadePanel, BorderLayout.CENTER);// �����嵽�����������
        shadePanel.setLayout(null);
        
        JTextArea J1 = new JTextArea("�汾��2018.3_Demo\n�������ԣ�Java\n"
    			+ "Email: 276302834@qq.com\n"+"For: ̰������Ϸ�������ʵ��");
        J1.setFocusable(false);
    	J1.setFont(f2);
    	J1.setEditable(false);
    	J1.setOpaque(false);//����͸��
    	shadePanel.add(J1);
    	J1.setBounds(10, 10, 400, 180);
    	icon = new ImageIcon("title.png");
    	icon.setImage(icon.getImage().getScaledInstance(120,120,Image.SCALE_SMOOTH));//����ͼƬ������
    	label = new JLabel(icon);
    	shadePanel.add(label);
    	label.setBounds(270, 0, 130, 130);
    	setVisible(true);
    }
    
    public static void main(String[] args) {
		new About();
	}
}

class InternetMonitor extends MouseAdapter{
	public void mouseClicked(MouseEvent e){
		JLabel JLabel_temp = (JLabel)e.getSource();
		String J_temp = JLabel_temp.getText();
		System.out.println(J_temp);
		URI uri ;
			try {
				uri = new URI(J_temp);
				Desktop desk=Desktop.getDesktop();
				if(Desktop.isDesktopSupported() && desk.isSupported(Desktop.Action.BROWSE)){
					try {
						desk.browse(uri);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
	}
	public void mouseEntered(MouseEvent e){
		JLabel JLabel_temp = (JLabel)e.getSource();
		JLabel_temp.setForeground(Color.red);
	}
	public void mouseExited(MouseEvent e){
		JLabel JLabel_temp = (JLabel)e.getSource();
		JLabel_temp.setForeground(Color.blue);
	}
}
