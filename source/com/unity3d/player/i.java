package com.unity3d.player;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextWatcher;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public final class i extends Dialog
  implements TextWatcher, View.OnClickListener
{
  private static int d = 1627389952;
  private static int e = -1;
  boolean a;
  private Context b = null;
  private UnityPlayer c = null;
  private int f;

  public i(Context paramContext, UnityPlayer paramUnityPlayer, String paramString1, int paramInt1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, String paramString2, int paramInt2, boolean paramBoolean4, boolean paramBoolean5)
  {
    super(paramContext);
    this.b = paramContext;
    this.c = paramUnityPlayer;
    paramUnityPlayer = getWindow();
    this.a = paramBoolean5;
    paramUnityPlayer.requestFeature(1);
    paramContext = paramUnityPlayer.getAttributes();
    paramContext.gravity = 80;
    paramContext.x = 0;
    paramContext.y = 0;
    paramUnityPlayer.setAttributes(paramContext);
    paramUnityPlayer.setBackgroundDrawable(new ColorDrawable(0));
    paramContext = createSoftInputView();
    setContentView(paramContext);
    paramUnityPlayer.setLayout(-1, -2);
    paramUnityPlayer.clearFlags(2);
    paramUnityPlayer.clearFlags(134217728);
    paramUnityPlayer.clearFlags(67108864);
    if (!this.a)
    {
      paramUnityPlayer.addFlags(32);
      paramUnityPlayer.addFlags(262144);
    }
    EditText localEditText = (EditText)findViewById(1057292289);
    paramUnityPlayer = (Button)findViewById(1057292290);
    a(localEditText, paramString1, paramInt1, paramBoolean1, paramBoolean2, paramBoolean3, paramString2, paramInt2);
    paramUnityPlayer.setOnClickListener(this);
    this.f = localEditText.getCurrentTextColor();
    a(paramBoolean4);
    this.c.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(paramContext)
    {
      public final void onGlobalLayout()
      {
        if (this.a.isShown())
        {
          Object localObject1 = new Rect();
          i.a(i.this).getWindowVisibleDisplayFrame((Rect)localObject1);
          Object localObject2 = new int[2];
          i.a(i.this).getLocationOnScreen(localObject2);
          localObject2 = new Point(((Rect)localObject1).left - localObject2[0], ((Rect)localObject1).height() - this.a.getHeight());
          localObject1 = new Point();
          i.this.getWindow().getWindowManager().getDefaultDisplay().getSize((Point)localObject1);
          int i = i.a(i.this).getHeight();
          int j = ((Point)localObject1).y;
          int k = i.a(i.this).getHeight() - ((Point)localObject2).y;
          if (k != i - j + this.a.getHeight())
            i.a(i.this).reportSoftInputIsVisible(true);
          else
            i.a(i.this).reportSoftInputIsVisible(false);
          localObject1 = new Rect(((Point)localObject2).x, ((Point)localObject2).y, this.a.getWidth(), k);
          i.a(i.this).reportSoftInputArea((Rect)localObject1);
        }
      }
    });
    localEditText.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public final void onFocusChange(View paramView, boolean paramBoolean)
      {
        if (paramBoolean)
          i.this.getWindow().setSoftInputMode(5);
      }
    });
    localEditText.requestFocus();
  }

  private static int a(int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if (paramBoolean1)
      i = 32768;
    else
      i = 524288;
    int j = 0;
    int k;
    if (paramBoolean2)
      k = 131072;
    else
      k = 0;
    if (paramBoolean3)
      j = 128;
    int i = i | k | j;
    if ((paramInt >= 0) && (paramInt <= 11))
    {
      int[] arrayOfInt = new int[12];
      int[] tmp72_70 = arrayOfInt;
      tmp72_70[0] = 1;
      int[] tmp76_72 = tmp72_70;
      tmp76_72[1] = 16385;
      int[] tmp82_76 = tmp76_72;
      tmp82_76[2] = 12290;
      int[] tmp88_82 = tmp82_76;
      tmp88_82[3] = 17;
      int[] tmp93_88 = tmp88_82;
      tmp93_88[4] = 2;
      int[] tmp97_93 = tmp93_88;
      tmp97_93[5] = 3;
      int[] tmp101_97 = tmp97_93;
      tmp101_97[6] = 8289;
      int[] tmp108_101 = tmp101_97;
      tmp108_101[7] = 33;
      int[] tmp114_108 = tmp108_101;
      tmp114_108[8] = 1;
      int[] tmp119_114 = tmp114_108;
      tmp119_114[9] = 16417;
      int[] tmp126_119 = tmp119_114;
      tmp126_119[10] = 17;
      int[] tmp132_126 = tmp126_119;
      tmp132_126[11] = 8194;
      tmp132_126;
      if ((arrayOfInt[paramInt] & 0x2) != 0)
        return arrayOfInt[paramInt];
      return arrayOfInt[paramInt] | i;
    }
    return i;
  }

  private void a(EditText paramEditText, String paramString1, int paramInt1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, String paramString2, int paramInt2)
  {
    paramEditText.setImeOptions(6);
    paramEditText.setText(paramString1);
    paramEditText.setHint(paramString2);
    paramEditText.setHintTextColor(d);
    paramEditText.setInputType(a(paramInt1, paramBoolean1, paramBoolean2, paramBoolean3));
    paramEditText.setImeOptions(33554432);
    if (paramInt2 > 0)
      paramEditText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(paramInt2) });
    paramEditText.addTextChangedListener(this);
    paramEditText.setSelection(paramEditText.getText().length());
    paramEditText.setClickable(true);
  }

  private void a(String paramString, boolean paramBoolean)
  {
    ((EditText)findViewById(1057292289)).setSelection(0, 0);
    this.c.reportSoftInputStr(paramString, 1, paramBoolean);
  }

  private String b()
  {
    EditText localEditText = (EditText)findViewById(1057292289);
    if (localEditText == null)
      return null;
    return localEditText.getText().toString();
  }

  public final String a()
  {
    Object localObject = ((InputMethodManager)this.b.getSystemService("input_method")).getCurrentInputMethodSubtype();
    if (localObject == null)
      return null;
    String str = ((InputMethodSubtype)localObject).getLocale();
    if ((str != null) && (!str.equals("")))
      return str;
    str = ((InputMethodSubtype)localObject).getMode();
    localObject = ((InputMethodSubtype)localObject).getExtraValue();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(str);
    localStringBuilder.append(" ");
    localStringBuilder.append((String)localObject);
    return (String)localStringBuilder.toString();
  }

  public final void a(int paramInt)
  {
    EditText localEditText = (EditText)findViewById(1057292289);
    if (localEditText != null)
    {
      if (paramInt > 0)
      {
        localEditText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(paramInt) });
        return;
      }
      localEditText.setFilters(new InputFilter[0]);
    }
  }

  public final void a(int paramInt1, int paramInt2)
  {
    EditText localEditText = (EditText)findViewById(1057292289);
    if (localEditText != null)
    {
      int i = localEditText.getText().length();
      paramInt2 += paramInt1;
      if (i >= paramInt2)
        localEditText.setSelection(paramInt1, paramInt2);
    }
  }

  public final void a(String paramString)
  {
    EditText localEditText = (EditText)findViewById(1057292289);
    if (localEditText != null)
    {
      localEditText.setText(paramString);
      localEditText.setSelection(paramString.length());
    }
  }

  public final void a(boolean paramBoolean)
  {
    EditText localEditText = (EditText)findViewById(1057292289);
    Button localButton = (Button)findViewById(1057292290);
    View localView = findViewById(1057292291);
    if (paramBoolean)
    {
      localEditText.setBackgroundColor(0);
      localEditText.setTextColor(0);
      localEditText.setCursorVisible(false);
      localEditText.setHighlightColor(0);
      localEditText.setOnClickListener(this);
      localEditText.setLongClickable(false);
      localButton.setTextColor(0);
      localView.setBackgroundColor(0);
      localView.setOnClickListener(this);
      return;
    }
    localEditText.setBackgroundColor(e);
    localEditText.setTextColor(this.f);
    localEditText.setCursorVisible(true);
    localEditText.setOnClickListener(null);
    localEditText.setLongClickable(true);
    localButton.setClickable(true);
    localButton.setTextColor(this.f);
    localView.setBackgroundColor(e);
    localView.setOnClickListener(null);
  }

  public final void afterTextChanged(Editable paramEditable)
  {
    this.c.reportSoftInputStr(paramEditable.toString(), 0, false);
    paramEditable = (EditText)findViewById(1057292289);
    int i = paramEditable.getSelectionStart();
    int j = paramEditable.getSelectionEnd();
    this.c.reportSoftInputSelection(i, j - i);
  }

  public final void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  protected final View createSoftInputView()
  {
    RelativeLayout localRelativeLayout = new RelativeLayout(this.b);
    localRelativeLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
    localRelativeLayout.setBackgroundColor(e);
    localRelativeLayout.setId(1057292291);
    Object localObject1 = new EditText(this.b)
    {
      public final boolean onKeyPreIme(int paramInt, KeyEvent paramKeyEvent)
      {
        if (paramInt == 4)
        {
          paramKeyEvent = i.this;
          i.a(paramKeyEvent, i.b(paramKeyEvent), true);
          return true;
        }
        if (paramInt == 84)
          return true;
        return super.onKeyPreIme(paramInt, paramKeyEvent);
      }

      protected final void onSelectionChanged(int paramInt1, int paramInt2)
      {
        i.a(i.this).reportSoftInputSelection(paramInt1, paramInt2 - paramInt1);
      }

      public final void onWindowFocusChanged(boolean paramBoolean)
      {
        super.onWindowFocusChanged(paramBoolean);
        if (paramBoolean)
          ((InputMethodManager)i.c(i.this).getSystemService("input_method")).showSoftInput(this, 0);
      }
    };
    Object localObject2 = new RelativeLayout.LayoutParams(-1, -2);
    ((RelativeLayout.LayoutParams)localObject2).addRule(15);
    ((RelativeLayout.LayoutParams)localObject2).addRule(0, 1057292290);
    ((EditText)localObject1).setLayoutParams((ViewGroup.LayoutParams)localObject2);
    ((EditText)localObject1).setId(1057292289);
    localRelativeLayout.addView((View)localObject1);
    localObject2 = new Button(this.b);
    ((Button)localObject2).setText(this.b.getResources().getIdentifier("ok", "string", "android"));
    localObject1 = new RelativeLayout.LayoutParams(-2, -2);
    ((RelativeLayout.LayoutParams)localObject1).addRule(15);
    ((RelativeLayout.LayoutParams)localObject1).addRule(11);
    ((Button)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject1);
    ((Button)localObject2).setId(1057292290);
    ((Button)localObject2).setBackgroundColor(0);
    localRelativeLayout.addView((View)localObject2);
    ((EditText)localRelativeLayout.findViewById(1057292289)).setOnEditorActionListener(new TextView.OnEditorActionListener()
    {
      public final boolean onEditorAction(TextView paramTextView, int paramInt, KeyEvent paramKeyEvent)
      {
        if (paramInt == 6)
        {
          paramTextView = i.this;
          i.a(paramTextView, i.b(paramTextView), false);
        }
        return false;
      }
    });
    localRelativeLayout.setPadding(16, 16, 16, 16);
    return (View)(View)localRelativeLayout;
  }

  public final boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((!this.a) && (paramMotionEvent.getAction() == 4))
      return true;
    return super.dispatchTouchEvent(paramMotionEvent);
  }

  public final void onBackPressed()
  {
    a(b(), true);
  }

  public final void onClick(View paramView)
  {
    a(b(), false);
  }

  public final void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.i
 * JD-Core Version:    0.6.0
 */