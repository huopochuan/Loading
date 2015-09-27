package com.wyc.loading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class LoadingView extends View{
   
	private static final int yuan=0;
	private static final int fangxing=1;
	private static final int sanjiao=2;
	private int shape=yuan; //��¼��ǰ��״
	private float downheigh=300;//��¼��ʼ����߶�
	private int g=9; //�������ٶȴ�С
    private int shapewidth=80;//��¼ͼ�������
    private float T; //��������ʱ��
    private float incretmenttime=0.2f; //ʱ������
    private float Time=0;
    private float angle=0; //��ת�Ƕ�
    private float incretmentangle=0.1f; //�Ƕ�����
    
    private float V; //����ʱ���ٶ�
    private int width; //�ؼ����;
    private int height; //�ؼ��߶�
    
    private Paint circlepaint;
    private Paint rectpaint;
    private Paint sanjiaopaint;
    private Path path;
    private float S;
   
    private static int UP=0;
    private static int DOWN=1;
    private int currentstate=DOWN;
	public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		circlepaint=new Paint();
		circlepaint.setColor(Color.RED);
		
		rectpaint=new Paint();
		rectpaint.setColor(Color.BLUE);
		
		sanjiaopaint=new Paint();
		sanjiaopaint.setColor(Color.GREEN);
		
		path=new Path();
	}

	public LoadingView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	
	}

	public LoadingView(Context context) {
		this(context,null);
		
	}
	
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
       	width=getMeasuredWidth();
       	height=getMeasuredHeight();
       	downheigh=height-shapewidth-50;
      	V=(float)Math.sqrt(2*g*downheigh);
	   	T=(float)Math.sqrt(2*downheigh/g);
	   	incretmentangle	=0; //Բ�ĽǶ�����Ϊ0û����ת
    }
   
    
    @Override
    protected void onDraw(Canvas canvas) {
    
    	if(shape==yuan){
    		canvas.save();
    		if(currentstate==DOWN) 	S=0.5f*g*Time*Time;
    		else S= downheigh-V*Time+0.5f*g*Time*Time;
    		canvas.translate(width/2, 0);
    	
    		canvas.drawCircle(0, S+shapewidth/2, shapewidth/2, circlepaint);
    	    
    		if(S>=downheigh&&currentstate==DOWN) {
    			shape=fangxing;
    			currentstate=UP;
    			T=Time;
    			Time=0;
    		}
    		if(S<=10&&currentstate==UP){
    			currentstate=DOWN;
    		
    			Time=0;
    		}
    		canvas.restore();
    	}
    	else if(shape==fangxing){
    		incretmentangle=(float)Math.PI/T/incretmenttime; //2Tʱ����ת180��
    		angle+=incretmentangle;
    		canvas.save();
    		if(currentstate==DOWN){
    			S=0.5f*g*Time*Time;
    		}
    		else{
    			S= downheigh-V*Time+0.5f*g*Time*Time;
    		}
    		canvas.translate(width/2, S+shapewidth/2);
    		canvas.rotate(angle);
    		canvas.drawRect(-shapewidth/2, -shapewidth/2, shapewidth/2, shapewidth/2, rectpaint);
    	    
    		if(S>=downheigh&&currentstate==DOWN) {
    			shape=sanjiao;
    			currentstate=UP;
    			
    			Time=0;
    		}
    		if(S<=10&&currentstate==UP){
    			currentstate=DOWN;
    			Time=0;
    		}
    		canvas.restore();
    		
    	}
    	else{
    		incretmentangle=(float)Math.PI/T/incretmenttime; //2Tʱ����ת180��
    		angle-=incretmentangle;
    		canvas.save();
    		if(currentstate==DOWN){
    			S=0.5f*g*Time*Time;
    		}
    		else{
    			S= downheigh-V*Time+0.5f*g*Time*Time;
    		}
    		canvas.translate(width/2, S+shapewidth/2);
    		canvas.rotate(angle);
    		
    		path.moveTo(0,  -shapewidth/2);// ����������
    		path.lineTo( -shapewidth/2,  shapewidth/2); 
    		path.lineTo( shapewidth/2, shapewidth/2); 
    		path.close(); // ʹ��Щ�㹹�ɷ�յĶ���� 
    		canvas.drawPath(path, sanjiaopaint); 
    	
    	    
    		if(S>=downheigh&&currentstate==DOWN) {
    			shape=yuan;
    			currentstate=UP;
    			
    			Time=0;
    		}
    		if(S<=10&&currentstate==UP){
    			currentstate=DOWN;
    			Time=0;
    		}
    		canvas.restore();
    	}
    	Time+=incretmenttime;
        postInvalidate();
    }
}
