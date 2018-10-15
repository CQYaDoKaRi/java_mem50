package map;

import java.io.*;

import javax.media.j3d.Shape3D;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.TriangleArray;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Material;

import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.geometry.*;

/**
 * ５０ｍメッシュ
 */
public class MEM50{

  // データ
  private int    head_array; // 配列数 - ヘッダーデータ
  private String head[];     // データ - ヘッダ
  private int    data[][];   // データ - 標高
  // データ - ポリゴン数
  private int    ndataw;
  private int    ndatah;
  // 描画
  private String attrPolygon;
  private String attrLight;

  /**
   * コンストラクタ
   */
  public MEM50(){
    // データ
    head_array   = 30;
    data         = null;
    head         = new String[head_array];
    // データ - ポリゴン数
    ndataw       = 0;
    ndatah       = 0;
    // 描画
    attrPolygon = "FILL";
    attrLight   = "OFF";
  }

  /**
   * データファイル読み込み結果 - ヘッダ
   */
  public String[] retHead(){

    return head;
  }

  /**
   * データファイル読み込み結果 - 標高データ
   */
  public int[][] retData(){

    return data;
  }

  /**
   * データファイル読み込み
   */
  public void read(File filename){

    int flag[] = null;

    try{
      FileReader fr = new FileReader(filename);
      BufferedReader fd = new BufferedReader(fr);

      String rdata;
      String flag_c;
      int n, m, i, head_n, head_byte, head_byte_n;
      int data_n = 0;
      int data_m = 0;

      for(n = -1; (rdata = fd.readLine()) != null; n++){

        // ヘッダ
        if(n == -1){
          head_n      = 0;
          head_byte   = 0;
          head_byte_n = 0;
          for(i = 0; i < head_array; i++){
            if(i == 0)       // [ 0]:１次メッシュレコード
              head_byte_n = 6;
            else if(i == 1)  // [ 1]:原図の縮尺（縮尺の分母）
              head_byte_n = 5;
            else if(i == 2)  // [ 2]:原図測量年紀（西暦）
              head_byte_n = 4;
            else if(i == 3)  // [ 3]:原図修正年紀（西暦）
              head_byte_n = 4;
            else if(i == 4)  // [ 4]:数値化年紀（西暦）
              head_byte_n = 4;
            else if(i == 5)  // [ 5]:東西方向の点数（データ数）
              head_byte_n = 3;
            else if(i == 6)  // [ 6]:南北方向の点数（データ数）
              head_byte_n = 3;
            else if(i == 7)  // [ 7]:区画左下の緯度 - 度
              head_byte_n = 3;
            else if(i == 8)  // [ 8]:区画左下の緯度 - 分
              head_byte_n = 2;
            else if(i == 9)  // [ 9]:区画左下の緯度 - 秒
              head_byte_n = 2;
            else if(i == 10) // [10]:区画左下の経度 - 度
              head_byte_n = 3;
            else if(i == 11) // [11]:区画左下の経度 - 分
              head_byte_n = 2;
            else if(i == 12) // [12]:区画左下の経度 - 秒
              head_byte_n = 2;
            else if(i == 13) // [13]:区画右上の緯度 - 度
              head_byte_n = 3;
            else if(i == 14) // [14]:区画右上の緯度 - 分
              head_byte_n = 2;
            else if(i == 15) // [15]:区画右上の緯度 - 秒
              head_byte_n = 2;
            else if(i == 16) // [16]:区画右上の経度 - 度
              head_byte_n = 3;
            else if(i == 17) // [17]:区画右上の経度 - 分
              head_byte_n = 2;
            else if(i == 18) // [18]:区画右上の経度 - 秒
              head_byte_n = 2;
            else if(i == 19) // [19]:図葉面数
              head_byte_n = 1;
            else if(i == 20) // [20]:１番目の図名
              head_byte_n = 10;
            else if(i == 21) // [21]:フラグ（１番目の図葉の位置を示すフラグ）
              head_byte_n = 1;
            else if(i == 22) // [22]:２番目の図名
              head_byte_n = 10;
            else if(i == 23) // [23]:フラグ（２番目の図葉の位置を示すフラグ）
              head_byte_n = 1;
            else if(i == 24) // [24]:３番目の図名
              head_byte_n = 10;
            else if(i == 25) // [25]:フラグ（３番目の図葉の位置を示すフラグ）
              head_byte_n = 1;
            else if(i == 26) // [26]:４番目の図名
              head_byte_n = 10;
            else if(i == 27) // [27]:フラグ（４番目の図葉の位置を示すフラグ）
              head_byte_n = 1;
            else if(i == 28) // [28]:記録レコード数
              head_byte_n = 3;
            else if(i == 29) // [29]:コメント
              head_byte_n = 40;

            head[head_n++] = rdata.substring(head_byte, head_byte + head_byte_n);
            head_byte += head_byte_n;
            if(i == 5)      // [ 5]:東西方向の点数（データ数）
              data_m = Integer.parseInt(head[head_n - 1]);
            else if(i == 6) // [ 6]:南北方向の点数（データ数）
              data_n = Integer.parseInt(head[head_n - 1]);
          }

          // レコードフラグ
          flag = new int[data_m];
          for(i = 0; i < data_m; i++){
            if(i > rdata.length() - 255)
              break;
            flag_c = String.valueOf(rdata.charAt(225 + i)).trim();
            if(flag_c.equals("") == false)
              flag[i] = Integer.parseInt(flag_c);
          }
        }
        // 標高
        else{
          if(n == 0)
            data = new int[data_n][data_m];
          for(i = 9, m = 0; i < rdata.length(); i += 5, m++){
            if(data_m <= m)
              break;
            data[n][m] = Integer.parseInt(rdata.substring(i, i + 5));
          }
        }
      }
      fr.close();
    }
    catch(Exception exception){
      exception.printStackTrace();
    }
  }

