package map;

import java.io.File;

/** ファイル　クラス
 */
public class FileInfo{

  public FileInfo(){
  }

  public static String Ext(File filename){

    String ret = null;

    if(filename != null){
      String fname = filename.getName();
      int i = fname.lastIndexOf('.');
      if (i > 0 && i < fname.length() - 1)
        ret = fname.substring(i + 1).toLowerCase();
    }

    return ret;
  }
}
