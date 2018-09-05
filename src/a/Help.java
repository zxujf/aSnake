package a;

import java.awt.*;
import javax.swing.*;

public class Help extends JDialog {
    private JPanel contentPane;
    private Font f = new Font("΢���ź�",Font.PLAIN,15);
    private JScrollPane scroll;
	
    public Help() {
        setTitle("��Ϸ����˵��");//���ô������
        Image img=Toolkit.getDefaultToolkit().getImage("title.png");//����ͼ��
        setIconImage(img);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModal(true);//����Ϊģ̬����
        setSize(410,380);
        setResizable(false);
        setLocationRelativeTo(null);
        contentPane = new JPanel();// �����������
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        ShadePanel shadePanel = new ShadePanel();// �������䱳�����
        contentPane.add(shadePanel, BorderLayout.CENTER);// �����嵽�����������
        shadePanel.setLayout(null);
        
        JTextArea J1 = new JTextArea("        ���ͨ�������ϵķ���������������ߵķ��򣬳������Լ��١�����Ϸ������򰴡�Esc��������ֱ�����¿�ʼ��Ϸ�����ո������ʵ����ͣ�Ϳ�ʼ��"
        		+ "��Ϸ�п�ͨ������˵����ı���Ϸ����ͼƬ���ߵ������Լ�ͷ������ͼ�������Թرջ���ʾ����͸ı����ƶ����ٶȡ�"
        		+ "�ߵ��ƶ��ٶ�Ĭ���ǡ����ܡ����ɸĳɡ����١��������ߡ��򡰷�񡱣�����߻򽵵��ѶȵĻ����Գ��ԡ�"
        		+ "����Ϸ����Ĳ������״̬����ʾ�ߵĵ�ǰ���ȣ��÷֣���Ϸ������ʱ�䣬ʳ�����ˢ�µ�ʱ�䣬ʣ���ӵ��ȡ�"
        		+ "��Ϸ��������ˢ�����ָ�����ʳ��Ե���õ��ĵ÷�Ҳ��һ��������������ڹ涨��ʱ���ڳԵ��Ļ���������ˢ�¡�"
        		+ "������ʳ��Ļ�Ҫѡ�����·�߲�����Ӧ�ü��١�"
        		+ "��Ϸ�м�����ײ��ȥ��������ǽ��ǽ��������ķ�λˢ�£���ҪС�Ķ�ܡ�"
        		+ "���ڼ���ԭ�򣬻�����п�����ǰ�ͻ����ǽ��״����������Ҳ�����ǽͻȻˢ������ǰ��״������С�Ķ�ܡ�"
        		+ "��Ϸ���ܳԵ�ǹģ���ĵ��ߣ��ܵõ�ֻ��һ���ӵ��ĵ���ǹ��"
        		+ "����ǹ�ܰ���Shift�������ӵ��ƻ���ǰ��ǽ������ǰû��ǽ���ӵ����ɳ��߽硣�ӵ������ۼӣ����ӵ��������䣬ֻ�ܵ�ǰһ���������䡣"
        		+ "����ǹС���ʳ��֣���������ϧ��");
        J1.setFocusable(false);
    	J1.setFont(f);
    	J1.setEditable(false);
    	J1.setOpaque(false);//����͸��
    	J1.setLineWrap(true);
    	
    	scroll = new JScrollPane(J1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    	scroll.setBorder(BorderFactory.createTitledBorder("How to play"));
    	scroll.setOpaque(false);
    	scroll.getViewport().setOpaque(false);//JScrollPane���ó�͸���������һ��
    	shadePanel.add(scroll);
    	scroll.setBounds(10, 10, 385, 330);
    	
    	setVisible(true);
    }
    
    public static void main(String[] args) {
		new Help();
	}
}