package com.controller.form;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CountActivityCompleteByPromoterForm {
	
	private String teamName;
	private String promoterName;
	private Integer countComplete;
	private Integer countMissing;
	private Integer countTotal;
	private Integer percent;
	
	public CountActivityCompleteByPromoterForm(CountActivityCompleteByPromoterForm countActivityCompleteByPromoterDTO) {
		this.teamName = countActivityCompleteByPromoterDTO.getTeamName();
		this.promoterName = countActivityCompleteByPromoterDTO.getPromoterName();
		this.countComplete = countActivityCompleteByPromoterDTO.getCountComplete();
		this.countMissing = countActivityCompleteByPromoterDTO.getCountMissing();
		this.countTotal = countActivityCompleteByPromoterDTO.getCountTotal();
		this.percent = countActivityCompleteByPromoterDTO.getPercent();
	}
	public CountActivityCompleteByPromoterForm() {
		// TODO Auto-generated constructor stub
	}

	
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getPromoterName() {
		return promoterName;
	}
	public void setPromoterName(String promoterName) {
		this.promoterName = promoterName;
	}
	public Integer getCountComplete() {
		return countComplete;
	}
	public void setCountComplete(Integer countComplete) {
		this.countComplete = countComplete;
	}
	public Integer getCountMissing() {
		return countMissing;
	}
	public void setCountMissing(Integer countMissing) {
		this.countMissing = countMissing;
	}
	public Integer getCountTotal() {
		return countTotal;
	}
	public void setCountTotal(Integer countTotal) {
		this.countTotal = countTotal;
	}
	
	public static List<CountActivityCompleteByPromoterForm> orderByPercent(List<CountActivityCompleteByPromoterForm> datas){
		List<CountActivityCompleteByPromoterForm> newDatas = new ArrayList<>();
		int cont = datas.size();
		for(int i=0; i<cont;i++) {
			CountActivityCompleteByPromoterForm element = (datas.stream().max(Comparator.comparing(CountActivityCompleteByPromoterForm::getPercent)).get());
		    newDatas.add(new CountActivityCompleteByPromoterForm(element));
		    datas.remove(element);
		}
		return newDatas;
	}
	public Integer getPercent() {
		return percent;
	}
	public void setPercent(Integer percent) {
		this.percent = percent;
	}
}
