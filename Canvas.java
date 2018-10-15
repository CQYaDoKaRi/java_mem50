package map;

import java.io.File;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.JPanel;

import java.util.Timer;
import java.util.TimerTask;

import javax.media.j3d.*;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * 描画
 */
public class Canvas extends JFrame{

  // フラグ
  private int            f_init        = 0;    // 0:OFF,1:Draw
  private int            f_point       = 1;    // 0:OFF,1:INIT,2:ReSet
  private int            f_draw        = 1;    // 0:OFF,1:INIT
  // オブジェクト - 数値地図処理
  private MEM50          MEM50         = null;
  // オブジェクト - 3D
  private Canvas3D       canvas        = null; // キャンバス
  private SimpleUniverse universe      = null; // ３D空間
  private BranchGroup    root          = null; // 物体定義
  private BranchGroup    map           = null; // 数値地図
  private TransformGroup map_pos       = null; // 表示位置
  private TransformGroup map_pos_trans = null; // 位置表示 - 視点移動（X,Y,Z)
  private TransformGroup map_pos_rot   = null; // 位置表示 - 回転
  private BranchGroup    map_obj       = null; // 地図オブジェクト
  private double         pos_x_trans   = 0;    // 表示位置 - 視点X座標
  private double         pos_y_trans   = 0;    // 表示位置 - 視点Y座標
  private double         pos_z_trans   = -1;    // 表示位置 - 視点Z座標
  private double         pos_x_rot     = -(Math.PI / 4);    // 表示位置 - 回転X座標
  private double         pos_y_rot     = 0;    // 表示位置 - 回転Y座標
  private double         pos_z_rot     = 0;    // 表示位置 - 回転Z座標
  // オブジェクト - ステータス表示用
  private int            windiw_w      = 0;    // ウインドウサイズ　幅
  private int            window_h      = 0;    // ウインドウサイズ　高
  private int            location_x    = 0;    // ウインドウ位置　X座標
  private int            location_y    = 0;    // ウインドウ位置　Y座標
  private Progress       progress      = null; // プログレス - スプラッシュウインドウ
  private String         statusMess    = "";
  private String         statusN       = "";
  // データ
  private String         head[];
  private int            data[][];
  private int            pointEW       = 200;
  private int            pointSN       = 200;
  // データ - 属性
  private String         attrPloygon   = "FILL";
  private String         attrLihgt     = "OFF";

  /**
   * コンストラクタ
   * @param content JPanel
   */
  public Canvas(JPanel content){

    MEM50 = new MEM50();

    // 初期化 - キャンバス生成
    canvas = Java3D.initCanvas(content);

    // 初期化 - ３D空間
    canvas3D();

    // 初期化 - 物体定義 -root
    canvasRoot();

    // 初期化 - 光源
    canvasLight();
  }

  /**
   * 初期化 - ３D空間
   */
  public void canvas3D(){

    // 初期化 - 空間生成
    universe = Java3D.initUniverse(canvas, 80.0);

    // イベント処理 - Key
    //Java3D.eventKey(universe);

    // イベント処理 - マウス
    Java3D.eventMouse(universe);
  }

  /**
   * 初期化 - 物体定義 -root
   */
  public void canvasRoot(){

    // 物体定義
    root = new BranchGroup();

    // オブジェク追加許可
    root.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
    root.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
    root.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
    root.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    root.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
    root.setCapability(DirectionalLight.ALLOW_DIRECTION_WRITE);
    // オブジェクト削除許可
    root.clearCapability(BranchGroup.ALLOW_DETACH);

    // 物体定義 - 数値地図
    map  = new BranchGroup();

    // オブジェク追加許可
    map.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
    map.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
    map.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
    map.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    map.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
    map.setCapability(DirectionalLight.ALLOW_DIRECTION_WRITE);
    // オブジェクト削除許可
    map.clearCapability(BranchGroup.ALLOW_DETACH);
  }

  /**
   * 初期化 - 光源
   */
  public void canvasLight(){

    // 光源の設定
    DirectionalLight  light = new DirectionalLight(
      true,
      new Color3f(  0.98f,  0.98f,  0.98f), // 色
      new Vector3f( 0.0f,    0.0f, -1.0f )  // 方向
        );

    // 有効領域 - 原点を中心とする半径 100.0 の範囲
    BoundingSphere bounds = new BoundingSphere(new Point3d(), 100.0);
    light.setInfluencingBounds(bounds);

    root.addChild(light);
  }

