package map;

import java.awt.TextField;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * 地図情報
 */
public class WinInfo extends JDialog implements ActionListener{

  // ラベル
  private TextField TX_Info_title;
  private TextField TX_Info_code;
  private TextField TX_Info_scale;
  private TextField TX_Info_year_org;
  private TextField TX_Info_year_rep;
  private TextField TX_Info_year_num;
  private TextField TX_Info_latitude_l;
  private TextField TX_Info_longitude_l;
  private TextField TX_Info_latitude_r;
  private TextField TX_Info_longitude_r;

  /**
   * コンストラクタ
   */
  public WinInfo(Frame parent, String info[]){

    super(parent);

    try{
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      init(info);
    }
    catch(Exception exception){
      exception.printStackTrace();
    }
  }

  /**
   * 初期化
   */
   private void init(String info[]) throws Exception{

    // Window
    setTitle("ファイル情報");
    setResizable(false);

    JPanel content = (JPanel) getContentPane();
    content.setLayout(null);

    // 地図情報
    infoLayout(content);

    // 地図情報のセット
    infoSet(info);
  }

  /**
   * 地図情報
   */
  private void infoLayout(JPanel content){

    // オブジェ - ラベル
    Label LB_Info_title;
    Label LB_Info_code;
    Label LB_Info_scale;
    Label LB_Info_year_org;
    Label LB_Info_year_rep;
    Label LB_Info_year_num;
    Label LB_Info_latitude_l;
    Label LB_Info_longitude_l;
    Label LB_Info_latitude_r;
    Label LB_Info_longitude_r;

    int LB_Info_x = 5;
    int LB_Info_y = 5;
    int LB_Info_w = 90;
    int LB_Info_h = 20;
    int LB_Info_s = 1;

    int TX_Info_w = 70;
    int TX_Info_h = 18;

    // オブジェ - ラベル - 図名
    LB_Info_title = new Label("図名");
    LB_Info_title.setBounds(LB_Info_x, LB_Info_y, LB_Info_w, LB_Info_h);
    content.add(LB_Info_title);
    TX_Info_title = new TextField();
    TX_Info_title.setBounds(LB_Info_x + LB_Info_w, LB_Info_y, TX_Info_w, TX_Info_h);
    content.add(TX_Info_title);

    // オブジェ - ラベル - メッシュコード
    LB_Info_code = new Label("メッシュコード");
    LB_Info_code.setBounds(LB_Info_x, LB_Info_y, LB_Info_w, LB_Info_h);
    content.add(LB_Info_code);
    TX_Info_code = new TextField();
    TX_Info_code.setBounds(LB_Info_x + LB_Info_w, LB_Info_y, TX_Info_w, TX_Info_h);
    content.add(TX_Info_code);

    // オブジェ - ラベル - 原図の縮尺
    LB_Info_y += LB_Info_h + LB_Info_s;
    LB_Info_scale = new Label("原図の縮尺");
    LB_Info_scale.setBounds(LB_Info_x, LB_Info_y, LB_Info_w, LB_Info_h);
    content.add(LB_Info_scale);
    TX_Info_scale = new TextField();
    TX_Info_scale.setBounds(LB_Info_x + LB_Info_w, LB_Info_y, TX_Info_w, TX_Info_h);
    content.add(TX_Info_scale);

    // オブジェ - ラベル - 原図測量年紀
    LB_Info_y += LB_Info_h + LB_Info_s;
    LB_Info_year_org = new Label("原図測量年紀");
    LB_Info_year_org.setBounds(LB_Info_x, LB_Info_y, LB_Info_w, LB_Info_h);
    content.add(LB_Info_year_org);
    TX_Info_year_org = new TextField();
    TX_Info_year_org.setBounds(LB_Info_x + LB_Info_w, LB_Info_y, TX_Info_w, TX_Info_h);
    content.add(TX_Info_year_org);

    // オブジェ - ラベル - 原図修正年紀
    LB_Info_y += LB_Info_h + LB_Info_s;
    LB_Info_year_rep = new Label("原図修正年紀");
    LB_Info_year_rep.setBounds(LB_Info_x, LB_Info_y, LB_Info_w, LB_Info_h);
    content.add(LB_Info_year_rep);
    TX_Info_year_rep = new TextField();
    TX_Info_year_rep.setBounds(LB_Info_x + LB_Info_w, LB_Info_y, TX_Info_w, TX_Info_h);
    content.add(TX_Info_year_rep);

    // オブジェ - ラベル - 数値化年紀
    LB_Info_y += LB_Info_h + LB_Info_s;
    LB_Info_year_num = new Label("数値化年紀");
    LB_Info_year_num.setBounds(LB_Info_x, LB_Info_y, LB_Info_w, LB_Info_h);
    content.add(LB_Info_year_num);
    TX_Info_year_num = new TextField();
    TX_Info_year_num.setBounds(LB_Info_x + LB_Info_w, LB_Info_y, TX_Info_w, TX_Info_h);
    content.add(TX_Info_year_num);

    // オブジェ - ラベル - 区画左下の緯度
    LB_Info_y += LB_Info_h + LB_Info_s;
    LB_Info_latitude_l = new Label("区画左下の緯度");
    LB_Info_latitude_l.setBounds(LB_Info_x, LB_Info_y, LB_Info_w, LB_Info_h);
    content.add(LB_Info_latitude_l);
    TX_Info_latitude_l = new TextField();
    TX_Info_latitude_l.setBounds(LB_Info_x + LB_Info_w, LB_Info_y, TX_Info_w, TX_Info_h);
    content.add(TX_Info_latitude_l);

    // オブジェ - ラベル - 区画左下の経度
    LB_Info_y += LB_Info_h + LB_Info_s;
    LB_Info_longitude_l = new Label("区画左下の経度");
    LB_Info_longitude_l.setBounds(LB_Info_x, LB_Info_y, LB_Info_w, LB_Info_h);
    content.add(LB_Info_longitude_l);
    TX_Info_longitude_l = new TextField();
    TX_Info_longitude_l.setBounds(LB_Info_x + LB_Info_w, LB_Info_y, TX_Info_w, TX_Info_h);
    content.add(TX_Info_longitude_l);

    // オブジェ - ラベル - 区画右上の緯度
    LB_Info_y += LB_Info_h + LB_Info_s;
    LB_Info_latitude_r = new Label("区画右上の緯度");
    LB_Info_latitude_r.setBounds(LB_Info_x, LB_Info_y, LB_Info_w, LB_Info_h);
    content.add(LB_Info_latitude_r);
    TX_Info_latitude_r = new TextField();
    TX_Info_latitude_r.setBounds(LB_Info_x + LB_Info_w, LB_Info_y, TX_Info_w, TX_Info_h);
    content.add(TX_Info_latitude_r);

    // オブジェ - ラベル - 区画右上の経度
    LB_Info_y += LB_Info_h + LB_Info_s;
    LB_Info_longitude_r = new Label("区画右上の経度");
    LB_Info_longitude_r.setBounds(LB_Info_x, LB_Info_y, LB_Info_w, LB_Info_h);
    content.add(LB_Info_longitude_r);
    TX_Info_longitude_r = new TextField();
    TX_Info_longitude_r.setBounds(LB_Info_x + LB_Info_w, LB_Info_y, TX_Info_w, TX_Info_h);
    content.add(TX_Info_longitude_r);
  }

