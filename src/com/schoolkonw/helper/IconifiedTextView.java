package com.schoolkonw.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IconifiedTextView extends LinearLayout
{
	//һ���ļ������ļ�����ͼ��
	//����һ����ֱ���Բ���
	private TextView	mText	= null;
	private ImageView	mIcon	= null;
	public IconifiedTextView(Context context, IconifiedText aIconifiedText) 
	{
		super(context);
		//���ò��ַ�ʽ
		this.setOrientation(HORIZONTAL);
		mIcon = new ImageView(context);
		//����ImageViewΪ�ļ���ͼ��
		mIcon.setImageDrawable(aIconifiedText.getIcon());
		//����ͼ���ڸò����е����λ��
		mIcon.setPadding(6, 14, 6, 14); 
		//��ImageView��ͼ����ӵ��ò�����
		addView(mIcon,  new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		//�����ļ�������䷽ʽ�������С
		mText = new TextView(context);
		mText.setText(aIconifiedText.getText());
		mText.setPadding(4, 6, 4, 6); 
		mText.setTextSize(24);
		mText.setWidth(LayoutParams.WRAP_CONTENT);
		mText.setHeight(LayoutParams.WRAP_CONTENT);
		//���ļ�����ӵ�������
		addView(mText, new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}
	//�����ļ���
	public void setText(String words)
	{
		mText.setText(words);
	}
	//����ͼ��
	public void setIcon(Drawable bullet)
	{
		mIcon.setImageDrawable(bullet);
	}
}

