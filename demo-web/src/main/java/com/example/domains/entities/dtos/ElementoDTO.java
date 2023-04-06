package com.example.domains.entities.dtos;

import lombok.Value;

@Value
public class ElementoDTO<K, V> {
	K key;
	V value;
}
