package com.midgard;

public class MailProxyAddress {
	String address;
	String name;
	String proxyName;
	
	public MailProxyAddress(String name, String proxyName, String address) {
		this.name = name;
		this.proxyName = proxyName;
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProxyName() {
		return proxyName;
	}

	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}
	
	
}
