package org.cocos2dx.lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Cocos2dxGLSurfaceView extends GLSurfaceView {
    private static final int HANDLER_CLOSE_IME_KEYBOARD = 3;

    private static final int HANDLER_OPEN_IME_KEYBOARD = 2;

    private static final String TAG = "Cocos2dxGLSurfaceView";

    private static Cocos2dxGLSurfaceView mCocos2dxGLSurfaceView;

    private static Cocos2dxTextInputWraper sCocos2dxTextInputWraper;

    private static Handler sHandler;

    private Cocos2dxEditText mCocos2dxEditText;

    private Cocos2dxRenderer mCocos2dxRenderer;

    public Cocos2dxGLSurfaceView(Context paramContext) {
        super(paramContext);
        initView();
    }

    public Cocos2dxGLSurfaceView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        initView();
    }

    public static void closeIMEKeyboard() {
        Message message = new Message();
        message.what = 3;
        sHandler.sendMessage(message);
    }

    private static void dumpMotionEvent(MotionEvent paramMotionEvent) {
        StringBuilder stringBuilder = new StringBuilder();
        int j = paramMotionEvent.getAction();
        int i = j & 0xFF;
        stringBuilder.append("event ACTION_");
        (new String[10])[0] = "DOWN";
        (new String[10])[1] = "UP";
        (new String[10])[2] = "MOVE";
        (new String[10])[3] = "CANCEL";
        (new String[10])[4] = "OUTSIDE";
        (new String[10])[5] = "POINTER_DOWN";
        (new String[10])[6] = "POINTER_UP";
        (new String[10])[7] = "7?";
        (new String[10])[8] = "8?";
        (new String[10])[9] = "9?";
        stringBuilder.append((new String[10])[i]);
        if (i == 5 || i == 6) {
            stringBuilder.append("(pid ");
            stringBuilder.append(j >> 8);
            stringBuilder.append(")");
        }
        stringBuilder.append("[");
        i = 0;
        while (i < paramMotionEvent.getPointerCount()) {
            stringBuilder.append("#");
            stringBuilder.append(i);
            stringBuilder.append("(pid ");
            stringBuilder.append(paramMotionEvent.getPointerId(i));
            stringBuilder.append(")=");
            stringBuilder.append((int) paramMotionEvent.getX(i));
            stringBuilder.append(",");
            stringBuilder.append((int) paramMotionEvent.getY(i));
            j = i + 1;
            i = j;
            if (j < paramMotionEvent.getPointerCount()) {
                stringBuilder.append(";");
                i = j;
            }
        }
        stringBuilder.append("]");
        Log.d(TAG, stringBuilder.toString());
    }

    private String getContentText() {
        return this.mCocos2dxRenderer.getContentText();
    }

    public static Cocos2dxGLSurfaceView getInstance() {
        return mCocos2dxGLSurfaceView;
    }

    public static void openIMEKeyboard() {
        Message message = new Message();
        message.what = 2;
        message.obj = mCocos2dxGLSurfaceView.getContentText();
        sHandler.sendMessage(message);
    }

    public static void queueAccelerometer(final float x, final float y, final float z, final long timestamp) {
        mCocos2dxGLSurfaceView.queueEvent(new Runnable() {
//          final long val$timestamp;
//          
//          final float val$x;
//          
//          final float val$y;
//          
//          final float val$z;

            public void run() {
                Cocos2dxAccelerometer.onSensorChanged(x, y, z, timestamp);
            }
        });
    }

    public void deleteBackward() {
        queueEvent(new Runnable() {
//          final Cocos2dxGLSurfaceView this$0;

            public void run() {
                mCocos2dxRenderer.handleDeleteBackward();
            }
        });
    }

    public Cocos2dxEditText getCocos2dxEditText() {
        return this.mCocos2dxEditText;
    }

    @SuppressLint("HandlerLeak")
    protected void initView() {
        setEGLContextClientVersion(2);
        setFocusableInTouchMode(true);
        mCocos2dxGLSurfaceView = this;
        sCocos2dxTextInputWraper = new Cocos2dxTextInputWraper(this);
        sHandler = new Handler() {
//        final Cocos2dxGLSurfaceView this$0;

            public void handleMessage(Message param1Message) {
                int i = param1Message.what;
                if (i != 2) {
                    if (i == 3 && mCocos2dxEditText != null) {
                        mCocos2dxEditText.removeTextChangedListener(Cocos2dxGLSurfaceView.sCocos2dxTextInputWraper);
                        ((InputMethodManager) Cocos2dxGLSurfaceView.mCocos2dxGLSurfaceView.getContext().getSystemService("input_method")).hideSoftInputFromWindow(mCocos2dxEditText.getWindowToken(), 0);
                        requestFocus();
                        Log.d("GLSurfaceView", "HideSoftInput");
                    }
                } else if (mCocos2dxEditText != null && mCocos2dxEditText.requestFocus()) {
                    mCocos2dxEditText.removeTextChangedListener(Cocos2dxGLSurfaceView.sCocos2dxTextInputWraper);
                    mCocos2dxEditText.setText("");
                    String str = (String) param1Message.obj;
                    mCocos2dxEditText.append(str);
                    Cocos2dxGLSurfaceView.sCocos2dxTextInputWraper.setOriginText(str);
                    mCocos2dxEditText.addTextChangedListener(Cocos2dxGLSurfaceView.sCocos2dxTextInputWraper);
                    ((InputMethodManager) Cocos2dxGLSurfaceView.mCocos2dxGLSurfaceView.getContext().getSystemService("input_method")).showSoftInput((View) mCocos2dxEditText, 0);
                    Log.d("GLSurfaceView", "showSoftInput");
                }
            }
        };
    }

    public void insertText(final String pText) {
        queueEvent(new Runnable() {
//          final Cocos2dxGLSurfaceView this$0;
//
//          final String val$pText;

            public void run() {
                mCocos2dxRenderer.handleInsertText(pText);
            }
        });
    }

    public boolean onKeyDown(final int pKeyCode, KeyEvent paramKeyEvent) {
        if (pKeyCode != 4 && pKeyCode != 82)
            return super.onKeyDown(pKeyCode, paramKeyEvent);
        queueEvent(new Runnable() {
//          final Cocos2dxGLSurfaceView this$0;
//
//          final int val$pKeyCode;

            public void run() {
                mCocos2dxRenderer.handleKeyDown(pKeyCode);
            }
        });
        return true;
    }

    public void onPause() {
        queueEvent(new Runnable() {
//          final Cocos2dxGLSurfaceView this$0;

            public void run() {
                mCocos2dxRenderer.handleOnPause();
            }
        });
        setRenderMode(0);
    }

    public void onResume() {
        super.onResume();
        setRenderMode(1);
        queueEvent(new Runnable() {
//          final Cocos2dxGLSurfaceView this$0;

            public void run() {
                mCocos2dxRenderer.handleOnResume();
            }
        });
    }

    protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        if (!isInEditMode())
            this.mCocos2dxRenderer.setScreenWidthAndHeight(paramInt1, paramInt2);
    }

    public boolean onTouchEvent(final MotionEvent paramMotionEvent) {
        int j = paramMotionEvent.getPointerCount();
        final int[] ids = new int[j];//var4
        final float[] xs = new float[j];//var5
        final float[] ys = new float[j];//var6
        int i;
        for (i = 0; i < j; i++) {
            ids[i] = paramMotionEvent.getPointerId(i);
            xs[i] = paramMotionEvent.getX(i);
            ys[i] = paramMotionEvent.getY(i);
        }
        i = paramMotionEvent.getAction() & 0xFF;



        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i != 5) {
                            if (i == 6) {
                                i = paramMotionEvent.getAction() >> 8;
                                int finalI = i;
                                queueEvent(new Runnable() {

                                    final int idPointerUp = ids[finalI];

                                    final float xPointerUp= xs[finalI];

                                    final float yPointerUp =ys[finalI];

                                    public void run() {
                                        mCocos2dxRenderer.handleActionUp(idPointerUp, xPointerUp, yPointerUp);
                                    }
                                });
                            }
                        } else {
                            i = paramMotionEvent.getAction() >> 8;
                            int finalI = i;
                            queueEvent(new Runnable() {

                                final int idPointerDown = ids[finalI];

                                final float xPointerDown= xs[finalI];

                                final float yPointerDown =ys[finalI];

                                public void run() {
                                    mCocos2dxRenderer.handleActionDown(idPointerDown, xPointerDown, yPointerDown);
                                }
                            });
                        }
                    } else {
                        queueEvent(new Runnable() {
//                            final Cocos2dxGLSurfaceView this$0;
//
//                            final int[] val$ids;
//
//                            final float[] val$xs;
//
//                            final float[] val$ys;

                            public void run() {
                                Cocos2dxGLSurfaceView.this.mCocos2dxRenderer.handleActionCancel(ids, xs, ys);
                            }
                        });
                    }
                } else {
                    queueEvent(new Runnable() {
//                        final Cocos2dxGLSurfaceView this$0;
//
//                        final int[] val$ids;
//
//                        final float[] val$xs;
//
//                        final float[] val$ys;

                        public void run() {
                            Cocos2dxGLSurfaceView.this.mCocos2dxRenderer.handleActionMove(ids, xs, ys);
                        }
                    });
                }
            } else {
                int finalI = i;
                queueEvent(new Runnable() {
//var1.getPointerId(0), var5[0], var6[0]
                    final int idUp = ids[0];

                    final float xUp= xs[0];

                    final float yUp =ys[0];

                    public void run() {
                        mCocos2dxRenderer.handleActionUp(idUp, xUp, yUp);
                    }
                });
            }
        } else {
            int finalI = i;
            queueEvent(new Runnable() {
                //var1.getPointerId(0), var5[0], var6[0]
                final int idDown = ids[0];

                final float xDown= xs[0];

                final float yDown =ys[0];

                public void run() {
                    Cocos2dxGLSurfaceView.this.mCocos2dxRenderer.handleActionDown(idDown, xDown, yDown);
                }
            });
        }
        return true;
    }

    public void setCocos2dxEditText(Cocos2dxEditText paramCocos2dxEditText) {
        this.mCocos2dxEditText = paramCocos2dxEditText;
        paramCocos2dxEditText = this.mCocos2dxEditText;
        if (paramCocos2dxEditText != null) {
            Cocos2dxTextInputWraper cocos2dxTextInputWraper = sCocos2dxTextInputWraper;
            if (cocos2dxTextInputWraper != null) {
                paramCocos2dxEditText.setOnEditorActionListener(cocos2dxTextInputWraper);
                this.mCocos2dxEditText.setCocos2dxGLSurfaceView(this);
                requestFocus();
            }
        }
    }

    public void setCocos2dxRenderer(Cocos2dxRenderer paramCocos2dxRenderer) {
        this.mCocos2dxRenderer = paramCocos2dxRenderer;
        setRenderer(this.mCocos2dxRenderer);
    }
}


/* Location:              C:\Users\spirit\Desktop\xq\tool\classes-dex2jar.jar!\org\cocos2dx\lib\Cocos2dxGLSurfaceView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */