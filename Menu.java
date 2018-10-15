package map;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.*;

/**
 * メニュー クラス
 */
public class Menu extends JFrame{

  // Window
  String WinTitle       = "数値地図";
  int    WinSizeW       = 813;
  int    WinSizeH       = 900;
  int    WinSizeStatusH = 20;
  // Window
  JPanel    content;
  // メニュー - ファイル - ファイル情報
  JMenuItem MenuFileInfo;
  // メニュー - 表示 - 塗つぶし
  JMenuItem MenuViewFill;
  // メニュー - 表示 - 線
  JMenuItem MenuViewLinel;
  // メニュー - 表示 - 点
  JMenuItem MenuViewPoint;
  // メニュー - ライト - Off
  JMenuItem MenuViewLightOff;
  // メニュー - ライト - Direct
  JMenuItem MenuViewLightDirect;
  // ステータス
  JLabel    LSstatus;

  // データ
  File      filename = null;
  String    head[];

  // オブジェクト
  Canvas    Canvas = null;

 /**
  * コンストラクタ
  */
  public Menu(){
    super();

    try{
      setDefaultCloseOperation(EXIT_ON_CLOSE);
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

    // Window
    setSize(new Dimension(WinSizeW, WinSizeH));
    setTitle(WinTitle);

    content = (JPanel) getContentPane();
    content.setLayout(new BorderLayout());

    // 初期化 - ３D
    Canvas = new Canvas(content);

    // 初期化 - メニュー
    initMenu();

    // 初期化 - ツールバー
    initToolBar(content);

    // 初期化 - ステータス
    initStatus(content);

    // 初期化 - ３Dパラメータ
    //??
    // 標高数 - 東西
    Canvas.pointEW(100);
    // 標高数 - 南北
    Canvas.pointSN(100);

  }

  /**
   * 初期化 - メニュー
   */
  void initMenu(){

    JMenuBar MenuBar1 = new JMenuBar();

    getRootPane().setJMenuBar(MenuBar1);

    // メニュー - ファイル
    JMenu MenuFile = new JMenu("ファイル(F)");
    MenuFile.getPopupMenu().setLightWeightPopupEnabled(false);
    MenuFile.setMnemonic('F');

    // メニュー - ファイル - 開く
    JMenuItem MenuFileOpen = new JMenuItem("開く(O)");
    MenuFileOpen.setMnemonic('O');
    MenuFileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
    MenuFileOpen.addActionListener(new MenuFile_Open(this));
    MenuFile.add(MenuFileOpen);

    // メニュー - セパレート
    MenuFile.addSeparator();

    // メニュー - ファイル - ファイル情報
    MenuFileInfo = new JMenuItem("ファイル情報(I)");
    MenuFileInfo.setMnemonic('I');
    MenuFileInfo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
    MenuFileInfo.addActionListener(new MenuFile_Info(this));
    MenuFileInfo.setEnabled(false);
    MenuFile.add(MenuFileInfo);

    // メニュー - セパレート
    MenuFile.addSeparator();

    // メニュー - ファイル - 終了
    JMenuItem MenuFileExit = new JMenuItem("終了(Q)");
    MenuFileExit.setMnemonic('Q');
    MenuFileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
    MenuFileExit.addActionListener(new MenuFile_Exit(this));
    MenuFile.add(MenuFileExit);

    // メニュー - ファイル - セット
    MenuBar1.add(MenuFile);

    // メニュー - 表示
    JMenu MenuView = new JMenu("表示(V)");
    MenuView.getPopupMenu().setLightWeightPopupEnabled(false);
    MenuView.setMnemonic('V');

    // メニュー - 表示 - セット
    MenuBar1.add(MenuView);

    JMenuItem MenuViewRun = new JMenuItem("再描画(R)");
    MenuViewRun.setMnemonic('R');
    MenuViewRun.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
    MenuViewRun.addActionListener(new MenuView_Run(this));
    MenuView.add(MenuViewRun);

    // メニュー - セパレート
    MenuView.addSeparator();

    // メニュー - 表示 - 設定
    JMenuItem MenuViewSetting = new JMenuItem("表示設定(S)");
    MenuViewSetting.setMnemonic('S');
    MenuViewSetting.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
    MenuViewSetting.addActionListener(new MenuView_Setting(this));
    MenuView.add(MenuViewSetting);

    // メニュー - セパレート
    MenuView.addSeparator();

    // 属性 - ポリゴン設定
    String attrPolygon = Canvas.attrPolygon(null);

    // メニュー - 表示 - 塗つぶし
    MenuViewFill = new JCheckBoxMenuItem("塗つぶし(F)");
    MenuViewFill.setMnemonic('F');
    MenuViewFill.addActionListener(new MenuView_AttrFill(this));
    if(attrPolygon.equals("FILL"))
      MenuViewFill.setSelected(true);
    else
      MenuViewFill.setSelected(false);
    MenuView.add(MenuViewFill);

    // メニュー - 表示 - 線
    MenuViewLinel = new JCheckBoxMenuItem("線(L)");
    MenuViewLinel.setMnemonic('L');
    MenuViewLinel.addActionListener(new MenuView_AttrLine(this));
    if(attrPolygon.equals("LINE"))
      MenuViewLinel.setSelected(true);
    else
      MenuViewLinel.setSelected(false);
    MenuView.add(MenuViewLinel);

    // メニュー - 表示 - 点
    MenuViewPoint = new JCheckBoxMenuItem("点(P)");
    MenuViewPoint.setMnemonic('P');
    MenuViewPoint.addActionListener(new MenuView_AttrPoint(this));
    if(attrPolygon.equals("POINT"))
      MenuViewPoint.setSelected(true);
    else
      MenuViewPoint.setSelected(false);
    MenuView.add(MenuViewPoint);

    // メニュー - セパレート
    MenuView.addSeparator();

    // 属性 - ライト設定
    String attrLight = Canvas.attrLight(null);

    // メニュー - 表示 - ライト
    JMenu MenuViewLight = new JMenu("ライト(L)");
    MenuViewLight.getPopupMenu().setLightWeightPopupEnabled(false);
    MenuViewLight.setMnemonic('L');
    MenuView.add(MenuViewLight);

    // メニュー - 表示 - ライト - Off
    MenuViewLightOff    = new JCheckBoxMenuItem("Off(O)");
    MenuViewLightOff.setMnemonic('O');
    MenuViewLightOff.addActionListener(new MenuView_LightOff(this));
    if(attrLight.equals("OFF"))
      MenuViewLightOff.setSelected(true);
    else
      MenuViewLightOff.setSelected(false);
    MenuViewLight.add(MenuViewLightOff);

    // メニュー - 表示 - ライト - Direction（太陽光）
    MenuViewLightDirect = new JCheckBoxMenuItem("Direction：太陽光(D)");
    MenuViewLightDirect.setMnemonic('D');
    MenuViewLightDirect.addActionListener(new MenuView_LightDirect(this));
    if(attrLight.equals("DIRECT"))
      MenuViewLightDirect.setSelected(true);
    else
      MenuViewLightDirect.setSelected(false);
    MenuViewLight.add(MenuViewLightDirect);

    // メニュー - ヘルプ
    JMenu MenuHelp = new JMenu("ヘルプ(H)");
    MenuHelp.getPopupMenu().setLightWeightPopupEnabled(false);
    MenuHelp.setMnemonic('H');

    // メニュー - ヘルプ - セット
    MenuBar1.add(MenuHelp);

    // メニュー - ヘルプ - バージョン情報
    JMenuItem MenuHelpAbout = new JMenuItem("バージョン情報(A)");
    MenuHelpAbout.setMnemonic('A');
    MenuHelpAbout.addActionListener(new MenuHelp_About(this));
    MenuHelp.add(MenuHelpAbout);
  }

  /**
   * 初期化 - ツールバー
   */
  void initToolBar(JPanel content){

    // アイコン
    JToolBar  BarFile = new JToolBar();

    // アイコン - ファイル
    JButton   BTFile  = new JButton();
    ImageIcon IMGFile = new ImageIcon(map.Menu.class.getResource("icon_file_open.png"));
    BTFile.setIcon(IMGFile);
    BTFile.setToolTipText("ファイルを開く");
    BTFile.addActionListener(new MenuFile_Open(this));
    BarFile.add(BTFile);
    BarFile.setBounds(0, 0, 100, 20);
    content.add(BarFile, BorderLayout.NORTH);
  }

  /**
   * 初期化 - ステータス
   */
  void initStatus(JPanel content){

    LSstatus = new JLabel();
    LSstatus.setText("No data");
    LSstatus.setBorder(new BevelBorder(BevelBorder.LOWERED));
    content.add(LSstatus, BorderLayout.SOUTH);
  }

  /**
   * 描画
   */
  void draw(){

    // 描画元ウインドウ情報
    Canvas.draw3D_wininfo(getLocation().x, getLocation().y, getSize().width, getSize().height);

    // 描画
    Canvas.draw3D();

    // ステータス
    LSstatus.setText(" 1/" + head[1] + "　" + head[20].trim());
  }

  /**
   * メニュー - ファイル - 開く
   */
  void MenuFile_Open(ActionEvent actionEvent){

    JFileChooser dialog_open = new JFileChooser();

    // ファイルフィルター
    dialog_open.setFileFilter(new MenuFile_OpenFilter_MEM());

    // 表示
    int ret = dialog_open.showOpenDialog(null);
    if(ret == dialog_open.APPROVE_OPTION){
      filename = dialog_open.getSelectedFile();
      if(filename != null){

        // 初期処理
        head = Canvas.init(filename);

        // Window タイトル
        String title = WinTitle + " - " + filename;
        setTitle(title);

        // メニューの有効化
        MenuFileInfo.setEnabled(true);

        // 描画
        draw();
      }
    }
  }

  /**
   * メニュー - ファイル - ファイル情報
   */
  void MenuFile_Info(ActionEvent actionEvent){

    WinInfo dialog = new WinInfo(this, head);

    Dimension dlgSize = dialog.getPreferredSize();
    Dimension frmSize = getSize();

    Point pos = getLocation();

    dialog.setLocation(
      (frmSize.width  - dlgSize.width)  / 2 + pos.x,
      (frmSize.height - dlgSize.height) / 2 + pos.y
    );

    dialog.setModal(true);
    dialog.setSize(new Dimension(180, 230));
    dialog.setVisible(true);
  }

  /**
   * メニュー - ファイル - 終了
   */
  void MenuFile_Exit(ActionEvent actionEvent){
    System.exit(0);
  }

  /**
   * メニュー - 表示 - 再描画
   */
  void MenuView_Run(ActionEvent actionEvent){

    // 描画
    draw();
  }

  /**
   * メニュー - 表示 - 設定
   */
  void MenuView_Setting(ActionEvent actionEvent){

    // 設定情報
    String settings[] = new String[2];

    // 標高数 - 東西
    settings[0] = String.valueOf(Canvas.pointEW(0));
    // 標高数 - 南北
    settings[1] = String.valueOf(Canvas.pointSN(0));

    // オブジェクト生成
    WinViewSetting dialog = new WinViewSetting(this, settings);

    // 表示位置
    Dimension dlgSize = dialog.getPreferredSize();
    Dimension frmSize = getSize();
    Point pos = getLocation();
    dialog.setLocation(
        pos.x + 5,
        pos.y + dlgSize.height + 75
        );

    // 表示サイズ
    dialog.setSize(new Dimension(330, 200));

    // 表示方法
    dialog.setModal(true);
    dialog.setVisible(true);

    if(dialog.getBT().equals("OK")){
      // 標高数 - 東西
      Canvas.pointEW(dialog.getPolygonW50());
      // 標高数 - 南北
      Canvas.pointSN(dialog.getPolygonH50());
    }
  }

  /**
   * メニュー - 表示 - タイプ
   */
  void MenuView_AttrPloygon(ActionEvent actionEvent, String attr){

    // 属性 - ポリゴン設定
    String attrPolygon = Canvas.attrPolygon(attr);

    if(attrPolygon.equals("FILL")){
      MenuViewFill.setSelected(true);
      MenuViewLinel.setSelected(false);
      MenuViewPoint.setSelected(false);
    }
    else if(attrPolygon.equals("LINE")){
      MenuViewFill.setSelected(false);
      MenuViewLinel.setSelected(true);
      MenuViewPoint.setSelected(false);
    }
    else if(attrPolygon.equals("POINT")){
      MenuViewFill.setSelected(false);
      MenuViewLinel.setSelected(false);
      MenuViewPoint.setSelected(true);
    }
  }

  /**
   * メニュー - 表示 - ライト
   */
  void MenuView_Light(ActionEvent actionEvent, String attr){

    // 属性 - ライト設定
    String attrLight = Canvas.attrLight(attr);

    if(attrLight.equals("OFF")){
      MenuViewLightOff.setSelected(true);
      MenuViewLightDirect.setSelected(false);
    }
    else if(attrLight.equals("DIRECT")){
      MenuViewLightOff.setSelected(false);
      MenuViewLightDirect.setSelected(true);
    }
  }

  /**
   * メニュー - ヘルプ - バージョン情報
   */
 void MenuHelp_About(ActionEvent actionEvent){

    WinAbout dialog = new WinAbout(this);

    Dimension dlgSize = dialog.getPreferredSize();
    Dimension frmSize = getSize();

    Point pos = getLocation();

    dialog.setLocation(
      (frmSize.width  - dlgSize.width)  / 2 + pos.x,
      (frmSize.height - dlgSize.height) / 2 + pos.y
    );

    dialog.setModal(true);
    dialog.pack();
    dialog.setVisible(true);
  }

}

/**
 * メニュー - ファイル -　開く
 */
class MenuFile_Open implements ActionListener{
  Menu adaptee;

