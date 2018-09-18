package com.example.liu04.facetest;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.aip.util.Base64Util;
import com.example.liu04.facetest.utils.BitmapUtil;
import com.example.liu04.facetest.utils.Config;
import com.example.liu04.facetest.utils.FileUtil;
import com.example.liu04.facetest.utils.HttpUtil;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Base64;

/**
 * Created by liu04 on 2017/12/28.
 */

public class FaceUtils {

    public static String sToken = "24.82c471c197c15e87f153ce563e916a47.2592000.1527015600.282335-10960075";

    public static final String SERVER_TOKEN_URL = "http://123.196.123.90:90/dxt_sale/login/login_getToken.action";

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static String match(String path1, String path2) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/match";
        try {
            // 本地文件路径
//            String filePath = "[本地文件路径]";
            byte[] imgData = FileUtil.readFileByBytes(path1);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

//            String filePath2 = "[本地文件路径]";
            byte[] imgData2 = FileUtil.readFileByBytes(path2);
            String imgStr2 = Base64Util.encode(imgData2);
            String imgParam2 = URLEncoder.encode(imgStr2, "UTF-8");

            String param = "images=" + imgParam + "," + imgParam2;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = sToken;

            String result = HttpUtil.post(url, accessToken, param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String add(String path1, String path2, String userid, String user_info) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/add";
        try {
            // 本地文件路径
            byte[] imgData = FileUtil.readFileByBytes(path1);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            byte[] imgData2 = FileUtil.readFileByBytes(path2);
            String imgStr2 = Base64Util.encode(imgData2);
            String imgParam2 = URLEncoder.encode(imgStr2, "UTF-8");

            String param = "uid=" + userid + "&user_info=" + user_info + "&group_id=" + "faceSign" + "&images=" + imgParam + "," + imgParam2;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = sToken;

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String rzlAddFace(Bitmap bmp, String userid, String user_info) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/add";
        try {
            // 本地文件路径
            byte[] imgData = BitmapUtil.bitmapToByte(bmp);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "uid=" + userid + "&user_info=" + user_info + "&group_id=faceSign" + "&images=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = sToken;

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String identify(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/identify";
        try {
            // 本地文件路径
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "group_id=" + "faceSign" + "&user_top_num=" + "1" + "&face_top_num=" + "1" + "&images=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = sToken;

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String detect(String path) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v1/detect";
        try {
            // 本地文件路径
            String filePath = path;
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "max_face_num=" + 5 + "&face_fields=" + "age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities" + "&image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = sToken;

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            Log.i("liudawei", "detect: --------->" + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String update(String path1, String path2, String userid, String user_info) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/update";
        try {
            // 本地文件路径
            String filePath = path1;
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String filePath2 = path2;
            byte[] imgData2 = FileUtil.readFileByBytes(filePath2);
            String imgStr2 = Base64Util.encode(imgData2);
            String imgParam2 = URLEncoder.encode(imgStr2, "UTF-8");

            String param = "uid=" + userid + "&user_info=" + user_info + "&group_id=" + "faceSign" + "&images=" + imgParam + "," + imgParam2;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = sToken;

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getUsers() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/group/getusers";
        try {
            String param = "group_id=" + "faceSign" + "&start=" + 0 + "&end=" + 100;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = sToken;

            String result = HttpUtil.post(url, accessToken, param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String delete(String uid) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/delete";
        try {
            String param = "uid=" + uid;
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = sToken;

            return HttpUtil.post(url, accessToken, param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String rzlDelete(String uid) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/delete";
        try {
            String param = "uid=" + uid+"&group_id=faceSign";
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = sToken;

            return HttpUtil.post(url, accessToken, param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getToakenFromServer(){
        String result = null;
        try {
            result = HttpUtil.postGeneralUrl(SERVER_TOKEN_URL,"application/x-www-form-urlencoded","", "utf-8");
            JSONObject jsonObject = new JSONObject(result);
            String status = jsonObject.getString("status");
            if("ok".equals(status)){
                return jsonObject.getString("token");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

}