  /**
   * 地図情報のセット
   */
  private void infoSet(String info[]){

    /* ヘッダ　確認表示
     String info = "50m 数値地図ヘッダ\n";
     info += "メッシュコード：" + info[0] + "\n";
     info += "原図の縮尺：1/" + info[1] + "\n";
     info += "原図測量年紀：" + info[2] + "年\n";
     info += "原図修正年紀：" + info[3] + "年\n";
     info += "数値化年紀：" + info[4] + "年\n";
     info += "東西方向の点数：" + info[5] + "\n";
     info += "南北方向の点数：" + info[6] + "\n";
     info += "区画左下の緯度：" + info[7]  + "\"" + info[8]  + "'" + info[9]  + "\n";
     info += "区画左下の経度：" + info[10] + "\"" + info[11] + "'" + info[12] + "\n";
     info += "区画右上の緯度：" + info[13] + "\"" + info[14] + "'" + info[15] + "\n";
     info += "区画右上の経度：" + info[16] + "\"" + info[17] + "'" + info[18] + "\n";
     info += "図葉面数：" + info[19] + "\n";
     info += "１番目の図名：" + info[20] + "[" + info[21] + "]\n";
     info += "２番目の図名：" + info[22] + "[" + info[23] + "]\n";
     info += "３番目の図名：" + info[24] + "[" + info[25] + "]\n";
     info += "４番目の図名：" + info[26] + "[" + info[27] + "]\n";
     info += "記録レコード数：" + info[28] + "\n";
     info += "コメント：" + info[29] + "\n";
     System.out.println(info);
     */

    String title = info[20];
    if(info[19] == "2")
      title += "," + info[22];
    else if(info[19] == "3")
      title += "," + info[24];
    else if(info[19] == "3")
      title += "," + info[26];

    TX_Info_title.setText(info[20]);
    TX_Info_code.setText(info[0]);
    TX_Info_scale.setText("1:" + info[1]);
    TX_Info_year_org.setText(info[2] + "年");
    TX_Info_year_rep.setText(info[3] + "年");
    TX_Info_year_num.setText(info[4] + "年");
    TX_Info_latitude_l.setText(info[7]  + "\"" + info[8]  + "'" + info[9]);
    TX_Info_longitude_l.setText(info[10] + "\"" + info[11] + "'" + info[12]);
    TX_Info_latitude_r.setText(info[13] + "\"" + info[14] + "'" + info[15]);
    TX_Info_longitude_r.setText(info[16] + "\"" + info[17] + "'" + info[18]);
  }

  /**
   *  イベント - ダイアログを閉じる
   */
  public void actionPerformed(ActionEvent actionEvent){

/*
    if(actionEvent.getSource() == BTok){
      dispose();
    }
 */
  }
}
