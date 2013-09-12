package com.schoolkonw.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class UploadFileUtil extends AsyncTask<Object, Integer, Void> {
	
	Context context;
	@SuppressLint("SdCardPath")
	String filePath=null;
	
	String uploadUrl="http://192.168.1.100:8082/schoolkonw/uploadFile.php";
	private ProgressDialog dialog = null;
	HttpURLConnection connection = null;
	DataOutputStream outputStream = null;
	DataInputStream inputStream = null;	
	String end = "\r\n";
	String twoHyphens = "--";
	String boundary = "******";
	
	public UploadFileUtil(Context context,String filePath){
		this.context=context;
		this.filePath=filePath;
	}
	
	protected void onPreExecute() {
		dialog = new ProgressDialog(context);
		dialog.setMessage("正在上传...");
		dialog.setIndeterminate(false);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setProgress(0);
		dialog.show();
	}

	protected Void doInBackground(Object... arg0) {
		File uploadFile = new File(filePath);
		long totalSize = uploadFile.length();
		long length = 0;
		int progress;
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 256 * 1024;// 256KB

		try {
			FileInputStream fileInputStream = new FileInputStream(new File(
					filePath));

			URL url = new URL(uploadUrl);
			connection = (HttpURLConnection) url.openConnection();

			// Set size of every block for post
			connection.setChunkedStreamingMode(256 * 1024);// 256KB

			// Allow Inputs & Outputs
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);

			// Enable POST method
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Charset", "UTF-8");
			connection.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);

			outputStream = new DataOutputStream(
					connection.getOutputStream());
			outputStream.writeBytes(twoHyphens + boundary + end);
			outputStream
					.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
							+ filePath + "\"" + end);
			outputStream.writeBytes(end);

			bytesAvailable = fileInputStream.available();
			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];

			// Read file
			bytesRead = fileInputStream.read(buffer, 0, bufferSize);

			while (bytesRead > 0) {
				outputStream.write(buffer, 0, bufferSize);
				length += bufferSize;
				progress = (int) ((length * 100) / totalSize);
				publishProgress(progress);

				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			}
			outputStream.writeBytes(end);
			outputStream.writeBytes(twoHyphens + boundary + twoHyphens
					+ end);
			publishProgress(100);

			// Responses from the server (code and message)
			//int serverResponseCode = connection.getResponseCode();
			//String serverResponseMessage = connection.getResponseMessage();

			/* 将Response显示于Dialog */
			// Toast toast = Toast.makeText(UploadtestActivity.this, ""
			// + serverResponseMessage.toString().trim(),
			// Toast.LENGTH_LONG);
			// showDialog(serverResponseMessage.toString().trim());
			/* 取得Response内容 */
			// InputStream is = connection.getInputStream();
			// int ch;
			// StringBuffer sbf = new StringBuffer();
			// while ((ch = is.read()) != -1) {
			// sbf.append((char) ch);
			// }
			//
			// showDialog(sbf.toString().trim());

			fileInputStream.close();
			outputStream.flush();
			outputStream.close();

		} catch (Exception ex) {
			// Exception handling
			// showDialog("" + ex);
			// Toast toast = Toast.makeText(UploadtestActivity.this, "" +
			// ex,
			// Toast.LENGTH_LONG);

		}
		return null;
	}
	
	protected void onProgressUpdate(Integer... progress) {
		dialog.setProgress(progress[0]);
	}

	@Override
	protected void onPostExecute(Void result) {
		try {
			dialog.dismiss();
		} catch (Exception e) {
		}
	}
}
