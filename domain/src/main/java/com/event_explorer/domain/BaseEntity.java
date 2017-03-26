package com.event_explorer.domain;

public class BaseEntity {
/*
	//TOD:Hack for compatibility with arangoDB _id. This must! be mapped elsewhere, not in domain
	public BaseEntity()
	{
		this.id = this._id;
	}*/
	public String id;
}
