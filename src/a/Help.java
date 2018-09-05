package a;

import java.awt.*;
import javax.swing.*;

public class Help extends JDialog {
    private JPanel contentPane;
    private Font f = new Font("微软雅黑",Font.PLAIN,15);
    private JScrollPane scroll;
	
    public Help() {
        setTitle("游戏规则说明");//设置窗体标题
        Image img=Toolkit.getDefaultToolkit().getImage("title.png");//窗口图标
        setIconImage(img);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModal(true);//设置为模态窗口
        setSize(410,380);
        setResizable(false);
        setLocationRelativeTo(null);
        contentPane = new JPanel();// 创建内容面板
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        ShadePanel shadePanel = new ShadePanel();// 创建渐变背景面板
        contentPane.add(shadePanel, BorderLayout.CENTER);// 添加面板到窗体内容面板
        shadePanel.setLayout(null);
        
        JTextArea J1 = new JTextArea("        玩家通过键盘上的方向键来控制蛇行走的方向，长按可以加速。在游戏结束后或按“Esc”键可以直接重新开始游戏，按空格键可以实现暂停和开始。"
        		+ "游戏中可通过点击菜单来改变游戏背景图片、蛇的身体以及头部的贴图，还可以关闭或显示网格和改变蛇移动的速度。"
        		+ "蛇的移动速度默认是“奔跑”，可改成“龟速”、“行走”或“疯狂”，想提高或降低难度的话可以尝试。"
        		+ "在游戏界面的侧边栏中状态区显示蛇的当前长度，得分，游戏所花的时间，食物随机刷新的时间，剩余子弹等。"
        		+ "游戏里面会随机刷出各种各样的食物，吃到后得到的得分也不一样，但玩家若不在规定的时间内吃到的话将会重新刷新。"
        		+ "因此想吃食物的话要选择最短路线并合理应用加速。"
        		+ "游戏中加入了撞上去会死亡的墙，墙会以随机的方位刷新，需要小心躲避。"
        		+ "由于技术原因，或许会有开局面前就会出现墙的状况，行走中也会出现墙突然刷新在蛇前的状况，需小心躲避。"
        		+ "游戏中能吃到枪模样的道具，能得到只有一颗子弹的道具枪。"
        		+ "道具枪能按“Shift”发射子弹破坏蛇前的墙，若蛇前没有墙，子弹将飞出边界。子弹数可累加，但子弹不能连射，只能等前一个射完再射。"
        		+ "道具枪小几率出现，且用且珍惜。");
        J1.setFocusable(false);
    	J1.setFont(f);
    	J1.setEditable(false);
    	J1.setOpaque(false);//背景透明
    	J1.setLineWrap(true);
    	
    	scroll = new JScrollPane(J1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    	scroll.setBorder(BorderFactory.createTitledBorder("How to play"));
    	scroll.setOpaque(false);
    	scroll.getViewport().setOpaque(false);//JScrollPane设置成透明需加上这一行
    	shadePanel.add(scroll);
    	scroll.setBounds(10, 10, 385, 330);
    	
    	setVisible(true);
    }
    
    public static void main(String[] args) {
		new Help();
	}
}