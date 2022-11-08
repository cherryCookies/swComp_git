package DB;

import java.awt.*;// 그래픽 처리를 위한 클래스
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;              // Swing 컴포넌트 클래스
import javax.swing.filechooser.FileNameExtensionFilter;

import coordinate_calling.multiOutputStream;

public class begin_window extends JFrame {
	static String DB_NAME=null;
	static String Chromosome=null;
	static String fdirectory=null;
	static String TABLE_NAME="";
	int flag;
	static String fdirectory_final=null;
	static String DB_NAME_final=null;
	static String Chromosome_final=null;
	static String TABLE_NAME_final=null;
	
	public begin_window() {
		JTextArea ta;
		JLabel lb1, lb2;
		JComboBox cb1,cb3;
		JComboBox jl1;
		JButton openButton, saveButton,jb1;
		JButton close;
		JButton jButton;
		String[] TABLE_LIST;

       setSize(300,250);
       Container c=getContentPane();
       c.setLayout(new GridLayout(0,2,5,5));
       
		setTitle("TRIMEZ");
		///setLayout(new FlowLayout());
      
		String[] DB_LIST = DB_connect2.DBUse();
		cb1 = new JComboBox(DB_LIST);
		jl1 = new JComboBox();
		cb3 = new JComboBox();
		jb1=new JButton("Click to Search");
		close=new JButton("Start");
		flag=0;

///종 이름 선택하는 코드

		cb1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
	            JComboBox comboBox = (JComboBox) e.getSource();
	            Object o = comboBox.getSelectedItem();
	            DB_NAME = cb1.getSelectedItem().toString();
	            String[] TABLE_LIST = DB_connect2.DBUse(DB_NAME);
	            
	            jl1.removeAllItems();
	            for (int i=0;i<TABLE_LIST.length;i++) {
	            	jl1.addItem(TABLE_LIST[i]);
	            }
		        	///JComboBox comboBox2 = (JComboBox) e.getSource();
		        	///Object o2 = comboBox.getSelectedItem();
		        String[] column_content = DB_connect2.DBUse(DB_NAME, "chromInfo","chrom");
		        ///column_content=column_content.split("\t\t");
		        cb3.removeAllItems();
		        for (int i=0;i<column_content.length;i++) {
			          cb3.addItem(column_content[i]);
		          }
		        }
			});

///track 이름 선택하는 코드
		jl1.addActionListener (new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cb1.getSelectedItem()!=null) {
		            JComboBox comboBox = (JComboBox) e.getSource();
		            Object o = comboBox.getSelectedItem();
		            TABLE_NAME = jl1.getSelectedItem().toString();
		            //if (TABLE_NAME)
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
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						JFileChooser fc=new JFileChooser();
						///FileNameExtensionFilter fliter=new FileNameExtensionFilter("txt");
						///fc.setFileFilter(fliter);
						int ret=fc.showOpenDialog(null);
						if(ret!=JFileChooser.APPROVE_OPTION) {
							JOptionPane.showMessageDialog(null,"파일을 선택하세요","경고",JOptionPane.ERROR_MESSAGE);
						}else {
							jb1.setText("선택 완료");
							fdirectory=fc.getSelectedFile().getPath().toString();
						}
					}
				});
				pack();
            }
		});
		close.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
            	///System.out.println(fdirectory.toString());  		
            		String fdirectory_final=fdirectory;
            		String DB_NAME_final=DB_NAME;
            		String Chromosome_final=Chromosome;
            		String TABLE_NAME_final=TABLE_NAME;
            		setDefaultCloseOperation(EXIT_ON_CLOSE);
            		dispose();
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
		c.add(jl1);
		c.add(new JLabel("Target Seq: "));
		c.add(jb1);
		c.add(close);
		///add(logScrollPane);
		setVisible(true);
	}
	
	/*
	public String getDirectory() {///System.out.println(fdirectory);
		String fdirectory_final=fdirectory;
		System.out.println(fdirectory_final);
		return fdirectory_final;
	}
	public String getDbName() {
		String DB_NAME_final=DB_NAME;
		System.out.println(DB_NAME_final);
		return DB_NAME_final;
		}
	public String getChrNme() {
		String Chromosome_final=Chromosome;
		System.out.println(Chromosome_final);
		return Chromosome_final;
		}
	  */
	//window에 padding주는 것
	@Override
	public Insets getInsets() {
		//Insets(int top,int left,int bottom,int right) 
		Insets in = new Insets(60,20,40,20);
		return in;
	}
	
	public static String[] main() {
		begin_window p = new begin_window();
		while(fdirectory_final==null || DB_NAME_final==null || Chromosome_final==null||TABLE_NAME_final==null) {
			fdirectory_final=fdirectory;
			DB_NAME_final = DB_NAME;
			Chromosome_final = Chromosome;
			TABLE_NAME_final=TABLE_NAME;
	    	///System.out.println("Hello--------------");
		}
		if(fdirectory_final!=null && DB_NAME_final!=null && Chromosome_final!=null && TABLE_NAME_final!=null)
			return new String[] {fdirectory_final,DB_NAME_final,Chromosome_final,TABLE_NAME_final};
		return null;
	}
}