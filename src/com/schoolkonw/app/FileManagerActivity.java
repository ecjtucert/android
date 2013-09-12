package com.schoolkonw.app;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.index.schoolkonw.R;
import com.schoolkonw.helper.IconifiedText;
import com.schoolkonw.helper.IconifiedTextListAdapter;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class FileManagerActivity extends ListActivity {
	private List<IconifiedText> directoryEntries = new ArrayList<IconifiedText>();
	private File currentDirectory = new File("/");
	//private File myTmpFile = null;
	//private int myTmpOpt = -1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		browseToRoot();
		this.setSelection(0);
	}

	// ����ļ�ϵͳ�ĸ�Ŀ¼
	private void browseToRoot() {
		browseTo(new File("/"));
	}
	// ������һ��Ŀ¼
	private void upOneLevel() {
		if (this.currentDirectory.getParent() != null)
			this.browseTo(this.currentDirectory.getParentFile());
	}

	// ���ָ����Ŀ¼,������ļ�����д򿪲���
	private void browseTo(final File file) {
		this.setTitle(file.getAbsolutePath());
		if (file.isDirectory()) {
			this.currentDirectory = file;
			fill(file.listFiles());
		} else {
			fileOptMenu(file);
		}
	}

	// ����������Ϊ����ListActivity��Դ
	private void fill(File[] files) {
		if(files == null)
		{
			Toast.makeText(FileManagerActivity.this, getString(R.string.strPermissionDeny), Toast.LENGTH_SHORT).show();
			this.browseTo(this.currentDirectory.getParentFile());
			return ;
		}
		// ����б�
		this.directoryEntries.clear();

		// ���һ����ǰĿ¼��ѡ��
		this.directoryEntries.add(new IconifiedText(
				getString(R.string.current_dir), getResources().getDrawable(
						R.drawable.folder)));
		// ������Ǹ�Ŀ¼�������һ��Ŀ¼��
		if (this.currentDirectory.getParent() != null)
			this.directoryEntries.add(new IconifiedText(
					getString(R.string.up_one_level), getResources()
							.getDrawable(R.drawable.uponelevel)));

		Drawable currentIcon = null;
		for (File currentFile : files) {
			// �ж���һ���ļ��л���һ���ļ�
			if (currentFile.isDirectory()) {
				currentIcon = getResources().getDrawable(R.drawable.folder);
			} else {
				// ȡ���ļ���
				String fileName = currentFile.getName();
				// �����ļ������ж��ļ����ͣ����ò�ͬ��ͼ��
				if (checkEndsWithInStringArray(fileName, getResources()
						.getStringArray(R.array.fileEndingImage))) {
					currentIcon = getResources().getDrawable(R.drawable.image);
				} else if (checkEndsWithInStringArray(fileName, getResources()
						.getStringArray(R.array.fileEndingWebText))) {
					currentIcon = getResources()
							.getDrawable(R.drawable.webtext);
				} else if (checkEndsWithInStringArray(fileName, getResources()
						.getStringArray(R.array.fileEndingPackage))) {
					currentIcon = getResources().getDrawable(R.drawable.packed);
				} else if (checkEndsWithInStringArray(fileName, getResources()
						.getStringArray(R.array.fileEndingAudio))) {
					currentIcon = getResources().getDrawable(R.drawable.audio);
				} else if (checkEndsWithInStringArray(fileName, getResources()
						.getStringArray(R.array.fileEndingVideo))) {
					currentIcon = getResources().getDrawable(R.drawable.video);
				} else {
					currentIcon = getResources().getDrawable(R.drawable.text);
				}
			}
			// ȷ��ֻ��ʾ�ļ���������ʾ·���磺/sdcard/111.txt��ֻ����ʾ111.txt
			int currentPathStringLenght = this.currentDirectory
					.getAbsolutePath().length();
			this.directoryEntries.add(new IconifiedText(currentFile
					.getAbsolutePath().substring(currentPathStringLenght),
					currentIcon));
		}
		Collections.sort(this.directoryEntries);
		IconifiedTextListAdapter itla = new IconifiedTextListAdapter(this);
		// �������õ�ListAdapter��
		itla.setListItems(this.directoryEntries);
		// ΪListActivity���һ��ListAdapter
		this.setListAdapter(itla);
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// ȡ��ѡ�е�һ����ļ���
		String selectedFileString = this.directoryEntries.get(position)
				.getText();

		if (selectedFileString.equals(getString(R.string.current_dir))) {
			// ���ѡ�е���ˢ��
			this.browseTo(this.currentDirectory);
		} else if (selectedFileString.equals(getString(R.string.up_one_level))) {
			// ������һ��Ŀ¼
			this.upOneLevel();
		} else {

			File clickedFile = null;
			clickedFile = new File(this.currentDirectory.getAbsolutePath()
					+ this.directoryEntries.get(position).getText());
			if (clickedFile != null)
				this.browseTo(clickedFile);
		}
	}

	// ͨ���ļ����ж���ʲô���͵��ļ�
	private boolean checkEndsWithInStringArray(String checkItsEnd,
			String[] fileEndings) {
		for (String aEnd : fileEndings) {
			if (checkItsEnd.endsWith(aEnd))
				return true;
		}
		return false;
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, 3, 0, "��Ŀ¼").setIcon(R.drawable.goroot);
		menu.add(0, 4, 0, "��һ��").setIcon(R.drawable.uponelevel);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case 3:
			this.browseToRoot();
			break;
		case 4:
			this.upOneLevel();
			break;
		}
		return false;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	// �����ļ��������򿪣��������Ȳ���
	public void fileOptMenu(final File file) {

		new AlertDialog.Builder(FileManagerActivity.this)
				.setTitle("ѡ���ļ�")
				.setMessage("ȷ��ѡ����ļ�?")
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent();
						intent.setAction(resource_upload.FILE_PATH_ACTION);
						intent.putExtra("filepath", file.getAbsolutePath());
						sendBroadcast(intent);
						FileManagerActivity.this.finish();
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).show();
	}

	// �õ���ǰĿ¼�ľ���·��
	public String GetCurDirectory() {
		return this.currentDirectory.getAbsolutePath();
	}

	// �ƶ��ļ�
	public void moveFile(String source, String destination) {
		new File(source).renameTo(new File(destination));
	}

}
