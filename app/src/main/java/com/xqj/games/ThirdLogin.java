package com.xqj.games;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.xqj.games.utils.BorderRelativeLayout;
import com.xqj.mytest.MyTest;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

public class ThirdLogin {
    public static String accCode = "";

    public static boolean isSwicthAccount = false;

    public static final boolean isThirdLogin = true;

    private static String loginParameter = "";

    public static String nowAcc = "";

    public static String nowPass = "";

    public static String nowType = "a";

    private static String pp_key = "piver_cbox";

    public static long regTime = 0L;

    public static boolean syncNetworking = false;

    static int time = 30;

    static AlertDialog timealert;

    static String urlBase = "http://43.136.63.204:30006/api/";
    static String ip = "http://43.136.63.204:30009";

    EditText accText = null;
    final static String TAG = "ThirdLogin";

    private View.OnClickListener btn_Login = new View.OnClickListener(){
//        final ThirdLogin this$0;

        public void onClick(View param1View) {
            Log.d(TAG, "yep, I'am loginBtn !");
            if (isShowCheckbox() && cbox != null && !cbox.isChecked()) {
                Toast.makeText((Context)mActivity, "需要勾选协议后才能进入游戏.", Toast.LENGTH_LONG).show();
                return;
            }
            final String pass = passText.getText().toString();
            final String acc = accText.getText().toString();
            if (acc == null || "".equals(acc)) {
                SuperTools.getInstance().showErrorDialog("系统信息", "请输入帐号。");
                return;
            }
            if (pass == null || "".equals(pass)) {
                SuperTools.getInstance().showErrorDialog("系统信息", "请输入密码。");
                return;
            }
            if (saveCbox.isChecked()) {
                SuperTools.getInstance().writeLocalFile("v_sdklogin_pass", pass);
                SuperTools.getInstance().writeLocalFile("v_sdklogin_acc", acc);
            }
            ThirdLogin.nowAcc = acc.trim();
            ThirdLogin.nowPass = pass.trim();
            ThirdLogin.nowType = "a";
            final AlertDialog alert = SuperTools.getInstance().showLoadingAlert(mActivity, "登录中。。。。");
            if (ThirdLogin.syncNetworking)
                return;
            ThirdLogin.syncNetworking = true;
            (new Thread(new Runnable() {
//                final ThirdLogin.null this$1;
//
//                final String val$acc;
//
//                final AlertDialog val$alert;
//
//                final String val$pass;

                public void run() {
                    try {
                        SuperTools superTools = SuperTools.getInstance();
                        StringBuilder stringBuilder = new StringBuilder();
//                        this();
                        stringBuilder.append(ThirdLogin.urlBase);
                        stringBuilder.append("checkuserpass?name=");
                        stringBuilder.append(URLEncoder.encode(acc.trim(), "Utf-8"));
                        stringBuilder.append("&pass=");
                        stringBuilder.append(URLEncoder.encode(pass.trim(), "Utf-8"));
                        JSONObject jSONObject = superTools.postHttpJson(stringBuilder.toString());
                        try {
                            Thread.sleep(500L);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        ThirdLogin.syncNetworking = false;
                        alert.dismiss();
                        try {
                            Activity activity1;
                            int i = 1;
                            if (jSONObject!=null)i = jSONObject.getInt("rt");
                            if (i != -2) {
                                if (i != -1) {
                                    if (i != 0) {
                                        mActivity.runOnUiThread(new Runnable() {
//                                            final ThirdLogin.null.null this$2;

                                            public void run() {
                                                ThirdLogin.this.run();
                                            }
                                        });
                                        return;
                                    }
                                    if (System.currentTimeMillis() - ThirdLogin.regTime < 60000L) {
                                        ThirdLogin.time = 30;
                                        ThirdLogin.timealert = null;
                                        while (true) {
                                            i = ThirdLogin.time;
                                            ThirdLogin.time = i - 1;
                                            if (i > 0) {
                                                activity1 = mActivity;
                                                Runnable runnable1 = new Runnable() {
//                                                    final ThirdLogin.null.null this$2;

                                                    public void run() {
                                                        int i = ThirdLogin.time;
                                                        if (ThirdLogin.timealert == null) {
                                                            ThirdLogin.timealert = (new AlertDialog.Builder((Context)mActivity)).create();
                                                            AlertDialog alertDialog1 = ThirdLogin.timealert;
                                                            StringBuilder stringBuilder1 = new StringBuilder();
                                                            stringBuilder1.append("帐号数据同步中，请待后(");
                                                            stringBuilder1.append(i);
                                                            stringBuilder1.append("s)");
                                                            alertDialog1.setMessage(stringBuilder1.toString());
                                                            ThirdLogin.timealert.show();
                                                            ThirdLogin.timealert.setCancelable(false);
                                                        }
                                                        AlertDialog alertDialog = ThirdLogin.timealert;
                                                        StringBuilder stringBuilder = new StringBuilder();
                                                        stringBuilder.append("帐号数据同步中，请待后(");
                                                        stringBuilder.append(i);
                                                        stringBuilder.append("s)");
                                                        alertDialog.setMessage(stringBuilder.toString());
                                                    }
                                                };
//                                                super(this);
                                                activity1.runOnUiThread(runnable1);
                                                Thread.sleep(1000L);
                                                continue;
                                            }
                                            if (ThirdLogin.timealert != null)
                                                ThirdLogin.timealert.dismiss();
                                            ThirdLogin.timealert = null;
                                            SuperTools.getInstance().showLoadingAlertOnUi(mActivity, "帐号数据同步完成！");
                                            return;
                                        }
                                    }
                                    SuperTools.getInstance().showErrorDialog("系统消息", "用户名或密码错误,请重试.");
                                    return;
                                }
                                SuperTools.getInstance().showErrorDialog("系统消息", "系统错误.");
                                return;
                            }
//                            accCode = activity1.getString("accCode");
                            Activity activity2 = mActivity;
                            Runnable runnable = new Runnable() {
//                                final ThirdLogin.null.null this$2;

                                public void run() {
                                    showTrueNameDilog(mActivity);
                                }
                            };
//                            super(this);
                            activity2.runOnUiThread(runnable);
                            return;
                        } catch (JSONException jSONException) {
                            jSONException.printStackTrace();
                            SuperTools.getInstance().showErrorDialog("系统消息", "系统错误.");
                            return;
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        SuperTools.getInstance().showErrorDialog("系统消息", "系统错误.");
                        return;
                    }
                }
            })).start();
        }
    };

    private CheckBox cbox = null;

    private View.OnClickListener hf_Login = new View.OnClickListener() {
//      final ThirdLogin this$0;

        public void onClick(View param1View) {
            Log.d(TAG, "helfshif客服点击按钮回调事件");
        }
    };

    private Activity mActivity = null;

    private RelativeLayout mRelativeLayout = null;

    EditText passText = null;

    CheckBox saveCbox = null;

    String unionId = "0";

    private ThirdLogin(RelativeLayout paramRelativeLayout) {
        mRelativeLayout = paramRelativeLayout;
        mActivity = SuperTools.getInstance().getCurActivity();
        unionId = PropertiesData.getInstance().getString("UNION_ID");
        setWidget();
    }

    public static void callLoginUi() {
        try {
            Intent intent = new Intent((Context) MyTest.getInstance(), LoginActivity.class);
//      this((Context)MyTest.getInstance(), LoginActivity.class);
            MyTest.getInstance().startActivity(intent);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static ThirdLogin create(RelativeLayout paramRelativeLayout) {
        return new ThirdLogin(paramRelativeLayout);
    }

    public static void entryGame(final String data) {
        Log.d(TAG, "entryGame: " +data);
        (new Thread() {
//        final String val$data;

            public void run() {
                SuperTools.getInstance().getCurActivity().runOnUiThread(new Runnable() {
//                final null this$0;

                    public void run() {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("===========entryGame:");
                        stringBuilder.append(data);
                        Log.d(TAG, stringBuilder.toString());
                        loginParameter = data;
                        if (MainActivity.getCurPageIndex() != 3) {
                            MainActivity.switchStatus(3);
                        } else {
                            if (MainActivity.getCurLayout() != null)
                                MainActivity.getCurLayout().removeAllViews();
                            Toast.makeText((Context) SuperTools.getInstance().getCurActivity(), "进入游戏", Toast.LENGTH_LONG).show();
                            CPPManager.getInstance().noticeLoginGame(loginParameter);
                        }
                    }
                });
            }
        }).start();
    }

    public static void fromClickLoginBtn() {
    }

    public static void fromCloseLoginUI() {
    }

    public static void fromLoginStatus(boolean paramBoolean) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("==========LoginStatus:");
        stringBuilder.append(String.valueOf(paramBoolean));
        stringBuilder.append(String.valueOf(paramBoolean));
        Log.d(TAG, "fromLoginStatus "+stringBuilder.toString());
        if (!paramBoolean)
            create(MainActivity.getCurLayout()).run();
    }

    public static void fromOpenLoginUI(boolean paramBoolean) {
        if (isSwicthAccount) {
            isSwicthAccount = false;
            Toast.makeText((Context) SuperTools.getInstance().getCurActivity(), "进入游戏", Toast.LENGTH_LONG).show();
            CPPManager.getInstance().noticeLoginGame(loginParameter);
        } else if (paramBoolean) {
            Toast.makeText((Context) SuperTools.getInstance().getCurActivity(), "进入游戏", Toast.LENGTH_LONG).show();
            CPPManager.getInstance().noticeLoginGame(loginParameter);
        } else {
            create(MainActivity.getCurLayout()).run();
        }
    }

    private void setWidget() {
        int j = SuperTools.getInstance().getWidth();
        int i = SuperTools.getInstance().getHeight();
        float f = j / 436.0F;
        try {
            PropertiesData.getInstance().getString("UNION_ID");
            InputStream inputStream = mActivity.getAssets().open("loginBg.png");
            Bitmap bitmap2 = GameView.getNewBitmap(BitmapFactory.decodeStream(inputStream));
            ImageView imageView = new ImageView((Context) mActivity);
//      this((Context)mActivity);
            imageView.setImageBitmap(bitmap2);
            RelativeLayout relativeLayout = mRelativeLayout;
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(j, i);
//      this(j, i);
            relativeLayout.addView((View) imageView, layoutParams);
            inputStream.reset();
            inputStream = mActivity.getAssets().open("loginBtn.png");
            Bitmap bitmap1 = BitmapFactory.decodeStream(inputStream);
            f *= 0.95F;
            i = GameView.getNewBitmap(bitmap1, f, f).getWidth();
            initLoginInputWidget();
            unionLayout(i);
            inputStream.reset();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    private void showTrueNameDilog(Activity paramActivity) {
        int i = SuperTools.getInstance().getWidth();
        SuperTools.getInstance().getHeight();
        new ScrollView((Context) paramActivity);
        final BorderRelativeLayout rl = new BorderRelativeLayout((Context) paramActivity);
        rl.setBackgroundColor(-7829368);
        rl.setBorderStrokeWidth(4.0F);
        rl.setNeedTopBorder(true);
        rl.setNeedLeftBorder(true);
        rl.setBorderColor(-1);
        BootstrapLabel bootstrapLabel = new BootstrapLabel((Context) paramActivity);
        bootstrapLabel.setText("实名认证");
        bootstrapLabel.setTextColor(-16777216);
        bootstrapLabel.setTextSize(40.0F);
        TextView textView2 = new TextView((Context) paramActivity);
        textView2.setText(" 姓　名：");
        textView2.setTextSize(25.0F);
        textView2.setTextColor(-1);
        TextView textView1 = new TextView((Context) paramActivity);
        textView1.setText(" 身份证：");
        textView1.setTextSize(25.0F);
        textView1.setTextColor(-1);
        final BootstrapEditText nameEdit = new BootstrapEditText((Context) paramActivity);
        nameEdit.setRounded(true);
        nameEdit.setWidth(300);
        final BootstrapEditText cardidEdit = new BootstrapEditText((Context) paramActivity);
        cardidEdit.setRounded(true);
        cardidEdit.setWidth(300);
        BootstrapButton bootstrapButton = new BootstrapButton((Context) paramActivity);
        bootstrapButton.setTextSize(30.0F);
        bootstrapButton.setText("确 定");
        bootstrapButton.setBottom(2);
        bootstrapButton.setBackgroundColor(-12303292);
        LinearLayout linearLayout2 = new LinearLayout((Context) paramActivity);
        linearLayout2.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout2.setGravity(1);
        linearLayout2.addView((View) bootstrapLabel);
        LinearLayout linearLayout3 = new LinearLayout((Context) paramActivity);
        linearLayout3.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        linearLayout3.addView((View) textView2);
        linearLayout3.addView((View) nameEdit);
        LinearLayout linearLayout4 = new LinearLayout((Context) paramActivity);
        linearLayout4.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        linearLayout4.addView((View) textView1);
        linearLayout4.addView((View) cardidEdit);
        LinearLayout linearLayout1 = new LinearLayout((Context) paramActivity);
        linearLayout1.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        linearLayout1.setGravity(1);
        linearLayout1.addView((View) bootstrapButton);
        i = (rl.getWidth() + i / 3 * 2) / 2;
        linearLayout2.setY(100);
        linearLayout3.setY('Ĭ');
        linearLayout4.setY('Ǵ');
        linearLayout1.setY('ʼ');
        rl.addView((View) linearLayout2);
        rl.addView((View) linearLayout3);
        rl.addView((View) linearLayout4);
        rl.addView((View) linearLayout1);
        mRelativeLayout.addView((View) rl);
        bootstrapButton.setOnClickListener(new View.OnClickListener() {
//          final ThirdLogin this$0;
//
//          final BootstrapEditText val$cardidEdit;
//
//          final BootstrapEditText val$nameEdit;
//
//          final BorderRelativeLayout val$rl;

            public void onClick(View param1View) {
                final String idnum = cardidEdit.getText().toString();
                final String cardname = nameEdit.getText().toString();
                final AlertDialog alert = SuperTools.getInstance().showLoadingAlert(mActivity, "验证中。。。。");
                if (syncNetworking)
                    return;
                syncNetworking = true;
                SuperTools.getInstance();
                SuperTools.runByThread(new Runnable() {
//                  final null this$1;
//
//                  final AlertDialog val$alert;
//
//                  final String val$cardname;
//
//                  final String val$idnum;

                    public void run() {
                        JSONObject jSONObject = postTureMsg(idnum, cardname);
                        try {
                            Thread.sleep(500L);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        syncNetworking = false;
                        alert.dismiss();
                        if (jSONObject == null) {
                            SuperTools.getInstance().showErrorDialog("系统消息", "网络超时，请重试。");
                            return;
                        }
                        try {
                            int i = jSONObject.getInt("rt");
                            if (i != -3) {
                                if (i != -2 && i != -1 && i != 0) {
                                    if (i == 1) {
                                        Activity activity = mActivity;
                                        Runnable runnable = new Runnable() {
//                                final null.null this$2;

                                            public void run() {
                                                SCDialog.create(mActivity, "系统消息", "实名成功！", "确认", "", new OnMyClickedListener() {
//                                        final null.null.null this$3;

                                                    public void onNegative(JSONObject param4JSONObject) {
                                                        mRelativeLayout.removeView((View) rl);
                                                        run();
                                                    }

                                                    public void onPositive(JSONObject param4JSONObject) {
                                                        mRelativeLayout.removeView((View) rl);
                                                        run();
                                                    }
                                                }).show();
                                            }
                                        };
//                            super(this);
                                        activity.runOnUiThread(runnable);
                                    }
                                } else {
                                    SuperTools.getInstance().showErrorDialog("系统消息", "输入错误，请确认身份证和名字。");
                                }
                            } else {
                                SuperTools.getInstance().showErrorDialog("系统消息", "未满18岁人士请让监护人协同认证。");
                            }
                        } catch (JSONException jSONException) {
                            jSONException.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void unionLayout(int paramInt) throws IOException {
        int j = SuperTools.getInstance().getWidth();
        int i = SuperTools.getInstance().getHeight();
        if (isShowCheckbox()) {
            LinearLayout linearLayout1 = new LinearLayout((Context) mActivity);
            LinearLayout linearLayout2 = new LinearLayout((Context) mActivity);
            paramInt = i / 5 * 4;
            linearLayout1.setY((paramInt + 30));
            linearLayout2.setY((paramInt + 120));
            cbox = new CheckBox((Context) mActivity);
            cbox.setScaleX(0.5F);
            cbox.setScaleY(0.5F);
            cbox.setChecked(true);
            cbox.setText("我已阅读并同意游戏用户协议和隐私保护指引");
            TextView textView = new TextView((Context) mActivity);
            textView.setText("用户协议和隐私保护指引");
            textView.setTextColor(-65536);
            textView.setTextSize(7);
            textView.setOnClickListener(new View.OnClickListener() {
//            final ThirdLogin this$0;

                public void onClick(View param1View) {
                    SuperTools.getInstance().openURL("http://xqpc.gxqiuming.cn/static/xy.html");
                }
            });
            cbox.setOnClickListener(new View.OnClickListener() {
//            final ThirdLogin this$0;

                public void onClick(View param1View) {
                    SharedPreferences.Editor editor = mActivity.getSharedPreferences("SP", 0).edit();
                    editor.putBoolean(pp_key, cbox.isChecked());
                    editor.commit();
                }
            });
            Activity activity = mActivity;
            paramInt = 0;
            if (activity.getSharedPreferences("SP", 0).getBoolean(pp_key, false))
                cbox.setChecked(true);
            linearLayout1.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            linearLayout1.setGravity(1);
            linearLayout1.addView((View) cbox);
            linearLayout2.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            linearLayout2.setGravity(1);
            linearLayout2.addView((View) textView);
            mRelativeLayout.addView((View) linearLayout1);
            mRelativeLayout.addView((View) linearLayout2);
            InputStream inputStream = mActivity.getAssets().open("th16.png");
            ImageView imageView = new ImageView((Context) mActivity);
            imageView.setImageBitmap(GameView.getNewBitmap(BitmapFactory.decodeStream(inputStream), 1.0F, 1.0F));
            imageView.setX((j - 150));
            imageView.setY(50.0F);
            imageView.setOnClickListener(new View.OnClickListener() {
//            final ThirdLogin this$0;

                public void onClick(View param1View) {
                    AlertDialog.Builder builder = new AlertDialog.Builder((Context) mActivity);
                    builder.setMessage("1、本游戏是一款玩法丰富的角色类游戏，适用于年满18周岁及以上的用户，建议未成年人在家长监护下使用游戏产品\n\n2、本游戏通过基于抽卡养成的玩家惊喜展开，通过主线任务推进剧情发展，游戏中还有养成培养、收集装备、副本体验等玩法，游戏中提供了少量基于文字的社交系统，该社交系统会严格遵循相关法律法规进行管理。\n3、本游戏中有用户实名认证系统，认证为未成年人的用户将接受以下管理:\n8周岁以上未满16周岁的用户，单次充值金额不得超过50元人民币每月充值金额累计不得超过200元人民币: 16周岁以上未满18周岁的用户，单次充值金额不得超过100元人民币，每月充值金额累计不得超过400元人民币。\n未成年人用户仅可在周五、周六、周日和法定节假日的20时至21时登录游戏。\n4、游戏在场景中的操作有利于锻炼玩家的手眼协调、空间感知能力和快速反应能力，游戏中的团队玩法有助于提升玩家的沟通能力、协作能力、大局观，游戏中的养成线培养有助于提升玩家的资源管理能力.数学统计能力。");
                    builder.setCancelable(true);
                    builder.show();
                }
            });
            mRelativeLayout.addView((View) imageView);
            String[] arrayOfString = new String[3];
            arrayOfString[0] = "抵制不良游戏 拒绝盗版游戏 注意自我保护 谨防受骗上当 适度游戏益脑 沉迷游戏伤身 合理安排时间 享受健康生活";
            arrayOfString[1] = "游戏名：寻秦 著作权人：广州盈正信息技术有限公司 软著登记号：2012SR027725";
            arrayOfString[2] = "审批文号：新广出审[201612895号 出版物号：ISBN978-7-7979-1517-5 出版单位：广州盈正信息技术有限公司";
            while (paramInt < arrayOfString.length) {
                TextView textView1 = new TextView((Context) mActivity);
                textView1.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
                textView1.setGravity(1);
                textView1.setText(arrayOfString[paramInt]);
                textView1.setTextColor(-1);
                textView1.setTextSize(6);
                textView1.setY((i - 120 + paramInt * 30));
                mRelativeLayout.addView((View) textView1);
                paramInt++;
            }
        }
    }

    public void doOfficelogin() {
    }

    public void initLoginInputWidget() {
        int i = SuperTools.getInstance().getWidth();
        int j = SuperTools.getInstance().getHeight() / 2;
        LinearLayout linearLayout1 = new LinearLayout((Context) mActivity);
        i /= 2;
        float f2 = (i - 300);
        linearLayout1.setX(f2);
        linearLayout1.setY(j);
        TextView textView2 = new TextView((Context) mActivity);
        textView2.setText("帐号：");
        float f1 = 20;
        textView2.setTextSize(f1);
        textView2.setTextColor(-1);
        linearLayout1.addView((View) textView2);
        accText = newEditInput(mActivity);
        textView2 = new TextView((Context) mActivity);
        textView2.setText("  ");
        Button button2 = newButton(mActivity);
        button2.setText("密保");
        button2.setTextSize(15.0F);
        button2.setOnClickListener(new View.OnClickListener() {
//          final ThirdLogin this$0;

            public void onClick(View param1View) {
                nowType = "b";
                run();
            }
        });
        linearLayout1.addView((View) accText);
        linearLayout1.addView((View) textView2);
        mRelativeLayout.addView((View) linearLayout1);
        linearLayout1 = new LinearLayout((Context) mActivity);
        linearLayout1.setX(f2);
        j += 150;
        linearLayout1.setY(j);
        textView2 = new TextView((Context) mActivity);
        textView2.setText("密码：");
        textView2.setTextSize(f1);
        textView2.setTextColor(-1);
        linearLayout1.addView((View) textView2);
        passText = newEditInput(mActivity);
        passText.setInputType(129);
        linearLayout1.addView((View) passText);
        mRelativeLayout.addView((View) linearLayout1);
        LinearLayout linearLayout2 = new LinearLayout((Context) mActivity);
        linearLayout2.setX((i - 160));
        j += 100;
        linearLayout2.setY(j);
        saveCbox = new CheckBox((Context) mActivity);
        saveCbox.setScaleX(0.6F);
        saveCbox.setScaleY(0.6F);
        saveCbox.setClickable(true);
        saveCbox.setText("保存密码");
        TextView textView1 = new TextView((Context) mActivity);
        textView1.setText("保存密码");
        textView1.setTextColor(-1);
        textView1.setTextSize(11.0F);
        mRelativeLayout.addView((View) linearLayout2);
        linearLayout2 = new LinearLayout((Context) mActivity);
        linearLayout2.setX((i - 260));
        linearLayout2.setY((j + 150));
        Button button3 = newButton(mActivity);
        button3.setText("登录");
        Button button1 = newButton(mActivity);
        button1.setText("注册");
        TextView textView3 = new TextView((Context) mActivity);
        textView3.setWidth(150);
        linearLayout2.addView((View) button1);
        linearLayout2.addView((View) textView3);
        linearLayout2.addView((View) button3);
        mRelativeLayout.addView((View) linearLayout2);
        button3.setOnClickListener(btn_Login);
        String str = SuperTools.getInstance().readLocalFile("issave");
        if ("".equals(str)) {
            saveCbox.setChecked(true);
            SuperTools.getInstance().writeLocalFile("issave", "1");
        }
        if ("1".equals(str)) {
            saveCbox.setChecked(true);
            accText.setText(SuperTools.getInstance().readLocalFile("v_sdklogin_acc"));
            passText.setText(SuperTools.getInstance().readLocalFile("v_sdklogin_pass"));
        }
        if ("2".equals(str))
            saveCbox.setChecked(false);
        saveCbox.setOnClickListener(new View.OnClickListener() {
//          final ThirdLogin this$0;

            public void onClick(View param1View) {
                String str;
                SuperTools superTools = SuperTools.getInstance();
                if (saveCbox.isChecked()) {
                    str = "1";
                } else {
                    str = "2";
                }
                superTools.writeLocalFile("issave", str);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
//          final ThirdLogin this$0;

            public void onClick(View param1View) {
//            ThirdLogin thirdLogin = this;
                showRegUi(mActivity);
            }
        });
    }

    public boolean isShowCheckbox() {
        return (unionId.equals("91141") || unionId.equals("97503") || unionId.equals("97504") || unionId.equals("97505") || unionId.equals("97506") || unionId.equals("97507"));
    }

    public Button newButton(Activity paramActivity) {
        Button button = new Button((Context) paramActivity);
        button.setBackgroundColor(-7829368);
        button.setTextColor(-1);
        button.setTextSize(23.0F);
        button.setTypeface(Typeface.DEFAULT_BOLD);
        ShapeDrawable shapeDrawable = new ShapeDrawable((Shape) new RoundRectShape(new float[]{10.0F, 10.0F, 10.0F, 10.0F, 10.0F, 10.0F, 10.0F, 10.0F}, null, null));
        shapeDrawable.getPaint().setColor(-7829368);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        button.setBackground((Drawable) shapeDrawable);
        return button;
    }

    public EditText newEditInput(Activity paramActivity) {
        EditText editText = new EditText((Context) paramActivity);
        ShapeDrawable shapeDrawable = new ShapeDrawable((Shape) new RoundRectShape(new float[]{10.0F, 10.0F, 10.0F, 10.0F, 10.0F, 10.0F, 10.0F, 10.0F}, null, null));
        editText.setWidth(400);
        shapeDrawable.getPaint().setColor(-1);
        editText.setBackground((Drawable) shapeDrawable);
        return editText;
    }

    public JSONObject postTureMsg(String paramString1, String paramString2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(urlBase);
        stringBuilder.append("gotruename");
        String str = stringBuilder.toString();
        try {
            SuperTools superTools = SuperTools.getInstance();
            StringBuilder stringBuilder1 = new StringBuilder();
//      this();
            stringBuilder1.append(str);
            stringBuilder1.append("?accCode=");
            stringBuilder1.append(accCode);
            stringBuilder1.append("&idnum=");
            stringBuilder1.append(paramString1);
            stringBuilder1.append("&cardname=");
            stringBuilder1.append(URLEncoder.encode(paramString2, "utf-8"));
            return superTools.postHttpJson(stringBuilder1.toString());
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public void run() {
        Log.d(TAG, "Begin to deal your business!");
        Intent intent = new Intent((Context) MainActivity.mActivity, MyTest.class);
        MainActivity.mActivity.startActivity(intent);
        Log.d(TAG, "login success");
    }

    public void showRegUi(final Activity activity) {
        int i = SuperTools.getInstance().getWidth();
        SuperTools.getInstance().getHeight();
        new ScrollView((Context) activity);
        final BorderRelativeLayout rl = new BorderRelativeLayout((Context) activity);
        rl.setBackgroundColor(-7829368);
        rl.setBorderStrokeWidth(4.0F);
        rl.setNeedTopBorder(true);
        rl.setNeedLeftBorder(true);
        rl.setBorderColor(-1);
        BootstrapLabel bootstrapLabel = new BootstrapLabel((Context) activity);
        bootstrapLabel.setText("注册帐号");
        bootstrapLabel.setTextColor(-16777216);
        bootstrapLabel.setTextSize(40.0F);
        TextView textView2 = new TextView((Context) activity);
        TextView textView3 = new TextView((Context) activity);
        textView3.setText("    帐号名：");
        textView3.setTextSize(25.0F);
        textView3.setTextColor(-1);
        final TextView passText = new TextView((Context) activity);
        passText.setText("    密 码：");
        passText.setTextSize(25.0F);
        passText.setTextColor(-1);
        TextView textView5 = new TextView((Context) activity);
        textView5.setText("    确认密码：");
        textView5.setTextSize(25.0F);
        textView5.setTextColor(-1);
        final BootstrapEditText nameEdit = new BootstrapEditText((Context) activity);
        nameEdit.setRounded(true);
        nameEdit.setWidth(300);
        final BootstrapEditText pass1Edit = new BootstrapEditText((Context) activity);
        pass1Edit.setRounded(true);
        pass1Edit.setWidth(300);
        final BootstrapEditText pass2Edit = new BootstrapEditText((Context) activity);
        pass2Edit.setRounded(true);
        pass2Edit.setWidth(300);
        TextView textView4 = new TextView((Context) activity);
        textView4.setText("    手机号：");
        textView4.setTextSize(25.0F);
        textView4.setTextColor(-1);
        final BootstrapEditText phoneEdit = new BootstrapEditText((Context) activity);
        phoneEdit.setRounded(true);
        phoneEdit.setWidth(300);
        final BootstrapEditText checkcodeEdit = new BootstrapEditText((Context) activity);
        checkcodeEdit.setRounded(true);
        checkcodeEdit.setWidth(300);
        checkcodeEdit.setHint("输入验证码");
        final BootstrapButton checkcodeButton = new BootstrapButton((Context) activity);
        checkcodeButton.setTextSize(25.0F);
        checkcodeButton.setText("获取");
        BootstrapButton bootstrapButton3 = new BootstrapButton((Context) activity);
        bootstrapButton3.setTextSize(30.0F);
        bootstrapButton3.setText("注  册");
        bootstrapButton3.setBottom(2);
        bootstrapButton3.setBackgroundColor(-12303292);
        BootstrapButton bootstrapButton2 = new BootstrapButton((Context) activity);
        bootstrapButton2.setTextSize(30.0F);
        bootstrapButton2.setText("返  回");
        bootstrapButton2.setBottom(2);
        bootstrapButton2.setBackgroundColor(-12303292);
        LinearLayout linearLayout1 = new LinearLayout((Context) activity);
        linearLayout1.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        linearLayout1.setOrientation(LinearLayout.VERTICAL);
        linearLayout1.setGravity(1);
        linearLayout1.addView((View) bootstrapLabel);
        LinearLayout linearLayout2 = new LinearLayout((Context) activity);
        linearLayout2.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        linearLayout2.addView((View) textView3);
        linearLayout2.addView((View) nameEdit);
        LinearLayout linearLayout5 = new LinearLayout((Context) activity);
        linearLayout5.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        linearLayout5.addView((View) passText);
        linearLayout5.addView((View) pass1Edit);
        LinearLayout linearLayout3 = new LinearLayout((Context) activity);
        linearLayout5.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        linearLayout3.addView((View) textView5);
        linearLayout3.addView((View) pass2Edit);
        LinearLayout linearLayout6 = new LinearLayout((Context) activity);
        linearLayout6.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        linearLayout6.addView((View) textView4);
        linearLayout6.addView((View) phoneEdit);
        LinearLayout linearLayout7 = new LinearLayout((Context) activity);
        linearLayout7.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        linearLayout7.addView((View) checkcodeEdit);
        linearLayout7.addView((View) checkcodeButton);
        LinearLayout linearLayout4 = new LinearLayout((Context) activity);
        linearLayout4.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        linearLayout4.setGravity(1);
        linearLayout4.addView((View) bootstrapButton2);
        textView2.setText("    ");
        linearLayout4.addView((View) textView2);
        linearLayout4.addView((View) bootstrapButton3);
        i = (rl.getWidth() + i / 3 * 2) / 2 - 250;
        linearLayout1.setY(100);
        linearLayout2.setY('ú');
        float f = i;
        linearLayout2.setX(f);
        linearLayout5.setY('Ɛ');
        linearLayout5.setX((i + 50));
        linearLayout3.setY('Ȧ');
        linearLayout3.setX((i - 50));
        linearLayout6.setY('ʼ');
        linearLayout6.setX(f);
        linearLayout7.setY('͒');
        linearLayout7.setX((i + 100));
        linearLayout4.setY('Ϩ');
        rl.addView((View) linearLayout1);
        rl.addView((View) linearLayout2);
        rl.addView((View) linearLayout5);
        rl.addView((View) linearLayout3);
        rl.addView((View) linearLayout6);
        rl.addView((View) linearLayout7);
        rl.addView((View) linearLayout4);
        mRelativeLayout.addView((View) rl);
        final HashMap<Object, Object> header = new HashMap<Object, Object>();
        bootstrapButton2.setOnClickListener(new View.OnClickListener() {
//          final ThirdLogin this$0;
//
//          final BorderRelativeLayout val$rl;

            public void onClick(View param1View) {
                mRelativeLayout.removeView((View) rl);
            }
        });
        bootstrapButton3.setOnClickListener(new View.OnClickListener() {
//          final ThirdLogin this$0;
//
//          final Activity val$activity;
//
//          final BootstrapEditText val$checkcodeEdit;
//
//          final Map val$header;
//
//          final String val$ip;
//
//          final BootstrapEditText val$nameEdit;
//
//          final BootstrapEditText val$pass1Edit;
//
//          final BootstrapEditText val$pass2Edit;
//
//          final TextView val$passText;
//
//          final BootstrapEditText val$phoneEdit;
//
//          final BorderRelativeLayout val$rl;

            public void onClick(View param1View) {
                final String username = nameEdit.getText().toString();
                final String pass1 = pass1Edit.getText().toString();
                String str3 = pass2Edit.getText().toString();
                final String phone = phoneEdit.getText().toString();
                final String checkCode = checkcodeEdit.getText().toString();
                if (TextUtils.isEmpty(username)) {
                    nameEdit.requestFocus();
                    SuperTools.getInstance().showLoadingAlert(mActivity, "请输入帐号名");
                    return;
                }
                if (!Pattern.compile("^[a-z0-9A-Z]+$").matcher(username).matches() || username.length() < 6 || username.length() > 12) {
                    SuperTools.getInstance().showLoadingAlert(mActivity, "帐号名错误，必需是数字和字母6-12个字符");
                    return;
                }
                if (TextUtils.isEmpty(pass1)) {
                    pass1Edit.requestFocus();
                    SuperTools.getInstance().showLoadingAlert(mActivity, "请输入密码");
                    return;
                }
                if (TextUtils.isEmpty(str3)) {
                    pass2Edit.requestFocus();
                    SuperTools.getInstance().showLoadingAlert(mActivity, "请重复输入密码");
                    return;
                }
                if (!pass1.equals(str3)) {
                    SuperTools.getInstance().showLoadingAlert(mActivity, "密码两次输入不同");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    phoneEdit.requestFocus();
                    SuperTools.getInstance().showLoadingAlert(mActivity, "请输入手机号");
                    return;
                }
                if (!Pattern.compile("^0?1[3|4|5|6|7|8|9][0-9]\\d{8}$").matcher(phone).matches()) {
                    SuperTools.getInstance().showLoadingAlert(mActivity, "手机号错误。");
                    return;
                }
                if (TextUtils.isEmpty(checkCode)) {
                    checkcodeEdit.requestFocus();
                    SuperTools.getInstance().showLoadingAlert(mActivity, "请输入验证码");
                    return;
                }
                SuperTools.runByThread(new Runnable() {
//                  final null this$1;
//
//                  final String val$checkCode;
//
//                  final String val$pass1;
//
//                  final String val$phone;
//
//                  final String val$username;

                    public void run() {
                        try {
                            boolean bool = syncNetworking;
                            if (bool) {
                                syncNetworking = false;
                                return;
                            }
                            syncNetworking = true;
                            StringBuilder stringBuilder2 = new StringBuilder();
//                      this();
                            stringBuilder2.append(ip);
                            stringBuilder2.append("/api/reg?name=");
                            stringBuilder2.append(URLEncoder.encode(username, "utf-8"));
                            stringBuilder2.append("&password=");
                            stringBuilder2.append(URLEncoder.encode(pass1, "utf-8"));
                            stringBuilder2.append("&phone=");
                            stringBuilder2.append(URLEncoder.encode(phone, "utf-8"));
                            stringBuilder2.append("&checkcode=");
                            stringBuilder2.append(checkCode);
                            String str = stringBuilder2.toString();
                            StringBuilder stringBuilder3 = new StringBuilder();
//                      this();
                            stringBuilder3.append("url=");
                            stringBuilder3.append(str);
                            Log.d(TAG, "xqj "+stringBuilder3.toString());
//                      JSONObject jSONObject = SuperTools.getInstance().postHttpJson(str, header);
                            JSONObject jSONObject = SuperTools.getInstance().postHttpJson(str);
                            StringBuilder stringBuilder1 = new StringBuilder();
//                      this();
                            stringBuilder1.append("rtjson=");
                            stringBuilder1.append(jSONObject.toString());
                            Log.d(TAG, "xqj "+stringBuilder1.toString());
                            if (!"ok".equals(jSONObject.getString("state"))) {
                                SuperTools.getInstance().showLoadingAlertOnUi(mActivity, jSONObject.getString("msg"));
                                syncNetworking = false;
                                return;
                            }
                            regTime = System.currentTimeMillis();
                            SuperTools.getInstance().writeLocalFile("v_sdklogin_acc", username);
                            SuperTools.getInstance().writeLocalFile("v_sdklogin_pass", pass1);
                            time = 30;
                            timealert = null;
                            while (true) {
                                int i = time;
                                time = i - 1;
                                if (i > 0) {
                                    Activity activity1 = activity;
                                    Runnable runnable1 = new Runnable() {
//                              final null.null this$2;

                                        public void run() {
                                            int i = time;
                                            if (timealert == null) {
                                                timealert = (new AlertDialog.Builder((Context) activity)).create();
                                                AlertDialog alertDialog1 = timealert;
                                                StringBuilder stringBuilder1 = new StringBuilder();
                                                stringBuilder1.append("帐号注册中。。。。。(");
                                                stringBuilder1.append(i);
                                                stringBuilder1.append("s)");
                                                alertDialog1.setMessage(stringBuilder1.toString());
                                                timealert.show();
                                                timealert.setCancelable(false);
                                            }
                                            AlertDialog alertDialog = timealert;
                                            StringBuilder stringBuilder = new StringBuilder();
                                            stringBuilder.append("帐号注册中。。。。。(");
                                            stringBuilder.append(i);
                                            stringBuilder.append("s)");
                                            alertDialog.setMessage(stringBuilder.toString());
                                        }
                                    };
//                          super(this);
                                    activity1.runOnUiThread(runnable1);
                                    Thread.sleep(1000L);
                                    continue;
                                }
                                if (timealert != null)
                                    timealert.dismiss();
                                timealert = null;
                                SuperTools.getInstance().showLoadingAlertOnUi(mActivity, "注册成功！！！");
                                Activity activity = mActivity;
                                Runnable runnable = new Runnable() {
//                            final null.null this$2;

                                    public void run() {
                                        mRelativeLayout.removeView((View) rl);
                                        accText.setText(SuperTools.getInstance().readLocalFile("v_sdklogin_acc"));
                                        passText.setText(SuperTools.getInstance().readLocalFile("v_sdklogin_pass"));
                                    }
                                };
//                        super(this);
                                SuperTools.runByUiThread(activity, runnable);
                                syncNetworking = false;
                                return;
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        } finally {
                            Exception exception;
                        }
                        syncNetworking = false;
                    }
                });
            }
        });
        nameEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//          final ThirdLogin this$0;
//
//          final String val$ip;
//
//          final BootstrapEditText val$nameEdit;

            public void onFocusChange(View param1View, boolean param1Boolean) {
                if (!param1Boolean) {
                    final String username = nameEdit.getText().toString();
                    if (username == null || "".equals(username)) {
                        SuperTools.getInstance().showLoadingAlert(mActivity, "请输入帐号名");
                        return;
                    }
                    if (!Pattern.compile("^[a-z0-9A-Z]+$").matcher(username).matches() || username.length() < 6 || username.length() > 12) {
                        nameEdit.requestFocus();
                        SuperTools.getInstance().showLoadingAlert(mActivity, "帐号名错误，必需是数字和字母6-12个字符");
                        return;
                    }
                    SuperTools.runByThread(new Runnable() {
//                    final null this$1;
//
//                    final String val$username;

                        public void run() {
                            StringBuilder stringBuilder1 = new StringBuilder();
                            stringBuilder1.append(ip);
                            stringBuilder1.append("/api/reqcheckname?name=");
                            stringBuilder1.append(username);
                            String str = stringBuilder1.toString();
                            JSONObject jSONObject = SuperTools.getInstance().postHttpJson(str);
                            PrintStream printStream = System.out;
                            StringBuilder stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("rtjson::");
                            stringBuilder2.append(jSONObject.toString());
                            printStream.println(stringBuilder2.toString());
                        }
                    });
                }
            }
        });
        checkcodeButton.setOnClickListener(new View.OnClickListener() {
//          final ThirdLogin this$0;
//
//          final BootstrapButton val$checkcodeButton;
//
//          final BootstrapEditText val$checkcodeEdit;
//
//          final Map val$header;
//
//          final String val$ip;
//
//          final BootstrapEditText val$nameEdit;
//
//          final BootstrapEditText val$pass1Edit;
//
//          final BootstrapEditText val$pass2Edit;
//
//          final BootstrapEditText val$phoneEdit;

            public void onClick(View param1View) {
                final String username = nameEdit.getText().toString();
                String str3 = pass1Edit.getText().toString();
                String str4 = pass2Edit.getText().toString();
                final String phone = phoneEdit.getText().toString();
                checkcodeEdit.getText().toString();
                if (TextUtils.isEmpty(username)) {
                    nameEdit.requestFocus();
                    SuperTools.getInstance().showLoadingAlert(mActivity, "请输入帐号名");
                    return;
                }
                if (!Pattern.compile("^[a-z0-9A-Z]+$").matcher(username).matches() || username.length() < 6 || username.length() > 12) {
                    SuperTools.getInstance().showLoadingAlert(mActivity, "帐号名错误，必需是数字和字母6-12个字符");
                    return;
                }
                if (TextUtils.isEmpty(str3)) {
                    pass1Edit.requestFocus();
                    SuperTools.getInstance().showLoadingAlert(mActivity, "请输入密码");
                    return;
                }
                if (TextUtils.isEmpty(str4)) {
                    pass2Edit.requestFocus();
                    SuperTools.getInstance().showLoadingAlert(mActivity, "请重复输入密码");
                    return;
                }
                if (!str3.equals(str4)) {
                    SuperTools.getInstance().showLoadingAlert(mActivity, "密码两次输入不同");
                    return;
                }
                if (str3.indexOf(",") != -1) {
                    SuperTools.getInstance().showLoadingAlert(mActivity, "密码含有非法字符");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    phoneEdit.requestFocus();
                    SuperTools.getInstance().showLoadingAlert(mActivity, "请输入手机号");
                    return;
                }
                if (!Pattern.compile("^0?1[3|4|5|6|7|8|9][0-9]\\d{8}$").matcher(phone).matches()) {
                    SuperTools.getInstance().showLoadingAlert(mActivity, "手机号错误。");
                    return;
                }
                checkcodeButton.setClickable(false);
                SuperTools.runByThread(new Runnable() {
//                  final null this$1;
//
//                  final String val$phone;
//
//                  final String val$username;

                    public void run() {
                        try {
                            Runnable runnable;
                            StringBuilder stringBuilder2 = new StringBuilder();
//                      this();
                            stringBuilder2.append(ip);
                            stringBuilder2.append("/api/reqcheckname?name=");
                            stringBuilder2.append(username);
                            String str2 = stringBuilder2.toString();
                            JSONObject jSONObject2 = SuperTools.getInstance().postHttpJson(str2);
                            boolean bool = "ok".equals(jSONObject2.getString("state"));
                            if (!bool) {
                                SuperTools.getInstance().showLoadingAlertOnUi(mActivity, jSONObject2.getString("msg"));
                                Activity activity = mActivity;
                                Runnable runnable1 = new Runnable() {
//                            final null.null this$2;

                                    public void run() {
                                        checkcodeButton.setClickable(true);
                                    }
                                };
//                        super(this);
                                SuperTools.runByUiThread(activity, runnable1);
                                return;
                            }
                            header.clear();
                            StringBuilder stringBuilder1 = new StringBuilder();
//                      this();
                            stringBuilder1.append(ip);
                            stringBuilder1.append("/api/reqcheckcode?phone=");
                            stringBuilder1.append(phone);
                            String str1 = stringBuilder1.toString();
                            JSONObject jSONObject1 = SuperTools.getInstance().postHttpJson(str1);
                            if (!"ok".equals(jSONObject1.getString("state"))) {
                                SuperTools.getInstance().showLoadingAlertOnUi(mActivity, jSONObject1.getString("msg"));
                                Activity activity = mActivity;
                                runnable = new Runnable() {
//                            final null.null this$2;

                                    public void run() {
                                        checkcodeButton.setClickable(true);
                                    }
                                };
//                        super(this);
                                SuperTools.runByUiThread(activity, runnable);
                                return;
                            }
                            header.put("cookie", jSONObject2.getString("cookie"));
                            SuperTools.getInstance().showLoadingAlertOnUi(mActivity, "发送成功，请查看手机信息。");
                            nameEdit.setClickable(false);
                            pass1Edit.setClickable(false);
                            pass2Edit.setClickable(false);
                            phoneEdit.setClickable(false);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}


/* Location:              C:\Users\spirit\Desktop\xq\tool\classes-dex2jar.jar!\com\xqj\games\class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */