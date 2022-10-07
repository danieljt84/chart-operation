package com.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;


//Classe respons�vel por ler os arquivos
@Component
public class FileReader {
	private File[] matches;
	private List<File> files;
	private File dir;
	static Path root;
	static public Path filePath;	         
	public FileReader() {
	  root = FileSystems.getDefault().getPath("").toAbsolutePath();
	  filePath = Paths.get(root.toString(),"src", "main", "resources");	         
	}

	
	public  File[] getChart() {
		File dir = new File("C:\\Users\\4P\\eclipse-workspace\\chart-operation\\src\\main\\resources\\chart");

		File[] matches = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.contains(".png");
			}
		});
		return matches;
	}
	
	public  File[] getPNG() {
		File dir = new File("C:\\Users\\4P\\eclipse-workspace\\chart-operation\\src\\main\\resources\\png");

		File[] matches = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.contains(".png");
			}
		});
		return matches;
	}
	public  String getChartByName(String nameTeam) {
		File dir = new File("C:\\Users\\4P\\eclipse-workspace\\chart-operation\\src\\main\\resources\\chart");

		File[] matches = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.contains(".png") && name.contains(nameTeam);
			}
		});
		return matches[0].toString();
	}
	public  File getPngByName(String nameTeam) {
		File dir = new File(filePath.toAbsolutePath()+"\\png");

		File[] matches = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.contains(".png") && name.contains(nameTeam);
			}
		});
		return matches[0];
	}

	public void deleteFile(File[] files) {
		Arrays.asList(files).forEach(element -> element.delete());
	}
}
