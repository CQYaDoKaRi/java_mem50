package map;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

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
public class WinViewSetting extends JDialog{

  // 表示範囲 - データ
  private int VAL_nPolygonW50 = 200; // 最大値
  private int VAL_nPolygonH50 = 200; // 最大値
  private int VAL_PolygonW50; // 現在値
  private int VAL_PolygonH50; // 現在値
  // 表示範囲 - スクロールバー
  private JScrollBar SB_PolygonW50;
  private JScrollBar SB_PolygonH50;
  // 表示範囲 - 現在値表示
  private Label      LB_PolygonW50;
  private Label      LB_PolygonH50;
  private Label      LB_Polygon50;
  // オブジェ
  private JButton    BT_ok = new JButton();
  private JButton    BT_cn = new JButton();
  private String     BT = "";

  /**
   * コンストラクタ
   * arg
   *  settings[0] : 50m 東西方向へ描画する標高数
   *  settings[1] : 50m 南北方向へ描画する標高数
   */
  public WinViewSetting(Frame parent, String[] settings){

    super(parent);

    try{
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      init(settings);
    }
    catch(Exception exception){
      exception.printStackTrace();
    }
  }

  /**
   * 初期化
   */
  private void init(String[] settings) throws Exception{

    // Window
    setTitle("表示設定");
    setResizable(true);

    JPanel content = (JPanel) getContentPane();
    content.setLayout(null);

    // 設定
    VAL_PolygonW50 = Integer.parseInt(settings[0]);
    VAL_PolygonH50 = Integer.parseInt(settings[1]);

    // 設定情報 50m
    Set50(content);
  }

  /**
   * 地図情報
   */
  private void Set50(JPanel content){

    // オブジェ - ラベル
    Label LB_title_PolygonEW;
    Label LB_title_PolygonSN;
    Label LB_title_Polygon;

    int LB_x, LB_y, LB_w, LB_h;
    int LB_x_init = 10;
    int LB_y_init = 20;
    int LB_titleW = 65;
    int LB_titleH = 20;

    // オブジェ - タイトルボーダー
    TitledBorder BR_50 = new TitledBorder(new LineBorder(Color.BLACK), "50m メッシュ　設定", TitledBorder.LEFT, TitledBorder.TOP);
    content.setBorder(BR_50);

    // オブジェ - ポリゴン数 - 東西
    LB_x = LB_x_init;
    LB_y = LB_y_init;
    LB_w = LB_titleW;
    LB_h = LB_titleH;

    LB_title_PolygonEW = new Label("南北標高数");
    LB_title_PolygonEW.setBounds(LB_x, LB_y, LB_w, LB_h);
    content.add(LB_title_PolygonEW);
    LB_x += LB_w;
    LB_w = 200;
    SB_PolygonW50 = new JScrollBar(JScrollBar.HORIZONTAL, VAL_PolygonW50, 10, 10, VAL_nPolygonW50 + 10);
    SB_PolygonW50.setSize(LB_h, LB_h - 3);
    SB_PolygonW50.setBounds(LB_x, LB_y, LB_w, LB_h - 3);
    SB_PolygonW50.addAdjustmentListener(new WinViewSetting_PolygonEW50(this));
    content.add(SB_PolygonW50);
    LB_x += LB_w + 5;
    LB_w = 25;
    LB_h = 17;
    LB_PolygonW50 = new Label(String.valueOf(VAL_PolygonW50));
    LB_PolygonW50.setBounds(LB_x, LB_y, LB_w, LB_h);
    LB_PolygonW50.setAlignment(Label.RIGHT);
    LB_PolygonW50.setBackground(Color.WHITE);
    content.add(LB_PolygonW50);

    // オブジェ - ポリゴン数 - 南北範囲
    LB_y_init += LB_titleH;
    LB_x = LB_x_init;
    LB_y = LB_y_init + 3;
    LB_w = LB_titleW;
    LB_h = LB_titleH;
    LB_title_PolygonSN = new Label("南北標高数");
    LB_title_PolygonSN.setBounds(LB_x, LB_y, LB_w, LB_h);
    content.add(LB_title_PolygonSN);
    LB_x += LB_w;
    LB_w = 200;
    SB_PolygonH50 = new JScrollBar(JScrollBar.HORIZONTAL, VAL_PolygonH50, 10, 10, VAL_nPolygonH50 + 10);
    SB_PolygonH50.setSize(LB_h, LB_h - 3);
    SB_PolygonH50.setBounds(LB_x, LB_y, LB_w, LB_h - 3);
    SB_PolygonH50.addAdjustmentListener(new WinViewSetting_PolygonSN50(this));
    content.add(SB_PolygonH50);
    LB_x += LB_w + 5;
    LB_w = 25;
    LB_h = 17;
    LB_PolygonH50 = new Label(String.valueOf(VAL_PolygonH50));
    LB_PolygonH50.setBounds(LB_x, LB_y, LB_w, LB_h);
    LB_PolygonH50.setAlignment(Label.RIGHT);
    LB_PolygonH50.setBackground(Color.WHITE);
    content.add(LB_PolygonH50);

    // オブジェ - ポリゴン数
    LB_y_init += LB_titleH;
    LB_x = LB_x_init;
    LB_y = LB_y_init + 3;
    LB_w = LB_titleW;
    LB_h = LB_titleH;
    LB_title_Polygon = new Label("ポリゴン数");
    LB_title_Polygon.setBounds(LB_x, LB_y, LB_w, LB_h);
    content.add(LB_title_Polygon);
    LB_x += LB_w + 5;
    LB_w = 38;
    LB_h = 17;
    LB_Polygon50 = new Label(String.valueOf(VAL_PolygonW50 * VAL_PolygonH50));
    LB_Polygon50.setBounds(LB_x, LB_y, LB_w, LB_h);
    LB_Polygon50.setAlignment(Label.RIGHT);
    LB_Polygon50.setBackground(Color.WHITE);
    content.add(LB_Polygon50);

    // ボタン
    LB_x  = LB_x_init;
    LB_y  = 130;
    LB_w  = 100;
    LB_h  = 24;
    BT_ok.setText("OK");
    BT_ok.addActionListener(new WinViewSetting_OK(this));
    BT_ok.setBounds(LB_x, LB_y, LB_w, LB_h);
    content.add(BT_ok);

    LB_x += LB_w + 5;
    BT_cn.setText("キャンセル");
    BT_cn.addActionListener(new WinViewSetting_CN(this));
    BT_cn.setBounds(LB_x, LB_y, LB_w, LB_h);
    content.add(BT_cn);
  }