  /**
   * 初期化 - ３D空間位置
   * @param  x double
   *         視点　X座標
   * @param  y double
   *         視点　Y座標
   * @param  z double
   *         視点　Z座標
   * @param  x_angle double
   *         視点　X角度 : Math.PI(180) / x
   * @param  y_angle double
   *         視点　Y角度 : Math.PI(180) / y
   * @param  z_angle double
   *         視点　Z角度 : Math.PI(180) / z
   * @return TransformGroup
   */
  public TransformGroup canvas3DTrans(double x, double y, double z, double x_angle, double y_angle, double z_angle){

    // 視点
    map_pos_trans = new TransformGroup(canvas3DPosTrans(x, y, z));
    map.addChild(map_pos_trans);

    // オブジェク追加許可
    map_pos_trans.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
    map_pos_trans.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
    map_pos_trans.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
    map_pos_trans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    map_pos_trans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
    map_pos_trans.setCapability(DirectionalLight.ALLOW_DIRECTION_WRITE);
    // オブジェクト削除許可
    map_pos_trans.clearCapability(BranchGroup.ALLOW_DETACH);

    // 回転
    map_pos_rot = new TransformGroup(canvas3DPosRot(x_angle, y_angle, z_angle));
    map_pos_trans.addChild(map_pos_rot);

    // オブジェク追加許可
    map_pos_rot.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
    map_pos_rot.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
    map_pos_rot.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
    map_pos_rot.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    map_pos_rot.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
    map_pos_rot.setCapability(DirectionalLight.ALLOW_DIRECTION_WRITE);
    // オブジェクト削除許可
    map_pos_rot.clearCapability(BranchGroup.ALLOW_DETACH);

    return map_pos_rot;
  }

  /**
   * 視点 - 平行移動
   * @param  x double
   *         視点　X座標
   * @param  y double
   *         視点　Y座標
   * @param  z double
   *         視点　Z座標
   * @return Transform3D
   * 　　　　 視点オブジェクト
   */
  public Transform3D canvas3DPosTrans(double x, double y, double z){

    Transform3D translation = new Transform3D();
    translation.setTranslation(new Vector3d(x, y, z));

    return translation;
  }

  /**
   * 視点 - 回転
   * @param  x double
   *         視点　角度 : Math.PI(180) / x
   * @param  y double
   *         視点　角度 : Math.PI(180) / y
   * @param  z double
   *         視点　角度 : Math.PI(180) / z
   * @return Transform3D
   * 　　　　 視点オブジェクト
   */
  public Transform3D canvas3DPosRot(double x, double y, double z){

  Transform3D translation = new Transform3D();
  if(x != 0)
    translation.rotX(x);
  if(y != 0)
    translation.rotX(y);
  if(z != 0)
    translation.rotX(z);

  return translation;
}

  /**
   * 初期処理
   * @param  filename　File
   *         ファイル名
   * @return  String[]
   *         ヘッダーデータ
   */
  public String[] init(File filename){

    f_init = 1;

    // データファイル読み込み
    MEM50.read(filename);

    // データファイル読み込み結果 - ヘッダ
    head = MEM50.retHead();

    // データファイル読み込み結果 - 標高データ
    data = MEM50.retData();

    return head;
  }

  /**
   * 標高数 - 東西
   * @param  EW int
   *         東西標高数
   *         0 == 設定値取得
   *         else 設定
   * @return int
   *         東西標高数
   */
  public int pointEW(int EW){

    if(EW > 0){
      pointEW = EW;

      // データセット - 表示標高数
      MEM50.points(pointEW, pointEW);
    }

    return pointEW;
  }

  /**
   * 標高数 - 南北
   * @param  EW int
   *         南北標高数
   *         0 == 設定値取得
   *         else 設定
   * @return int
   *         南北標高数
   */
  public int pointSN(int SW){

    if(SW > 0){
      pointSN = SW;

      // データセット - 表示標高数
      MEM50.points(pointEW, pointEW);
    }

    return pointSN;
  }

  /**
   * 視点設定 - 平行移動 - X座標
   * @param  pos double
   *         視点　X座標
   * @return double
   *         視点　X座標
   */
  public double posTransX(double pos){

    if(pos != 0){
      pos_x_trans = pos;
      f_point     = 2;
    }
    return pos_x_trans;
  }

