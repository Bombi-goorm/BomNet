package com.bombi.core.fasttest.pricechart;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChartNodeInfo {
	private String id;
	private String name;

	public ChartNodeInfo(String id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
			return false;
		ChartNodeInfo that = (ChartNodeInfo)o;
		return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getName());
	}
}
