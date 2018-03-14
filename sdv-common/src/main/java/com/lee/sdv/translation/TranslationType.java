package com.lee.sdv.translation;

public interface TranslationType<K, V> {

	public String getType();

	public V translation(K k);

	public String TRANSLATION_KEY = "sdv";
}
