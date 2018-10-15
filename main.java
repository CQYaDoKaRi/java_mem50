package map;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/** main クラス
 */
public class main{

  /** コンストラクタ
   */
  public main(){

    Menu BARmenu = new Menu();

   BARmenu.setVisible(true);
  }

  /** エントリポイント
   */
  public static void main(String[] args){

    SwingUtilities.invokeLater(new Runnable(){
      public void run(){
        try{
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception exception){
          exception.printStackTrace();
        }

        new main();
      }
    }
    );
  }

}
