package com.interview.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

public class Rename {

	public File[] listFile(String path) {
		File file = new File(path);
		File[] files = file.listFiles();
		return files;
	}

	private void copyFile(File source, File dest) throws IOException {
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(source).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
	}

	/**
	 * 验证传入的字符串是否整个匹配正表达式
	 * 
	 * @param regex
	 *            : 正则表达式
	 * @param decStr
	 *            ：要匹配的字符串
	 * @return ：若匹配，则返回true;否则，返回false;
	 */
	public boolean validate(String regex, String decStr) {
		// 表达式对象
		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		// 创建 Matcher 对象
		Matcher m = p.matcher(decStr);
		// 是否完全匹配
		boolean yesorno = m.matches(); // 该方法尝试将整个输入序列与该模式匹配
		return yesorno;
	}

	/**
	 * 验证传入的字符串是否有子字符串匹配正表达式
	 * 
	 * @param regex
	 *            : 正则表达式
	 * @param decStr
	 *            ：要匹配的字符串
	 * @return ：若匹配，则返回true;否则，返回false;
	 */
	public boolean validate2(String regex, String decStr) {
		// 表达式对象
		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		// 创建 Matcher 对象
		Matcher m = p.matcher(decStr);
		// 是否完全匹配
		boolean yesorno = m.find(); // 该方法扫描输入序列以查找与该模式匹配的下一个子序列。
		return yesorno;
	}

	/**
	 * 替换给定字符串中匹配正则表达式的子字符串
	 * 
	 * @param regex
	 *            ：正则表达式
	 * @param decStr
	 *            ：所要匹配的字符串
	 * @param replaceStr
	 *            ：将符合正则表达式的子串替换为该字符串
	 * @return：返回替换以后新的字符串
	 */
	public String replace(String regex, String decStr) {
		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(decStr);
		// 替换
		String newstring = m.replaceFirst("").trim();
		return newstring;
	}

	public void renameFile(String path) {

	}

	@Test
	public void listFile() throws IOException {
		// 读取网上邻居的文件 //user:pass@ip/
//		String sourcePath = "//192.168.1.1/sda6/练字书法教程/河小象";
//		String sourcePath = "E:\\练字书法教程\\河小象初级（有序）上168节";
		String sourcePath = "E:\\练字书法教程\\河小象";
		String targetDir = "E:/河小象";
		File[] files = listFile(sourcePath);
		int i = 0 ;
		for (File source : files) {
			String fileName = source.getName();
			String newFileName = replace("[0-9]{3}", fileName) ;
			String targetPath = targetDir + "/" + newFileName;
			File dest = new File( targetPath);
			if(dest.exists()){
				Assert.fail("   target : " + newFileName +  " Exists!");
			} else {
				copyFile(source, dest);
				System.out.println(++i + " source : " + fileName + "   target : " + newFileName);
			}
			
		}
	}

}
