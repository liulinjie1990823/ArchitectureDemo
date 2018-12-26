package com.llj.architecturedemo.utils;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtil {

	public static String saveFileInLocal(byte[] response, String targetName,
                                         String targetDir) {
		String path = targetDir + "/" + targetName;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File sdCardDir = new File(targetDir);
			if (!sdCardDir.exists()) {
				sdCardDir.mkdirs();
			}
			if (!sdCardDir.isDirectory()) {
				sdCardDir.delete();
				sdCardDir.mkdirs();
			}
			File saveFile = new File(sdCardDir, targetName);
			FileOutputStream outStream;
			try {
				outStream = new FileOutputStream(saveFile);
				outStream.write(response);
				outStream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return path;
	}

	public static boolean delete(String path) {
		if (TextUtils.isEmpty(path)) {
			return false;
		}

		File file = new File(path);

		try {
			int count = 3;
			while (count > 0) {
				boolean ret = file.delete();
				if (ret) {
					return true;
				}
				count--;
			}
		} catch (Exception e) {

		}
		return false;
	}

	/**
	 * 删除指定文件夹下所有文件, 路径结尾包不包含最后的路径分隔符都支持
	 * @param folderPath 文件夹完整绝对路径, 包不包含最后的路径分隔符都可以
	 * @param isDeleteFolderSelf 是否删除这个文件夹本身
	 * @return
	 */
	public static boolean deleteFolder(String folderPath, boolean isDeleteFolderSelf) {
		boolean flag = false;
		try {
			File file = new File(folderPath);
			if (!file.exists()) {
				return flag;
			}
			if (!file.isDirectory()) {
				return flag;
			}
			String[] tempList = file.list();
			if (tempList == null) {
				return flag;
			}
			File temp = null;
			for (int i = 0; i < tempList.length; i++) {
				if (folderPath.endsWith(File.separator)) {
					temp = new File(folderPath + tempList[i]);
				} else {
					temp = new File(folderPath + File.separator + tempList[i]);
				}
				if (temp.isFile()) {
					temp.delete();
				}
				if (temp.isDirectory()) {
					deleteFolder(folderPath + "/" + tempList[i], true);
				}
			}

			if (isDeleteFolderSelf) {
				file.delete();
			}
			flag = true;

		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public static void copyFile(String sourcePath, String destPath) throws IOException {

		File sourceFile = new File(sourcePath);
		File destFile = new File(destPath);

		if (!destFile.getParentFile().exists())
			destFile.getParentFile().mkdirs();

		if (!destFile.exists()) {
			destFile.createNewFile();
		}

		FileChannel source = null;
		FileChannel destination = null;

		try {
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			destination.transferFrom(source, 0, source.size());
		} finally {
			if (source != null) {
				source.close();
			}
			if (destination != null) {
				destination.close();
			}
		}
	}
}