  /**
   * 視点設定 - 平行移動 - Y座標
   * @param  pos double
   *         視点　Y座標
   * @return double
   *         視点　Y座標
   */
  public double posTransY(double pos){

    if(pos != 0){
      pos_y_trans = pos;
      f_point     = 2;
    }
    return pos_y_trans;
  }

  /**
   * 視点設定 - 平行移動 - Z座標
   * @param  pos double
   *         視点　Z座標
   * @return double
   *         視点　Z座標
   */
  public double posTransZ(double pos){

    if(pos != 0){
      pos_z_trans = pos;
      f_point     = 2;
    }
    return pos_z_trans;
  }

  /**
   * 視点設定 - 回転移動 - X座標
   * @param  pos double
   *         視点　X座標
   * @return double
   *         視点　X座標
   */
  public double posRotX(double pos){

    if(pos != 0){
      pos_x_rot = pos;
      f_point   = 2;
    }
    return pos_x_rot;
  }

  /**
   * 視点設定 - 回転移動 - Y座標
   * @param  pos double
   *         視点　Y座標
   * @return double
   *         視点　Y座標
   */
  public double posRotY(double pos){

    if(pos != 0){
      pos_y_rot = pos;
      f_point   = 2;
    }
    return pos_y_rot;
  }

  /**
   * 視点設定 - 回転移動 - Z座標
   * @param  pos double
   *         視点　Z座標
   * @return double
   *         視点　Z座標
   */
  public double posRotZ(double pos){

    if(pos != 0){
      pos_z_rot = pos;
      f_point   = 2;
    }
    return pos_z_rot;
  }

  /**
   * 視点 - 回転
   * @param  x double
   *         視点　角度 : Math.PI(180) / x
   * @param  y double
   *         視点　角度 : Math.PI(180) / y
   * @param  z double
   *         視点　角度 : Math.PI(180) / z
   * @return Transform3D
   * 　　　　 視点オブジェクト
   */
  public Transform3D posRot(double x, double y, double z){

  Transform3D translation = new Transform3D();
  if(x != 0)
    translation.rotX(x);
  if(y != 0)
    translation.rotX(y);
  if(z != 0)
    translation.rotX(z);

  return translation;
}

  /**
   * 属性 - ポリゴン設定
   * @param  attr String
   *         属性
   * @return String
   *         属性
   */
  public String attrPolygon(String attr){

    if(attr != null){
      attrPloygon = attr;

      // データセット - 属性 - ポリゴン
      MEM50.attrPolygon(attrPloygon);
    }

    return attrPloygon;
  }

  /**
   * 属性 - ライト設定
   * @param  attr String
   *         属性
   * @return String
   *         属性
   */
  public String attrLight(String attr){

    if(attr != null){
      attrLihgt = attr;

      // データセット - 属性 - ライト
      MEM50.attrLight(attrLihgt);
    }

    return attrLihgt;
  }

  /**
   * 描画元ウインドウ情報
   * @param x int
   *        ウインドウ位置　X座標
   * @param y int
   *        ウインドウ位置　Y座標
   * @param w int
   *        ウインドウサイズ　幅
   * @param h int
   *        ウインドウサイズ　高
   */
  public void draw3D_wininfo(int x, int y, int w, int h){

    // ウインドウサイズ　幅
    windiw_w   = w;
    // ウインドウサイズ　高
    window_h   = h;
    // ウインドウ位置　X座標
    location_x = x;
    // ウインドウ位置　Y座標
    location_y = y;
  }

