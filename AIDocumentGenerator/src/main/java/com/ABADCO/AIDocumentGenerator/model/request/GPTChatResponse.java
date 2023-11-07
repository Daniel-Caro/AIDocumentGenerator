package com.ABADCO.AIDocumentGenerator.model.request;

import java.time.LocalDate;
import java.util.List;

import com.ABADCO.AIDocumentGenerator.model.pojo.Message;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GPTChatResponse {

	private String id;
	private String object;
	private LocalDate created;
	private String model;
	private List<Choice> choices;
	
	

	public GPTChatResponse() {
		super();
	}

	public GPTChatResponse(String id, String object, LocalDate created, String model, List<Choice> choices) {
		super();
		this.id = id;
		this.object = object;
		this.created = created;
		this.model = model;
		this.choices = choices;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public LocalDate getCreated() {
		return created;
	}

	public void setCreated(LocalDate created) {
		this.created = created;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Choice> getChoices() {
		return choices;
	}

	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}

	public static class Choice {

        private Integer index;
        private Message message;
        @JsonProperty("finish_reason")
        private String finishReason;
        
        
        
		public Choice() {
			super();
		}
		public Choice(Integer index, Message message, String finishReason) {
			super();
			this.index = index;
			this.message = message;
			this.finishReason = finishReason;
		}
		public Integer getIndex() {
			return index;
		}
		public void setIndex(Integer index) {
			this.index = index;
		}
		public Message getMessage() {
			return message;
		}
		public void setMessage(Message message) {
			this.message = message;
		}
		public String getFinishReason() {
			return finishReason;
		}
		public void setFinishReason(String finishReason) {
			this.finishReason = finishReason;
		}
  
        
        

    }
	
}
