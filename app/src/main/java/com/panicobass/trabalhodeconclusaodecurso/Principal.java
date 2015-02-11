package com.panicobass.trabalhodeconclusaodecurso;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Principal extends Activity implements OnTouchListener,
		OnDragListener {

	private String[] Container = { "00", "1", "2", "3", "4", "5", "6", "7" };

	
	/*********************************************************************************************/
	/**								    Variáveis do menu										*/
	/********************************************************************************************/
	
	private static final int EXPORTAR = Menu.FIRST;

	
	/*********************************************************************************************/
	/**							  												 				 */
	/**							Declarção dos Componentes da aplicação 							 */
	/*********************************************************************************************/

	
	/*********************************************************************************************/
	/**				 ImageButton que são os componentes que irão se movimentar					*/
	/********************************************************************************************/
	private ImageButton container_1, 
						container_2, 
						container_3, 
						container_4,
						container_5, 
						container_6, 
						container_7;
	
	/*********************************************************************************************/
	/**				 RelativeLayout que são os layouts onde os componetes serão colocados   	*/
	/********************************************************************************************/
	private RelativeLayout layout_1, 
						   layout_2, 
						   layout_3, 
						   layout_4, 
						   layout_5,
						   layout_6, 
						   layout_7;

	/*********************************************************************************************/
	/**				 Strings que guardam a posição atual e nova posição dos componentes   		*/
	/********************************************************************************************/
	private String  posicaoNova_1 = "topo",
				    posicaoNova_2 = "esquerda",
				    posicaoNova_3 = "direita",
				    posicaoNova_4 = "meioTopo",				   
				    posicaoNova_5 = "meio",	
				    posicaoNova_6 = "meioBase", 	
				    posicaoNova_7 = "base";
	 
	
	
	/**********************************************************************************************/
	/**				 Efeito que dado ao entrar e sair da área de Arrastar e Soltar			   	 */
	/********************************************************************************************/
	Drawable normalShape;
	Drawable enterShape;
	
	
	/******************************************************************************************/
	/**				Variáveis do dialogFragment para insersão do endereço do site			 */
	/****************************************************************************************/
	 
	// ligando o componente xml com o componente java
	TextView text;
	EditText edtDialogo;
	Button ok;
	String site;
	
	
	/**********************************************************************************************/
	/**						Método principal chamado quando á aplicação inicia				   	 */
	/***																						 /
	/*******************************************************************************************/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.principal);
		
		/**********************************************************************************************/
		/**	 			Verificar se tem conexão, se não tiver fecha a aplicação				   	 */
		/********************************************************************************************/	
		
		if(TemConexao() == false){
			mostraAlerta();
		}

		/**********************************************************************************************/
		/**				 Inicializa todos os componetes para serem usados pela aplicação		   	 */
		/**					Ligas os Objetos da classe JAVA com Objetos do XML						*/
		/*******************************************************************************************/
			
		normalShape = getResources().getDrawable(R.drawable.bg);
		enterShape = getResources().getDrawable(R.drawable.bg_over);

		layout_1 = (RelativeLayout) findViewById(R.id.r1);
		layout_2 = (RelativeLayout) findViewById(R.id.r2);
		layout_3 = (RelativeLayout) findViewById(R.id.r3);
		layout_4 = (RelativeLayout) findViewById(R.id.r4);
		layout_5 = (RelativeLayout) findViewById(R.id.r5);
		layout_6 = (RelativeLayout) findViewById(R.id.r6);
		layout_7 = (RelativeLayout) findViewById(R.id.r7);
		
		container_1 = (ImageButton) findViewById(R.id.container_1);
		container_2 = (ImageButton) findViewById(R.id.container_2);
		container_3 = (ImageButton) findViewById(R.id.container_3);
		container_4 = (ImageButton) findViewById(R.id.container_4);
		container_5 = (ImageButton) findViewById(R.id.container_5);
		container_6 = (ImageButton) findViewById(R.id.container_6);
		container_7 = (ImageButton) findViewById(R.id.container_7);
		
				
		/**********************************************************************************************/
		/**	 Configura Layouts e containers pra que possam usar Drag And Drop - Arrastar e Soltar 	 */
		/********************************************************************************************/
		
		layout_1.setOnDragListener(this);
		layout_2.setOnDragListener(this);
		layout_3.setOnDragListener(this);
		layout_4.setOnDragListener(this);
		layout_5.setOnDragListener(this);
		layout_6.setOnDragListener(this);
		layout_7.setOnDragListener(this);

		container_1.setOnTouchListener(this);
		container_2.setOnTouchListener(this);
		container_3.setOnTouchListener(this);
		container_4.setOnTouchListener(this);
		container_5.setOnTouchListener(this);
		container_6.setOnTouchListener(this);
		container_7.setOnTouchListener(this);

	}
	
	/**********************************************************************************************/
	/**	 					Método para verificar se há conexão com internet		 		   	 */
	/********************************************************************************************/	
	private boolean TemConexao() {
		boolean lblnRet = false;
		try {
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			if (cm.getActiveNetworkInfo() != null
					&& cm.getActiveNetworkInfo().isAvailable()
					&& cm.getActiveNetworkInfo().isConnected()) {
				lblnRet = true;
			} else {
				lblnRet = false;
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return lblnRet;
	}

	/**********************************************************************************************/
	/**	 					Método para informar que aplicação será fecha			 		   	 */
	/********************************************************************************************/	
	private void mostraAlerta() {
		AlertDialog.Builder informa = new AlertDialog.Builder(Principal.this);
		informa.setTitle("Alerta!").setMessage("Sem conexão com a internet app será fechado!");
		informa.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface arg0, int arg1) {
	               Principal.this.finish();
	            }
	        });
		informa.show();
	}

	/**********************************************************************************************/
	/**	 						Método para criar Menu da aplicação					 		   	 */
	/********************************************************************************************/	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		menu.add(0, EXPORTAR, 0, "Enviar mudanças");
		return super.onCreateOptionsMenu(menu);
	}

	/**********************************************************************************************/
	/**				 			Método chamdo quando item é selecionado no Menu		 		   	 */
	/********************************************************************************************/
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		/** Envias as mudanças ao WebSite */
		case EXPORTAR:
						
			/*************************************************************************/
			/**  Mostrar um DialogFrafment para ser inserido o endereço so website  */
			/***********************************************************************/

			final Dialog dialog = new Dialog(Principal.this);
			dialog.setTitle("Alerta");

			/** inflando o layout pra criação do DialogFragment*/
			dialog.setContentView(R.layout.fragment_edit_site);

			
			/*************************************************************************/
			/**  				Cria os componentes no DialogFrafment 				*/
			/***********************************************************************/
			text = (TextView) dialog.findViewById(R.id.txt_Fragment);
			edtDialogo = (EditText) dialog.findViewById(R.id.txt_your_name);
			text.setText("Insira o endereço do WebSite");
			ok = (Button) dialog.findViewById(R.id.btnOk);

			
			/*************************************************************************/
			/**  					Mostra o DialogFrafment 						*/
			/***********************************************************************/
			dialog.show();
			
			/*************************************************************************/
			/**  				Metodo para envio posições dos layouts				*/
			/***********************************************************************/
			ok.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					site = edtDialogo.getText().toString();
					String[] insereContainer = { 
							 "Container_1", posicaoNova_1,
							 "Container_2", posicaoNova_2,
							 "Container_3", posicaoNova_3,
							 "Container_4", posicaoNova_4,
							 "Container_5", posicaoNova_5,
							 "Container_6", posicaoNova_6,
							 "Container_7", posicaoNova_7
							 };
				
					EnviaContainer container1 = new EnviaContainer();
					container1.execute(insereContainer);
					dialog.dismiss();
					}
			});
			
			break;
		
		/** Finaliza o aplicativo */
		case R.id.action_settings:
			this.finish();

		default:
			return super.onContextItemSelected(item);
		}
		return super.onContextItemSelected(item);
	}
	
	/***************************************************************************/
	/**					Drag and Drop efeito de arrasar e soltar			  */
	/*************************************************************************/

	public boolean onTouch(View view, MotionEvent me) {
		int action = me.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			ClipData data = ClipData.newPlainText("", "");
			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

			view.startDrag(data, shadowBuilder, view, 0);
			view.setVisibility(View.INVISIBLE);
			return true;
		}
		return false;
	}

	/***************************************************************************/
	/**		Drag and Drop método chamado no evento de arrasar e soltar		  */
	/*************************************************************************/
	@Override
	public boolean onDrag(View v, DragEvent event) {

		switch (event.getAction()) {
		
		/***************************************************************************/
		/**				Ao entrar na área de que pode fazer o Drag and Drop		  */
		/*************************************************************************/
		case DragEvent.ACTION_DRAG_ENTERED:
			
			v.setBackground(enterShape);
			break;

		/***************************************************************************/
		/**				Ao Sair da área de que pode fazer o Drag and Drop		  */
		/*************************************************************************/
		case DragEvent.ACTION_DRAG_EXITED:
			v.setBackground(normalShape);
			break;

		/***************************************************************************/
		/**						Ao fazer o Drop	- soltar o componente			  */
		/*************************************************************************/
		case DragEvent.ACTION_DROP:

			View view = (View) event.getLocalState();

			/***********************************************************************************/
		    /**							  verifica container 1 e 7		   					  */
			/** Se conteiner estiver na posição TOPO  o outro estará na posição BASE		 */
			/********************************************************************************/

			if ((v == layout_1) || (v == layout_7)) {

				if (view.getParent() == layout_1) {
					layout_1.removeAllViews();
					layout_7.removeAllViews();
					layout_7.addView(container_1);
					layout_1.addView(container_7);
					
					posicaoNova_1 = "base";
				    posicaoNova_7 = "topo";

				} else if (view.getParent() != layout_1) {
					layout_7.removeAllViews();
					layout_1.removeAllViews();
					layout_1.addView(container_1);
					layout_7.addView(container_7);
					
					posicaoNova_1 = "topo";
					posicaoNova_7 = "base";
					
				} else {
					layout_1.removeAllViews();
					layout_7.removeAllViews();
					layout_1.addView(container_1);
					layout_7.addView(container_7);
					
					
					posicaoNova_1 = "topo";
					posicaoNova_7 = "base";
				}
			}

				
			/***********************************************************************************/
		    /**							   verifica container 2 e 3		   					  */
			/** Se conteiner estiver na posição esquerda  o outro estará na posição direita	 */
			/********************************************************************************/
			
			if ((v == layout_2) || (v == layout_3)) {

				if (view.getParent() == layout_2) {
					layout_2.removeAllViews();
					layout_3.removeAllViews();
					layout_3.addView(container_2);
					layout_2.addView(container_3);
					
					
					posicaoNova_2 = "direita";
					posicaoNova_3 = "esquerda";

				} else if (view.getParent() != layout_2) {
					layout_3.removeAllViews();
					layout_2.removeAllViews();
					layout_2.addView(container_2);
					layout_3.addView(container_3);
					
					posicaoNova_2 = "esquerda";
					posicaoNova_3 = "direita";
				} else {
					layout_2.removeAllViews();
					layout_3.removeAllViews();
					layout_2.addView(container_2);
					layout_3.addView(container_3);
					
					posicaoNova_2 = "direita";
					posicaoNova_3 = "esquerda";
				}
			}		
		
	
			/***********************************************************************************/
		    /**						 verifica container 4 ,5 e  6		   					  */
			/**    Se conteiner estiver na posição MEIOTOPO  o outro estará na posição MEIO  */
			/**             		  Eo outro na posição MEIOBASE					        */
			/*******************************************************************************/

			if ((v == layout_4) || (v == layout_6) || (v == layout_5)) {

				if (view.getParent() == layout_4) {
					layout_4.removeAllViews();
					layout_5.removeAllViews();
					layout_6.removeAllViews();
					layout_6.addView(container_4);
					layout_5.addView(container_5);
					layout_4.addView(container_6);
					
					
					posicaoNova_4 = "meioBase";
					posicaoNova_5 = "meio";
					posicaoNova_6 = "meioTopo";

				} else if (view.getParent() == layout_6) {
					layout_4.removeAllViews();
					layout_5.removeAllViews();
					layout_6.removeAllViews();
					layout_6.addView(container_6);
					layout_5.addView(container_4);
					layout_4.addView(container_5);
					
					
					posicaoNova_4 = "meio";
					posicaoNova_5 = "meioTopo";
					posicaoNova_6 = "meioBase";

				} else if (view.getParent() == layout_5) {
					layout_4.removeAllViews();
					layout_5.removeAllViews();
					layout_6.removeAllViews();
					layout_6.addView(container_6);
					layout_5.addView(container_5);
					layout_4.addView(container_4);
					
				
					posicaoNova_4 = "meioTopo";
					posicaoNova_5 = "meio";
					posicaoNova_6 = "meioBase";
				}
			}

			else {
				view.setVisibility(View.VISIBLE);

			}

			break;
			
			/***************************************************************************/
			/**		Ao terminar de fazer o Drag and Drop	- soltar o componente	  */
			/*************************************************************************/
		case DragEvent.ACTION_DRAG_ENDED:
			v.setBackground(normalShape);

		default:
			View defaut = (View) event.getLocalState();
			defaut.setVisibility(View.VISIBLE);
			break;
		}

		return true;
	}

	 
	/*****************************************************************************/
	/**				 Classe para enviar as mudanças de Layout					*/
	/***************************************************************************/  	
	public class EnviaContainer extends AsyncTask<String, Void, String> {

		private final int TIMEOUT_MILLISEC = 10000;
	
		@Override
		protected String doInBackground(String... parametros) {
			try {
	 
				/*****************************************************************************/
				/**		Insere os dados no arquivo Json para enviar ao servidor				*/
				/***************************************************************************/ 
				JSONObject json = new JSONObject();
				json.put("nomeContainer_1", parametros[0]);
				json.put("posicaoNova_1", parametros[1]);
				
				json.put("nomeContainer_2", parametros[2]);
				json.put("posicaoNova_2", parametros[3]);
				
				json.put("nomeContainer_3", parametros[4]);
		        json.put("posicaoNova_3", parametros[5]);
				
				json.put("nomeContainer_4", parametros[6]);
				json.put("posicaoNova_4", parametros[7]);
				
				json.put("nomeContainer_5", parametros[8]);
				json.put("posicaoNova_5", parametros[9]);
				
				json.put("nomeContainer_6", parametros[10]);
				json.put("posicaoNova_6", parametros[11]);
				
				json.put("nomeContainer_7", parametros[12]);
				json.put("posicaoNova_7", parametros[13]);
				
				/** Passa os parametros de conexão */
				HttpParams httpParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParams,
						TIMEOUT_MILLISEC);
				HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
				HttpClient client = new DefaultHttpClient(httpParams);

				
				/*****************************************************************************/
				/**		Url do servidor que saré enviado o arquivo Json com os dados		*/
				/***************************************************************************/
				String url = "http://"+ site +"/_php/insereContainer.php";

				HttpPost request = new HttpPost(url);
				request.setEntity(new ByteArrayEntity(json.toString().getBytes(
						"UTF8")));
				request.setHeader("json", json.toString());
				HttpResponse response = client.execute(request);
				HttpEntity entity = response.getEntity();

				
				/***************************************************************************/
				/** 	  Mostra o retorno do arquivo JSON que foi enviado ao servidor	  */
				/*************************************************************************/
				if (entity != null) {
					InputStream instream = entity.getContent();

					final String result = convertStreamToString(instream);
					Log.i("Read from server", result);

					Handler mHandler = new Handler(Looper.getMainLooper());
					mHandler.post(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(getApplicationContext(), result,
									Toast.LENGTH_LONG).show();
						}
					});

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		/***************************************************************************/
		/** 	    Método executado quando a classe de envio é chamada			  */
		/*************************************************************************/
		@Override
		protected void onPostExecute(String result) {
			/*** Colacar algo aqui caso queira passar uma messagem ao usuário */
		}

		
		/*******************************************************************************************************/
		/**     Método executado para fazer a conversão do objeto JSON que veio do servidor pra uma String	  */
		/*****************************************************************************************************/
		public String convertStreamToString(InputStream is) {

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();

			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return sb.toString();
		}
	}
	
}