  /**
   * 描画
   */
  public void draw3D(){

    if(f_init != 0){

      // プログレス - スプラッシュウインドウ
      int pos_x = location_x;
      int pos_y = location_y;
      if(windiw_w > 0)
        pos_x += windiw_w / 2;
      if(window_h > 0)
        pos_y += window_h / 2;
      progress = new Progress(pos_x, pos_y, 250, 16, statusMess);

      // タイマー設定
      Timer timer = new Timer(true);
      StatusTimer timer_task = new StatusTimer(this);
      timer.scheduleAtFixedRate(timer_task, 0, 1000);

      if(map_obj != null){
        statusN = "";
        statusMess = "シーンクリア";
        map_obj.detach();

        statusN = "";
        statusMess = "ノード削除";
        map.removeChild(map_obj);
        map_obj = null;
      }
      else
        universe.addBranchGraph(root);

      if(f_point == 1 || f_point == 2){
        statusN = "";
        statusMess = "視点設定中";
        // 初期設定
        if(f_point == 1){
          map_pos = canvas3DTrans(pos_x_trans, pos_y_trans, pos_z_trans, pos_x_rot, pos_y_rot, pos_z_rot);
        }
        // 再設定
        else{
          map_pos_trans.setTransform(canvas3DPosTrans(pos_x_trans, pos_y_trans, pos_z_trans));
          map_pos_rot.setTransform(canvas3DPosRot(pos_x_rot, pos_y_rot, pos_z_rot));
        }
        f_point = 0;
      }

      statusN = "";
      statusMess = "データ生成中";
      map_obj = MEM50.proc(head, data);

      statusN = "";
      statusMess = "コンパイル中";
      map_obj.compile();

      // 描画 - データ描画
      statusN    = "";
      statusMess = "描画中";
      map_pos.addChild(map_obj);
      if(f_draw == 1){
        root.addChild(map);
        f_draw = 0;
      }

      // タイマー終了
      timer.cancel();

      // プログレス - スプラッシュウインドウを閉じる
      progress.close();
    }
  }

  /**
   * 描画 - ステータバーに処理状況を表示
   */
  public void draw3D_status(){

    if(statusN == null)
      statusN  = "";
    else
      statusN += ".";

    progress.setMessage(statusMess + statusN);
  }

}

/**
 * ステータス - タイマー描画
 */
class StatusTimer extends TimerTask{

  private Canvas draw3D;

  /**
   * コンストラクタ
   * @param draw3D Canvas
   *        Canvas クラス
   */
  public StatusTimer(Canvas draw3D){
    this.draw3D = draw3D;
  }

  /**
   * タイマー処理
   */
  public void run(){
    draw3D.draw3D_status();
  }

}

/**
 * プログレス
 */
class Progress extends JWindow{

  // 表示文字
  private String strMsg = "";
  // 背景色
  private Color color      = new Color(  0,   0,   0);
  // 文字色
  private Color color_font = new Color(255, 255, 255);

  /**
   * コンストラクタ
   * プログレス - スプラッシュウインドウを開く
   * @param x int
   *        位置　Y座標
   * @param y int
   *        位置　X座標
   * @param w int
   *        幅
   * @param h int
   *        高
   * @param strMessage String
   *        表示文字
   */
  public Progress(int x, int y, int w, int h, String strMessage){

    strMsg = strMessage;

    int pos_x = x;
    int pos_y = y;
    if(w > 0)
      pos_x -= w / 2;
    if(y > 0)
      pos_y -= h / 2;

    // 表示位置
    this.setBounds(pos_x, pos_y, w, h);
    // 背景色
    this.setBackground(color);
    // 表示
    this.setVisible(true);
  }

  /**
   * プログレス - スプラッシュウインドウを閉じる
   */
  public void close(){
    this.setVisible(false);
  }

  /**
   * 表示文字列設定
   * @param strMessage String
   *        文字列
   */
  public void setMessage(String strMessage){

    strMsg = "";
    strMsg = strMessage;
    paint(this.getGraphics());
  }

  /**
   * 背景色設定
   * @param R int
   *        赤(0 - 255)
   * @param G int
   *        緑(0 - 255)
   * @param B int
   *        青(0 - 255)
   */
  public void setColor(int R, int G, int B){

    color = new Color(R, G,  B);
  }

  /**
   * 文字色設定
   * @param R int
   *        赤(0 - 255)
   * @param G int
   *        緑(0 - 255)
   * @param B int
   *        青(0 - 255)
   */
  public void setColorFont(int R, int G, int B){

    color_font = new Color(R, G,  B);
  }

  /**
   * 再描画用
   */
  public void paint(Graphics g){

    // 背景色
    g.setColor(color);
    // 画面を塗る
    g.fillRect(0, 0, getSize().width, getSize().height);
    // 背景色
    this.setBackground(color);
    // 文字色
    g.setColor(color_font);
    // 文字表示
    if(strMsg.length() > 0)
      g.drawString(strMsg, 3, this.getSize().height - 4);
  }
}
