package DB;

import java.awt.*;// 그래픽 처리를 위한 클래스
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;              // Swing 컴포넌트 클래스
import javax.swing.filechooser.FileNameExtensionFilter;

public class begin_window extends JFrame {
	///JTextField tf1, tf2;
	public String fdirectory;
	JTextArea ta;
	JLabel lb1, lb2;
	JComboBox cb1, cb2, cb3;
	JButton openButton, saveButton,jb1, close;
	///JScrollPane logScrollPane;
	///JFrame frame;
	JButton jButton;
	public String DB_NAME="";
	String fpath;
	String TABLE_NAME;
	public String Chromosome;
	String[] TABLE_LIST;
	
	public begin_window() {
       setSize(300,250);
       Container c=getContentPane();
       c.setLayout(new GridLayout(0,2,5,5));
       
		setTitle("TRIMEZ");
		///setLayout(new FlowLayout());
      
		String[] DB_LIST = DB_connect2.DBUse();
		cb1 = new JComboBox(DB_LIST);
		cb2 = new JComboBox();
		cb3 = new JComboBox();
		jb1=new JButton("Click to Search");
		close=new JButton("Start");

///종 이름 선택하는 코드
		cb1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
	            JComboBox comboBox = (JComboBox) e.getSource();
	            Object o = comboBox.getSelectedItem();
	            DB_NAME = cb1.getSelectedItem().toString();
	            String[] TABLE_LIST = DB_connect2.DBUse(DB_NAME);
	            
	            cb2.removeAllItems();
	            for (int i=0;i<TABLE_LIST.length;i++) {
		            cb2.addItem(TABLE_LIST[i]);
	            }
	            
		        	///JComboBox comboBox2 = (JComboBox) e.getSource();
		        	///Object o2 = comboBox.getSelectedItem();
		        String[] column_content = DB_connect2.DBUse(DB_NAME, "chromInfo","chrom");
		        ///String[] TABLE_ROW=column_content.split("\t");
		        cb3.removeAllItems();
		        for (int i=0;i<column_content.length;i++) {
			          cb3.addItem(column_content[i]);
		          }
		        }
			});

///track 이름 선택하는 코드
		cb2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cb1.getSelectedItem()!=null) {
		            JComboBox comboBox = (JComboBox) e.getSource();
		            Object o = comboBox.getSelectedItem();
		            TABLE_NAME = cb2.getSelectedItem().toString();
		            String[] TABLE_ROW = DB_connect2.DBUse(DB_NAME, TABLE_NAME,"*");
		    		///ta.removeAll();
		    		///ta.append(DB_NAME);
		    		///ta.append(TABLE_NAME); 
				}
			}
	    }); 
		cb3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox comboBox = (JComboBox) e.getSource();
				Object o = comboBox.getSelectedItem();
				Chromosome= cb3.getSelectedItem().toString();
			}
	    }); 

		jb1.addActionListener(new ActionListener(){
			@Override
            public void actionPerformed(ActionEvent e) {
				//파일 찾는 윈도우를 통해서 파일 이름과 경로를 추출
					///FileDialog fd=new FileDialog((Frame) c, "파일 열기", FileDialog.LOAD);
					JFileChooser fc=new JFileChooser();
					///FileNameExtensionFilter fliter=new FileNameExtensionFilter("txt");
					///fc.setFileFilter(fliter);
					int ret=fc.showOpenDialog(null);
					if(ret!=JFileChooser.APPROVE_OPTION) {
						JOptionPane.showMessageDialog(null,"파일을 선택하세요","경고",JOptionPane.ERROR_MESSAGE);
					}
					fdirectory=fc.getSelectedFile().getPath();
					pack();
					jb1.setText("선택 완료");
            }
		});
		close.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
				dispose();
				return;
			}
		});
//DB_name하고 TABLE 잘 쿼리 됐는지 확인하려면 밑에 주석들 풀어서 textfield를 확인해보세요.
		///ta=new JTextArea(40,80);
		///logScrollPane = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


		c.add(new JLabel("Species: "));
		c.add(cb1);
		c.add(new JLabel("Chromosome: "));
		c.add(cb3);
		c.add(new JLabel("Track DB: "));
		c.add(cb2);
		c.add(new JLabel("Target Seq: "));
		c.add(jb1);
		c.add(close);
		///add(logScrollPane);
		
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
	//window에 padding주는 것
	@Override
	public Insets getInsets() {
		//Insets(int top,int left,int bottom,int right) 
		Insets in = new Insets(60,20,40,20);
		return in;
	}
	/*
    public static void main(String args[]) {
    	begin_window p = new begin_window();
    	///System.out.println(DB_NAME);
    	///System.out.println(fdirectory);
 */
}