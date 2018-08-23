package com.gmail.ivanjermakov1.telegramspeechrecognitionbot.util;


import com.gmail.ivanjermakov1.telegramspeechrecognitionbot.exception.DownloadException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;

public class WebDownloader {
	
	/**
	 * Download file by corresponding download link
	 *
	 * @param url      link to file that needed to be downloaded
	 * @param filePath path where file should be saved
	 * @throws DownloadException if accrues error while downloading
	 */
	public static void download(String url, String filePath) throws DownloadException {
		try {
			File file = new File(filePath);
			FileUtils.copyURLToFile(new URL(url), file);
		} catch (Exception e) {
			throw new DownloadException();
		}
	}
	
}
