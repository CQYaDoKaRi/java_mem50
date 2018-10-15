package map;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** About クラス
 *  - 情報
 */
public class WinAbout extends JDialog implements ActionListener{

  JButton BTok = new JButton();

  /**
   * コンストラクタ
   */
  public WinAbout(Frame parent){
    super(parent);
    try{
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      init();
    }
    catch(Exception exception){
      exception.printStackTrace();
    }
  }

  /**
   * 初期化
   */
  private void init() throws Exception{

    JPanel content = new JPanel();

    // contentdow - タイトル
    setTitle("バージョン情報");
    // contentdow - サイズ
    setResizable(true);
    // contentdow - 内部
    content.setLayout(new BorderLayout());

    // contentdow - 内部 - イメージアイコン
    JPanel    PLimage = new JPanel();
    JLabel    LBimage = new JLabel();
    ImageIcon icon    = new ImageIcon(map.Menu.class.getResource("icon_about.png"));

    LBimage.setIcon(icon);
    PLimage.setLayout(new FlowLayout());
    PLimage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    PLimage.add(LBimage, null);
    content.add(PLimage, BorderLayout.WEST);

    // contentdow - 内部 - 情報
    JPanel PLinfo   = new JPanel();
    JLabel LBapp    = new JLabel();
    JLabel LBver    = new JLabel();
    JLabel LBcright = new JLabel();

    LBapp.setText("数値地図");
    LBver.setText("0.0.0.1");
    LBcright.setText("Takashi Inada");

    PLinfo.setLayout(new GridLayout(4, 1));
    PLinfo.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 10));
    PLinfo.add(LBapp   , null);
    PLinfo.add(LBver   , null);
    PLinfo.add(LBcright, null);
    content.add(PLinfo, BorderLayout.CENTER);

    // contentdow - 内部 - ボタン
    JPanel PLbt = new JPanel();

    BTok.setText("OK");
    BTok.addActionListener(this);

    PLbt.setLayout(new FlowLayout());
    PLbt.add(BTok, null);
    content.add(PLbt, BorderLayout.SOUTH);

    getContentPane().add(content, null);
  }

  /**
   *  イベント - ダイアログを閉じる
   */
  public void actionPerformed(ActionEvent actionEvent){
    if(actionEvent.getSource() == BTok){
      dispose();
    }
  }

}
