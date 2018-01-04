package com.study.wechatjumphelper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;

import static java.lang.Math.max;

/**
 * Created by cj on 2018/1/4.
 * desc 寻找中心点
 * 计算按压时间的核心类
 */

public class JumpUtils {
    private static float press ;
    private static int pieceBoard ;
    private static int baseHeight;
    static {
        TypedValue value = new TypedValue();
        MyApplication.getContext().getResources().getValue(R.dimen.press_coefficient, value,false);
        press = value.getFloat();
        MyApplication.getContext().getResources().getValue(R.dimen.piece_body_width, value,false);
        pieceBoard = (int) value.getFloat();
        MyApplication.getContext().getResources().getValue(R.dimen.piece_base_height_1_2, value,false);
        baseHeight = (int) value.getFloat();
        Log.e("hero","---配置文件中的value==="+press+"---"+pieceBoard+"---"+baseHeight);
    }

    /**
     * 寻找中心点
     *
     * @param filePath 图片的位置
     */
    public static int jumpJump(String filePath) {
        if (!TextUtils.isEmpty(filePath) && filePath.endsWith(".png")) {
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            if (bitmap == null) {
                Log.e("hero","---取到的bitmap是null的--"+bitmap);
                return 0 ;
            }
            Log.e("hero", "--图片的宽高为--width--" + bitmap.getWidth() + "---height--" + bitmap.getHeight());
            if (!bitmap.isRecycled()) {
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();

                int piece_x_sum = 0;
                int piece_x_c = 0;
                int piece_y_max = 0;
                int board_x = 0;
                int board_y = 0;
                int scan_x_border = w / 8;//  # 扫描棋子时的左右边界
                int scan_start_y = 0;//  # 扫描的起始 y 坐标

                for (int i = h / 3; i >= h * 2 / 3; i -= 50) {
                    //i表示w轴坐标
                    int[] last_pixel = getColor(bitmap,0,i);

//                    Log.e("hero","---该点的rgb值--=="+last_pixel[0]+"--"+last_pixel[1]+"---"+last_pixel[2]);
                    for (int j = 1; j < w; j++) {
                        //j表示x轴坐标
                        int[] pixel = getColor(bitmap, j, i);
                        if ( pixel[0] != last_pixel[0] || pixel[1] != last_pixel[1] || pixel[2] != last_pixel[2]){
                            scan_start_y = i - 50;
                            break;
                        }
                    }
                    if (scan_start_y != 0) break;

                }
//                Log.e("hero","--scan_start_y--"+scan_start_y);
                for (int i = scan_start_y; i < h * 2 / 3; i++) {
                    for (int j = scan_x_border; j < w - scan_x_border; j++) {
                        int[] pixel = getColor(bitmap, j, i);
                        if ((50 < pixel[0] && pixel[0] < 60) && (53 < pixel[1] &&  pixel[1] < 63) && (95 < pixel[2] && pixel[2]< 110)){
                            piece_x_sum += j;
                            piece_x_c += 1;
                            piece_y_max = max(i, piece_y_max);
                        }
                    }
                }
                if (!(piece_x_sum != 0 && piece_x_c != 0)){
                    //TODO 返回 0,0,0,0
                    Log.e("hero","---piece_x_sum或者piece_x_c有一个0哟-"+piece_x_sum+"---"+piece_x_c);
                    return 0;
                }
                int piece_x = piece_x_sum / piece_x_c;
                int piece_y = piece_y_max - baseHeight;//应该有配置文件去做
//                Log.e("hero","----棋子的坐标点是--x==="+piece_x+"--y==="+piece_y);
                int board_x_start ,board_x_end;
                if (piece_x < w / 2){
                    board_x_start = piece_x;
                    board_x_end = w;
                }else {
                    board_x_start = 0;
                    board_x_end = piece_x;
                }
                int ii= 0;
                for (int i = h / 3; i < h * 2 / 3; i++) {
                    ii = i;
                    int[] last_pixel = getColor(bitmap, 0, i);
                    if (board_x != 0 || board_y != 0){
                        break;
                    }
                    int board_x_sum = 0;
                    int board_x_c = 0;
                    for (int j = board_x_start; j < board_x_end; j++) {
                        int[] pixel = getColor(bitmap, j, i);
                        if (Math.abs(j - piece_x) < pieceBoard){
                            continue;
                        }
                        if (Math.abs(pixel[0] - last_pixel[0]) + Math.abs(pixel[1] -  last_pixel[1]) + Math.abs(pixel[2] - last_pixel[2]) > 10){
                            board_x_sum += j;
                            board_x_c += 1;
                        }
                    }
                    if (board_x_sum != 0 ){
                        board_x = board_x_sum / board_x_c;
                    }
                }
                int[] last_pixel = getColor(bitmap,board_x,ii);
                int kk = 0;
                for (int k = ii + 274; k >= ii; k--) {
                    kk = k;
                    int[] pixel = getColor(bitmap, board_x, k);
                    if (Math.abs(pixel[0] - last_pixel[0]) +Math.abs(pixel[1] - last_pixel[1]) + Math.abs(pixel[2] - last_pixel[2]) < 10){
                        break;
                    }
                }
                board_y = (ii + kk) / 2;
                for (int l = ii; l < ii + 200; l++) {
                    int[] pixel = getColor(bitmap, board_x, l);
                    if (Math.abs(pixel[0] - 245) +Math.abs(pixel[1] - 245) + Math.abs(pixel[2] - 245) == 0){
                        board_y = l + 10;
                        break;
                    }
                }
                Log.e("hero","----四个值=="+piece_x+"--"+piece_y+"--"+board_x+"---"+board_y);
                if (!(board_x != 0 && board_y != 0)){
                    //TODO 返回 0,0,0,0
                    return 0 ;
                }
                return jumpInstance(new int[]{piece_x,piece_y,board_x,board_y});
            }
        }
        return 0;
    }
    private static int[] getColor(Bitmap bitmap,int x,int y){
        int colorPixel = bitmap.getPixel(x, y);

        return new int[]{ Color.red(colorPixel), Color.green(colorPixel),Color.blue(colorPixel)};
    }
    private static int jumpInstance(int[] location){
        int instance = (int) Math.sqrt(Math.pow((location[2] - location[0]),2)
                + Math.pow((location[3] - location[1]),2));
        Log.e("hero","---跳跃的距离--"+instance+"---系数--"+press+"");

        int v = (int) (instance * press);
        return Math.max(v, 200);//1.392 按压时间系数
    }
}