  // コンテキスト
  MenuFile_Open(Menu adaptee){
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent actionEvent){
    adaptee.MenuFile_Open(actionEvent);
  }
}

/**
 * メニュー - ファイル -　開く　フィルター MEM
 */
class MenuFile_OpenFilter_MEM extends FileFilter{

  public boolean accept(File filename){

     boolean ret = false;

     if(filename != null){
       if(filename.isDirectory())
         ret = true;
       else{
         String fext = FileInfo.Ext(filename);
         if(fext.equals("mem"))
           ret = true;
       }
     }
     return ret;
   }

   public String getDescription(){

     return "数値地図 50m(*.mem)";
  }
}

/**
 * メニュー - ファイル - ファイル情報
 */
class MenuFile_Info implements ActionListener{
  Menu adaptee;

  // コンテキスト
  MenuFile_Info(Menu adaptee){
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent actionEvent){
    adaptee.MenuFile_Info(actionEvent);
  }
}

/**
 * メニュー - 表示 - 設定
 */

class MenuView_Setting implements ActionListener{
  Menu adaptee;

  // コンテキスト
  MenuView_Setting(Menu adaptee){
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent actionEvent){
    adaptee.MenuView_Setting(actionEvent);
  }
}

/**
 * メニュー - 表示 - 再描画
 */
class MenuView_Run implements ActionListener{

