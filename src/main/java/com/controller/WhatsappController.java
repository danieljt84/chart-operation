package com.controller;

import java.io.File;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.service.WhatsappService;
import com.util.FileReader;

@Controller
public class WhatsappController {

	@Autowired
	WhatsappService whatsappService;
	@Autowired
	FileReader fileReader;

	public void run() {

		File[] pngs = fileReader.getPNG();
		whatsappService.openWhatsapp();
		whatsappService.findContact("Luciene");
		for (int i = 0; i < pngs.length; i++) {
			try {
				File png = pngs[i];
				String teamName = png.getName().split(".png")[0];
				whatsappService.sendImage(png.getAbsolutePath());
				whatsappService.sendTeam(teamName);
			} catch (Exception e) {
			}
		}

		fileReader.deleteFile(pngs);
		fileReader.deleteFile(fileReader.getChart());
	}

}