  /**
   * データ取得 - 表示範囲 - 東西
   */
  public int getPolygonW50(){

    return VAL_PolygonW50;
  }

  /**
   * データ取得 - 表示範囲 - 南北
   */
  public int getPolygonH50(){

    return VAL_PolygonH50;
  }

  /**
   * データ取得 - ボタン
   */
  public String getBT(){

    return BT;
  }

  /**
   * メニュー - ポリゴン数 - 東西範囲
   */
  void PolygonEW50(String val){

    VAL_PolygonW50 = Integer.parseInt(val);
    LB_PolygonW50.setText(val);

    LB_Polygon50.setText(String.valueOf(VAL_PolygonW50 * VAL_PolygonH50));
  }

  /**
   * メニュー - ポリゴン数 - 南北範囲
   */
  void PolygonSN50(String val){

    VAL_PolygonH50 = Integer.parseInt(val);
    LB_PolygonH50.setText(val);

    LB_Polygon50.setText(String.valueOf(VAL_PolygonW50 * VAL_PolygonH50));
  }

  /**
   * ボタン - OK
   */
  void BT(ActionEvent actionEvent){

    if(actionEvent.getSource() == BT_ok){
      BT = "OK";
    }
    dispose();
  }
}

/**
 * オブジェ - ポリゴン数 - 東西範囲
 */
class WinViewSetting_PolygonEW50 implements AdjustmentListener{

  WinViewSetting adaptee;

  // コンテキスト
  WinViewSetting_PolygonEW50(WinViewSetting adaptee){
    this.adaptee = adaptee;
  }

  public void adjustmentValueChanged(AdjustmentEvent actionEvent){

    JScrollBar sb = (JScrollBar)actionEvent.getSource();

    adaptee.PolygonEW50(String.valueOf(sb.getValue()));
  }
}

/**
 * オブジェ - ポリゴン数 - 南北範囲
 */
class WinViewSetting_PolygonSN50 implements AdjustmentListener{

  WinViewSetting adaptee;

  // コンテキスト
  WinViewSetting_PolygonSN50(WinViewSetting adaptee){
    this.adaptee = adaptee;
  }

  public void adjustmentValueChanged(AdjustmentEvent actionEvent){

    JScrollBar sb = (JScrollBar)actionEvent.getSource();

    adaptee.PolygonSN50(String.valueOf(sb.getValue()));
  }
}

/**
 * オブジェ - ボタン - OK
 */
class WinViewSetting_OK implements ActionListener{

  WinViewSetting adaptee;

  // コンテキスト
  WinViewSetting_OK(WinViewSetting adaptee){
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent actionEvent){

    adaptee.BT(actionEvent);
  }
}

/**
 * オブジェ - ボタン - キャンセル
 */
class WinViewSetting_CN implements ActionListener{

  WinViewSetting adaptee;

  // コンテキスト
  WinViewSetting_CN(WinViewSetting adaptee){
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent actionEvent){

    adaptee.BT(actionEvent);
  }
}
