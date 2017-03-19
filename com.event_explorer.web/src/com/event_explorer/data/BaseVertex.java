package com.event_explorer.data;


import com.arangodb.entity.DocumentField;
import com.arangodb.entity.DocumentField.Type;
import com.event_explorer.domain.BaseEntity;

public class BaseVertex<T extends BaseEntity> {

	@DocumentField(Type.ID)
	private String id;

	@DocumentField(Type.KEY)
	private String key;

	@DocumentField(Type.REV)
	private String revision;

	public BaseVertex(T entity) {
		setId(entity.id);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		this.key = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

}
