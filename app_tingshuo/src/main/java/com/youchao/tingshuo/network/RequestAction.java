package com.youchao.tingshuo.network;

import android.content.SharedPreferences;

import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.youchao.tingshuo.utils.CommonUtlis;
import com.youchao.tingshuo.utils.ConstantUtlis;
import com.youchao.tingshuo.utils.L;
import com.youchao.tingshuo.utils.MD5Util;
import com.youchao.tingshuo.utils.SharedPreferencesUtils;
import com.youchao.tingshuo.utils.utlis.EnDecryptUtlis;
import com.youchao.tingshuo.utils.utlis.JsonUtils;

import java.util.HashMap;
import java.util.Iterator;
import static com.youchao.tingshuo.utils.SharedPreferencesUtils.mContext;
/**
 * Created by dell on 2017/9/11.
 */

public class RequestAction {


    public final static int TAG_LOGIN = 0;
    public static String FUNC_LOGIN = "login";

    public final static int TAG_SENDYZM = 1;
    public final String FUNC_SENDYZM = "http://api.youchaotech.com/V1/member/send_sms";


    public final static int TAG_REGISTER = 2;
    public final String FUNC_REGISTER = "reg";

    //发送验证码
    public RequestHandle sendyzm(String mobile) {

        return null;
    }
}