  /**
   * データセット - 表示標高数
   * arg:
   * int EW : 東西標高数
   * int SW : 南北標高数
   */
  public void points(int EW, int SW){

    ndataw = EW;
    ndatah = SW;
  }

  /**
   * データデータセット - 属性 - ポリゴン
   * arg:
   * String attr == POINT : 点
   *                LINE  : 線（ワイヤーフレーム）
   *                FILL  : 塗つぶし
   */
  public void attrPolygon(String attr){

    attrPolygon = attr;
  }

  /**
   * データセット表示 - 属性 - ライト
   * arg:
   * String attr == null   : ライトなし
   *                DIRECT : 太陽光線
   */
  public void attrLight(String attr){

    attrLight = attr;
  }

  /**
   * データ表示
   */
  public BranchGroup proc(String head[], int data[][]){

    // 初期値 - 座標
    double X_init = -1; // 基準Ｘ座標
    double Y_init = +1; // 基準Ｙ座標
    double Z_init =  0; // 基準Ｚ座標
    // 初期値 - メッシュサイズ
    double C =  0.02;
    // 初期値 - 標高スケール
    double H =  0.00008;

    // 表示 - 座標
    double X = X_init;  // Ｘ座標
    double Y = Y_init;  // Ｙ座標
    double Z = Z_init;  // Ｚ座標
    // 表示 - 標高
    double zNW, zNE, zSW, zSE;
    double  NW,  NE,  SW,  SE;
    // 表示 - 標高別色
    Color3f colorH;

    // 物体定義
    BranchGroup map = new BranchGroup();

    // 物体定義 - 削除許可
    map.setCapability(BranchGroup.ALLOW_DETACH);

    // 数値
    int    k, l, m, n, f;
    int    n_len, m_len;
    double h;
    // 配列
    int       ver_n    = 3;
    double    ver_x[]  = new double[ver_n];
    double    ver_y[]  = new double[ver_n];
    double    ver_z[]  = new double[ver_n];
    Point3d[] vertices = new Point3d[ver_n];
    Color3f[] colors   = new Color3f[ver_n];

    n_len = data.length;
    if(ndataw != 0)
      n_len = ndataw;
    for(n = 0; n < n_len - 1; n++){
      m_len = data[n].length;
      if(ndatah != 0)
        m_len = ndatah;
      for(m = 0; m < m_len - 1; m++){

        // 標高
        NW = data[n    ][m    ];
        if(NW < 0)
          NW = 0;
        NE = data[n    ][m + 1];
        if(NE < 0)
          NE = 0;
        SW = data[n + 1][m    ];
        if(SW < 0)
          SW = 0;
        SE = data[n + 1][m + 1];
        if(SE < 0)
          SE = 0;

        zNW = NW * H;
        zNE = NE * H;
        zSW = SW * H;
        zSE = SE * H;

        // 三角形２つで描画
        for(l = 0; l < 2; l++){

          if(l == 0){
            // 頂点座標
            ver_x[0] = X;
            ver_y[0] = Y;
            ver_z[0] = Z + zNW;
            ver_x[1] = ver_x[0];
            ver_y[1] = ver_y[0] - C;
            ver_z[1] = Z + zSW;
            ver_x[2] = ver_x[1] + C;
            ver_y[2] = ver_y[1];
            ver_z[2] = Z + zSE;
            // 標高別色（陸・海）
            f = 0;
            if(zNW == 0)
              f++;
            if(zSW == 0)
              f++;
            if(zSE == 0)
              f++;
            // 平均標高
            h = (NW + SW + SE) / 3;
          }
          else{
            // 頂点座標
            ver_x[0] = X;
            ver_y[0] = Y;
            ver_z[0] = Z + zNW;
            ver_x[1] = ver_x[0] + C;
            ver_y[1] = ver_y[0] - C;
            ver_z[1] = Z + zSE;
            ver_x[2] = ver_x[1];
            ver_y[2] = ver_y[0];
            ver_z[2] = Z + zNE;
            // 標高別色（陸・海）
            f = 0;
            if(zNW == 0)
              f++;
            if(zSE == 0)
              f++;
            if(zNE == 0)
              f++;
            // 平均標高
            h = (NW + SE + NE) / 3;
          }

          // 標高別色 - 海
          if(f > 2){
            colorH = new Color3f(0.0941f, 0.3490f, 0.5490f);
          }
          // 標高別色 - 陸
          else{
            if(h >= 3800)
              colorH = new Color3f(0.9373f, 0.9216f, 0.9373f);
            else if(h >= 3700)
              colorH = new Color3f(0.9059f, 0.8902f, 0.9059f);
            else if(h >= 3600)
              colorH = new Color3f(0.8392f, 0.8275f, 0.8392f);
            else if(h >= 3500)
              colorH = new Color3f(0.8078f, 0.7961f, 0.8078f);
            else if(h >= 3400)
              colorH = new Color3f(0.7412f, 0.7451f, 0.7412f);
            else if(h >= 3300)
              colorH = new Color3f(0.7098f, 0.7137f, 0.7098f);
            else if(h >= 3200)
              colorH = new Color3f(0.6784f, 0.6667f, 0.6784f);
            else if(h >= 3100)
              colorH = new Color3f(0.6471f, 0.6253f, 0.6471f);
            else if(h >= 3000)
              colorH = new Color3f(0.5804f, 0.5882f, 0.5804f);
            else if(h >= 2900)
              colorH = new Color3f(0.5490f, 0.5569f, 0.5490f);
            else if(h >= 2800)
              colorH = new Color3f(0.4824f, 0.4745f, 0.4824f);
            else if(h >= 2700)
              colorH = new Color3f(0.4824f, 0.4275f, 0.3882f);
            else if(h >= 2600)
              colorH = new Color3f(0.4824f, 0.3961f, 0.3216f);
            else if(h >= 2500)
              colorH = new Color3f(0.4824f, 0.3490f, 0.2234f);
            else if(h >= 2400)
              colorH = new Color3f(0.4824f, 0.3176f, 0.1608f);
            else if(h >= 2300)
              colorH = new Color3f(0.4824f, 0.2706f, 0.0941f);
            else if(h >= 2200)
              colorH = new Color3f(0.4824f, 0.2353f, 0.0627f);
            else if(h >= 2100)
              colorH = new Color3f(0.5176f, 0.2706f, 0.0314f);
            else if(h >= 2000)
              colorH = new Color3f(0.5490f, 0.3176f, 0.0314f);
            else if(h >= 1900)
              colorH = new Color3f(0.5804f, 0.3490f, 0.0627f);
            else if(h >= 1800)
              colorH = new Color3f(0.8250f, 0.3961f, 0.0941f);
            else if(h >= 1700)
              colorH = new Color3f(0.7098f, 0.4745f, 0.1922f);
            else if(h >= 1600)
              colorH = new Color3f(0.7098f, 0.4745f, 0.1922f);
            else if(h >= 1500)
              colorH = new Color3f(0.7412f, 0.5098f, 0.2235f);
            else if(h >= 1400)
              colorH = new Color3f(0.8078f, 0.5569f, 0.2588f);
            else if(h >= 1300)
              colorH = new Color3f(0.8392f, 0.5882f, 0.3216f);
            else if(h >= 1200)
              colorH = new Color3f(0.8392f, 0.6353f, 0.3529f);
            else if(h >= 1100)
              colorH = new Color3f(0.8392f, 0.6667f, 0.3882f);
            else if(h >= 1000)
              colorH = new Color3f(0.8292f, 0.7451f, 0.4196f);
            else if(h >= 900)
              colorH = new Color3f(0.8392f, 0.8275f, 0.4824f);
            else if(h >= 800)
              colorH = new Color3f(0.8392f, 0.8275f, 0.3882f);
            else if(h >= 700)
              colorH = new Color3f(0.8078f, 0.7961f, 0.3529f);
            else if(h >= 600)
              colorH = new Color3f(0.7098f, 0.7961f, 0.3216f);
            else if(h >= 500)
              colorH = new Color3f(0.6471f, 0.7451f, 0.2588f);
            else if(h >= 400)
              colorH = new Color3f(0.5490f, 0.7137f, 0.2235f);
            else if(h >= 300)
              colorH = new Color3f(0.4824f, 0.6667f, 0.1922f);
            else if(h >= 200)
              colorH = new Color3f(0.3882f, 0.6353f, 0.1608f);
            else if(h >= 100)
              colorH = new Color3f(0.3216f, 0.5882f, 0.0941f);
            else if(h >= 70)
              colorH = new Color3f(0.2235f, 0.5569f, 0.0627f);
            else if(h >= 50)
              colorH = new Color3f(0.1608f, 0.5098f, 0.0f   );
            else if(h >= 40)
              colorH = new Color3f(0.0941f, 0.4745f, 0.0f   );
            else if(h >= 30)
              colorH = new Color3f(0.0627f, 0.4275f, 0.0314f);
            else if(h >= 20)
              colorH = new Color3f(   0.0f, 0.3690f, 0.0627f);
            else if(h >= 10)
              colorH = new Color3f(   0.0f, 0.3490f, 0.0941f);
            else
              colorH = new Color3f(   0.0f, 0.3176f, 0.0941f);
          }

          // 頂点座標
          for(k = 0; k < 3; k++){
            vertices[k] = new Point3d(ver_x[k], ver_y[k], ver_z[k]);
            colors[k] = colorH;
          }

          // 物体の定義
          TriangleArray geometry = new TriangleArray(vertices.length, TriangleArray.COORDINATES | TriangleArray.COLOR_3 | TriangleArray.NORMALS);
          // 頂点座標の設定
          geometry.setCoordinates(0, vertices);
          // 頂点の色の設定
          geometry.setColors(0, colors);

          // 物体生成 - ポリゴン
          PolygonAttributes attr = new PolygonAttributes();
          // 物体生成 - 物体の見栄え
          Appearance appearance = new Appearance();

          // 物体生成 - ポリゴン - 点
          if(attrPolygon.equals("POINT"))
            attr.setPolygonMode(PolygonAttributes.POLYGON_POINT);
          // 物体生成 - ポリゴン - 線
          else if(attrPolygon.equals("LINE"))
            attr.setPolygonMode(PolygonAttributes.POLYGON_LINE);
          // 物体生成 - ポリゴン - 塗りつぶし
          else
            attr.setPolygonMode(PolygonAttributes.POLYGON_FILL);
          // 物体生成 - ポリゴン設定
          appearance.setPolygonAttributes(attr);

          // 物体生成 - 光源＆法線ベクトル
          if(!attrLight.equals("OFF")){
            // 光源 - 質感生成
            Material material = new Material();
            // 光源 - ライト設定
            material.setLightingEnable(true);
            // 高原 - 物体色
            material.setDiffuseColor(colorH);
            // 光源 - 質感設定
            appearance.setMaterial(material);
          }

          // 物体生成
          Shape3D shape = new Shape3D(geometry, appearance);
          map.addChild(shape);

          // 物体生成 - 法線ベクトル
          if(!attrLight.equals("OFF")){
            // 法線 - オブジェクト生成
            GeometryInfo geoinfo = new GeometryInfo(GeometryInfo.TRIANGLE_ARRAY);
            // 法線 - 頂点座標の設定
            geoinfo.setCoordinates(vertices);
            // 法線 - 法線ベクトルを生成
            NormalGenerator normalgen = new NormalGenerator();
            // 法線 - 法線ベクトル取得
            normalgen.generateNormals(geoinfo);
            // 法線ベクトル生成
            Shape3D polygons = new Shape3D(geoinfo.getGeometryArray(), appearance);
            map.addChild(polygons);
          }
        }
        X += C;
      }
      X  = X_init;
      Y -= C;
    }

    return map;
  }
}
