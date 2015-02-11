package com.panicobass.trabalhodeconclusaodecurso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class Splash extends Activity {

	private Thread mSplashThread;
	private boolean mblnClicou = false;

	/*********************************************************************************************/
	/**				Evento chamado quando a activity é executada pela primeira vez				*/
	/*******************************************************************************************/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		/**********************************************************************************************/
		/**						thread para mostrar a tela de Splash								 */	
		/**		 Tela de splash carrega durante 3 segundos e depois vai para tela principal		 	*/
		/*******************************************************************************************/
		mSplashThread = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (this) {
						wait(3000);
						mblnClicou = true;
					}
				} catch (InterruptedException ex) {
				}

				if (mblnClicou) {
					finish();
					Intent i = new Intent();
					i.setClass(Splash.this, Principal.class);
					startActivity(i);
				}
			}
		};

		mSplashThread.start();
	}

	@Override
	public void onPause() {
		super.onPause();

		/*********************************************************************************************/
		/**	garante que quando o usuário clicar no botão "Voltar" o sistema deve finalizar a thread */
		/*******************************************************************************************/
		mSplashThread.interrupt();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			synchronized (mSplashThread) {
				mblnClicou = true;
				mSplashThread.notifyAll();
			}
		}
		return true;
	}
}
