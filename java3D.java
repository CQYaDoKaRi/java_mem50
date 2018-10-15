package map;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.BoundingSphere;

import javax.vecmath.Point3d;

import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.PlatformGeometry;
import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;

/**
 */
class Java3D{

  public Java3D(){
  }

  /**
   * 初期化 - キャンバス生成
   */
  public static Canvas3D initCanvas(JPanel content){

    Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
    content.add(canvas, BorderLayout.CENTER);

    return canvas;
  }

  /**
   * 初期化 - 空間生成
   */
  public static SimpleUniverse initUniverse(Canvas3D canvas, double dist){

    // ３D - 空間（空間を構成するオブジェクト群）の作成
    SimpleUniverse universe = new SimpleUniverse(canvas);

    // 視点 - Ｚ方向へ2.41移動
    universe.getViewingPlatform().setNominalViewingTransform();
    // 視点 - 奥のクリップ面 (ファー・クリップ面) までの距離設定
    universe.getViewer().getView().setBackClipDistance(dist);
    // 視点 - 移動
    universe.getViewingPlatform().getViewPlatformTransform();

    return universe;
  }

  /**
   * イベント処理 - Key
   * 動作
   * 　↑　　　　　　　　 前進
   * 　↓　　　　　　　　 後退
   * 　←　　　　　　　　 左に向きを変える
   * 　→　　　　　　　　 右に向きを変える
   * 　PgUp　　　　　　　下に向きを変える (ティルト・ダウン)
   * 　pgDn　　　　　　　上に向きを変える (ティルト・アップ)
   * 　Alt + ←　　　　　左に平行移動
   * 　Alt + →　　　　　右に平行移動
   * 　Alt + PgUp 　　　上昇 (上方向に平行移動)
   * 　Alt + pgDn　　　 下降 (下方向に平行移動)
   * 　Shift + 他のキー　移動量を大きくする
   */
  public static void eventKey(SimpleUniverse universe){

    // 視点側の TransformGroup を取得
    TransformGroup viewtrans = universe.getViewingPlatform().getViewPlatformTransform();

    // 視点側のキーイベント
    KeyNavigatorBehavior keybehavior = new KeyNavigatorBehavior(viewtrans);

    // 有効領域 - 原点を中心とする半径 100.0 の範囲
    BoundingSphere bounds = new BoundingSphere(new Point3d(), 100.0);
    keybehavior.setSchedulingBounds(bounds);

    // キー設定
    PlatformGeometry vp = new PlatformGeometry();
    vp.addChild(keybehavior);
    universe.getViewingPlatform().setPlatformGeometry(vp);
  }

  /**
   * イベント処理 - マウス
   * 動作
   * 　右クリック　　　　回転
   * 　左クリック　　　　横方向移動
   */
  public static void eventMouse(SimpleUniverse universe){

    // 視点側の TransformGroup を取得
    TransformGroup viewtrans = universe.getViewingPlatform().getViewPlatformTransform();

    // 視点側のマウスイベント
    MouseBehavior MouseRotate    = new MouseRotate(viewtrans);
    MouseBehavior MouseTranslate = new MouseTranslate(viewtrans);
    MouseBehavior MouseZoom      = new MouseZoom(viewtrans);

    // 有効領域 - 原点を中心とする半径 100.0 の範囲
    BoundingSphere bounds = new BoundingSphere(new Point3d(), 100.0);
    MouseRotate.setSchedulingBounds(bounds);
    MouseTranslate.setSchedulingBounds(bounds);
    MouseZoom.setSchedulingBounds(bounds);

    // マウス設定
    PlatformGeometry vp = new PlatformGeometry();
    vp.addChild(MouseRotate);
    vp.addChild(MouseTranslate);
    vp.addChild(MouseZoom);
    universe.getViewingPlatform().setPlatformGeometry(vp);
  }
}