  Menu adaptee;

  // コンテキスト
  MenuView_Run(Menu adaptee){
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent actionEvent){
    adaptee.MenuView_Run(actionEvent);
  }
}

/**
 * メニュー - 表示 - 塗つぶし
 */
class MenuView_AttrFill implements ActionListener{

  Menu adaptee;

  // コンテキスト
  MenuView_AttrFill(Menu adaptee){
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent actionEvent){
    adaptee.MenuView_AttrPloygon(actionEvent, "FILL");
  }
}

/**
 * メニュー - 表示 - 線
 */
class MenuView_AttrLine implements ActionListener{

  Menu adaptee;

  // コンテキスト
  MenuView_AttrLine(Menu adaptee){
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent actionEvent){
    adaptee.MenuView_AttrPloygon(actionEvent, "LINE");
  }
}

/**
 * メニュー - 表示 - 点
 */
class MenuView_AttrPoint implements ActionListener{

  Menu adaptee;

  // コンテキスト
  MenuView_AttrPoint(Menu adaptee){
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent actionEvent){
    adaptee.MenuView_AttrPloygon(actionEvent, "POINT");
  }
}

/**
 * メニュー - 表示 - ライト - Off
 */
class MenuView_LightOff implements ActionListener{

  Menu adaptee;

  // コンテキスト
  MenuView_LightOff(Menu adaptee){
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent actionEvent){
    adaptee.MenuView_Light(actionEvent, "OFF");
  }
}

/**
 * メニュー - 表示 - ライト - Direction
 */
class MenuView_LightDirect implements ActionListener{

  Menu adaptee;

  // コンテキスト
  MenuView_LightDirect(Menu adaptee){
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent actionEvent){
    adaptee.MenuView_Light(actionEvent, "DIRECT");
  }
}

/**
 * メニュー - ファイル - 終了　クラス
 */
class MenuFile_Exit implements ActionListener{
  Menu adaptee;

  // コンテキスト
  MenuFile_Exit(Menu adaptee){
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent actionEvent){
    adaptee.MenuFile_Exit(actionEvent);
  }
}

/**
 * メニュー - ヘルプ - バージョン情報　クラス
 */
class MenuHelp_About implements ActionListener{
  Menu adaptee;

  // コンテキスト
  MenuHelp_About(Menu adaptee){
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent actionEvent){
    adaptee.MenuHelp_About(actionEvent);
  }
}
