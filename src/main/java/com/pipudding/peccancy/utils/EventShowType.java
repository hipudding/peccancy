package com.pipudding.peccancy.utils;

import java.util.List;

public class EventShowType {

		String text;
		
		String result;
		
		List<String> images;
		
		List<FlowHistoryType> flows;

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}

		public List<String> getImages() {
			return images;
		}

		public void setImages(List<String> images) {
			this.images = images;
		}

		public List<FlowHistoryType> getFlows() {
			return flows;
		}

		public void setFlows(List<FlowHistoryType> flows) {
			this.flows = flows;
		}
		
		
}
